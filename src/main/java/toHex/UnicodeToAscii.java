package toHex;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.CharSet;
import org.apache.commons.lang.CharSetUtils;
import org.apache.log4j.Logger;

import sun.io.ByteToCharConverter;
import sun.io.CharToByteConverter;

public class UnicodeToAscii {
	private static Logger logger = Logger.getLogger(UnicodeToAscii.class);

	/**
	 * UnicodeToAscii 构造子注解。
	 */
	public UnicodeToAscii() {
		super();
	}

	/**
	 * 将Ascii转换成中文字符串
	 */
	public static String asciiToChineseString(String s) {
		if (s == null)
			return s;
		char[] orig = s.toCharArray();
		byte[] dest = new byte[orig.length];
		for (int i = 0; i < orig.length; i++) {
			dest[i] = (byte) (orig[i] & 0xFF);
		}
		try {
			/*
			 * ByteToCharConverter toChar = ByteToCharConverter
			 * .getConverter("gb2312"); return new
			 * String(toChar.convertAll(dest));
			 */
			return new String(getChars(dest));
		} catch (Exception e) {
			System.out.println(e);
			return s;
		}
	}

	/**
	 * 将中文字符串转换成Ascii
	 */
	public static String chineseStringToAscii(String s) {
		if (s == null)
			return s;
		try {
			/*
			 * CharToByteConverter toByte = CharToByteConverter
			 * .getConverter("utf-8");
			 */
			// byte[] orig = toByte.convertAll(s.toCharArray());

			byte[] orig = getBytes(s.toCharArray());
			char[] dest = new char[orig.length];
			for (int i = 0; i < orig.length; i++) {
				dest[i] = (char) (orig[i] & 0xFF);
			}

			System.out.println(String.valueOf(dest) + "=");
			return new String(dest);
		} catch (Exception e) {
			System.out.println(e);
			return s;
		}
	}

	/**
	 * 将字符数组转为字节数组
	 * 
	 * @param chars
	 * @return
	 */
	public static byte[] getBytes(char[] chars) {
		Charset cs = Charset.forName("UTF-8");
		CharBuffer cb = CharBuffer.allocate(chars.length);
		cb.put(chars);
		cb.flip();
		ByteBuffer bb = cs.encode(cb);

		return bb.array();
	}

	/**
	 * 将字节数组转为字符数组
	 * 
	 * @param chars
	 * @return
	 */
	public static char[] getChars(byte[] bytes) {
		Charset cs = Charset.forName("UTF-8");
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes);
		bb.flip();
		CharBuffer cb = cs.decode(bb);

