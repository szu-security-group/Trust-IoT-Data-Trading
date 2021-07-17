package com.nonRepudiation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.NonRepudiation;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Numeric;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

public class ContractGas {

    private String file; // file to be outsourced
    private BigInteger data_s2; // the file data of S2

    private int messageBitLength; // the bit length of a data block; it determines the underlying finite field of the message.
    private long fileSize; // the size of the file in bytes

    private int blocks; // total number of data blocks, which is equal to

    private int randomBlock = 5;

    private static final Logger log = LoggerFactory.getLogger(ContractGas.class);

    public BigInteger[] gasUsed = new BigInteger[7];

    private String gasFile = "gas.xls";

    public static void main(String[] args) throws Exception {
        ContractGas file = new ContractGas();
        int blocksize;
        for (int i=1; i<=50; i++) {
            blocksize = 8 * i;
            file.init("D:\\learning_data\\Blockchain\\云存储审计\\fisco\\InnerProductBasedVS\\InnerProductBasedVS\\98688B", blocksize);
            String hash_s1 = file.getHashOfS1();
            log.info("hash of s1: " + hash_s1);
            String s2 = file.getS2() + "";
            log.info("s2: " + s2);
            String hash_s2 = SHA256Util.getSHA256StrJava(s2);
            log.info("hash of s2: " + hash_s2);
            String verifyResult = file.getVerifyResultOfFile(hash_s1, hash_s2);
            log.info("verify result of s: " + verifyResult);
            file.run(hash_s1, s2, verifyResult);
        }
    }


    public ContractGas() {}

    public void init(String file, int blockSize) {
        // 1. Divide the file into two parts, Large S1 and Small S2
        this.file = file;
        java.io.File f = new java.io.File(file);

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

    public void run(String hash_s1, String s2, String verifyResult) {
        try {
            FileInputStream propertiesFIS = new FileInputStream("./properties");
            Properties properties = new Properties();
            properties.load(propertiesFIS);
            propertiesFIS.close();
            String arbitratorAddress = properties.getProperty("arbitratorAddress");
            String cloudAddress = properties.getProperty("cloudAddress");
            String clientAddress = properties.getProperty("clientAddress");
            BigInteger serviceFee = new BigInteger(properties.getProperty("serviceFee"));
            BigInteger penaltyFee = new BigInteger(properties.getProperty("penaltyFee"));
            BigInteger duringTime = new BigInteger(properties.getProperty("duringTime"));
            boolean isFirst = true;

            // 1. We start by creating a new web3j instance to connect to remote nodes on the network.
            Web3j web3j = Web3j.build(new HttpService(properties.getProperty("httpService")));
            log.info("Connected to Etherum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());

            // 2. We then need to load our Ethereum wallet file
            Credentials arbitratorCredentials = WalletUtils.loadCredentials(properties.getProperty("arbitratorWalletPassword"), "./wallet/"+arbitratorAddress+".json");
            Credentials cloudCredentials = WalletUtils.loadCredentials(properties.getProperty("cloudWalletPassword"), "./wallet/"+cloudAddress+".json");
            Credentials clientCredentials = WalletUtils.loadCredentials(properties.getProperty("clientWalletPassword"), "./wallet/"+clientAddress+".json");
            log.info("Credentials loaded");

            ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(10000000L)); // 20 Gwei, GasLimit 1000000

            // deploy small contract
            NonRepudiation nonRepudiation = NonRepudiation.deploy(web3j, arbitratorCredentials, provider, cloudAddress, clientAddress, penaltyFee, serviceFee, duringTime).send();
            String contractAddress = nonRepudiation.getContractAddress();
            log.info("Smart contract: " + contractAddress);

            // 3. call smart contract
            log.info("Call smart contract");
            NonRepudiation client = NonRepudiation.load(contractAddress, web3j, clientCredentials, provider);
            NonRepudiation cloud = NonRepudiation.load(contractAddress, web3j, cloudCredentials, provider);
            NonRepudiation arbitrator = NonRepudiation.load(contractAddress, web3j, arbitratorCredentials, provider);

            if (!isFirst) {
                // restart
                arbitrator.restart().send();
            }

            int i = 0;
            // 1. Client request service
            gasUsed[i++] = client.requestService("service1").send().getGasUsed();
            log.info("Client request service");

            // 2. Cloud do round1
            byte[] verify_result = DatatypeConverter.parseHexBinary(verifyResult);
            byte[] hashS1 = DatatypeConverter.parseHexBinary(hash_s1);

            Function function = new Function("cloudDoRound1",
                    Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(verify_result),
                            new org.web3j.abi.datatypes.generated.Bytes32(hashS1)),
                    Collections.<TypeReference<?>>emptyList()
            );
            String encodedFunction = FunctionEncoder.encode(function);

            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                    cloudCredentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();

            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, BigInteger.valueOf(20000000000L), BigInteger.valueOf(10000000L), contractAddress, penaltyFee, encodedFunction);
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, cloudCredentials);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
            String transactionHash = ethSendTransaction.getTransactionHash();
            EthGetTransactionReceipt transactionReceipt;

