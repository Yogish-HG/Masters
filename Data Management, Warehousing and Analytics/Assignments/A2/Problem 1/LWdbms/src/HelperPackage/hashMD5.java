package HelperPackage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class hashMD5 {
    public static String hashPassword(String password) {
        try {
            // Create an instance of the MD5 algorithm
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            // Convert the password string to bytes
            byte[] passwordBytes = password.getBytes();

            // Perform the MD5 hashing
            byte[] hashedBytes = md5.digest(passwordBytes);

            // Convert the hashed bytes to a hexadecimal representation
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            // Return the hashed password as a string
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle any exceptions thrown by the MD5 algorithm
            System.out.println("Error hashing the password: " + e.getMessage());
            return null;
        }
    }

}
