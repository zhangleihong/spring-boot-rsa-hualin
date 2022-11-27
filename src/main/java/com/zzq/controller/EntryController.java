package com.zzq.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzq.api.IDistinguishTicketEntryService;
import com.zzq.common.Result;
import com.zzq.config.AuthAccess;
import com.zzq.config.RSAService;
import com.zzq.dto.PartnerOrderDetailDTO;
import com.zzq.entity.Entry;
import com.zzq.entity.Notice;
import com.zzq.entity.TransferDiscountOrderRequest;
import com.zzq.mapper.EntryMapper;
import com.zzq.model.TicketEntity;
import com.zzq.service.IEntryService;
import com.zzq.service.INoticeService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.*;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

import cn.hutool.json.JSONUtil;
import org.springframework.web.multipart.MultipartFile;

import static com.zzq.PartnerHelper.*;



//import static com.zzq.PartnerHelper.*;
//import static com.zzq.RSAUtil.decryptByPrivateKey;
//import static com.zzq.RSAUtil.encryptByPublicKey;

//import static com.zzq.PartnerHelper.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since 2022-09-19
 */
@RestController
@RequestMapping("/entry")
public class EntryController {

    @Resource
    INoticeService noticeService;
    @Resource
    IEntryService entryService;

    @Resource
    private EntryMapper entryMapper;

    String jsonStr;


    List<PartnerOrderDetailDTO> partnerOrderDetailDTOS = CollUtil.newArrayList();



    String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPw/tUyCpSpPnaJpC/e+9Yf9qU+Ft+DxMmcxKeh5S1SAad1Oots6WuymDbBCIOwSyKbmXK6IxZfD+fZPlsGyh8+XVgpdxwo+YcVY7paB7Qt5YGq2psbRA4LWCKnIqa/gXleyMQh7i68AcJuCfe1oWCnJJico0IqsgQVw6IOWXz/QIDAQAB";
    String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI/D+1TIKlKk+domkL9771h/2pT4W34PEyZzEp6HlLVIBp3U6i2zpa7KYNsEIg7BLIpuZcrojFl8P59k+WwbKHz5dWCl3HCj5hxVjuloHtC3lgaramxtEDgtYIqcipr+BeV7IxCHuLrwBwm4J97WhYKckmJyjQiqyBBXDog5ZfP9AgMBAAECgYA4fJKFz1gBvNyGwFxjQDjclu+1VJfuQdIOa2xHaZIeYDxpSzzv1AQnV1ES/N682V28zn+kaBatGkqNQElYFpujVgPjnu60F+kvX3Fa8F+gwy5S6LBxxWGae1N7YAWN5bzsPLTkxh94RRianhDNg5KmbOMT1fwj/SWqlns1uY5vwQJBAOQV7yeJ25SJU30FKDeCHwD38XLhFgo9E0CFXvd5H4wlT+F7j+0ZIP5xwk/Z2wKpuj+gNW0cMi/Lj7VPQmzBPMkCQQChXD7m07ciW7zjvsKfy4IvCoG3CqzNKv2qfEJ0wxUXdXjkYpdhYF4wKuzKrJXEyQM+Iaub4SjxwP5p0SBAcXuVAkBcQvsglUU7Sml5MvLXsUz1SVZjz+Toc1gsS0bk+BvoxtcPMLpBgF62TjH5tvg+lYV/Y0D5R+SNLNyOzObjsy7xAkAN/0eBlHn9ZAHVq7WMUQYA+KNZ8nYc8g7BHDhTkpygxVLwVNEW5FonSYHy7terE1jRqfAN/JElr30iRr7KeE2lAkAoHLxqZmN0vdkFmHp/zGtENRP89iEcH1LsBGWYSpbH5zy1N7zimQR2/Yj5OTcKysOcfC2W7ihKIn5Bb75UaQK1";
    String hepublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCrYJVfhBBZhMLPDWibEac661EY0Lrfrn0KMLd060eIWg63prrwZBxVWe+L+3H111smy14t5Pu3xDz2HNhMGl2ASxhblw+Pk/1TNm4LevBE+/nYlhux6alP8t8LA3EjmGrOOsdp7DB2XVUlTKH192OIKMJzp2Fpl6lVVfpT2bcFgQIDAQAB";
    @Resource
    IDistinguishTicketEntryService distinguishService;

    //识别文本框
    @GetMapping(value = "/distinguishTicketEntry")
    public Entry distinguish(@RequestParam(defaultValue = "") String desc1,
                             @RequestParam(defaultValue = "") String desc2){
        //jsonStr = "大额行号：402332010004，机构代码：000018493，鄞州银行沈惠芝jr0002  电话13858369634";
        desc1 = desc1.replace("\"","");
        desc2 = desc2.replace("\"","");
        //System.out.println(distinguishService.distinguishEntity(desc1,desc2));
        return distinguishService.distinguishEntity(desc1,desc2);
    }

