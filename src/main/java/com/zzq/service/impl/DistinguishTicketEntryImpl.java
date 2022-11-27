package com.zzq.service.impl;

import com.zzq.api.IDistinguishTicketEntryService;
import com.zzq.entity.Entry;
import com.zzq.model.TicketEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DistinguishTicketEntryImpl implements IDistinguishTicketEntryService {
    // 机构名称
    private static final List<String> entityNameList = Arrays.asList("银行", "农村");
    // 机构代码
    private static final List<String> entityCodeList = Arrays.asList("机构代码", "票交所机构代码", "机构号", "会员机构号");
    // 票交所会员代码
    private static final List<String> ticketExMemberCodeList = Arrays.asList("票交所会员代码", "会员代码", "会员号");
    // 大额行号
    private static final List<String> largeCodeList = Arrays.asList("大额行号", "大额号", "会员大额支付系统行号", "机构行号", "联行号", "行号");
    // 地址
    private static final List<String> addressList = Collections.singletonList("地址");
    // 交易员
    private static final List<String> traderList = Arrays.asList("交易员");
    // 交易员号
    private static final List<String> traderCodeList = Arrays.asList("ID", "id", "Id");
    //右边
    private static final List<String> postalCodeList = Arrays.asList("邮编");
    // 电话
    private static final List<String> phoneNumList = Arrays.asList("电话");
    // 传真
    private static final List<String> faxNUmList = Arrays.asList("传真");

    // 类别分割字符
    private static final String lineBreakChar = "\n";
    // key value分隔字符
    private static final List<String> breakChars = Arrays.asList(":", "：", " ");

    @Override
    public Entry distinguishEntity(String distinguishMsg,String distinguishMsg2) {
        List<String> entitys = Arrays.asList(distinguishMsg.split("\\\\n"));
        List<String> entitys2 = Arrays.asList(distinguishMsg2.split("\\\\n"));
//        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^**************************)()))))))))))))))))))))");
//        System.out.println(entitys);
        Map<String, String> ticketEntityMaps = distinguishColumns(entitys);
        Map<String, String> ticketEntityMaps2 = distinguishColumns(entitys2);
        return buildResponse(ticketEntityMaps,ticketEntityMaps2);
    }

    private Map<String, String> distinguishColumns(List<String> entityList) {
        Map<String, String> map = new HashMap<>();
        // 取出机构名称，放入map
        map.put("entityName", buildMapValue("entityName", entityNameList, entityList));
        map.put("entityCode", buildMapValue("entityCode", entityCodeList, entityList));
//        map.put("ticketExMemberCode", buildMapValue("ticketExMemberCode", ticketExMemberCodeList, entityList));
//        map.put("largeCodeList", buildMapValue("largeCodeList", largeCodeList, entityList));
//        map.put("addressList", buildMapValue("addressList", addressList, entityList));
        map.put("traderCodeList", buildMapValue("traderCodeList", traderCodeList, entityList));
        map.put("traderList", buildMapValue("traderList", traderList, entityList));

//        map.put("postalCodeList", buildMapValue("postalCodeList", postalCodeList, entityList));
//        map.put("phoneNumList", buildMapValue("phoneNumList", phoneNumList, entityList));
//        map.put("faxNUmList", buildMapValue("faxNUmList", faxNUmList, entityList));
        return map;
    }

    /**
     * 构建map
     *
     * @param columnName 字段名称，对应表单的一项
     * @param nameList   该项可能的名称列表
     * @param entityList 入参转为list
     */
    private String buildMapValue(String columnName, List<String> nameList, List<String> entityList) {
        //识别机构名称
        if(columnName.equals("entityName")){
            for (String entity : entityList){
                if(!entity.contains("：")&&(entity.contains("公司")||entity.contains("农村"))){
                    return entity;
                }

            }
        }
        //识别机构代码
        if(columnName.equals("entityCode")){
            for (String entity : entityList){
                String institutionCode = "";
                if(entity.contains("：")){
                    institutionCode = entity.substring(entity.indexOf("：") + 1);
                }

                if(institutionCode.length()==9){
                    return institutionCode;
                }
            }
        }
        //识别交易员名称
        if(columnName.equals("traderList")){
            for (String entity : entityList){
                if(entity.equals("")){
                    return "";
                }
                String zhihan = "";
                if(entity.contains("：")){
                    zhihan = entity.substring(0,entity.indexOf("："));
                }
                if(zhihan.equals("交易员")||zhihan.equals("交易员1")||zhihan.equals("交易员2")){
                    return entity.substring(entity.indexOf("：") + 1);
                }
            }
        }


        for (String key : nameList) {
            for (String entity : entityList) {
                entity = entity.trim();
                // 命中
                if (entity.contains(key)) {
                    return buildValue(key, entity);
                }
            }
        }
        return "";
    }

    private String buildValue(String key, String entity) {
        for (String breakChar : breakChars) {
            if (entity.contains(breakChar)) {
                return entity.substring(entity.indexOf("：") + 1);
            }
        }
        //todo 目前必须有分隔符，没有分隔符的情况暂时不太好用
//        return entity.replace(key,"");
        return "";
    }

    private Entry buildResponse(Map<String, String> EntityMap1,Map<String, String> EntityMap2) {
        Entry entry = new Entry();
        //买入机构名称
        entry.setRebuy_branch_name(EntityMap1.get("entityName"));
        //买入机构代码
        entry.setRebuy_branch_code(EntityMap1.get("entityCode"));
        //买入交易员名称
        entry.setRebuy_trader_name(EntityMap1.get("traderList"));
        //买入交易员ID
        entry.setRebuy_trader_id(EntityMap1.get("traderCodeList"));
        //卖出机构名称
        entry.setSale_branch_name(EntityMap2.get("entityName"));
        //卖出机构代码
        entry.setSale_branch_code(EntityMap2.get("entityCode"));
        //卖出交易员名称
        entry.setSale_trader_name(EntityMap2.get("traderList"));
        //卖出交易员ID
        entry.setSale_trader_id(EntityMap2.get("traderCodeList"));
        return entry;
    }
}
