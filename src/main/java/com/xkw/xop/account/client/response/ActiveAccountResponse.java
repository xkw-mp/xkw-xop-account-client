/*
 * xkw.com Inc. Copyright (c) 2021 All Rights Reserved.
 */
package com.xkw.xop.account.client.response;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ActiveAccountResponse
 * description
 *
 * @author LiuJibin
 */
public class ActiveAccountResponse {
    /**
     * http响应码
     */
    private Integer httpStatusCode;
    /**
     * 请求Id，用于排查错误
     */
    private String requestId;
    /**
     * 详细错误码，用于XOP问题排查
     */
    private Integer errorCode;
    /**
     * 错误说明
     */
    private String errorMsg;
    /**
     * 激活结果
     */
    private List<ActiveResultDto> accountStatusList;


    public boolean isSuccess() {
        return httpStatusCode == 200;
    }

    public List<ActiveResultDto> getAccountStatusList() {
        return accountStatusList;
    }

    public void setAccountStatusList(List<ActiveResultDto> accountStatusList) {
        this.accountStatusList = accountStatusList;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        String accountStatusStr = "[";
        if (accountStatusList != null && accountStatusList.size() > 0) {
            accountStatusStr += accountStatusList.stream().map(ActiveResultDto::toString)
                .collect(Collectors.joining("，"));
        }
        accountStatusStr += "]";
        return "ActiveAccountResponse{" + "activeResult=" + accountStatusStr + ", httpStatusCode=" + httpStatusCode
           + ", requestId='" + requestId + '\'' + ", errorCode=" + errorCode
            + ", errorMsg = '" + errorMsg + '\'' + '}';
    }
}
