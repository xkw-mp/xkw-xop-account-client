/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.account.client.request.impl;

import com.xkw.xop.account.client.entity.XopDeviceToken;

/**
 * XopDeviceRequestBase
 * description
 *
 * @author LiuJibin
 */
public class XopDeviceRequestBase extends XopHttpRequestBase {

    private String deviceTokenStr;
    private XopDeviceToken deviceToken;
    private String pkgTag;
    private String accountId;

    public String getDeviceTokenStr() {
        return deviceTokenStr;
    }

    public void setDeviceTokenStr(String deviceTokenStr) {
        this.deviceTokenStr = deviceTokenStr;
    }

    public XopDeviceToken getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(XopDeviceToken deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getPkgTag() {
        return pkgTag;
    }

    public void setPkgTag(String pkgTag) {
        this.pkgTag = pkgTag;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
