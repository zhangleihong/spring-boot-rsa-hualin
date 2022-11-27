package com.zzq.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
@Data
public class Trade implements Serializable {

    private static final long serialVersionUID = 1L;


    private String partnerCode;
    private String requestNo;
    private String requestTime;
    private String data1;

}
