package com.cars.model.sys;


import com.cars.util.page.Page;

import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by wangyupeng on 2017/12/20.
 */
@Table(name = "demo_sys_log")
public class SysLog extends Page {
    @Id
    private String logId;
    private String userName;
    private String operaIp;
    @OrderBy("DESC")
    private String operaDate;
    @Transient
    private String startDate;
    @Transient
    private String endDate;
    private String operaUrl;
    private String methodName;
    private Long dealTime;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOperaIp() {
        return operaIp;
    }

    public void setOperaIp(String operaIp) {
        this.operaIp = operaIp;
    }

    public String getOperaDate() {
        return operaDate;
    }

    public void setOperaDate(String operaDate) {
        this.operaDate = operaDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOperaUrl() {
        return operaUrl;
    }

    public void setOperaUrl(String operaUrl) {
        this.operaUrl = operaUrl;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Long getDealTime() {
        return dealTime;
    }

    public void setDealTime(Long dealTime) {
        this.dealTime = dealTime;
    }

    @Override
    public String toString() {
        return "SysLog{" +
                "logId='" + logId + '\'' +
                ", userName='" + userName + '\'' +
                ", operaIp='" + operaIp + '\'' +
                ", operaDate=" + operaDate +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", operaUrl='" + operaUrl + '\'' +
                ", methodName='" + methodName + '\'' +
                ", dealTime=" + dealTime +
                '}';
    }
}
