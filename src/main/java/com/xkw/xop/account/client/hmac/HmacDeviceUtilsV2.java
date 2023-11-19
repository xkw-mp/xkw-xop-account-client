/*
 * xkw.com Inc. Copyright (c) 2023 All Rights Reserved.
 */
package com.xkw.xop.account.client.hmac;

import java.util.Map;

/**
 * HmacDeviceUtilsV2
 * XOP设备对接签名工具，使用xop-hmac v2
 *
 * @author LiuJibin
 */
public class HmacDeviceUtilsV2 extends HmacDeviceUtils {

    /**
     * xop的签名逻辑，签名过程包括
     * 1、需传入appId，secret，Map参数，和body；若Map中有nonce会被替换为固定格式值，body若有值也会进行签名
     * 2、签名过程：map参数进行排序后+body（有值时会签名）以上字符串，使用hmac-sha256（secret为盐）得到签名值
     *
     * @param appId          应用凭证Id
     * @param secret         应用凭证秘钥
     * @param deviceAppId    设备App的Id
     * @param token          设备Token
     * @param timeStamp      生成Token时使用的timestamp
     * @param nonce          随机字符串
     * @param urlParam       url参数
     * @param requestBodyStr 请求体字符串
     * @return 返回签名结果，包括：时间戳，签名值，随机nonce
     * @throws RuntimeException SHA1签名异常
     */
    public static HmacResult sign(String appId, String secret, String deviceAppId, String token, Long timeStamp, String nonce
        , final Map<String, ?> urlParam, String requestBodyStr) throws RuntimeException {
        return sign(appId, secret, deviceAppId, token, timeStamp, nonce, urlParam, requestBodyStr, HmacConst.KEY_SIGN_V2
            , HmacUtilsV2::getSignatureString);
    }

    /**
     * 验证签名
     *
     * @param parameters     请求参数
     * @param secret         请求秘钥
     * @param requestBodyStr 请求body
     * @return true代表验签成功，false代表验签失败
     * @throws RuntimeException SHA1签名异常
     */
    public static boolean validateRequest(final Map<String, ?> parameters, String secret, String requestBodyStr) throws RuntimeException {
        return validateRequest(parameters, secret, requestBodyStr, HmacConst.KEY_SIGN_V2, HmacUtilsV2::getSignatureString);
    }
}
