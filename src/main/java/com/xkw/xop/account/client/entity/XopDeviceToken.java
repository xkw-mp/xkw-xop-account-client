/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.account.client.entity;

/**
 * XopDeviceToken
 * XOP设备Token
 * @author wxb
 */
public class XopDeviceToken {
    private String token;
    private String deviceId;
    private String deviceAppId;
    private String nonce;
    private Long timestamp;
    private String sn;

    public XopDeviceToken() {}

    public XopDeviceToken(String token, String deviceAppId, String deviceId, String nonce, Long timestamp, String sn) {
        this.token = token;
        this.deviceAppId = deviceAppId;
        this.deviceId = deviceId;
        this.nonce = nonce;
        this.timestamp = timestamp;
        this.sn = sn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceAppId() {
        return deviceAppId;
    }

    public void setDeviceAppId(String deviceAppId) {
        this.deviceAppId = deviceAppId;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public String toString() {
        return "XopDeviceToken{" +
                "token='" + token + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", deviceAppId='" + deviceAppId + '\'' +
                ", nonce='" + nonce + '\'' +
                ", timestamp=" + timestamp +
                ", sn='" + sn + '\'' +
                '}';
    }
}
