package com.zzq.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@TableName(value = "item_list",autoResultMap = true)
public class PartnerOrderDetailDTO implements Serializable{

    @ApiModelProperty("票据号")
    private String draft_no;

    @ApiModelProperty("子票区间")
    private String draft_no_interval;

    @ApiModelProperty("票据金额")
    private String draft_amount;

    //    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("到期日")
    private String end_date;

    @ApiModelProperty("承兑行行号")
    private String cd_bank_no;

    @ApiModelProperty("贴现行行号")
    private String tie_bank_no;

    @ApiModelProperty("贴现行名称")
    private String tie_bank_name;
}
