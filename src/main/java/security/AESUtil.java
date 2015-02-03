package security;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import toHex.StringToHex;

/**
 * AES加密解密
 * 
 * @author zhangyong
 * 
 */
public class AESUtil {

	public static void main(String[] args) {
		String bef_str = "中号ask大家";
		String hx ="BB01CF7070A8DF744881E350B3CDF2FE";
		
		String password = "test";
		byte[] aft = entrypt(bef_str.getBytes(), password);
		System.out.println(aft);
		StringToHex.printHexString(aft);
		System.out.println();
		String hexS = StringToHex.bytesToHexString(aft);
		System.out.println(hexS.toUpperCase());
		byte[] aft_ = StringToHex.hexStringToBytes(hexS);
		StringToHex.printHexString(aft_);
		System.out.println();
		System.out.println(aft_);
		System.out.println(aft.equals(aft_));
		System.out.println(decrypt(aft_,password));
		/**
		 * [B@1ff5ea7
BB01CF7070A8DF744881E350B3CDF2FE
BB01CF7070A8DF744881E350B3CDF2FE
BB01CF7070A8DF744881E350B3CDF2FE
[B@1813fac
		 */
		Long ss = null;
		System.out.println(Long.valueOf(1) +ss );
	}

	public static byte[] entrypt(byte[] content, String password) {
		try {

			SecretKey secretKey = getKey(password);
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(content);
			String aft_aes = parseByte2HexStr(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decrypt(byte[] atf_aes, String password) {
		try {
//			byte[] content = parseHexStr2Byte(aft_aes);
			SecretKey secretKey = getKey(password);
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(atf_aes);
			String bef_aes = new String(result);
			return bef_aes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static SecretKey getKey(String strKey) {
		try {

			KeyGenerator generator = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(strKey.getBytes());

			generator.init(128, secureRandom);

			return generator.generateKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		}
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < result.length; i++) {
			int value = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2));
			result[i] = (byte) value;
		}
		return result;
	}

	public static String parseByte2HexStr(byte[] content) {

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < content.length; i++) {
			String hex = Integer.toHexString(content[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;

			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
}
