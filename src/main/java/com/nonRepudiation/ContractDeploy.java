package com.nonRepudiation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.NonRepudiation;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.Properties;

public class ContractDeploy {
    private static final Logger log = LoggerFactory.getLogger(ContractDeploy.class);

    public static void main(String[] args) throws Exception {
        new ContractDeploy().run();
    }

    private void run() throws Exception {
        FileInputStream propertiesFIS = new FileInputStream("./properties");
        Properties properties = new Properties();
        properties.load(propertiesFIS);
        propertiesFIS.close();
        String arbitratorAddress = properties.getProperty("arbitratorAddress");
        String cloudAddress = properties.getProperty("dataOwnerAddress");
        String clientAddress = properties.getProperty("dataBuyerAddress");
        BigInteger serviceFee = new BigInteger(properties.getProperty("serviceFee"));
        BigInteger penaltyFee = new BigInteger(properties.getProperty("penaltyFee"));
        BigInteger duringTime = new BigInteger(properties.getProperty("duringTime"));

        // 1. We start by creating a new web3j instance to connect to remote nodes on the network.
        Web3j web3j = Web3j.build(new HttpService(properties.getProperty("httpService")));
        log.info("Connected to Etherum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());

        // 2. We then need to load our Ethereum wallet file
        Credentials arbitratorCredentials = WalletUtils.loadCredentials(properties.getProperty("arbitratorWalletPassword"), "./wallet/"+arbitratorAddress+".json");
        log.info("Credentials loaded");

        ContractGasProvider provider = new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(10000000L)); // 20 Gwei, GasLimit 1000000

        // 3. deploy smart contract
        log.info("Call smart contract");
        NonRepudiation nonRepudiation = NonRepudiation.deploy(web3j, arbitratorCredentials, provider, cloudAddress, clientAddress, penaltyFee, serviceFee, duringTime).send();
        System.out.println(nonRepudiation.getContractAddress()); // 0xd5fc572617b0f1fcd70c2300443a8c3d5eec8557

        web3j.shutdown();
    }
}
