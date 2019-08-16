package com.zzq.config.rsa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maxwell
 * @Title: zhangzq
 * @ProjectName spring-boot-rsa
 * @Description: RSA控制器
 * @date 2019/8/16 15:23
 * @email: bestzijia@gmail.com
 * @github: https://github.com/winterme/
 * @csdn: https://blog.csdn.net/yali_aini
 */
@RestController
public class RSAController {

    @Autowired
    private RSAService rsaService;

    @RequestMapping("/getBase64PublicKey")
    public String getBase64PublicKey(){
        return rsaService.getPublicKey();
    }

}
