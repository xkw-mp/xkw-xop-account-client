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
    private static final int MAX_LENGTH = 32;

    /**
     * 使用AES进行加密
     *
     * @param data 需要加密的内容
     * @param key  密码
     * @return 加密后的字符串
     */
    public static String encrypt(String data, String key) {
        if (key == null || key.isEmpty()) {
            return null;
        }
        if (key.length() > MAX_LENGTH) {
            key = key.substring(0, MAX_LENGTH);
        }
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(CHAR_ENCODING), AES);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] result = cipher.doFinal(data.getBytes(CHAR_ENCODING));
            byte[] enc64 = Base64.getEncoder().encode(result);
            return new String(enc64, CHAR_ENCODING);
        } catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

}
