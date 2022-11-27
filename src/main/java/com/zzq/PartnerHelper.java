package com.zzq;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工具类，定向报单相关业务，加密，签名等
 * @author lq
 * @date 2022/7/8 15:18
 */
public class PartnerHelper {

	private static Logger LOGGER = LoggerFactory.getLogger(PartnerHelper.class);
	/**
	 * 加密方式
	 */
	public static final String KEY_ALGORITHM = "RSA";
	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
	/**
	 * 公钥算法
	 */
	public static final String PUBLIC_KEY = "RSAPublicKey";
	/**
	 * 私钥算法
	 */
	public static final String PRIVATE_KEY = "RSAPrivateKey";

	/**
	 * 密钥长度
	 */
	private static final int KEYSIZE = 1024;

	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	private static final String SHA256 = "SHA-256";

	public static String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex.toUpperCase());
		}
		return sign.toString();
	}

	/**
	 * 对输入字符串进行sha256散列.
	 *
	 * @throws NoSuchAlgorithmException
	 */
	public static String sha256(byte[] input) throws NoSuchAlgorithmException {
		return digest(input, SHA256, null, 1);
	}

	public static String sha256(byte[] input, byte[] salt) throws NoSuchAlgorithmException {
		return digest(input, SHA256, salt, 1);
	}

	public static String sha256(byte[] input, byte[] salt, int iterations) throws NoSuchAlgorithmException {
		return digest(input, SHA256, salt, iterations);
	}

	/**
	 * 对字符串进行散列, 支持md5与sha1算法.
	 *
	 * @throws NoSuchAlgorithmException
	 */
	private static String digest(byte[] input, String algorithm, byte[] salt, int iterations)
			throws NoSuchAlgorithmException {

		MessageDigest digest = MessageDigest.getInstance(algorithm);

		if (salt != null) {
			digest.update(salt);
		}

		byte[] result = digest.digest(input);

		for (int i = 1; i < iterations; i++) {
			digest.reset();
			result = digest.digest(result);
		}
		return formatString(byte2hex(result));

	}

	private static String formatString(String sourceStr) {
		if (sourceStr == null) {
			return null;
		}
		return sourceStr.replaceAll("\\r", "").replaceAll("\\n", "");
	}


	public static byte[] decryptBASE64(String content) {
		return Base64.decodeBase64(content);
	}

	public static String encryptBASE64(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	/**
	 * 用私钥对信息生成数字签名
	 *
	 * @param data       待签名数据
	 * @param privateKey 私钥
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		// 解密由base64编码的私钥
		byte[] keyBytes = decryptBASE64(privateKey);
		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 取私钥匙对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);

		return encryptBASE64(signature.sign());
	}

	public static String sign(String str, String privateKey) throws Exception {
		return sign(str.getBytes(StandardCharsets.UTF_8), privateKey);
	}

	/**
	 * 校验数字签名
	 *
	 * @param data      加密数据
	 * @param publicKey 公钥
	 * @param sign      数字签名
	 * @return 校验成功返回true，失败返回false
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		// 解密由base64编码的公钥
		byte[] keyBytes = decryptBASE64(publicKey);
		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);
		// 验证签名是否正常
		return signature.verify(decryptBASE64(sign));
	}

	public static boolean verify(String str, String publicKey, String sign) throws Exception {
		return verify(str.getBytes(StandardCharsets.UTF_8), publicKey, sign);
	}

	/**
	 * 解密<br>
	 * 私钥解密
	 *
	 * @param data 加密数据
	 * @param key  私钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(key);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		// 解密时超过字节报错。为此采用分段解密的办法来解密
		byte[] res = null;
		for (int i = 0; i < data.length; i += MAX_DECRYPT_BLOCK) {
			byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + MAX_DECRYPT_BLOCK));
			res = ArrayUtils.addAll(res, doFinal);
		}
		return res;
	}

	/**
	 * 解密 <br>
	 * 用私钥解密
	 *
	 * @param data 加密数据
	 * @param key  私钥
	 * @return 消息
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(String data, String key) throws Exception {
		return new String(decryptByPrivateKey(decryptBASE64(data), key), StandardCharsets.UTF_8);
	}


	/**
	 * 加密<br>
	 * 用公钥加密
	 *
	 * @param data 待加密消息
	 * @param key  公钥
	 * @return 密文
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String data, String key) throws Exception {
		// 对公钥解密
		byte[] keyBytes = decryptBASE64(key);
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] dataBytes = data.getBytes("UTF-8");
		// 加密时超过117字节就报错。为此采用分段加密的办法来加密
		byte[] enBytes = null;
		for (int i = 0; i < dataBytes.length; i += MAX_ENCRYPT_BLOCK) {
			// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
			byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(dataBytes, i, i + MAX_ENCRYPT_BLOCK));
			enBytes = ArrayUtils.addAll(enBytes, doFinal);
		}
		return encryptBASE64(enBytes);
	}

	public static HashMap<String, String> initKey() {
		HashMap<String, String> keyMap = new HashMap<String, String>();
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(KEYSIZE);//生成大小 1024
			KeyPair keyPair = keyPairGen.generateKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();//获取公钥
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();//获取私钥
			keyMap.put(PUBLIC_KEY, encryptBASE64(publicKey.getEncoded()));//获取公钥Base64编码
			keyMap.put(PRIVATE_KEY, encryptBASE64(privateKey.getEncoded()));//获取密钥Base64编码
		} catch (Exception e) {
			LOGGER.error("getRandomKey error:", e);
		}
		return keyMap;
	}


	/**
	 * @param params
	 * @desc 所有非空请求参数按参数值ASCII码字典序排序, 将排序后的所有参数字符串拼接成一个字符串str, 对str进行SHA256运算，得到sign
	 * @return
	 * @throws Exception
	 */
	public static String getSignByParams(List<String> params) throws Exception {
		if (CollectionUtils.isNotEmpty(params)) {
			Collections.sort(params);
			String sortedStr = params.stream().collect(Collectors.joining());
			String sign = digest(sortedStr.getBytes(StandardCharsets.UTF_8));
			return sign.toUpperCase();
		}
		return "";
	}

	private static String digest(byte[] contents) throws Exception {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		byte[] digestbyte = messageDigest.digest(contents);
		return new String(Hex.encodeHex(digestbyte));
	}


}
