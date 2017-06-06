package com.toda.consultant.util.crypt;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/***
 * AES加密解密
 */
public class AESCrypt {
    private static AESCrypt aes;
    private Cipher cipher;
    private SecretKeySpec key;
    private AlgorithmParameterSpec spec;
    public static final String SEED_16_CHARACTER = "b4212@7Dc8d985dA9%f&#0e3c35a209a";

    public static AESCrypt getInstance(){
        if (aes == null) {
            synchronized (AESCrypt.class) {
                if (aes == null) {
                   aes = new AESCrypt();
                }
            }
        }
        return aes;
    }

    private AESCrypt(){
        try {
            // hash password with SHA-256 and crop the output to 128-bit for key
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(SEED_16_CHARACTER.getBytes("UTF-8"));
            byte[] keyBytes = new byte[32];
            System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            key = new SecretKeySpec(keyBytes, "AES");
            spec = getIV();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public AlgorithmParameterSpec getIV() {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);
        return ivParameterSpec;
    }

    public String encrypt(String plainText){
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);
            byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
            String encryptedText = new String(Base64.encode(encrypted,
                    Base64.DEFAULT), "UTF-8");
            return encryptedText;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String decrypt(String cryptedText){
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            byte[] bytes = Base64.decode(cryptedText, Base64.DEFAULT);
            byte[] decrypted = cipher.doFinal(bytes);
            String decryptedText = new String(decrypted, "UTF-8");
            return decryptedText;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
