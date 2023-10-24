/*
 * xkw.com Inc. Copyright (c) 2022 All Rights Reserved.
 */
package com.xkw.xop.account.client.response;

/**
 * ActiveResultDto
 * description
 *
 * @author LiuJibin
 */
public class ActiveResultDto {

    /**
     * 账号/权益Id
     */
    private String accountId;

    /**
     * 激活结果，
     */
    private Integer activeResult;
    /**
     * 激活结果说明
     */
    private String activeDesc;
    /**
     * 生效时间戳，仅在查询到有效激活记录时有效
     */
    private Long createTime;
    /**
     * 失效时间戳，仅在查询到有效激活记录时有效
     */
    private Long endTime;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getActiveResult() {
        return activeResult;
    }

    public void setActiveResult(Integer activeResult) {
        this.activeResult = activeResult;
    }

    public String getActiveDesc() {
        return activeDesc;
    }

    public void setActiveDesc(String activeDesc) {
        this.activeDesc = activeDesc;
    }

    /**
     * 调用成功，包括激活成功/激活中
     * @return boolean
     */
    public boolean isSuccess() {
        return ActiveAccountErrorEnum.isSuccess(activeResult);
    }

    /**
     * 是否已激活，
     * @return boolean
     */
    public boolean isActivated() {
        return ActiveAccountErrorEnum.SUCCESS.getCode().equals(activeResult);
    }

    /**
     * 是否激活中
     * @return boolean
     */
    public boolean isActivating() {
        return ActiveAccountErrorEnum.ACTIVATING.getCode().equals(activeResult);
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "ActiveResultDto{" + "accountId='" + accountId + "'" + ", activeResult=" + activeResult
            + ", activeDesc='" + activeDesc + "', createTime: " + createTime + ", endTime: " + endTime + "}";
    }
}