            while (true) {
                transactionReceipt = web3j.ethGetTransactionReceipt(transactionHash).send();
                if (transactionReceipt.getResult() != null) {
                    break;
                }
                Thread.sleep(15000);
            }
            gasUsed[i++] = transactionReceipt.getResult().getGasUsed();

            log.info("Cloud do Round1");

            // 3. Client confirm
            Function confirmFunction = new Function(
                    "clientConfirm",
                    Arrays.<Type>asList(),
                    Collections.<TypeReference<?>>emptyList()
            );
            String encodedConfirmFunction = FunctionEncoder.encode(confirmFunction);

            EthGetTransactionCount ethGetConfirmTransactionCount = web3j.ethGetTransactionCount(
                    clientCredentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
            BigInteger confirmNonce = ethGetConfirmTransactionCount.getTransactionCount();

            RawTransaction rawConfirmTransaction = RawTransaction.createTransaction(confirmNonce, BigInteger.valueOf(20000000000L), BigInteger.valueOf(10000000L), contractAddress, serviceFee, encodedConfirmFunction);
            byte[] signedConfirmMessage = TransactionEncoder.signMessage(rawConfirmTransaction, clientCredentials);
            String hexConfirmValue = Numeric.toHexString(signedConfirmMessage);
            ethSendTransaction = web3j.ethSendRawTransaction(hexConfirmValue).sendAsync().get();
            transactionHash = ethSendTransaction.getTransactionHash();
            while (true) {
                transactionReceipt = web3j.ethGetTransactionReceipt(transactionHash).send();
                if (transactionReceipt.getResult() != null) {
                    break;
                }
                Thread.sleep(15000);
            }
            gasUsed[i++] = transactionReceipt.getResult().getGasUsed();
            log.info("Client confirm");

            // 4. Cloud do round2
            gasUsed[i++] = cloud.cloudDoRound2(s2, new BigInteger(randomBlock+"")).send().getGasUsed();
            log.info("Cloud do round2");

            // 5. Client arbitrate
            gasUsed[i++] = client.arbitrate().send().getGasUsed();
            log.info("Client arbitrate");

            // 6. Client lauch off-chain arbitrate
            gasUsed[i++] = client.lauchoffChainArbitrate().send().getGasUsed();
            log.info("Client lauch off-chain arbitrate");

            // 7. Arbitrator do off-chain arbitration
            gasUsed[i++] = arbitrator.offChainArbitrate(new BigInteger("0")).send().getGasUsed();
            log.info("Arbitrator do off-chain arbitration");

            writeArrayToExcel(gasUsed, gasFile);
            web3j.shutdown();
//            System.exit(0);
        }
        catch (Exception e) {
            System.out.println(e);
            run(hash_s1, s2, verifyResult);
        }

    }
}
