package security;

import java.io.FileInputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import jodd.util.Base64;

public class KeyGenerater {

	private byte[] priKey;

	private byte[] pubKey;

	private static final String CERT_TYPE = "X.509";

	public void generate() {
		try {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");

			SecureRandom secrand = new SecureRandom();
			secrand.setSeed("test".getBytes());
			keygen.initialize(1024, secrand);
			// KeyPair pair = keygen.generateKeyPair();
			KeyPair pair = keygen.genKeyPair();
			PublicKey pubkey = pair.getPublic();
			PrivateKey prikey = pair.getPrivate();
			pubKey = jodd.util.Base64.encodeToByte(pubkey.getEncoded());
			priKey = Base64.encodeToByte(prikey.getEncoded());
			// pubKey = Base64.en(pubkey.getEncoded());
			// priKey = Base64.encode(prikey.getEncoded());
			System.out.println("pubKey=:" + new String(pubKey));
			System.out.println("priKey=:" + new String(priKey));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void generateKeyByCert(String keyPath, String password)
			throws Exception {
		KeyStore ks = getKeyStore(keyPath, password);
		X509Certificate certfile = (X509Certificate) ks.getCertificate("RSA");

	}

	/**
	 * 获得Certificate
	 * 
	 * @param certificatePath
	 *            证书路径
	 * @return Certificate 证书
	 * @throws Exception
	 */
	public static Certificate getCertificate(String certificatePath)
			throws Exception {
		// 实例化证书工厂
		CertificateFactory certificateFactory = CertificateFactory
				.getInstance("X.509");
		// 取得证书文件流
		FileInputStream in = new FileInputStream(certificatePath);
		// 生成证书
		Certificate certificate = certificateFactory.generateCertificate(in);
		// 关闭证书文件流
		in.close();
		return certificate;
	}
	/**
	* 获得Certificate
	* @param keyStorePath 密钥库路径
	* @param alias 别名
	* @param password 密码
	* @return Certificate 证书
	* @throws Exception
	*/
	public static Certificate getCertificate(String keyStorePath,String alias,String password) throws Exception{
		KeyStore ks = getKeyStore(keyStorePath, password);
		return ks.getCertificate(alias);
	}
	/**
	 * 由KeyStore获得私钥
	 * 
	 * @param keyStorePath
	 *            密钥库路径
	 * @param alias
	 *            别名
	 * @param password
	 *            密码
	 * @return PrivateKey 私钥
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String keyStorePath, String alias,
			String password) throws Exception {
		KeyStore ks = getKeyStore(keyStorePath, password);
		Enumeration<String> keyAalias = ks.aliases();
		
		while(keyAalias.hasMoreElements()){//如果不知道别名时，需要keystore中遍历
			alias  = keyAalias.nextElement();
		}
		return (PrivateKey) ks.getKey(alias, password.toCharArray());
	}

	/**
	 * 由Certificate获得公钥
	 * 
	 * @param certificatePath
	 *            证书路径
	 * @return PublicKey 公钥
	 * @throws Exception
	 */
	public static PublicKey getPublicKeyByCert(String certificatePath)
			throws Exception {
		Certificate certificate = getCertificate(certificatePath);
		return certificate.getPublicKey();
		
	}
	/**
	* 获得KeyStore
	* @param keyStorePath 密钥库路径
	* @param password 密码
	* @return KeyStore 密钥库
	* @throws Exception
	*/
	public static KeyStore getKeyStore(String keyPath,String password)throws Exception{
		//实例化密钥库
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		//获取密钥库文件流
		FileInputStream fis = new FileInputStream(keyPath);
		//加载密钥库
		ks.load(fis, password.toCharArray());
		fis.close();
		return ks;
	}

	public byte[] getPriKey() {

		return priKey;

	}

	public byte[] getPubKey() {

		return pubKey;

	}

	public static void main(String[] args) {
		KeyGenerater kGenerater = new KeyGenerater();
		kGenerater.generate();
	}
}
