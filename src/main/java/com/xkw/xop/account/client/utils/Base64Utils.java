/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.account.client.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64工具类
 *
 * @author wxb
 */
public class Base64Utils {

    public static String encode(String str) {
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] bytes = encoder.encode(str.getBytes(StandardCharsets.UTF_8.name()));
            return new String(bytes, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new RuntimeException("base64 encode string error:"+ e.getMessage());
        }
    }

    public static String decode(String str) {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] bytes = decoder.decode(str.getBytes(StandardCharsets.UTF_8.name()));
            return new String(bytes, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new RuntimeException("base64 decode string error:"+ e.getMessage());
        }
    }

}
