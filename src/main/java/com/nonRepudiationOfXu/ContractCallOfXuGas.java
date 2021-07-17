package com.nonRepudiationOfXu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.NonRepudiationOfXu;
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
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

public class ContractCallOfXuGas {

    private String file; // file to be outsourced
    private BigInteger[] data; // the file data

    private int messageBitLength; // the bit length of a data block; it determines the underlying finite field of the message.
    private long fileSize; // the size of the file in bytes

    private int blocks; // total number of data blocks, which is equal to

    public BigInteger p;
    public BigInteger g;
    public BigInteger q;
    private int randomBlock = 10;
    private BigInteger[] gasUsed = new BigInteger[5];

    BigInteger[] hashOfBlock;

    private String gasFile = "gasXu.xls";

    private static final Logger log = LoggerFactory.getLogger(ContractCallOfXuGas.class);

    public static void main(String[] args)
    {
        int blocksize;
        ContractCallOfXuGas file = new ContractCallOfXuGas();
        for (int i=1; i<=10; i++) {
            blocksize = 256;
            String p = "277417041989071300852895191627759233443";
            String q = "138708520994535650426447595813879616721";
            String g = "89797699099474495392104989732172808255";
            file.init("file"+i, p, g, q, blocksize);
            long divideStartTime = System.currentTimeMillis();
            BigInteger verifyResult = file.getHashOfFile();
            log.info("verify result of file = " + verifyResult);
            BigInteger hash_s1 = file.getHashOfS1();
            log.info("hash of S1 = " + hash_s1);
            long divideEndTime = System.currentTimeMillis();
            System.out.println(divideEndTime - divideStartTime);
            BigInteger s2 = file.getS2();
            log.info("S2 = " + s2);
            //file.arbitrate(s2, hash_s1);
            //file.run(verifyResult, hash_s1, s2);
        }

//        file.arbitrate(s2, hash_s1);
    }

    public ContractCallOfXuGas() {}

    public void init(String file, String p, String g, String q, int blocksize) {
        this.p = new BigInteger(p);
        this.g = new BigInteger(g);
        this.q = new BigInteger(q);

        this.file = file;
        File f = new File(file);

        this.fileSize = f.length();
        this.messageBitLength = blocksize; // each data block is 1024bits; it will always be a multiple of 8 bits

        this.blocks = (int) Math.ceil(this.fileSize * 8 / (double) messageBitLength);
        this.data = new BigInteger[this.blocks];

        byte[] block = new byte[this.messageBitLength / 8];

        try
        {
            FileInputStream source = new FileInputStream(this.file);
            for (int i = 0; i < data.length; i++)
            {
                source.read(block);

                this.data[i] = new BigInteger(1, block);
                // fill zero
                Arrays.fill(block, (byte)0);
            }
            source.close();
        } catch (Exception e)
        {
            System.out.println("Exception in InnerProductBasedVS when reading file into memory: " + e);
        }
    }

    public BigInteger getHashOfFile() {
        BigInteger hash = null;
        BigInteger data1;

        BigInteger sumOfData = new BigInteger("0");

        for (int i=0; i<data.length; i++) {
            sumOfData = sumOfData.add(this.data[i]);
        }

        if (sumOfData.compareTo(this.q) == -1) {
            hash = this.g.modPow(sumOfData, this.p);
        }
        else {
            hash = this.g.modPow(sumOfData.mod(this.q), this.p);
        }
        return hash;
    }

    public BigInteger getS2() {
        // Choose the first block of the file as S1
        return this.data[this.randomBlock];
    }

    public BigInteger getHashOfS1() {
        hashOfBlock = new BigInteger[data.length-1]; // 每一个块的哈希

        int j = 0;
        for (int i=0; i<data.length-1; i++) {
            if (j == this.randomBlock) {
                j++;
            }
            if (this.data[i+1].compareTo(this.q) == -1) {
                hashOfBlock[i] = this.g.modPow(this.data[j], this.p);
            }
            else {
                hashOfBlock[i] = this.g.modPow(this.data[j].mod(this.q), this.p);
            }
            j++;

        }

        BigInteger tempMultiValue = new BigInteger("1");
        for (int i=0; i<data.length-1; i++) {
            tempMultiValue = tempMultiValue.multiply(hashOfBlock[i]).mod(this.p);
        }

        return tempMultiValue;
    }

    public void arbitrate(BigInteger s1, BigInteger hash_s2) {
        BigInteger hash_s1 = this.g.modPow(s1, this.p);
//        log.info("hash of s1 = " + hash_s1);
        BigInteger hash_s1_multiply_s2 = hash_s1.multiply(hash_s2).mod(this.p);
        log.info("hash_s1 * hash_s2 = " + hash_s1_multiply_s2);
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
    public void run(BigInteger verifyResult, BigInteger hash_s1, BigInteger s2) {
        try {
            FileInputStream propertiesFIS = new FileInputStream("./properties");
            Properties properties = new Properties();
            properties.load(propertiesFIS);
            propertiesFIS.close();
            String arbitratorAddress = properties.getProperty("arbitratorAddress");
            String cloudAddress = properties.getProperty("cloudAddress");
            String clientAddress = properties.getProperty("clientAddress");
            BigInteger duringTime = new BigInteger(properties.getProperty("duringTime"));

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
//            NonRepudiationOfXu nonRepudiation = NonRepudiationOfXu.deploy(web3j, arbitratorCredentials, provider, cloudAddress, clientAddress, duringTime, this.g, this.p, this.q).send();
//            String contractAddress = nonRepudiation.getContractAddress();
//            log.info("Smart contract: " + contractAddress);
//
//            // call smart contract
//            log.info("Call smart contract");
//            NonRepudiationOfXu client = NonRepudiationOfXu.load(contractAddress, web3j, clientCredentials, provider);
//            NonRepudiationOfXu cloud = NonRepudiationOfXu.load(contractAddress, web3j, cloudCredentials, provider);


//            int i = 0;
//            // 1. Client request service
//            gasUsed[i++] = client.requestService("service1").send().getGasUsed();
//            log.info("Client request service");
//
//            // 2. Cloud do round1
//            gasUsed[i++] = cloud.cloudDoRound1(verifyResult, hash_s1).send().getGasUsed();
//            log.info("Cloud do Round1");
//
//            // 3. Client confirm
//            gasUsed[i++] = client.clientConfirm().send().getGasUsed();
//            log.info("Client confirm");
//
//            // 4. Cloud do round2
//            gasUsed[i++] = cloud.cloudDoRound2(s2, new BigInteger(randomBlock+"")).send().getGasUsed();
//            log.info("Cloud do round2");

            String contractAddress = "0x777F30EF11838c7D4ADaF4529a2D556a501dFA63";
            // 5. Client arbitrate
            Function function = new Function("arbitrate",
                    Arrays.<Type>asList(),
                    Collections.<TypeReference<?>>emptyList()
            );
            String encodedFunction = FunctionEncoder.encode(function);

            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                    clientCredentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();

            Transaction transaction = Transaction.createFunctionCallTransaction(clientCredentials.getAddress(), nonce, Contract.GAS_PRICE, Contract.GAS_LIMIT, contractAddress, encodedFunction);
            BigInteger t = web3j.ethEstimateGas(transaction).sendAsync().get().getAmountUsed();


            log.info("Client arbitrate");

//            writeArrayToExcel(gasUsed, gasFile);
            web3j.shutdown();
        }
        catch (Exception e) {
            System.out.println(e);
            run(verifyResult, hash_s1, s2);
        }

    }
}
