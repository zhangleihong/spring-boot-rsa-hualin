package com.zzq.controller;

import com.zzq.config.rsa.RSAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author maxwell
 * @Title: zhangzq
 * @ProjectName spring-boot-rsa
 * @Description: 登陆控制器
 * @date 2019/8/16 14:44
 * @email: bestzijia@gmail.com
 * @github: https://github.com/winterme/
 * @csdn: https://blog.csdn.net/yali_aini
 */
@RestController
public class LoginController {

    @Autowired
    private RSAService rsaService;

    @RequestMapping("/login")
    public String login(String password , String useranme){

        if( rsaService.decodeRsa(password).equals("pkusoft") ){
            return "认证成功！";
        }else{
            return "认证失败！";
        }

    }

}
