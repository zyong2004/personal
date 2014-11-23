package toHex;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.junit.Test;

import junit.framework.TestCase;


public class CharUtil extends TestCase{

	private static final Logger logger = Logger.getLogger(CharUtil.class);
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}

	public CharUtil() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String [] strs = new String[]{"www.baidu.com", "!@#$%^&*()_+{}[]|\"'?/:;<>,.","！￥……（）——：；“”‘’《》，。？、", "不要啊", "やめて", "韩佳人", "한가인"};
		for (String s1 : strs) {
			logger.info("测试字符串："+s1);
			logger.info("正则判断："+isChineseStr(s1)+"--"+isChineseByUnicode(s1));

			logger.info("Unicode 判断结果："+isChinese(s1));
			char [] ch = s1.toCharArray();
			for (char c : ch) {
				logger.info(c+"--"+(isChinese(c)?"是":"否"));
			}
		}
	
	}
	@Test
	public void test(){
		String s1 ="www.baidu.com";
		System.out.println("测试字符串："+s1);
		System.out.println("正则判断："+isChineseStr(s1)+"--"+isChineseByUnicode(s1));

		System.out.println("Unicode 判断结果："+isChinese(s1));
		char [] ch = s1.toCharArray();
		for (char c : ch) {
			System.out.println(c+"--"+(isChinese(c)?"是":"否"));
		}
	}
	/**
	 * 根据UNICODE判断中文汉字和符号
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	/**
	 * 判断中文汉字和符号
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		if (null == str) {
			return false;
		}
		char[] ch = str.toCharArray();
		for (char c : ch) {
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 正则表达式判断汉字范围 </br>
	 * <em>U+4E00..U+9FA5 CJK Unified Ideographs 1.1</em></br>
	 * <em>U+9FA6..U+9FBB CJK Unified Ideographs 4.1</em>
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChineseStr(String str) {
		if (null == str) {
			return false;
		}
		Pattern p = Pattern.compile("[\\u4E00-\\u9FBF]");
		return p.matcher(str).find();
	}
	
	/**
	 * 只能判断部分CJK字符  （CJK统一汉字）
	 * @param str
	 * @return
	 */
	public static boolean isChineseByUnicode(String str)
	{
		if(null ==str){
			return false;
		}
		//大小写不同：\\p表示包含 \\P 表示不包含在内
		//\\p{Cn}表示Unicode中未被定义字符的编码，\\P{Cn}表示Unicode中已经被定义的编码
		String reg ="\\p{InCJK Unified Ideographs}&&\\P{Cn}}";
		Pattern p = Pattern.compile(reg);
		return p.matcher(str).find();
	}	
}
