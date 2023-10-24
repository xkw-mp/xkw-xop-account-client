/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.account.client.request;

import com.xkw.xop.account.client.entity.XopDeviceToken;
import kong.unirest.HttpMethod;

import java.util.Map;

/**
 * XopDeviceRequest
 * Xop设备 Http 请求
 *
 * @author LiuJibin
 */
public interface XopDeviceRequest {

    /**
     * 请求方法，现在支持GET/POST
     * @param httpMethod get/post
     * @return XopDeviceRequest
     */
    XopDeviceRequest httpMethod(HttpMethod httpMethod);

    /**
     * uri
     * @param uri uri
     * @return XopDeviceRequest
     */
    XopDeviceRequest uri(String uri);

    /**
     * 自定义Header
     * @param customHeaders 自定义Header Map
     * @return XopDeviceRequest
     */
    XopDeviceRequest customHeaders(Map<String, String> customHeaders);

    /**
     * query请求参数
     * @param queryParams query参数
     * @return XopDeviceRequest
     */
    XopDeviceRequest queryParams(Map<String, Object> queryParams);

    /**
     * 请求体，仅在POST方法中使用
     * @param body 请求体，Object
     * @return XopDeviceRequest
     */
    XopDeviceRequest body(Object body);

    /**
     * 设备Token字符串
     * @param deviceTokenStr 设备Token字符串，来自于设备
     * @return XopDeviceRequest
     */
    XopDeviceRequest deviceTokenStr(String deviceTokenStr);

    /**
     * 设备Token，与deviceTokenStr同时存在的话，优先使用deviceToken
     * @param deviceToken 设备Token
     * @return XopDeviceRequest
     */
    XopDeviceRequest deviceToken(XopDeviceToken deviceToken);

    /**
     * 设备包标签，来自于XOP后端管理设置
     * @param pkgTag 设备包标签
     * @return XopDeviceRequest
     */
    XopDeviceRequest pkgTag(String pkgTag);

    /**
     * 以下为Get方法
     * @return HttpMethod
     */
    HttpMethod getHttpMethod();

    String getUri();

    Map<String, String> getCustomHeaders();

    Map<String, Object> getQueryParams();

    Object getBody();

    XopDeviceToken getDeviceToken();

    String getPkgTag();

}