		return cb.array();
	}

	/**
	 * 中文转ascii
	 * 
	 * @param s
	 *            要进行转换的字符串
	 * @param bl
	 *            是否进行转换,一个开关控制 , true代表需要转换。
	 * @return 转换后的字符串
	 */

	public static String ChineseStringToAscii(String s, boolean bl) {
		if (!bl)
			return s;
		else
			return chineseStringToAscii(s);
	}

	/**
	 * ascii转字符串
	 * 
	 * @param s
	 * @param bl
	 * @return
	 */
	public static String AsciiToChineseString(String s, boolean bl) {
		if (!bl)
			return s;
		else
			return asciiToChineseString(s);
	}

	/**
	 * 根据输入的源串(中文或中西文混合)返回其拼音首字母,以小写返回,如果首字符非拼音字母,则统一返回*号
	 * 
	 * @param str
	 *            源串(中文或中西文混合)
	 * @return 返回str的拼音首字母,以小写返回,如果首字符非拼音字母,则统一返回*号
	 */
	public static String getFirstCharOfString(String str) {
		String firstChar = "*";

		if (str == null || str.length() <= 0)
			return firstChar;

		try {
			byte firstCharBytes[] = new byte[2];
			int gbcode;

			firstCharBytes[0] = str.getBytes("gb2312")[0];
			gbcode = firstCharBytes[0] & 0x000000ff;
			if (str.length() > 1 || gbcode >= 0xb0) {
				firstCharBytes[1] = str.getBytes("gb2312")[1];
				gbcode = (firstCharBytes[0] & 0x000000ff) * 0x100
						+ (firstCharBytes[1] & 0x000000ff);
			}

			if (gbcode >= 0xb0a1 && gbcode <= 0xb0c4)
				firstChar = "a";
			else if (gbcode >= 0xb0c5 && gbcode <= 0xb2c0)
				firstChar = "b";
			else if (gbcode >= 0xb2c1 && gbcode <= 0xb4ed)
				firstChar = "c";
			else if (gbcode >= 0xb4ee && gbcode <= 0xb6e9)
				firstChar = "d";
			else if (gbcode >= 0xb6ea && gbcode <= 0xb7a1)
				firstChar = "e";
			else if (gbcode >= 0xb7a2 && gbcode <= 0xb8c0)
				firstChar = "f";
			else if (gbcode >= 0xb8c1 && gbcode <= 0xb9fd)
				firstChar = "g";
			else if (gbcode >= 0xb9fe && gbcode <= 0xbbf6)
				firstChar = "h";
			else if (gbcode >= 0xbbf7 && gbcode <= 0xbfa5)
				firstChar = "j";
			else if (gbcode >= 0xbfa6 && gbcode <= 0xc0ab)
				firstChar = "k";
			else if (gbcode >= 0xc0ac && gbcode <= 0xc2e7)
				firstChar = "l";
			else if (gbcode >= 0xc2e8 && gbcode <= 0xc4c2)
				firstChar = "m";
			else if (gbcode >= 0xc4c3 && gbcode <= 0xc5b5)
				firstChar = "n";
			else if (gbcode >= 0xc5b6 && gbcode <= 0xc5bd)
				firstChar = "o";
			else if (gbcode >= 0xc5be && gbcode <= 0xc6d9)
				firstChar = "p";
			else if (gbcode >= 0xc6da && gbcode <= 0xc8ba)
				firstChar = "q";
			else if (gbcode >= 0xc8bb && gbcode <= 0xc8f5)
				firstChar = "r";
			else if (gbcode >= 0xc8f6 && gbcode <= 0xcbf9)
				firstChar = "s";
			else if (gbcode >= 0xcbfa && gbcode <= 0xcdd9)
				firstChar = "t";
			else if (gbcode >= 0xcdda && gbcode <= 0xcef3)
				firstChar = "w";
			else if (gbcode >= 0xcef4 && gbcode <= 0xd1b8)
				firstChar = "x";
			else if (gbcode >= 0xd1b9 && gbcode <= 0xd4d0)
				firstChar = "y";
			else if (gbcode >= 0xd4d1 && gbcode <= 0xd7f9)
				firstChar = "z";
			else
				gbcode = firstCharBytes[0];

			if (gbcode >= 'A' && gbcode <= 'Z')
				gbcode += 32;
			if (gbcode >= 'a' && gbcode <= 'z')
				firstChar = String.valueOf((char) gbcode);
		} catch (Exception e) {
			System.out.println("getFirstCharOfString Exception: "
					+ e.getMessage());
		}

		return firstChar;
	}

	public static String getCnASCII(String cnStr)
			throws UnsupportedEncodingException {
		StringBuffer strBuf = new StringBuffer();
		// 将字符串转换成字节序列
		byte[] bGBK = cnStr.getBytes("utf-8");
		for (int i = 0; i < bGBK.length; i++) {
			System.out.println(Integer.toHexString(bGBK[i] & 0xff));
			// 将每个字符转换成 ASCII 码
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	/**
	 * 半角、全角字符判断
	 * 
	 * @param c
	 *            字符
	 * @return true ：半角； false ：全角
	 */
	public static boolean isDbcCase(char c) {
		// 基本拉丁字母（即键盘上可见的，空格、数字、字母、符号）
		if (c >= 32 && c <= 127) {
			return true;
		}
		// 日文半角片假名和符号
		else if (c >= 65377 && c <= 65439) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串长度取得（区分半角、全角）
	 * 
	 * @param str
	 *            字符串
	 * @return 字符串长度
	 */
	public static int getLength(String str) {
		int len = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (isDbcCase(c)) { // 半角
				len = len + 1;
			} else { // 全角
				len = len + 2;
			}
		}
		return len;
	}

	/**
	 * 把中文字符串转换为十六进制 Unicode 编码字符串 编译后和tools.oschina.net的Native/Ascii一致
	 */
	public static String stringToUnicode(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			 if(ch>=19968&&ch<=171941){//汉字范围 \u4e00-\u9fa5 (中文)
			str += "\\u" + Integer.toHexString(ch);
			// str += Integer.toHexString(ch);
			 }else{
				 str+=s.charAt(i);
			 }
		}
		return str;
	}

	/**
	 * 把十六进制 Unicode 编码字符串转换为中文字符串
	 */
	public static String unicodeToString(String str) {
		/**
		 * [:alnum:] 文字数字字符 [:alpha:] 文字字符 [:digit:] 数字字符 [:graph:]
		 * 非空字符（非空格、控制字符） [:lower:] 小写字符 [:cntrl:] 控制字符 [:print:] 非空字符（包括空格）
		 * [:punct:] 标点符号 [:space:] 所有空白字符（新行，空格，制表符） [:upper:] 大写字符 [:xdigit:]
		 * 十六进制数字（0-9，a-f，A-F）
		 */
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}

	
	/** 
	  * 判断是否为中文字符 
	  * @param c 
	  * @return 
	  */  
	public  boolean isChinese(char c) {  
	     Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);  
	     if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
	            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
	            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A  
	            || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  
	            || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
	            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {  
	        return true;  
	    }  
	    return false;  
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// 看看那句能打印出中文。说明解码编码正确
		/*
		 * System.out.println(0xff); String str = "中文"; System.out.println("1:"
		 * + new String(str.getBytes("GBK"), "ISO8859_1"));
		 * System.out.println("2:" + new String(str.getBytes("GBK"), "utf-8"));
		 * System.out.println("3:" + new String(str.getBytes("GBK"), "GB2312"));
		 * System.out.println("4:" + new String(str.getBytes("GBK"), "GBK"));
		 * System.out.println("5:" + new String(str.getBytes("ISO8859_1"),
		 * "GBK")); System.out.println("6:" + new
		 * String(str.getBytes("ISO8859_1"), "ISO8859_1"));
		 * System.out.println("7:" + new String(str.getBytes("ISO8859_1"),
		 * "GB2312")); System.out.println("8:" + new
		 * String(str.getBytes("ISO8859_1"), "utf-8")); System.out.println("9:"
		 * + new String(str.getBytes("utf-8"), "GBK")); System.out.println("10:"
		 * + new String(str.getBytes("utf-8"), "utf-8"));
		 * System.out.println("11:" + new String(str.getBytes("utf-8"),
		 * "GB2312")); System.out.println("12:" + new
		 * String(str.getBytes("utf-8"), "ISO8859_1")); System.out
		 * .println("13:" + new String(str.getBytes("GB2312"), "GB2312"));
		 * System.out.println("14:" + new String(str.getBytes("GB2312"),
		 * "ISO8859_1")); System.out.println("15:" + new
		 * String(str.getBytes("GB2312"), "utf-8")); System.out.println("16:" +
		 * new String(str.getBytes("GB2312"), "GBK"));
		 * System.out.println(getFirstCharOfString("中"));
		 */
		System.out.println(stringToUnicode("中国adasd"));
		 System.out.println(unicodeToString("\u4e2d\u56fdadasd"));
		String s = "中国";
		char c[] = s.toCharArray();

		for (int i = 0; i < c.length; i++) {
			logger.info(Integer.toBinaryString((int) c[i]));
			logger.info(Integer.toHexString((int) c[i]));
		}
		byte[] b1 = "你好".getBytes("utf-8");

		byte[] b2 = new String(b1, 0, b1.length, "UTF-8").getBytes("GBK");

		System.out.println(new String(b1, 0, b1.length));

		System.out.println(new String(b2, 0, b2.length));

		logger.info(new String(s.getBytes("utf-8"), "gbk"));
		// String utf8String = IOUtils.toString(IOUtils.toInputStream(s,
		// "gbk"));
		// logger.info(utf8String);
		// System.out.println(new String(c));
		logger.info(chineseStringToAscii("中国"));
		 System.out.println(asciiToChineseString(chineseStringToAscii("中国")));
		 System.out.println(getCnASCII("中国"));
	}

}
