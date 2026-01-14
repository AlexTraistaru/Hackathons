package com.example.hack_sie25.util;

import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
    private static final String KEY = "0123456789abcdef"; // demo key (16 bytes)
    private static final String IV  = "abcdef9876543210"; // demo IV (16 bytes)

    public static String encrypt(String plain){
        try{
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
            c.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] enc = c.doFinal(plain.getBytes());
            return Base64.encodeToString(enc, Base64.NO_WRAP);
        }catch(Exception e){ return plain; }
    }

    public static String decrypt(String enc){
        try{
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
            c.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] dec = c.doFinal(Base64.decode(enc, Base64.NO_WRAP));
            return new String(dec);
        }catch(Exception e){ return enc; }
    }
}
