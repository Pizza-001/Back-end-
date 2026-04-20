package com.pet.hospital.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {

    /**
     * 将字符串进行 SHA-256 加密
     * @param str 原始字符串
     * @return 加密后的十六进制字符串
     */
    public static String encryptSha256(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 Algorithm not found", e);
        }
    }

    /**
     * 校验原密码与加密后的密码是否匹配
     */
    public static boolean matches(String rawPassword, String encryptedPassword) {
        return encryptSha256(rawPassword).equals(encryptedPassword);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
