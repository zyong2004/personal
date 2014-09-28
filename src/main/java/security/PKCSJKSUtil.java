package security;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.util.Enumeration;

/**
 * <p>
 * Title: PKCS12与JKS格式证书库转换工具
 * </p>
 * 
 * @author zhangyong
 * 
 */
public class PKCSJKSUtil {

	private static String PKCS12 = "PKCS12";
	private static String JKS = "jks";

	/**
	 * 从PKCS12格式转换为JKS格式
	 * 
	 * @param srcFile
	 *            JKS格式证书库
	 * @param srcPass
	 *            JKS格式证书库密码
	 * @param destFile
	 *            PKCS12格式证书库
	 * @param destPass
	 *            PKCS12格式证书库密码
	 */
	public void PFX2JKS(String srcFile, String srcPass, String destFile,
			String destPass) {

		try {
			KeyStore inputKeystore = KeyStore.getInstance(PKCS12);

			FileInputStream fis = new FileInputStream(srcFile);

			char[] srcPwd = null;
			char[] destPwd = null;
			if (srcPass == null || srcPass.trim().equals("")) {
				srcPwd = null;
			} else {
				srcPwd = srcPass.toCharArray();
			}

			if (destPass == null || destPass.trim().equals("")) {
				destPwd = null;
			} else {
				destPwd = destPass.toCharArray();
			}

			inputKeystore.load(fis, srcPwd);

			fis.close();

			KeyStore outStore = KeyStore.getInstance(JKS);

			outStore.load(null, destPwd);

			Enumeration enms = inputKeystore.aliases();

			while (enms.hasMoreElements()) {
				String alias = (String) enms.nextElement();
				System.out.println("alias=[" + alias + "]");
				if (outStore.containsAlias(alias)) {
					System.out.println(destFile + " already contains " + alias);

					break;
				}
				if (inputKeystore.isCertificateEntry(alias)) {
					System.out.println("importing certificate " + alias);
					outStore.setCertificateEntry(alias,
							inputKeystore.getCertificate(alias));
				}
				if (inputKeystore.isKeyEntry(alias)) {
					Key key = inputKeystore.getKey(alias, srcPwd);
					Certificate[] certs = inputKeystore
							.getCertificateChain(alias);
					outStore.setKeyEntry(alias, key, destPwd, certs);
				}
			}
			FileOutputStream fos = new FileOutputStream(destFile);
			outStore.store(fos, destPwd);

			Enumeration enn = outStore.aliases();
			while (enn.hasMoreElements()) {
				String tmp = (String) enn.nextElement();
				System.out.println(tmp);
				System.out.println(outStore.getCertificate(tmp));
			}
			fos.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 从JKS格式转换为PKCS12格式
	 * 
	 * @param srcFile
	 *            JKS格式证书库
	 * @param srcPass
	 *            JKS格式证书库密码
	 * @param destFile
	 *            PKCS12格式证书库
	 * @param destPass
	 *            PKCS12格式证书库密码
	 */
	public void JKS2PFX(String srcFile, String srcPass, String destFile,
			String destPass) {

		try {
			char[] srcPwd = null;
			char[] destPwd = null;
			if (srcPass == null || srcPass.trim().equals("")) {
				srcPwd = null;
			} else {
				srcPwd = srcPass.toCharArray();
			}

			if (destPass == null || destPass.trim().equals("")) {
				destPwd = null;
			} else {
				destPwd = destPass.toCharArray();
			}
			KeyStore inStore = KeyStore.getInstance(JKS);

			FileInputStream fis = new FileInputStream(srcFile);
			inStore.load(fis, srcPwd);

			KeyStore outStore = KeyStore.getInstance(PKCS12);
			Enumeration enums = inStore.aliases();
			while (enums.hasMoreElements()) {
				String alias = (String) enums.nextElement();
				System.out.println("JKS2PFX =alias=[" + alias + "]");
				outStore.load(null, destPwd);
				if (inStore.isKeyEntry(alias)) {
					Key key = inStore.getKey(alias, srcPwd);
					Certificate[] certs = inStore.getCertificateChain(alias);
					outStore.setKeyEntry(alias, key, destPwd, certs);
				}
				String fName = destFile.substring(0, destFile.indexOf(".pfx"));

				fName += "_" + alias + ".pfx";
				FileOutputStream fos = new FileOutputStream(destFile);
				outStore.store(fos, destPwd);
				fos.close();
				outStore.deleteEntry(alias);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		String pfxsrcFile = "c:/users/zhangyong/icbc_lvmama.pfx"; String
		pfxsrcPass = "12345678"; String jksdestFile =
		"c:/users/zhangyong/11.jks"; String jksdestPass = "12345678"; String
		jkssrcFile = "c:/users/zhangyong/icbc_lvmama.jks"; String jkssrcPass
		= "12345678"; String pfxdestFile = "c:/users/zhangyong/test.pfx";
		String pfxdestPass = "12345678";
		
		
		PKCSJKSUtil c = new PKCSJKSUtil(); 
		c.PFX2JKS(pfxsrcFile,pfxsrcPass, jksdestFile, jksdestPass); 
		//c.JKS2PFX(jkssrcFile,jkssrcPass, pfxdestFile, pfxdestPass);
	/*	String flag = "P2J";
		if (args.length < 5) {
			System.out.println("用法：");
			System.out
					.println("    KeyStoreConv <转换标志> <源证书库文件名> <源证书库密码> <目标证书库文件名> <目标证书库密码>");
			System.out.println("    转换标志: P2J -- 从PKCS12转换为JKS格式");
			System.out.println("             J2P -- 从JKS转换为PKCS12格式");
			System.out
					.println("      注意：　1、如果从JKS转换为PKCS12且源JKS中有多个密钥对或证书，则每个密钥对或证书单独保存为一个文件。");
		} else {
			flag = args[0].toUpperCase();
			if (!(flag.equals("P2J") || flag.equals("J2P")))
				flag = "P2J";

			PKCSJKSUtil c = new PKCSJKSUtil();
			if (flag.equals("P2J")) {
				c.PFX2JKS(args[1], args[2], args[3], args[4]);
			} else {
				c.JKS2PFX(args[1], args[2], args[3], args[4]);
			}
		}*/
	}

}
