package com.nonRepudiationOfXu;

import org.web3j.NonRepudiationOfXu;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Numeric;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Test {
    BigInteger p = new BigInteger("104182191522651000468444503342056680324826576333073935755113966233942723624787");
    BigInteger q = new BigInteger("52091095761325500234222251671028340162413288166536967877556983116971361812393");
    BigInteger g = new BigInteger("36991591110632132309619989870840915286066474907184832351904077556737989885092");

    public static void main(String[] args) throws IOException, CipherException, ExecutionException, InterruptedException {
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
//        log.info("Connected to Etherum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());

        // 2. We then need to load our Ethereum wallet file
        Credentials arbitratorCredentials = WalletUtils.loadCredentials(properties.getProperty("arbitratorWalletPassword"), "./wallet/"+arbitratorAddress+".json");
        Credentials cloudCredentials = WalletUtils.loadCredentials(properties.getProperty("cloudWalletPassword"), "./wallet/"+cloudAddress+".json");
        Credentials clientCredentials = WalletUtils.loadCredentials(properties.getProperty("clientWalletPassword"), "./wallet/"+clientAddress+".json");
//        log.info("Credentials loaded");

        ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(124999880L)); // 20 Gwei, GasLimit 1000000

        // deploy small contract

//        log.info("Smart contract: " + contractAddress);

        // call smart contract

        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                clientCredentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();

        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, BigInteger.valueOf(20000000000L), BigInteger.valueOf(10000000L), "0x88097c93eeAe8C470e0514B655e549633068E2E6", new BigInteger("8000000000000000000"));
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, clientCredentials);
        String hexValue = Numeric.toHexString(signedMessage);
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        String transactionHash = ethSendTransaction.getTransactionHash();
        EthGetTransactionReceipt transactionReceipt;
    }

    public Test(){}
    public void test () {
        BigInteger res = g.modPow(q, p);
        System.out.println(res);
        System.out.println(p.mod(q));

    }

}
