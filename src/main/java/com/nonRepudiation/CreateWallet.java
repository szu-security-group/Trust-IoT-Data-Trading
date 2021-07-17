package com.nonRepudiation;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/*
* Create wallet of cloud, client and arbitrator
* */
public class CreateWallet {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {
        String fileName = WalletUtils.generateNewWalletFile(
                "your 339339",
                new File("./wallet"));

    }
}
