package com.zzq.entity;

import lombok.Data;

@Data
public class TransferDiscountOrderRequest {

    private String partnerCode;
    private String requestNo;
    private String requestTime;
    private String sign;
    private String data;

}
