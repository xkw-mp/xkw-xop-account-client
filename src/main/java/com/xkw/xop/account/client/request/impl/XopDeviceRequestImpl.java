/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.account.client.request.impl;

import com.xkw.xop.account.client.request.XopDeviceRequest;
import com.xkw.xop.account.client.entity.XopDeviceToken;
import kong.unirest.HttpMethod;

import java.util.Map;

/**
 * XopDeviceRequestImpl
 * @author LiuJibin
 */
public class XopDeviceRequestImpl extends XopDeviceRequestBase
    implements XopDeviceRequest {

    @Override
    public XopDeviceRequest httpMethod(HttpMethod httpMethod) {
        setHttpMethod(httpMethod);
        return this;
    }

    @Override
    public XopDeviceRequest uri(String uri) {
        setUri(uri);
        return this;
    }

    @Override
    public XopDeviceRequest customHeaders(Map<String, String> customHeaders) {
        setCustomHeaders(customHeaders);
        return this;
    }

    @Override
    public XopDeviceRequest queryParams(Map<String, Object> queryParams) {
        setQueryParams(queryParams);
        return this;
    }

    @Override
    public XopDeviceRequest body(Object body) {
        setBody(body);
        return this;
    }

    @Override
    public XopDeviceRequest deviceToken(XopDeviceToken deviceToken) {
        this.setDeviceToken(deviceToken);
        return this;
    }

    @Override
    public XopDeviceRequest deviceTokenStr(String deviceTokenStr) {
        setDeviceTokenStr(deviceTokenStr);
        return this;
    }

    @Override
    public XopDeviceRequest pkgTag(String packageTag) {
        setPkgTag(packageTag);
        return this;
    }
}
