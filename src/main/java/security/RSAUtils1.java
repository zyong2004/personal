package security;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;



public class RSAUtils1 {

	
	private static String RSA = "RSA";
	
	private static String privateStr = "";
	
	private static String publicStr = "";

	/**
	 * 随机生成RSA密钥对(默认密钥长度为1024)
	 * 
	 * @return
	 */
	public static KeyPair generateRSAKeyPair() {
		return generateRSAKeyPair(1024);
	}

	/**
	 * 随机生成RSA密钥对
	 * 
	 * @param keyLength
	 *            密钥长度，范围：512～2048<br>
	 *            一般1024
	 * @return
	 */
	public static KeyPair generateRSAKeyPair(int keyLength) {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
			kpg.initialize(keyLength);
			return kpg.genKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 用公钥加密 <br>
	 * 每次加密的字节数，不能超过密钥的长度值减去11
	 * 
	 * @param data
	 *            需加密数据的byte数据
	 * @param pubKey
	 *            公钥
	 * @return 加密后的byte型数据
	 */
	public static byte[] encryptData(byte[] data, PublicKey publicKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA);
			System.out.println(cipher.getProvider());
			// 编码前设定编码方式及密钥
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			// 传入编码数据并返回编码结果
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 用私钥解密
	 * 
	 * @param encryptedData
	 *            经过encryptedData()加密返回的byte数据
	 * @param privateKey
	 *            私钥
	 * @return
	 */
	public static byte[] decryptData(byte[] encryptedData, PrivateKey privateKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(encryptedData);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 通过公钥byte[](publicKey.getEncoded())将公钥还原，适用于RSA算法
	 * 
	 * @param keyBytes
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey getPublicKey(byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 通过私钥byte[]将公钥还原，适用于RSA算法
	 * 
	 * @param keyBytes
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PrivateKey getPrivateKey(byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 使用N、e值还原公钥
	 * 
	 * @param modulus
	 * @param publicExponent
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey getPublicKey(String modulus, String publicExponent)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		BigInteger bigIntModulus = new BigInteger(modulus);
		BigInteger bigIntPrivateExponent = new BigInteger(publicExponent);
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 使用N、d值还原私钥
	 * 
	 * @param modulus
	 * @param privateExponent
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PrivateKey getPrivateKey(String modulus, String privateExponent)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		BigInteger bigIntModulus = new BigInteger(modulus);
		BigInteger bigIntPrivateExponent = new BigInteger(privateExponent);
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
		try {
			Base64Utils mBase64Utils = new Base64Utils();
			byte[] buffer = mBase64Utils.decode(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}

	/**
	 * 从字符串中加载私钥<br>
	 * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
	 * 
	 * @param privateKeyStr
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
		try {
			Base64Utils mBase64Utils = new Base64Utils();
			byte[] buffer = mBase64Utils.decode(privateKeyStr);
			// X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA);
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}

	/**
	 * 从文件中输入流中加载公钥
	 * 
	 * @param in
	 *            公钥输入流
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static PublicKey loadPublicKey(InputStream in) throws Exception {
		try {
			return loadPublicKey(readKey(in));
		} catch (IOException e) {
			throw new Exception("公钥数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥输入流为空");
		}
	}

	/**
	 * 从文件中加载私钥
	 * 
	 * @param keyFileName
	 *            私钥文件名
	 * @return 是否成功
	 * @throws Exception
	 */
	public static PrivateKey loadPrivateKey(InputStream in) throws Exception {
		try {
			return loadPrivateKey(readKey(in));
		} catch (IOException e) {
			throw new Exception("私钥数据读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥输入流为空");
		}
	}

	/**
	 * 读取密钥信息
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private static String readKey(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String readLine = null;
		StringBuilder sb = new StringBuilder();
		while ((readLine = br.readLine()) != null) {
			if (readLine.charAt(0) == '-') {
				continue;
			} else {
				sb.append(readLine);
				sb.append('\r');
			}
		}

		return sb.toString();
	}

	/**
	 * 打印公钥信息
	 * 
	 * @param publicKey
	 */
	public static void printPublicKeyInfo(PublicKey publicKey) {
		RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
		System.out.println("----------RSAPublicKey----------");
		System.out.println("Modulus.length=" + rsaPublicKey.getModulus().bitLength());
		System.out.println("Modulus=" + rsaPublicKey.getModulus().toString());
		System.out.println("PublicExponent.length=" + rsaPublicKey.getPublicExponent().bitLength());
		System.out.println("PublicExponent=" + rsaPublicKey.getPublicExponent().toString());
	}

	public static void printPrivateKeyInfo(PrivateKey privateKey) {
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
		System.out.println("----------RSAPrivateKey ----------");
		System.out.println("Modulus.length=" + rsaPrivateKey.getModulus().bitLength());
		System.out.println("Modulus=" + rsaPrivateKey.getModulus().toString());
		System.out.println("PrivateExponent.length=" + rsaPrivateKey.getPrivateExponent().bitLength());
		System.out.println("PrivatecExponent=" + rsaPrivateKey.getPrivateExponent().toString());

	}

//	/**
//	 * @param model
//	 * @param flag encrypt-加密  decrypt-解密
//	 * @return
//	 * @throws Exception 
//	 */
//	public static Object operateClass(Object model, String flag) throws Exception {
//		Field[] field;
//		// 获取实体类的所有属性，返回Field数组
//		try {
//			field = model.getClass().getDeclaredFields();
//			// 遍历所有属性
//			for (int j = 0; j < field.length; j++) {
//				// 获取属性的名字
//				String name = field[j].getName();
//				// 将属性的首字符大写，方便构造get，set方法
//				name = name.substring(0, 1).toUpperCase() + name.substring(1);
//				// 获取属性的类型
//				String type = field[j].getGenericType().toString();
//				// 如果type是类类型，则前面包含"class "，后面跟类名
//				if (type.equals("class java.lang.String")) {
//					Method m = model.getClass().getMethod("get" + name);
//					Method s = model.getClass().getMethod("set" + name, String.class);
//					if("OrgId".equals(name)){
//						// 调用getter方法获取属性值
//						String value = (String) m.invoke(model);
//						if (value != null) {
//							if ("decrypt".equals(flag)) {
//								value = decryptData(value);
//								Log.i("【解密成功,解密后数据为】:",value.toString());
//							} else {
//								value = encryptData(value);
//							}
//							s.invoke(model, value);
//						} 
//					}
//				}
//			}
//		} catch (Exception e) {
//			Log.e("【加密解密失败】:",e.toString());
//			throw new Exception("加密失败");
//		}
//		return model;
//	}

	/**
	 * 加密
	 * 
	 * @param model
	 * @return
	 */
	public static String encryptData(String param) throws Exception {
		if (publicStr.isEmpty()) {
			getPublicKey();
		}
		PublicKey publicKey = loadPublicKey(publicStr);
		byte[] encryptByte = encryptData(param.getBytes(), publicKey);
		Base64Utils mBase64Utils = new Base64Utils();
		param = mBase64Utils.encode(encryptByte);
		return param;
	}

	/**
	 * 解密
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public static String decryptData( String param) throws Exception {
		if (privateStr.isEmpty()) {
			getPrivateKey();
		}
		
		
		PrivateKey privateKey = RSAUtils1.loadPrivateKey(privateStr);
		Base64Utils mBase64Utils = new Base64Utils();
		byte[] decryptByte = RSAUtils1.decryptData(mBase64Utils.decode(param), privateKey);
		param = new String(decryptByte);
		return param;
	}
	
	
	/**
	 * 获取私钥
	 * 
	 * @throws IOException
	 */
	private static void getPrivateKey() throws IOException {
		File file = new File("D:\\personal\\personal\\src\\main\\resources\\pkcs8_rsa_private_key.pem");
		if (file.exists() || !file.isDirectory()) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String temp = null;
			StringBuffer sb = new StringBuffer();
			temp = br.readLine();
			while (temp != null) {
				sb.append(temp + " ");
				temp = br.readLine();
			}
			privateStr = sb.toString().replace(" ", "").replace("-----BEGINPRIVATEKEY-----", "")
					.replace("-----ENDPRIVATEKEY-----", "");
			br.close();
		}

	}

	/**
	 * 获取私钥
	 * 
	 * @throws IOException
	 */
//	public static void getPrivateKey(String keyFileName, Context context) throws IOException {
//		File file = new File(PropertiesUtil.getInstance().getProperties("privateKey", ""));
//		if (file.exists() || !file.isDirectory()) {
//			BufferedReader br = new BufferedReader(new FileReader(file));
//			String temp = null;
//			StringBuffer sb = new StringBuffer();
//			temp = br.readLine();
//			while (temp != null) {
//				sb.append(temp + " ");
//				temp = br.readLine();
//			}
//			
//			
//			
//			encrypt(String content, String cerFile,Context context);
//					
//			try {
//				privateStr =getKeyFromFile(keyFileName, context);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
//			
//			
//			= sb.toString().replace(" ", "").replace("-----BEGINPRIVATEKEY-----", "")
//					.replace("-----ENDPRIVATEKEY-----", "");
//		}

//	}

//	/**
//	 * 获取公钥
//	 * 
//	 * @throws IOException
//	 */
//	public static void getPublicKey(String keyFileName, Context context) throws IOException {
//		File file = new File(PropertiesUtil.getInstance().getProperties("publicKey", ""));
//		if (file.exists() || !file.isDirectory()) {
//			BufferedReader br = new BufferedReader(new FileReader(file));
//			String temp = null;
//			StringBuffer sb = new StringBuffer();
//			temp = br.readLine();
//			while (temp != null) {
//				sb.append(temp + " ");
//				temp = br.readLine();
//			}
//			try {
//				publicStr =getKeyFromFile(keyFileName, context);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
					
					
//					sb.toString().replace(" ", "").replace("-----BEGINPUBLICKEY-----", "")
//					.replace("-----ENDPUBLICKEY-----", "");
//		}
//	}
	
	
	


	
	/**
	 * 获取公钥
	 * 
	 * @throws IOException
	 */
	private static void getPublicKey() throws IOException {
		File file = new File("D:\\personal\\personal\\src\\main\\resources\\rsa_public_key.pem");
		if (file.exists() && !file.isDirectory()) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String temp = null;
			StringBuffer sb = new StringBuffer();
			temp = br.readLine();
			while (temp != null) {
				sb.append(temp + " ");
				temp = br.readLine();
			}
			publicStr = sb.toString().replace(" ", "").replace("-----BEGINPUBLICKEY-----", "")
					.replace("-----ENDPUBLICKEY-----", "");
			br.close();
		}
	}
	
	public static void main(String[] args) throws Exception {
//		Security.addProvider(new BouncyCastleProvider());
//		Provider p [] = Security.getProviders();
		/*for (Provider provider : p) {
			System.out.println(provider);	
		}*/
		
		String s1 = encryptData("123456");
		System.out.println("s1 ="+s1);
		String s2 = encryptData("123456");
		System.out.println("s2="+s2);
		System.out.println("s1 ="+decryptData(s1));
		System.out.println("s2="+decryptData(s2));
	}
}
