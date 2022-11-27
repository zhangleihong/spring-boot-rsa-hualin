package com.zzq.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zzq.dto.PartnerOrderDetailDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2022-09-19
 */
@Getter
@Setter
@Data
@ApiModel(value = "Entry对象")
@TableName(autoResultMap = true)
public class Entry implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("批次信息")
    private String serial_info;

    @ApiModelProperty("报单类型：1-新增；2-修改")
    private String quote_type;

    @ApiModelProperty("业务编号")
    private String serial_no;

    @ApiModelProperty("合计金额")
    private String total_amount;

    @ApiModelProperty("华林卖出利率")
    private String sale_rate;

    @ApiModelProperty("华林买入利率")
    private String buy_rate;

    @ApiModelProperty("承兑行信息")
    private String cd_bank_type;

    @ApiModelProperty("贴现行信息")
    private String tx_bank_type;

//    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("交易日")
    private String deal_date;

    @ApiModelProperty("期限信息")
    private String expire;

    @ApiModelProperty("华林买入机构名称")
    private String rebuy_branch_name;

    @ApiModelProperty("华林买入机构代码")
    private String rebuy_branch_code;

    @ApiModelProperty("华林买入非法人产品名称")
    private String rebuy_prod_name;

    @ApiModelProperty("华林买入非法人产品代码")
    private String rebuy_prod_code;

    @ApiModelProperty("华林买入机构交易员名称")
    private String rebuy_trader_name;

    @ApiModelProperty("华林买入机构交易员编号")
    private String rebuy_trader_id;

    @ApiModelProperty("华林卖出机构名称")
    private String sale_branch_name;

    @ApiModelProperty("华林卖出机构代码")
    private String sale_branch_code;

    @ApiModelProperty("华林卖出非法人产品名称")
    private String sale_prod_name;

    @ApiModelProperty("华林卖出非法人产品代码")
    private String sale_prod_code;

    @ApiModelProperty("华林卖出机构交易员名称")
    private String sale_trader_name;

    @ApiModelProperty("华林卖出机构交易员编号")
    private String sale_trader_id;

    @ApiModelProperty("联系人姓名")
    private String contact_name;

    @ApiModelProperty("联系人电话")
    private String contact_number;

    @ApiModelProperty(value = "item_list")
    @TableField(value = "item_list",typeHandler = FastjsonTypeHandler.class)
    private List<PartnerOrderDetailDTO> item_list;

//    @ApiModelProperty("票据号")
//    private String draftNo;
//
//    @ApiModelProperty("子票区间")
//    private String draftNoInterval;
//
//    @ApiModelProperty("票据金额")
//    private String draftAmount;
//
////    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
////    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @ApiModelProperty("到期日")
//    private String endDate;
//
//    @ApiModelProperty("承兑行行号")
//    private String cdBankNo;
//
//    @ApiModelProperty("贴现行行号")
//    private String tieBankNo;
//
//    @ApiModelProperty("贴现行名称")
//    private String tieBankName;


}
