/*
 * xkw.com Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.xkw.xop.account.client.hmac;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.xkw.xop.account.client.hmac.HmacUtils.getSignatureString;

/**
 * HmacDeviceUtils
 * XOP设备对接签名工具
 *
 * @author LiuJibin
 */
public class HmacDeviceUtils {

    /**
     * xop的签名逻辑，签名过程包括
     * 1、需传入appId，secret，Map参数，和body；若Map中有nonce会被替换为固定格式值，body若有值也会进行签名
     * 2、签名过程：map参数进行排序后+body（有值时会签名）+secret 以上字符串进行md5得到签名值
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
        Map<String, Object> map = new HashMap<>(Optional.ofNullable(urlParam).map(Map::size).orElse(0) + 8);
        Optional.ofNullable(urlParam).ifPresent(map::putAll);

        map.remove(HmacConst.KEY_SIGN);

        map.put(HmacConst.KEY_APP_ID, appId);
        map.put(HmacConst.KEY_TIMESTAMP, timeStamp);
        map.put(HmacConst.KEY_DEVICE_APP_ID, deviceAppId);
        map.put(HmacConst.KEY_DEVICE_TOKEN, token);
        map.put(HmacConst.KEY_NONCE, nonce);

        String sha1Str = getSignatureString(map, secret, requestBodyStr);
        map.put(HmacConst.KEY_SIGN, sha1Str);
        return new HmacResult(timeStamp, sha1Str, nonce);
    }

    /**
     * 请求验签
     *
     * @param parameters     请求参数
     * @param secret         请求秘钥
     * @param requestBodyStr 请求body
     * @return true代表验签成功，false代表验签失败
     * @throws RuntimeException SHA1签名异常
     */
    public static boolean validateRequest(final Map<String, ?> parameters, String secret, String requestBodyStr) throws RuntimeException {
        Map<String, Object> map = new HashMap<>(parameters);
        String sign = (String) map.remove(HmacConst.KEY_SIGN);
        String md5Str = getSignatureString(map, secret, requestBodyStr);
        return md5Str.equalsIgnoreCase(sign);
    }
}
