package com.zzq.model;

import lombok.Data;

@Data
public class TicketEntity {

    /**
     * 机构名称
     */
    private String entiryName;
    /**
     * 机构代码
     */
    private String entityCode;
    /**
     * 票交所会员代码
     */
    private String ticketExMemberCode;
//    /**
//     * 票交所机构代码
//     */
//    private String ticketExCode;
    /**
     * 大额行号
     */
    private String largeCode;
    /**
     * 地址
     */
    private String address;
    /**
     * 交易员
     */
    private String trader;
    /**
     * 交易员号
     */
    private String traderCode;
    /**
     * 邮编
     */
    private String postalCode;
    /**
     * 电话
     */
    private String phoneNum;
    /**
     * 传真
     */
    private String faxNum;
    /**
     * 划款方式
     */
    private String transferMethod;
    /**
     * 法定代表人
     */
    private String legalRepresentative;
    /**
     * 组织机构代码
     */
    private String organizationCode;

}
