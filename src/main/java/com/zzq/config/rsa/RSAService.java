package com.zzq.config.rsa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

/**
 * @author maxwell
 * @Title: zhangzq
 * @ProjectName spring-boot-rsa
 * @Description: RSA服务
 * @date 2019/8/16 15:16
 * @email: bestzijia@gmail.com
 * @github: https://github.com/winterme/
 * @csdn: https://blog.csdn.net/yali_aini
 */
@Service
public class RSAService {

    @Value("${rsa.private}")
    private String privateKey;
    @Value("${rsa.publickey}")
    private String publicKey;

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    /**
     * 解码 rsa 加密的数据
     * @param str
     * @return
     */
    public String decodeRsa(String str){
        String result = "";
        try {
            // 先使用 BASE64 解码
            byte[] decodeBuffer = new BASE64Decoder().decodeBuffer(str);
            PrivateKey prtvateKey = RSAUtils.getPrtvateKey(privateKey);
            result = new String(RSAUtils.decrypt(decodeBuffer, prtvateKey));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
