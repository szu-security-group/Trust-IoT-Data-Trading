package com.nonRepudiation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;

public class OffchainComputationCost {

    private String file; // file to be outsourced
    private BigInteger data_s2; // the file data of S2

    private int messageBitLength; // the bit length of a data block; it determines the underlying finite field of the message.
    private long fileSize; // the size of the file in bytes

    private int blocks; // total number of data blocks, which is equal to

    private int randomBlock = 5;

    private static final Logger log = LoggerFactory.getLogger(OffchainComputationCost.class);

    public BigInteger[] gasUsed = new BigInteger[7];

    private String gasFile = "gas.xls";

    public static void main(String[] args) throws Exception {

        int blocksize = 8;

        long hashtime = 0;
        long clienttime = 0;

        int baseSize = 1024 * 1024;
        int loop = 10;

        // 1. 计算开销, 256bit的密钥S2,对S进行加密，S的大小从10MB-100MB
        for (int i=0; i<10; i++) {
            OffchainComputationCost offchainComputationCost = new OffchainComputationCost();

            // 生成密钥
            byte[] S2 = new byte[0];

            try {
                //生成随机密钥
                S2 = AES256Util.initkey();
            } catch (Exception e) {
                e.printStackTrace();
            }

            long divideTime = 0;
            long offchainTime = 0;

            for (int j = 1; j <= loop; j++) {
                long divideStartTime = System.currentTimeMillis();
                // 计算加密成S1的开销
                byte[] S1 = offchainComputationCost.divide("file" + (i + 1), S2);
                long divideEndTime = System.currentTimeMillis();
                divideTime += (divideEndTime - divideStartTime);

                // 计算Hash(S1)、Hash(S2)、Tag(S)的总开销
                divideStartTime = System.currentTimeMillis();
                String hash_s1 = SHA256Util.getSHA256StrJava(S1.toString());
                String hash_s2 = SHA256Util.getSHA256StrJava(S2.toString());
                String result = offchainComputationCost.getVerifyResultOfFile(hash_s1, hash_s2);
                divideEndTime = System.currentTimeMillis();
                offchainTime += (divideEndTime - divideStartTime);


            }
            System.out.println("time1:" + divideTime / loop);
            System.out.println("time2:" + offchainTime);
        }
/*


        // 计算链下计算开销
        for (int i=0; i<10; i++) {
            file.init("file" + (i + 1), blocksize);
            long allTime = 0;
            long allTime1 = 0;
            for (int j = 1; j <= loop; j++) {
                long divideStartTime = System.currentTimeMillis();
                String hash_s1 = file.getHashOfS1();
                long divideEndTime1 = System.currentTimeMillis();
                String s2 = file.getS2() + "";
                String hash_s2 = SHA256Util.getSHA256StrJava(s2);
                String verifyResult = file.getVerifyResultOfFile(hash_s1, hash_s2);
                long divideEndTime = System.currentTimeMillis();
                allTime += (divideEndTime - divideStartTime);
                allTime1 += (divideEndTime1 - divideStartTime);
            }
            System.out.println("time:" + allTime / loop);
            System.out.println("time1:" + allTime1 / loop);
        }
 */
        // 固定文件大小，测试off-chain off 随着
//        for (int i=0; i<10; i++) {
//
//            System.out.println(blocksize);
//
//            blocksize = blocksize * 2;
//
//            long allTime = 0;
//            for (int j = 1; j <= loop; j++) {
//                //System.out.println(j);
//                long divideStartTime = System.currentTimeMillis();
//                OffchainComputationCost file = new OffchainComputationCost();
//                file.init("file1", blocksize);
//                long divideEndTime = System.currentTimeMillis();
//                allTime += (divideEndTime - divideStartTime);
//            }
//            System.out.println("time:" + allTime / loop);
//        }
    }


    public OffchainComputationCost() {}

    public byte[] divide(String file, byte[] key) {
        byte[] data = new byte[0];
        try {
            StringBuffer buffer = new StringBuffer();
            InputStream is = new FileInputStream(file);
            String line; // 用来保存每行读取的内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            line = reader.readLine(); // 读取第一行
            while (line != null) { // 如果 line 为空说明读完了
                buffer.append(line); // 将读到的内容添加到 buffer 中
                buffer.append("\n"); // 添加换行符
                line = reader.readLine(); // 读取下一行
            }
            reader.close();
            is.close();

            //加密
            data = AES256Util.encrypt(buffer.toString().getBytes(), key);
        } catch (Exception e) {
        }
        return data;
    }
    /*
    public void uinit(String file, int blockSize) {
        // 1. Divide the file into two parts, Large S1 and Small S2
        this.file = file;
        File f = new File(file);

        this.fileSize = f.length();
        this.messageBitLength = blockSize; // each blocks sizes

        this.blocks = (int) Math.ceil(this.fileSize * 8 / (double) messageBitLength);

        byte[] block = new byte[this.messageBitLength / 8];


        try
        {
            FileOutputStream file_s1 = new FileOutputStream("S1");
            FileOutputStream file_s2 = new FileOutputStream("S2");

            FileInputStream source = new FileInputStream(this.file);

            for (int i = 0; i < this.blocks; i++)
            {
                source.read(block);

                // write S2
                if (i == randomBlock) {
                    file_s2.write(block);
                    this.data_s2 = new BigInteger(1, block);
                }
                // write S1
                else {
                    file_s1.write(block);
                }
                // fill zero
                Arrays.fill(block, (byte)0);
            }
            source.close();
            file_s1.close();
            file_s2.close();
        } catch (Exception e)
        {
            System.out.println("Exception in InnerProductBasedVS when reading file into memory: " + e);
        }
    }
    */

