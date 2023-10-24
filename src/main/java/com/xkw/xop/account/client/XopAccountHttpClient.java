/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.account.client;

import com.xkw.xop.account.client.request.XopAccountRequest;
import com.xkw.xop.account.client.response.ActiveAccountResponse;
import com.xkw.xop.account.client.response.XopHttpResponse;

import java.util.List;

/**
 * XopAccountHttpClient
 * 学科网开放平台XOP-账号请求的Http客户端
 *
 * @author LiuJibin
 */
public interface XopAccountHttpClient {

    /**
     * 获取XopDeviceRequest， GET方法
     * @return XopDeviceRequest
     */
    XopAccountRequest getRequest();

    /**
     * 获取XopDeviceRequest， POST方法
     * @return XopDeviceRequest
     */
    XopAccountRequest postRequest();

    /**
     * 发送设备请求
     * @param request XopAccountRequest
     * @return XOPHttpResponse
     */
    XopHttpResponse<String> sendRequest(XopAccountRequest request);

    /**
     * 激活账号/权益
     * @param pkgTag 设备标签，对应账号/权益等级
     * @param accountIds 账号Id/权益Id列表，不要超过20个
     * @return ActiveAccountResponse
     */
    ActiveAccountResponse activeAccount(String pkgTag, List<String> accountIds);

    /**
     * 激活账号/权益，适用于单个账号激活
     * @param pkgTag 设备标签，对应账号/权益等级
     * @param accountId 账号Id/权益Id
     * @return ActiveAccountResponse
     */
    ActiveAccountResponse activeAccount(String pkgTag, String accountId);

    /**
     * 查看账号/权益激活状态
     * @param accountIds 账号Id/权益Id，不要超过20个
     * @return ActiveAccountResponse
     */
    ActiveAccountResponse queryAccountStatus(List<String> accountIds);

    /**
     * 查看账号/权益激活状态，适用于单个账号
     * @param accountId 账号Id/权益Id
     * @return ActiveAccountResponse
     */
    ActiveAccountResponse queryAccountStatus(String accountId);
}
