package security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5 {
	private static Logger LOG = LoggerFactory.getLogger(MD5.class);
	
	public static String md5String(String entrypt){
		MessageDigest mgd;
		try {
			mgd = MessageDigest.getInstance("MD5");
			byte b [] =	mgd.digest(entrypt.getBytes());
				
			return	parseByte2HexStr(b);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		return null;
	}

	
	public static String string2Hex(String s){
		
		return null;
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
	
	public static void main(String[] args) {
		LOG.info(md5String("中国"));
		LOG.info(md5GBKString("中国"));
	}
	
	public static String md5GBKString(String str){
		MessageDigest mgd;
		try {
			byte[] b = str.getBytes("GBK");
			mgd = MessageDigest.getInstance("MD5");
			byte returnb [] =	mgd.digest(b);
				
			return	parseByte2HexStr(returnb);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
