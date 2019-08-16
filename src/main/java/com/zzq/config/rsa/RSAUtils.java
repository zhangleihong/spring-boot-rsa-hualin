package com.zzq.config.rsa;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author maxwell
 * @Title: zhangzq
 * @ProjectName spring-boot-rsa
 * @Description: rsa 工具类
 * @date 2019/8/16 14:50
 * @email: bestzijia@gmail.com
 * @github: https://github.com/winterme/
 * @csdn: https://blog.csdn.net/yali_aini
 */
public class RSAUtils {
    /**
     * 指定算法名称
     */
    private static final String ALGORITHM = "RSA";

    /**
     * 密钥长度
     */
    private static final int KEY_SIZE = 2048;

    /**
     * 随机生成密钥对
     * @return 密钥对KeyPair
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        // 获取指定算法的密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        // 初始化密钥对生成器，指定密钥的长度，使用默认的安全随机数源
        keyPairGenerator.initialize(KEY_SIZE);

        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 将公钥私钥进行报错到指定的位置
     * @param key
     * @param file 指定文件，比如说new File("D:/pub.txt")
     * @throws IOException
     */
    public static void saveKeyByBase64(Key key , File file) throws IOException {
        // 获取密钥编码后的数据
        byte[] encoded = key.getEncoded();
        String encode = new BASE64Encoder().encode(encoded);

        if( !file.getParentFile().exists() ){
            file.getParentFile().mkdirs();
        }
        if(!file.exists()){
            file.createNewFile();
        }
        new FileOutputStream(file).write(encode.getBytes());
    }

    /**
     * 根据公钥的base64文件获取公钥对象
     * @param pubkeyBase64
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PublicKey getPublicKey(String pubkeyBase64) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] encPubKey = new BASE64Decoder().decodeBuffer(pubkeyBase64);
        // 创建 已编码的公钥规格
        X509EncodedKeySpec encPubKeySpec = new X509EncodedKeySpec(encPubKey);
        // 获取指定算法的密钥工厂, 根据 已编码的公钥规格, 生成公钥对象
        return KeyFactory.getInstance(ALGORITHM).generatePublic(encPubKeySpec);
    }

    /**
     * 根据密钥的base64文件获取私钥对象
     * @param priKeyBase64
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrtvateKey(String priKeyBase64) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] encPriKey = new BASE64Decoder().decodeBuffer(priKeyBase64);
        // 创建 已编码的私钥规格
        PKCS8EncodedKeySpec encPriKeySpec = new PKCS8EncodedKeySpec(encPriKey);
        // 获取指定算法的密钥工厂, 根据 已编码的私钥规格, 生成私钥对象
        return KeyFactory.getInstance(ALGORITHM).generatePrivate(encPriKeySpec);
    }

    /**
     * 公钥加密数据
     */
    public static byte[] encrypt(byte[] plainData, PublicKey pubKey) throws Exception {
        // 获取指定算法的密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 初始化密码器（公钥加密模型）
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        // 加密数据, 返回加密后的密文
        return cipher.doFinal(plainData);
    }

    /**
     * 私钥解密数据
     */
    public static byte[] decrypt(byte[] cipherData, PrivateKey priKey) throws Exception {
        // 获取指定算法的密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 初始化密码器（私钥解密模型）
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        // 解密数据, 返回解密后的明文
        return cipher.doFinal(cipherData);
    }

    public static void main(String[] args) throws Exception {
        KeyPair keyPair = RSAUtils.generateKeyPair();

        RSAUtils.saveKeyByBase64(keyPair.getPublic(), new File("D:/rsa/pub.1509.txt"));
        RSAUtils.saveKeyByBase64(keyPair.getPrivate(), new File("D:/rsa/pri.1509.txt"));

    }

}
