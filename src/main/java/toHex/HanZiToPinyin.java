package toHex;

import java.util.HashSet;
import java.util.Set;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import org.apache.log4j.Logger;

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
		String[] tmp = new String[srcChar.length];
		try {
			for (char c : srcChar) {
				// 判断是否为汉字
				// logger.info(Character.toString(c));
				if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
					tmp = PinyinHelper.toHanyuPinyinStringArray(c, format);
					dest += tmp[0];
				} else {
					dest += Character.toString(c).toUpperCase();
				}
				/*
				 * if(c>=19968&&c<171941){ tmp =
				 * PinyinHelper.toHanyuPinyinStringArray(c, format); dest +=
				 * tmp[0]; }else{ dest +=Character.toString(c).toUpperCase(); }
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dest;
	}

	public static String getSpellBySplit(String srcStr, String separate) {
	        if (null == srcStr) {
	            return "";
	        }
	        char[] srcChar = srcStr.toCharArray();
	        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
	        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);// 是否大小写
	        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 是否有音调
	        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);//
	        StringBuffer sbf = new StringBuffer();
	        String[] tmp = new String[srcChar.length];
	        try {
	            for (char c : srcChar) {
	                // 判断是否为汉字
	                // logger.info(Character.toString(c));
	                if (Character.toString(c).matches("[\\u4E00-\\u9FBF]+")) {
	                    tmp = PinyinHelper.toHanyuPinyinStringArray(c, format);
	                    sbf.append(tmp[0]);
	                    sbf.append(separate);
	                } else {
	                    sbf.append(Character.toString(c));
	                }
	            }
	        } catch (Exception e) {
	            logger.error(e);
	            e.printStackTrace();
	        }
	        return sbf.toString().toUpperCase();
	    }

	/**
	 * 提取汉字首字母拼音
	 * 
	 * @param str
	 * @return
	 */
	public static String getPinYinHeadChar(String str) {
		String convert = "";
		if (str == null) {
			return convert;
		}
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			// 提取汉字首字母
			String pinyinArray[] = PinyinHelper.toHanyuPinyinStringArray(c);
			if (null != pinyinArray) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += c;
			}
		}
		return convert;
	}
	/**
	 * 获取汉字串拼音，英文字符不变 如：单田芳： dan,chan,shantianfang
	 * 
	 * @param chinese
	 *            汉字串
	 * @return 汉语拼音
	 */
	public static String cn2Spell(String chinese) {
		
		StringBuffer pybf = new StringBuffer();
		char[] arr = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > 128) {
				try {
					String[] arrays = PinyinHelper.toHanyuPinyinStringArray(
							arr[i], defaultFormat);
					if (null != arrays && arrays.length > 0) {
						for (int x = 0; x < arrays.length; x++) {
							pybf.append(arrays[x]);
							if (x != arrays.length - 1) {
								pybf.append(",");
							}
						}
					}
				} catch (Exception  e) {
					e.printStackTrace();
				}
			} else {
				pybf.append(arr[i]);
			}
		}
		return pybf.toString();
	}

	/**
	 * 字符串集合转换字符串(逗号分隔) 单田芳 dantianfang,shantianfang,chantianfang
	 * 
	 * @param stringSet
	 * @return
	 */
	public static String makeStringByStringSet(String src) {
		if(src == null||"".equals(src)) {
			return "";
		}
		Set<String> stringSet = getPinyinnew(src);
		StringBuilder str = new StringBuilder();
		int i = 0;
		for (String s : stringSet) {
			if (i == stringSet.size() - 1) {
				str.append(s);
			} else {
				str.append(s + ",");
			}
			i++;
		}
		if(str.length() > 1000) {
			return str.substring(0, 999).toLowerCase();
		}
		return str.toString().toLowerCase();
	}
	/**
	 * 获取拼音集合
	 * 
	 * @param src
	 * @return Set<String>
	 */
	public static Set<String> getPinyinnew(String src) {
		if (src != null && !src.trim().equalsIgnoreCase("")) {
			char[] srcChar;
			srcChar = src.toCharArray();
			// 汉语拼音格式输出类
			HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();

			// 输出设置，大小写，音标方式等
			hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

			String[][] temp = new String[src.length()][];
			for (int i = 0; i < srcChar.length; i++) {
				char c = srcChar[i];
				// 是中文或者a-z或者A-Z转换拼音(我的需求，是保留中文或者a-z或者A-Z)
				if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {
					try {
						temp[i] = PinyinHelper.toHanyuPinyinStringArray(
								srcChar[i], hanYuPinOutputFormat);
					} catch (Exception  e) {
						e.printStackTrace();
					}
				} else if (((int) c >= 65 && (int) c <= 90)
						|| ((int) c >= 97 && (int) c <= 122)) {
					temp[i] = new String[] { String.valueOf(srcChar[i]) };
				} else {
					temp[i] = new String[] { "" };
				}
			}
			String[] pingyinArray = Exchange(temp);
			Set<String> pinyinSet = new HashSet<String>();
			for (int i = 0; i < pingyinArray.length; i++) {
				pinyinSet.add(pingyinArray[i]);
			}
			return pinyinSet;
		}
		return null;
	}

	/**
	 * 递归
	 * @param strJaggedArray
	 * @return
	 */
	public static String[] Exchange(String[][] strJaggedArray) {
		String[][] temp = DoExchange(strJaggedArray);
		return temp[0];
	}

	/**
	 * 递归
	 * @param strJaggedArray
	 * @return
	 */
	private static String[][] DoExchange(String[][] strJaggedArray) {
		int len = strJaggedArray.length;
		if (len >= 2) {
			int len1 = strJaggedArray[0].length;
			int len2 = strJaggedArray[1].length;
			int newlen = len1 * len2;
			String[] temp = new String[newlen];
			int Index = 0;
			for (int i = 0; i < len1; i++) {
				for (int j = 0; j < len2; j++) {
					temp[Index] = strJaggedArray[0][i] + strJaggedArray[1][j];
					Index++;
				}
			}
			String[][] newArray = new String[len - 1][];
			for (int i = 2; i < len; i++) {
				newArray[i - 1] = strJaggedArray[i];
			}
			newArray[0] = temp;
			return DoExchange(newArray);
		} else {
			return strJaggedArray;
		}
	}

	public static void main(String[] args) {

		logger.info(getSpellBySplit("其他。，阿萨德ｘ．，adasdasd","\n"));
	}

}
