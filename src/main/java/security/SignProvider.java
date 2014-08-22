package security;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

import jodd.util.Base64;

public class SignProvider {

	private SignProvider (){}

	public static boolean veritfy(String pubKey,String plainText,byte[] signText){
		try {
			
			  // 解密由base64编码的公钥,并构造X509EncodedKeySpec对象      
		X509EncodedKeySpec bobPubkey = new X509EncodedKeySpec(Base64.decode(pubKey));
		// RSA对称加密算法   	
		KeyFactory factory = KeyFactory.getInstance("RSA");
		// 取公钥匙对象   
		PublicKey key = factory.generatePublic(bobPubkey);
		   // 解密由base64编码的数字签名   
		byte [] signed = Base64.decode(signText);
		Signature sign = Signature.getInstance("MD5withRSA");
		sign.initVerify(key);
		sign.update(plainText.getBytes());
		
		if(sign.verify(signed)){
			return true;
		}else{
			return false;
		}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

}
