package security;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;

import jodd.util.Base64;

public class Signaturer {

	public static byte[] sign(String privateKey, String plainText) {
		try {
			System.out.println("签名前:" + plainText);
			PKCS8EncodedKeySpec priPkcs8 = new PKCS8EncodedKeySpec(
					Base64.decode(privateKey));
			KeyFactory factory;

			factory = KeyFactory.getInstance("RSA");

			PrivateKey priKey = factory.generatePrivate(priPkcs8);

			Signature signer = Signature.getInstance("MD5withRSA");

			signer.initSign(priKey);
			signer.update(plainText.getBytes());
			byte[] befsigned = signer.sign();
			System.out.println("签名后：" + plainText);
			byte[] signed = jodd.util.Base64.encodeToByte(befsigned);

			return signed;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	* 私钥加密
	* @param data 待加密数据
	* @param keyStorePath 密钥库路径
	* @param alias 别名
	* @param password 密码
	* @return byte[] 加密数据
	* @throws Exception
	*/
	public static byte[] encryByPriKey(byte[]data,String keyStorePath,String alias,String password) throws Exception{
		PrivateKey prikey = KeyGenerater.getPrivateKey(keyStorePath, alias, password);
		System.out.println(" encryByPriKey prikey algorithm :"+prikey.getAlgorithm());
		// 对数据加密
		Cipher cipher = Cipher.getInstance(prikey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, prikey);
		return cipher.doFinal(data);
	}
	
	/**
	* 私钥解密
	* @param data 待解密数据
	* @param keyStorePath 密钥库路径
	* @param alias 别名
	* @param password 密码
	* @return byte[] 解密数据
	* @throws Exception
	*/
	public static byte[] decryByPriKey(byte[]data,String keyStorePath,String alias,String password) throws Exception{
		
		PrivateKey prikey = KeyGenerater.getPrivateKey(keyStorePath, alias, password);
		// 对数据加密
		System.out.println("decryByPriKey prikey:"+prikey.getAlgorithm());
		Cipher cipher = Cipher.getInstance(prikey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, prikey);
		return cipher.doFinal(data);
	/*	Cipher cipher = Cipher.getInstance(prikey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, prikey);
		return cipher.doFinal(data);*/
	}
	
	/**
	* 公钥加密
	* @param data 待加密数据
	* @param certificatePath 证书路径
	* @return byte[] 加密数据
	* @throws Exception
	*/
	public static byte[] encryByPubKey(byte[]data,String certificatePath) throws Exception{
		
		PublicKey pubkey = KeyGenerater.getPublicKeyByCert(certificatePath);
		System.out.println("encryByPubKey pubkey:"+pubkey.getAlgorithm());
		// 对数据加密
		Cipher cipher = Cipher.getInstance(pubkey.getAlgorithm());
		
		cipher.init(Cipher.ENCRYPT_MODE, pubkey);
		return cipher.doFinal(data);	
				
	}
	/**
	* 公钥解密
	* @param data 待解密数据
	* @param certificatePath 证书路径
	* @return byte[] 解密数据
	* @throws Exception
	*/
	public static byte[] decryByPubKey(byte[] data,String certificatePath) throws Exception{
		
		PublicKey pubkey = KeyGenerater.getPublicKeyByCert(certificatePath);
		System.out.println("decryByPubKey pubkey:"+pubkey.getAlgorithm());
		Cipher cipher = Cipher.getInstance(pubkey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, pubkey);
		return cipher.doFinal(data);
		
	}
	
	
	/**
	* 签名
	* @param keyStorePath 密钥库路径
	* @param alias 别名
	* @param password 密码
	* @return byte[] 签名
	* @throws Exception
	*/
	public static byte[] sign(byte[] data, String keyStorePath, String alias,
			String password) throws Exception {
		X509Certificate cert = (X509Certificate) KeyGenerater.getCertificate(keyStorePath, alias, password);
		
		Signature sign = Signature.getInstance(cert.getSigAlgName());
		PrivateKey priKey = KeyGenerater.getPrivateKey(keyStorePath, alias, password);
		
		sign.initSign(priKey);
		sign.update(data);
		return sign.sign();
		
	}
	
	
	public static boolean vertify(byte[]data,byte[] signed,String certificatePath)throws Exception{
		X509Certificate cert = (X509Certificate) KeyGenerater.getCertificate(certificatePath);
		Signature sign = Signature.getInstance(cert.getSigAlgName());
		sign.initVerify(cert);
		sign.update(data);
		return sign.verify(signed);
	}
	public static void main(String[] args) {
		String s = "hello world";

		System.out.println(jodd.util.Base64.encodeToString(s));

	}
}
