package com.ningpai.deposit.bean;

import lombok.Data;


/**
 * Created by youzipi on 16/10/9.
 */
public class WithdrawForm {

    private String status;

    private Integer pageNo = 1;

    private Integer pageSize = 15;

    private Long customerId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
