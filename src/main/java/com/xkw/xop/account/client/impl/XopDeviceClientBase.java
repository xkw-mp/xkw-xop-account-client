/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.account.client.impl;

import com.xkw.xop.account.client.entity.XopDeviceToken;
import com.xkw.xop.account.client.hmac.HmacConst;
import com.xkw.xop.account.client.hmac.HmacDeviceUtils;
import com.xkw.xop.account.client.hmac.HmacDeviceUtilsV2;
import com.xkw.xop.account.client.hmac.HmacResult;
import com.xkw.xop.account.client.hmac.XopHmacVersionEnum;
import com.xkw.xop.account.client.request.impl.XopDeviceRequestBase;
import com.xkw.xop.account.client.response.XopHttpResponse;
import com.xkw.xop.account.client.utils.Base64Utils;
import com.xkw.xop.account.client.utils.XopClientUtils;
import kong.unirest.Config;

import java.util.HashMap;
import java.util.Map;

import static com.xkw.xop.account.client.utils.Constants.GSON;

/**
 * XopDeviceClientBase
 * 设备请求客户端基类，
 *
 * @author LiuJibin
 */
public class XopDeviceClientBase extends XopClientBase {

    public XopDeviceClientBase(String gatewayHost, String appId, String secret, Config config, XopHmacVersionEnum hmacVersionEnum) {
        super(gatewayHost, appId, secret, config, hmacVersionEnum);
    }

    /**
     * 发送设备请求
     *
     * @param request XopRequest
     * @return XOPHttpResponse
     */
    public XopHttpResponse<String> sendRequest(XopDeviceRequestBase request) {
        if (request == null) {
            throw new IllegalArgumentException("request不能为空");
        }
        XopDeviceToken deviceToken = request.getDeviceToken();
        if (deviceToken == null) {
            deviceToken = parseToken(request.getDeviceTokenStr());
        }
        if (deviceToken == null || XopClientUtils.isEmpty(deviceToken.getDeviceAppId()) || XopClientUtils.isEmpty(deviceToken.getToken())
            || XopClientUtils.isEmpty(deviceToken.getNonce()) || deviceToken.getTimestamp() == null) {
            throw new RuntimeException("XopDeviceToken对象及属性不能为空");
        }
        String bodyString = request.getBodyString();
        Map<String, Object> paramsMap = new HashMap<>(8);
        if (request.getQueryParams() != null) {
            paramsMap.putAll(request.getQueryParams());
        }
        paramsMap.put(HmacConst.KEY_URL, request.getUri());
        HmacResult result;
        if (XopHmacVersionEnum.V2 == hmacVersionEnum) {
            result = HmacDeviceUtilsV2.sign(appId, secret, deviceToken.getDeviceAppId(), deviceToken.getToken()
                , deviceToken.getTimestamp(), deviceToken.getNonce(), paramsMap, bodyString);
        } else {
            result = HmacDeviceUtils.sign(appId, secret, deviceToken.getDeviceAppId(), deviceToken.getToken()
                , deviceToken.getTimestamp(), deviceToken.getNonce(), paramsMap, bodyString);
        }

        Map<String, String> headerMap = getHeaderMap(deviceToken, request.getPkgTag(), result);
        return getHttpResponse(request, headerMap, bodyString, request.getCustomHeaders());
    }

    protected Map<String, String> getHeaderMap(XopDeviceToken deviceToken, String pkgTag, HmacResult result) {
        Map<String, String> headerMap = super.getHeaderMap(result);
        headerMap.put(HmacConst.KEY_DEVICE_APP_ID, deviceToken.getDeviceAppId());
        headerMap.put(HmacConst.KEY_DEVICE_TOKEN, deviceToken.getToken());
        if (!XopClientUtils.isEmpty(pkgTag)) {
            headerMap.put(HmacConst.KEY_DEVICE_PACKAGE_TAG, pkgTag);
        }
        return headerMap;
    }

    public static XopDeviceToken parseToken(String tokenStr) {
        final String decodeToken = Base64Utils.decode(tokenStr);
        return GSON.fromJson(decodeToken, XopDeviceToken.class);
    }

}