    public String getHashOfS1() {
        String res = getFileSHA1("S1");
        int len = res.length();
        if (res.length() < 64) {
            for (int i=0; i<64-len; i++) {
                res = "0" + res;
            }
        }
        return res;
    }

    public BigInteger getS2() {
        return this.data_s2;
    }

    public String getVerifyResultOfFile(String s1, String s2) {
        String res = "";
        for (int i=0; i<s1.length(); i++) {
            int a = getIntByChar(s1.charAt(i));
            int b = getIntByChar(s2.charAt(i));
            String c = getCharByInt(a ^ b);
            res += c;
        }
        return res;
    }

    public int getIntByChar(char c) {
        int res = 0;
        if (c == 'f') {
            res = 15;
        }
        else if (c == 'e') {
            res = 14;
        }
        else if (c == 'd') {
            res = 13;
        }
        else if (c == 'c') {
            res = 12;
        }
        else if (c == 'b') {
            res = 11;
        }
        else if (c == 'a') {
            res = 10;
        }
        else {
            res = Integer.parseInt(String.valueOf(c));
        }
        return res;
    }
    public String getCharByInt(int num) {
        String res = "";
        if (num>=0 && num <=9) {
            res = num+"";
        }
        else if (num == 10) {
            res = "a";
        }
        else if (num == 11) {
            res = "b";
        }
        else if (num == 12) {
            res = "c";
        }
        else if (num == 13) {
            res = "d";
        }
        else if (num == 14) {
            res = "e";
        }
        else if (num == 15) {
            res = "f";
        }
        return res;
    }

    public static byte[] toByteArray(BigInteger bi, int length) {
        byte[] array = bi.toByteArray();
        // 这种情况是转换的array超过25位
        if (array[0] == 0) {
            byte[] tmp = new byte[array.length - 1];
            System.arraycopy(array, 1, tmp, 0, tmp.length);
            array = tmp;
        }
        // 假如转换的byte数组少于24位，则在前面补齐0
        if (array.length < length) {
            byte[] tmp = new byte[length];
            System.arraycopy(array, 0, tmp, length - array.length, array.length);
            array = tmp;
        }
        return array;
    }

    private static String getFileSHA1(String file) {
        String str = "";
        try {
            str = getHash(file, "SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    private static String getHash(String file, String hashType) throws Exception {
        InputStream fis = new FileInputStream(file);
        byte buffer[] = new byte[1024];
        MessageDigest md5 = MessageDigest.getInstance(hashType);
        for (int numRead = 0; (numRead = fis.read(buffer)) > 0; ) {
            md5.update(buffer, 0, numRead);
        }
        fis.close();
        return toHexString(md5.digest());
    }
    private static String toHexString(byte b[]) {
        StringBuilder sb = new StringBuilder();
        for (byte aB : b) {
            sb.append(Integer.toHexString(aB & 0xFF));
        }

        return sb.toString();
    }

    public void writeArrayToExcel(BigInteger[] data, String string) {
        int rowNum = data.length;
        try {
            File file = new File(string);
            FileWriter fw;
            if(!file.exists()){
                fw = new FileWriter(string);//首次写入获取
                fw.write( "blocksize" + "\t");
                fw.write( "requestService" + "\t");
                fw.write( "cloudDoRoun1" + "\t");
                fw.write( "clientConfim" + "\t");
                fw.write( "cloudDoRoun2" + "\t");
                fw.write( "onchain-arbitrate" + "\t");
                fw.write( "lauchOffchainArbitration" + "\t");
                fw.write( "arbitratorDoOffchainArbitration" + "\t");
                fw.write("\n");
            }else{
                //如果文件已存在，那么就在文件末尾追加写入
                fw = new FileWriter(string, true);//这里构造方法多了一个参数true,表示在文件末尾追加写入
            }

            fw.write(this.messageBitLength + "\t");
            for (int i = 0; i < rowNum; i++) {
                fw.write(data[i]+ "\t"); // tab 间
            }
            fw.write("\n"); // 换行
            fw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
