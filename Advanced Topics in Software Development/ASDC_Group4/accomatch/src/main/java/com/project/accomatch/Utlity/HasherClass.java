package com.project.accomatch.Utlity;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HasherClass {

    /** Secret key used for encryption and decryption*/
    private static final String SECRET_KEY = "ThisIsASecretKey";

    /**
     * Encrypts an email address using AES encryption.
     * @author Yogish Honnadevipura Gopalakrishna
     * @param email The email address to be encrypted.
     * @return The encrypted email address as a Base64-encoded string.
     */
    public static String hashEmail(String email) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(email.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Decrypts an encrypted email address using AES decryption.
     * @author Yogish Honnadevipura Gopalakrishna
     * @param encryptedText The encrypted email address as a Base64-encoded string.
     * @return The decrypted email address as a plain text string.
     */
    public static String unHashEmail(String encryptedText) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
