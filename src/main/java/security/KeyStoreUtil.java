package security;

import java.io.FileInputStream;
import java.security.KeyStore;

public class KeyStoreUtil {

	
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
}
