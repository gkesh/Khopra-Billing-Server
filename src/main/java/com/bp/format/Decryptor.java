/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.format;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 *
 * @author Admin
 */
public class Decryptor {
    private static final String KEY = "1xdzayS2vBNpqg14lXn01af31a491xal";
    public static String decrypt(String data) throws NoSuchAlgorithmException,
            InvalidKeyException, InvalidAlgorithmParameterException,
            NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException {
        Security.setProperty("crypto.policy", "unlimited");
        
        byte[] cyphertextBytes = Base64.getDecoder().decode(data.getBytes());
        IvParameterSpec iv = new IvParameterSpec(cyphertextBytes, 0, 16);
        cyphertextBytes = Arrays.copyOfRange(cyphertextBytes, 16, cyphertextBytes.length);
        SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] plaintext = cipher.doFinal(cyphertextBytes);
        
        int lastLength = plaintext.length;
        for (int i = plaintext.length - 1; i > plaintext.length - 16; i--) {
            if (plaintext[i] == (byte) 0) {
                lastLength--;
            } else {
                break;
            }
        }
        
        return new String(plaintext, 0, lastLength);
    }
}
