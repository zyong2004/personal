package toHex;
/**
 * （针对英文）  加密的时候会用到
 * @author zhangyong
 *
 */
public class StringToHex {
	/* Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。  
	 * @param src byte[] data  
	 * @return hex string  
	 */     
	public static String bytesToHexString(byte[] src){  
	    StringBuilder stringBuilder = new StringBuilder("");  
	    if (src == null || src.length <= 0) {  
	        return null;  
	    }  
	    for (int i = 0; i < src.length; i++) {  
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    return stringBuilder.toString();  
	}  
	/** 
	 * Convert hex string to byte[] 
	 * @param hexString the hex string 
	 * @return byte[] 
	 */  
	public static byte[] hexStringToBytes(String hexString) {  
	    if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        //System.out.println(charToByte(hexChars[pos])<<4);
	        //System.out.println(charToByte(hexChars[pos + 1]));
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	}  
	/** 
	 * Convert char to byte 
	 * @param c char 
	 * @return byte 
	 */  
	 private static byte charToByte(char c) {  
		 int index = "0123456789ABCDEF".indexOf(c);
		 if(index==-1){
			 index = "0123456789abcdef".indexOf(c);  
		 }
	    return (byte)index;  
	}  
	 
	 
	//将指定byte数组以16进制的形式打印到控制台  
	 public static void printHexString( byte[] b) {    
	    for (int i = 0; i < b.length; i++) {   
	      String hex = Integer.toHexString(b[i] & 0xFF);   
	      if (hex.length() == 1) {   
	        hex = '0' + hex;   
	      }   
	      System.out.print(hex.toUpperCase() );   
	    }   
	   
	 }  
	    /**  
	     * 字符串转换成十六进制字符串 
	     * @param String str 待转换的ASCII字符串 
	     * @return String 每个Byte之间空格分隔，如: [61 6C 6B] 
	     */    
	    public static String str2HexStr(String str)  
	    {    
	  
	        char[] chars = "0123456789ABCDEF".toCharArray();    
	        StringBuilder sb = new StringBuilder("");  
	        byte[] bs = str.getBytes();    
	        int bit;    
	          
	        for (int i = 0; i < bs.length; i++)  
	        {    
	            bit = (bs[i] & 0x0f0) >> 4;    
	            sb.append(chars[bit]);    
	            bit = bs[i] & 0x0f;    
	            sb.append(chars[bit]);  
	            sb.append(' ');  
	        }    
	        return sb.toString().trim();    
	    }  
	    /**  
	     * 十六进制转换字符串 
	     * @param String str Byte字符串(Byte之间无分隔符 如:[616C6B]) 
	     * @return String 对应的字符串 
	     */    
	    public static String hexStr2Str(String hexStr)  
	    {    
	        String str = "0123456789ABCDEF";    
	        char[] hexs = hexStr.toCharArray();    
	        byte[] bytes = new byte[hexStr.length() / 2];    
	        int n;    
	  
	        for (int i = 0; i < bytes.length; i++)  
	        {    
	            n = str.indexOf(hexs[2 * i]) * 16;    
	            n += str.indexOf(hexs[2 * i + 1]);    
	            bytes[i] = (byte) (n & 0xff);    
	        }    
	        return new String(bytes);    
	    }  
	 
	 public static void main(String[] args) {
		byte [] b = hexStringToBytes("E58898E5BEB7E58D8E");
		//System.out.println(8>>3);
		printHexString(b);
		
		
		System.out.println(Integer.toHexString(1112));
		System.out.println(0x0f0+"=="+0x0f);
		System.out.println(str2HexStr("this is a example1112"));
		System.out.println(hexStr2Str("E58898E5BEB7E58D8E"));
	}
}
