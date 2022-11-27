package com.zzq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RSAController {

    @Autowired
    private RSAService rsaService;

    @RequestMapping("/getBase64PublicKey")
    public String getBase64PublicKey(){
        return rsaService.getPublicKey();
    }

}
