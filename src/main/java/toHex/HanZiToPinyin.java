package toHex;

import org.apache.log4j.Logger;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

public class HanZiToPinyin {

	private static Logger logger = Logger.getLogger(HanZiToPinyin.class);
	/**
	 * :case 输出大小写设置
	 * 
	 * :lower-case 大写输出 :upper-case 小写输出 :tone 音调标记设置
	 * 
	 * :without-tone 不标记音调 :with-tone-mark 直接在输出拼音上标记音调 :with-tone-number 使用数字1
	 * 2 3 4来表示音调 :v-char 拼音ü的显示设置
	 * 
	 * :with-u-and-colon 使用u:来表示 :with-u-unicode 直接表示为ü :with-v 使用字母v来表示
	 * :separator 每个汉字之间的分隔符
	 * 
	 * 若不设置，表示汉字之间的拼音没有间隔
	 * 
	 * 分隔符不仅仅局限于字符，你还可以试试字符串，或是其他别的什么东西。
	 * 
	 * @param srcStr
	 */
	public static String getPinyin(String srcStr) {
		char[] srcChar = srcStr.toCharArray();
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);// 是否大小写
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 是否有音调
		format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);//
		String dest = "";
		String [] tmp  = new String[srcChar.length];
		try {
			for (char c : srcChar) {
				//判断是否为汉字
				logger.info(Character.toString(c));
				if(Character.toString(c).matches("[\\u4E00-\\u9FA5]+")){
					tmp = PinyinHelper.toHanyuPinyinStringArray(c, format);
					dest += tmp[0];
				}else{
					dest +=Character.toString(c).toUpperCase();
				}
				/*if(c>=19968&&c<171941){
					tmp = PinyinHelper.toHanyuPinyinStringArray(c, format);
					dest += tmp[0];
				}else{
					dest +=Character.toString(c).toUpperCase();
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dest;
	}

	
	/**
	 * 提取汉字首字母拼音
	 * @param str
	 * @return
	 */
	public static String getPinYinHeadChar(String str){
		String convert ="";
		if(str==null){
			return convert;
		}
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			//提取汉字首字母
			String pinyinArray[]= PinyinHelper.toHanyuPinyinStringArray(c);
			if(null!=pinyinArray){
				convert+=pinyinArray[0].charAt(0);
			}else{
				convert+=c;
			}
		}
		return convert;
	}
	
	
	public static void main(String[] args) {

		logger.info(getPinyin("其他别的什么东國家啊三分大賽的。，阿萨德ｘ．，"));
	}

}
