/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.account.client.impl;

import com.xkw.xop.account.client.request.impl.XopHttpRequestBase;
import com.xkw.xop.account.client.response.XopHttpResponse;
import com.xkw.xop.account.client.response.impl.XopHttpResponseImpl;
import com.xkw.xop.account.client.utils.XopClientUtils;
import com.xkw.xop.account.client.hmac.HmacConst;
import com.xkw.xop.account.client.hmac.HmacResult;
import com.xkw.xop.account.client.hmac.HmacUtils;
import kong.unirest.Config;
import kong.unirest.HttpMethod;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.xkw.xop.account.client.utils.Constants.CONTENT_TYPE_JSON;

/**
 * XopClientBase
 * description
 *
 * @author LiuJibin
 */
public class XopClientBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(XopClientBase.class);

    protected final String gatewayHost;
    protected final String appId;
    protected final String secret;

    protected final UnirestInstance client;

    public XopClientBase(String gatewayHost, String appId, String secret, Config config) {
        this.gatewayHost = gatewayHost;
        this.appId = appId;
        this.secret = secret;
        if (config == null) {
            config = new Config();
        }
        client = new UnirestInstance(config);
    }

    protected XopHttpResponse<String> getHttpResponse(XopHttpRequestBase request, Map<String, String> headerMap
        , String bodyString, Map<String, String> customHeaderMap) {
        // valid request
        if (request.getHttpMethod() == null) {
            throw new IllegalArgumentException("http method can NOT be null");
        }
        if (XopClientUtils.isEmpty(request.getUri())) {
            throw new IllegalArgumentException("http uri can NOT be null");
        }
        HttpMethod method = request.getHttpMethod();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("XOP {}: {}, queryParams:{}", method.name(), request.getUri(), request.getQueryParams());
        }
        String fullUri = gatewayHost + request.getUri();
        HttpResponse<String> response;

        String requestId = XopClientUtils.getRequestId();
        headerMap.put(HmacConst.KEY_REQUEST_ID, requestId);
        long startTime = 0;
        if (LOGGER.isDebugEnabled()) {
            startTime = System.currentTimeMillis();
        }
        // 校验CustomerHeader
        if (customHeaderMap != null && customHeaderMap.size() > 0) {
            Map<String, String> wholeHeaderMap = new HashMap<>(headerMap);
            wholeHeaderMap.putAll(customHeaderMap);
            if (headerMap.size() + customHeaderMap.size() != wholeHeaderMap.size()) {
                // 输出相同Header
                Set<String> dupHeaderSet = new HashSet<>(customHeaderMap.keySet());
                dupHeaderSet.retainAll(headerMap.keySet());
                throw new IllegalArgumentException("自定义Header中包含XOP客户端所传Key： " + String.join(",", dupHeaderSet));
            }
            headerMap.putAll(customHeaderMap);
        }
        if (method == HttpMethod.GET) {
            response = client.get(fullUri)
                .headers(headerMap)
                .queryString(request.getQueryParams())
                .asString();
        } else {
            HttpRequestWithBody req = client.post(fullUri).headers(headerMap).queryString(request.getQueryParams());
            if (XopClientUtils.isEmpty(bodyString)) {
                response = req.asString();
            } else {
                //body传Object对象会使用 json 序列化的重载方法
                response = req.body(bodyString).asString();
            }
        }
        if (LOGGER.isDebugEnabled()) {
            int status = XopClientUtils.getStatus(response);
            if (XopClientUtils.statusSuccess(status)) {
                LOGGER.debug("XOP SendRequest success, time spent: {} ms, response body: {},",
                    System.currentTimeMillis() - startTime, XopClientUtils.getResponseBody(response));
            } else {
                LOGGER.debug("XOP SendRequest failed, status: {}, requestId: {}, time spent: {} ms, body:{}", status,
                    requestId, System.currentTimeMillis() - startTime, XopClientUtils.getResponseBody(response));
            }
        }
        return new XopHttpResponseImpl<>(response, requestId);
    }

    protected HmacResult getHmacResult(String uri, Map<String, Object> queryParams, String bodyString) {
        Map<String, Object> map = new HashMap<>(8);
        if (queryParams != null) {
            map.putAll(queryParams);
        }
        map.put(HmacConst.KEY_URL, uri);
        return HmacUtils.sign(appId, secret, map, bodyString);
    }

    protected Map<String, String> getHeaderMap(HmacResult result) {
        Map<String, String> headerMap = new HashMap<>(8);
        headerMap.put(HmacConst.KEY_APP_ID, appId);
        headerMap.put(HmacConst.KEY_TIMESTAMP, result.getTimeStamp().toString());
        headerMap.put(HmacConst.KEY_SIGN, result.getSign());
        headerMap.put(HmacConst.KEY_NONCE, result.getNonce());
        headerMap.put("Content-Type", CONTENT_TYPE_JSON);
        return headerMap;
    }

}
