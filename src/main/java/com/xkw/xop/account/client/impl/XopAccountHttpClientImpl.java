/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.account.client.impl;

import com.google.gson.reflect.TypeToken;
import com.xkw.xop.account.client.XopAccountHttpClient;
import com.xkw.xop.account.client.hmac.XopHmacVersionEnum;
import com.xkw.xop.account.client.request.XopAccountRequest;
import com.xkw.xop.account.client.request.XopActiveAccountDto;
import com.xkw.xop.account.client.request.impl.XopAccountRequestImpl;
import com.xkw.xop.account.client.request.impl.XopDeviceRequestBase;
import com.xkw.xop.account.client.request.impl.XopDeviceRequestImpl;
import com.xkw.xop.account.client.response.ActiveAccountResponse;
import com.xkw.xop.account.client.response.ActiveResultDto;
import com.xkw.xop.account.client.response.XopHttpResponse;
import com.xkw.xop.account.client.response.XopResponse;
import com.xkw.xop.account.client.utils.Constants;
import com.xkw.xop.account.client.utils.XopClientUtils;
import com.xkw.xop.account.client.entity.XopDeviceToken;
import com.xkw.xop.account.client.utils.AesUtils;
import com.xkw.xop.account.client.utils.Base64Utils;
import com.xkw.xop.account.client.hmac.HmacUtils;
import kong.unirest.Config;
import kong.unirest.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.xkw.xop.account.client.utils.Constants.GSON;

/**
 * XopAccountHttpClient
 * 学科网开放平台XOP-按账号计费请求的Http客户端
 *
 * @author LiuJibin
 */
public class XopAccountHttpClientImpl extends XopDeviceClientBase
    implements XopAccountHttpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger("XopAccountHttpClient");

    public XopAccountHttpClientImpl(String gatewayHost, String appId, String secret, Config config, XopHmacVersionEnum hmacVersionEnum) {
        super(gatewayHost, appId, secret, config, hmacVersionEnum);
    }

    @Override
    public XopAccountRequest getRequest() {
        XopAccountRequestImpl request = new XopAccountRequestImpl();
        request.setHttpMethod(HttpMethod.GET);
        return request;
    }

    @Override
    public XopAccountRequest postRequest() {
        XopAccountRequestImpl request = new XopAccountRequestImpl();
        request.setHttpMethod(HttpMethod.POST);
        return request;
    }

    @Override
    public XopHttpResponse<String> sendRequest(XopAccountRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request不能为空");
        }
        if (XopClientUtils.isEmpty(request.getAccountId())) {
            throw new IllegalArgumentException("accountId不能为空");
        }
        XopDeviceToken deviceToken = buildXopToken(request.getAccountId());
        XopDeviceRequestBase requestImpl = (XopDeviceRequestBase)request;
        requestImpl.setDeviceToken(deviceToken);
        return super.sendRequest(requestImpl);
    }

    @Override
    public ActiveAccountResponse activeAccount(String pkgTag, String accountId) {
        return activeAccount(pkgTag, Arrays.asList(accountId));
    }
    @Override
    public ActiveAccountResponse activeAccount(String pkgTag, List<String> accountIds) {
        return activeAccountRequest(HttpMethod.POST, pkgTag, accountIds);
    }

    @Override
    public ActiveAccountResponse queryAccountStatus(String accountId) {
        return queryAccountStatus(Arrays.asList(accountId));
    }

    @Override
    public ActiveAccountResponse queryAccountStatus(List<String> accountIds) {
        return activeAccountRequest(HttpMethod.GET, null, accountIds);
    }

    private ActiveAccountResponse activeAccountRequest(HttpMethod httpMethod, String pkgTag, List<String> accountIds) {
        if (accountIds == null || accountIds.size() == 0) {
            throw new IllegalArgumentException("accountId不能为空");
        }
        for (String accountId : accountIds) {
            if (XopClientUtils.isEmpty(accountId)) {
                throw new IllegalArgumentException("accountId不能为空");
            }
        }
        XopDeviceToken token = buildXopToken(accountIds.get(0));
        XopDeviceRequestImpl request = new XopDeviceRequestImpl();
        request.httpMethod(httpMethod)
            .pkgTag(pkgTag)
            .deviceToken(token)
            .uri(Constants.ACCOUNT_ACTIVE_API);
        Map<String, Object> paramMap = new HashMap<>(4);
        if (httpMethod == HttpMethod.GET) {
            String accountIdStr = accountIds.stream().collect(Collectors.joining(","));
            paramMap.put("account_ids", accountIdStr);
            request.queryParams(paramMap);
        } else {
            List<XopActiveAccountDto> requestList = accountIds.stream().map(XopActiveAccountDto::new)
                .collect(Collectors.toList());
            request.body(requestList);
        }
        XopHttpResponse<String> response = super.sendRequest(request);
        ActiveAccountResponse res = new ActiveAccountResponse();
        res.setRequestId(response.getRequestId());
        res.setHttpStatusCode(response.getStatus());
        // get errorCode
        res.setErrorCode(-1);
        if (!XopClientUtils.isEmpty(response.getBody())) {
            try {
                XopResponse<List<ActiveResultDto>> result =
                    GSON.fromJson(response.getBody(), new TypeToken<XopResponse<List<ActiveResultDto>>>(){}.getType());
                res.setErrorCode(result.getCode());
                res.setErrorCode(result.getCode());
                res.setErrorMsg(result.getMsg());
                res.setAccountStatusList(result.getData());
            } catch (Exception e) {
                // error
                LOGGER.warn("Parse Active Result error, ", e);
                res.setErrorCode(-1);
                res.setErrorMsg("Parse Active Result error");
                res.setAccountStatusList(new ArrayList<>());
                return res;
            }
        }
        return res;
    }


    protected XopDeviceToken buildXopToken(String accountId) {
        long timestamp = System.currentTimeMillis() / 1000L;
        String nonce = HmacUtils.getNonce();
        String token = buildTokenStr(appId, accountId, timestamp, nonce, secret);
        return new XopDeviceToken(token, appId, accountId, nonce, timestamp, accountId);
    }

    protected String buildTokenStr(String deviceAppId, String accountId, Long timestamp, String nonce, String secret) {
        String source = String.format("{\"deviceAppId\":\"%s\",\"deviceId\":\"%s\",\"nonce\":\"%s\",\"sn\":\"%s\",\"timestamp\":%s}",
                deviceAppId, accountId, nonce, accountId, timestamp);
        String encrypt = AesUtils.encrypt(source, secret);
        return Base64Utils.encode(encrypt);
    }
}
