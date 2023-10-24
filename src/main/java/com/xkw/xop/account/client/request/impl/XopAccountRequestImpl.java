/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.account.client.request.impl;

import com.xkw.xop.account.client.request.XopAccountRequest;
import kong.unirest.HttpMethod;

import java.util.Map;

/**
 * XopAccountRequestImpl
 * XopAccountRequest实现类
 *
 * @author LiuJibin
 */
public class XopAccountRequestImpl  extends XopDeviceRequestBase
    implements XopAccountRequest {

    @Override
    public XopAccountRequest httpMethod(HttpMethod httpMethod) {
        setHttpMethod(httpMethod);
        return this;
    }

    @Override
    public XopAccountRequest uri(String uri) {
        setUri(uri);
        return this;
    }

    @Override
    public XopAccountRequest customHeaders(Map<String, String> customHeaders) {
        setCustomHeaders(customHeaders);
        return this;
    }

    @Override
    public XopAccountRequest queryParams(Map<String, Object> queryParams) {
        setQueryParams(queryParams);
        return this;
    }

    @Override
    public XopAccountRequest body(Object body) {
        setBody(body);
        return this;
    }

    public XopAccountRequest pkgTag(String pkgTag) {
        setPkgTag(pkgTag);
        return this;
    }

    public XopAccountRequest accountId(String accountId) {
        setAccountId(accountId);
        return this;
    }

    public String getPkgTag() {
        return super.getPkgTag();
    }

    public String getAccountId() {
        return super.getAccountId();
    }
}