    @PostMapping(value = "/exitEntry")
    public JSONObject save(@RequestBody Entry entrys) throws Exception {

        if (entrys.getQuote_type().equals("新增")){
            entrys.setQuote_type("1");
        }
        else{
            entrys.setQuote_type("2");
        }
        //entrys.setItem_list(jsonStr);

        entrys.setItem_list(partnerOrderDetailDTOS);
        entryService.save(entrys);

        String RSAPublicKey = initKey().get("RSAPublicKey");
        String RSAPrivateKey = initKey().get("RSAPrivateKey");
//        System.out.println("公钥："+RSAPublicKey);
//        System.out.println("私钥："+RSAPrivateKey);
        String str = JSONUtil.toJsonStr(entrys);
        System.out.println("str:"+str);
        String data = encryptByPublicKey(str,hepublicKey);
        //String data = testEncrypt(privateKey,str);
        System.out.println("公钥加密后："+data);
        String requestNo = getId();
        System.out.println("requestNo:"+requestNo);
        String requestTime = String.valueOf((System.currentTimeMillis()));
        System.out.println("requestTime:"+requestTime);


        List<String> params = new ArrayList();
        params.add(data);
        params.add(requestNo);
        params.add(requestTime);
        String sign = getSignByParams(params);
        System.out.println("生成签名：" + sign);
        //访问接口传参
        JSONObject param = new JSONObject();
        param.put("partnerCode","SDRY");
        param.put("requestNo",requestNo);
        param.put("requestTime",requestTime);
        param.put("sign",sign);
        param.put("data",data);
        //定义接收数据
        JSONObject result = new JSONObject();

        String url = "https://pjuat.chinalin.com/bmtp-api/partner/order";

        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        //请求参数转JOSN字符串
        StringEntity entity = new StringEntity(param.toString(), "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        JSONObject res = new JSONObject();
        try {
            HttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = JSON.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                System.out.println("返回值："+result);
                Iterator iter2 = result.entrySet().iterator();
                while (iter2.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter2.next();
                    if(entry.getKey().toString()=="data"){
                        System.out.println(entry.getKey().toString());
                        String xujiemi = entry.getValue().toString();
                        //System.out.println(xujiemi);
                        //String res = testDecrypt(hepublicKey, xujiemi);
                        res = JSONObject.parseObject(decryptByPrivateKey(xujiemi,privateKey));
                        System.out.println("解密后"+res);
                    }

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            result.put("error", "连接错误！");
        }
        return res;
    }
    @RequestMapping(value = "/backData/notify")
    public Result returnAll(@RequestBody TransferDiscountOrderRequest transferDiscountOrderRequest) throws Exception {
        String res = decryptByPrivateKey(transferDiscountOrderRequest.getData(),privateKey);
        System.out.println("解密响应后"+res);
        //String res = "{\"serial_no\":\"qw12342\",\"order_status\":\"0\",\"reason\":\"驳回测试\"}";
        JSONObject jsonObject = JSON.parseObject(res);
        Notice notice = new Notice();
        notice.setSerial_no(jsonObject.getString("serial_no"));
        notice.setOrder_status(jsonObject.getString("order_status"));
        notice.setReason(jsonObject.getString("reason"));

        noticeService.saveOrUpdate(notice);


        return Result.success();
    }


    public static String getId(){
        return UUID.randomUUID().toString().replace("-", "");
    }


    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        partnerOrderDetailDTOS = CollUtil.newArrayList();

        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        for (List<Object> row : list) {
            if(row.get(1).toString()!=""){
                PartnerOrderDetailDTO partnerOrderDetailDTO = new PartnerOrderDetailDTO();
                partnerOrderDetailDTO.setDraft_no(row.get(1).toString());
                partnerOrderDetailDTO.setDraft_no_interval("");
                partnerOrderDetailDTO.setDraft_amount(row.get(6).toString());
                partnerOrderDetailDTO.setEnd_date(row.get(8).toString());
                partnerOrderDetailDTO.setCd_bank_no(row.get(3).toString());
                partnerOrderDetailDTO.setTie_bank_no("");

                partnerOrderDetailDTO.setTie_bank_name(row.get(5).toString());

                partnerOrderDetailDTOS.add(partnerOrderDetailDTO);
            }

        }
//        jsonStr = JSONArray.fromObject(partnerOrderDetailDTOS).toString();
//        System.out.println("jsonStr--------------------------------------------------"+jsonStr);

        return Result.success(true);
    }

    @AuthAccess
    @GetMapping("/{id}")
    public Entry findOne(@PathVariable String id) {
        QueryWrapper<Entry> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("serial_no", id);
        System.out.println("--------------------------明细存储测试----------------------------");

        System.out.println(entryService.getOne(queryWrapper));

        return entryService.getOne(queryWrapper);
    }



}

