package com.zzq;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.entity.StringEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//import static com.zzq.FunHttp.getHttpResponse;
import static org.apache.commons.codec.binary.Hex.DEFAULT_CHARSET;

@SpringBootApplication // spring 启动注解
public class Application {

    public static void main(String[] args) {
        SpringApplication.run( Application.class , args );
//        String url = "https://ets-ext.qhhrly.cn";
//        JSONObject params = new JSONObject();
//        params.put("requestNo","32789132397460887492115688467350");
//        params.put("requestTime","1574168484871");
//        params.put("sign","6EF0BC4DBB41759ABD03A9F6ED6278718383C5FD685F111FD870BD34FF5A48EA");
//        params.put("data","GGmqq5467CGGH+paMyJvTZUnO/Op9Oo/2lUts+q6bwh5umaMTVyGxdaVS/FdUwUJZghVKF zWfkkhyn1EnVRpf30SEwKAh7bSCZztKkCd8QbULLGFVy0fjAypqGmD84DnhD78aQALNbDapcuU43fS4LrF JpWHMokN9PsErWfspEQ=");
//        HttpPost httpPost = getHttpPost(url,params);
//        JSONObject response = getHttpResponse(httpPost);
//        System.out.println(response);
    }

//    public static HttpPost getHttpPost(String url, String params) {
//        HttpPost httpPost = getHttpPost(url);
//        if (StringUtils.isNotBlank(params))
//            httpPost.setEntity(new StringEntity(params, DEFAULT_CHARSET.toString()));
//        httpPost.addHeader(HttpClientConstant.ContentType_JSON);
//        return httpPost;
//    }
//    public static HttpGet getHttpGet(String url) {
//        return new HttpGet(url);
//    }

}
