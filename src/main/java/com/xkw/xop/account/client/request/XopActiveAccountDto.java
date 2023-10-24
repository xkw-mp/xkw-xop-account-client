/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.account.client.request;

/**
 * XopActiveRequest
 * description
 *
 * @author LiuJibin
 */
public class XopActiveAccountDto {
    /**
     * 账号/权益Id
     */
    private String accountId;

    public XopActiveAccountDto() {}

    public XopActiveAccountDto(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
