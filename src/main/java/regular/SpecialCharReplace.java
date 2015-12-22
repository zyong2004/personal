package regular;

public class SpecialCharReplace {
	 public static void main(String[] args) {  
	        String str = "$ \\";  
	        /* 
	         * string.replaceAll()中的特殊字符 $ 与 \  
	         *  
	         * 由于 $ 字符在作为替换内容时，是一个特殊字符，指反向引用前面的分组内容，所以把 
	         * 某字符替换成 $ 字符时，因该在前面加上转义字符 \。 
	         * \ 字符就不用说了，本身就是转义字符，但为什么在作为替换内容时要使用四个 \ 字符 
	         * ，这里又不是用在正则表达式里？这就是因为 \ 字符在作为替换内容里也是一个特殊字 
	         * 符，它用来将前面讲的 $ 字符进行转换的，所以也为特殊字符。以下是replaceAll的 
	         * 源码片断，从源码就可以看出 \$ 是两个特殊字符 
	         *  
	         * if (nextChar == '\\') { 
	         *      cursor++; 
	         *      nextChar = replacement.charAt(cursor); 
	         *      result.append(nextChar); 
	         *      cursor++; 
	         * } else if (nextChar == '$') { 
	         *      // Skip past $ 
	         *      cursor++; 
	         *      ... 
	         * }else { 
	         *      result.append(nextChar); 
	         *      cursor++; 
	         * } 
	         */  
	        System.out.println(str.replaceAll("\\$(\\W)\\\\", "\\\\$1\\$"));// \ $  
	    }  
}
