/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.account.client.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * AES加解密工具类
 *
 * @author wxb
 */
public class AesUtils {
    private static final String CHAR_ENCODING = "UTF-8";
    private static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String AES = "AES";

    /**
     * 使用AES算法对字符串进行加密
     *
     * @param data 需要加密的内容
     * @param key  加密密码
     * @return 加密后的字符串
     */
    public static String encrypt(String data, String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(CHAR_ENCODING), AES);
            SecretKeySpec secKey = new SecretKeySpec(secretKey.getEncoded(), AES);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secKey);
            byte[] result = cipher.doFinal(data.getBytes(CHAR_ENCODING));
            byte[] enc64 = Base64.getEncoder().encode(result);
            return new String(enc64, CHAR_ENCODING);
        } catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * 使用AES算法对字符串进行解密
     *
     * @param data 待解密内容
     * @param key  解密密钥
     * @return 解密后的字符串
     */
    public static String decrypt(String data, String key) {
        try {
            byte[] encrypted1 = Base64.getDecoder().decode(data);
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(CHAR_ENCODING), AES);
            SecretKeySpec secKey = new SecretKeySpec(secretKey.getEncoded(), AES);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secKey);
            byte[] result = cipher.doFinal(encrypted1);
            return new String(result, CHAR_ENCODING);
        } catch (Exception e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

}
