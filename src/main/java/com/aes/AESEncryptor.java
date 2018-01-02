package com.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密解密器
 * @ClassName: AESEncryptor 
 * @author 麦小兜 
 * @date 2016年12月02日 下午5:37:00 
 *
 */
public abstract class AESEncryptor {
	private static final String VIPARA = "0102030409960708";
	private static final String CODE = "UTF-8";
	private static String codeKey = "qianyino2o201712";
	/**
	 * 对明文进行加密
	 * @param cleartext 需要加密的明文
	 * @throws Exception
	 * @return  返回加密后的密文
	 */
	public static String encrypt(String cleartext) throws Exception {
	    IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());  
	    SecretKeySpec key = new SecretKeySpec(codeKey.getBytes(), "AES");  
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
	    cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);  
	    byte[] encryptedData = cipher.doFinal(cleartext.getBytes(CODE));  
	    return Base64.encode(encryptedData);  
	}
	  
	/**
	 * 解密
	 * @Title: 解密操作 
	 * @Description: 对加密过的密文进行解密
	 * @param encrypted 加密过的密文
	 * @return 成功返回解密后的明文,失败返回null
	 */
	public static String decrypt(String encrypted){
		try {
			byte[] byteMi = Base64.decode(encrypted);
			IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
			SecretKeySpec key = new SecretKeySpec(codeKey.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
			byte[] decryptedData = cipher.doFinal(byteMi);
			return new String(decryptedData,CODE);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(AESEncryptor.encrypt("sa"));
		System.out.println(AESEncryptor.encrypt("Admin81919048"));
		System.out.println("mongodb");
		System.out.println(AESEncryptor.encrypt("mongodb"));
		System.out.println(AESEncryptor.encrypt("admin81919048"));
		System.out.println("mysql");
		System.out.println(AESEncryptor.encrypt("root"));
		System.out.println(AESEncryptor.encrypt("root"));
	}
} 
