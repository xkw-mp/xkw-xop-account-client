/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.account.client.request;

import kong.unirest.HttpMethod;

import java.util.Map;

/**
 * XopAccountRequest
 * Xop Http 请求
 *
 * @author LiuJibin
 */
public interface XopAccountRequest {

    /**
     * 请求方法，现在支持GET/POST
     * @param httpMethod get/post
     * @return XopAccountRequest
     */
    XopAccountRequest httpMethod(HttpMethod httpMethod);

    /**
     * uri
     * @param uri uri
     * @return XopAccountRequest
     */
    XopAccountRequest uri(String uri);

    /**
     * 自定义Header
     * @param customHeaders 自定义Header Map
     * @return XopAccountRequest
     */
    XopAccountRequest customHeaders(Map<String, String> customHeaders);

    /**
     * query请求参数
     * @param queryParams query参数
     * @return XopAccountRequest
     */
    XopAccountRequest queryParams(Map<String, Object> queryParams);

    /**
     * 请求体，仅在POST方法中使用
     * @param body 请求体，Object
     * @return XopAccountRequest
     */
    XopAccountRequest body(Object body);

    /**
     * 设备包标签，来自于XOP后端管理设置
     * @param packageTag 设备包标签
     * @return XopRequest
     */
    XopAccountRequest pkgTag(String packageTag);

    /**
     * SC的账号Id，用于标识用户
     * @param accountId 账号Id/权益Id
     * @return XopRequest
     */
    XopAccountRequest accountId(String accountId);

    /**
     * 以下为Get方法
     * @return HttpMethod
     */
    HttpMethod getHttpMethod();

    String getUri();

    Map<String, String> getCustomHeaders();

    Map<String, Object> getQueryParams();

    Object getBody();

    String getPkgTag();

    String getAccountId();
}
