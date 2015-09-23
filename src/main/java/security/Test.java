package security; 

import java.security.Provider;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import toHex.StringToHex;

public class Test {

	public static void main(String[] args) throws Exception {
		String regex = "\\$\\{(.+?)\\}";  
		String value ="123123${name} ${s}";
		String template ="账户充值${cash}元，已经成功，祝您购物愉快。客服电话：400-666-6699。【驴妈妈】";
		Map map = new HashMap();
		map.put("name","$100");
		
		System.out.println(composeMessage(value,map));
		Pattern pattern = Pattern.compile(regex);   
		Matcher matcher = pattern.matcher(value); 
		System.out.println(matcher.find());
		System.out.println("\\$"+"======"+"\\\\\\$");
		value = value.replaceAll("\\$", "\\\\\\$");	//前两个对3-4转义，5对6转义
		System.out.println(value);
	//	testCert1();
	}
	
	public static void test1(){
		String signText ="aGVsbG8gd29ybGQ=";
		KeyGenerater keygen = new KeyGenerater();
		keygen.generate();
		System.out.println(certPath);
		byte [] prikey = keygen.getPriKey();
		byte[]	pubkey = keygen.getPubKey();
		String pubkeys = new String(pubkey);
		String prikeys = new String(prikey);
		System.out.println("pubKey=:"+pubkeys);
		System.out.println("priKey=:"+prikeys);
		String s  ="helloword";
		String privateKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDt5C+wWrO6OExoHA7xF187sO3FnjSxylbfnd22vbXBhNh5AMGAnAhcvleUoJnirv1s3CpbEIKhLqMW20dr22UHr4F046fOinCRIjuZSTMU/3XXFwVkNW99GD6cFqxHOJAqQ/QH360sHdgPrG9SpYHHGcbACFvkVtqgZMPRYhsYBQIDAQAB";
		
		byte [] signed  =	Signaturer.sign(prikeys, s);
		boolean  b = SignProvider.veritfy(pubkeys,s, signed);
		System.out.println(b);
	/*	for (Provider p :Security.getProviders()) {
		    System.out.println("Provider: " + p.getName());      
		    for (Provider.Service service : p.getServices()){      
		    System.out.println("  Algorithm: " + service.getAlgorithm());     
		    }    
		    System.out.println("\n"); 
		}*/
		String ss= "a";
		System.out.println(0xff+"=="+0x0f);
		System.out.println(Integer.toHexString(16));
		System.out.println(Integer.valueOf("3e8", 16));
	}
static	String certPath = "C:\\Documents and Settings\\zhangyong\\zlex.cer";
static	String password = "123456";
static	String alias = "www.zlex.org";
static	String keyStorePath =  "C:\\Documents and Settings\\zhangyong\\zlex.keystore";
	
	/**
	 * 公钥加密，私钥解密
	 * @throws Exception 
	 */
	public static void testCert1() throws Exception{
	
		String s= "公钥加密，私钥解密";
		byte[] encry = Signaturer.encryByPubKey(s.getBytes(),certPath);
		
		byte [] decry = Signaturer.decryByPriKey(encry, keyStorePath, alias, password);
		
		String output = new String(decry);
		
		System.out.println("加密前:\n" + s);
		System.err.println("解密后:\n" + output);
		
	}
	/**
	 * 私钥加密，公钥解密
	 * @throws Exception 
	 */
	public static void testCert2()throws Exception{
		String s= "私钥加密，公钥解密";
		byte[] enctry = Signaturer.encryByPriKey(s.getBytes(), keyStorePath, alias, password);
//		byte[] enctry = Signaturer.encryByPubKey(s.getBytes(), certPath);
		
		byte[] decry  = Signaturer.decryByPubKey(enctry, certPath);
		String output = new String(decry);
		System.out.println("加密前:\n" + s);
		System.err.println("解密后:\n" + output);
	}
	
	
	public static void testSign1()throws Exception{
		String s = "私钥签名，公钥验证";
		byte[] sign = Signaturer.sign(s.getBytes(),keyStorePath,alias,password);
		
		System.err.println("签名："+StringToHex.bytesToHexString(sign));
		//验证签名
		boolean status = Signaturer.vertify(s.getBytes(), sign, certPath);
		System.out.println(status);
	}
	
	
	
	/**
	 * 替换模板中的变量。变量的标识符为${}。
	 * 例如，模板中${name}变量将会被Map列表中键名为name的键值替换，如果Map列表中不存在所需要
	 * 的键名，则会被替换成空。
	 * @param template 模板
	 * @param data  参数列表
	 * @return
	 * @throws Exception
	 * @author Brian
	 */
	public static String composeMessage(String template, Map<String,Object> data) {   
		String regex = "\\$\\{(.+?)\\}";   
		Pattern pattern = Pattern.compile(regex);   
		Matcher matcher = pattern.matcher(template); 
		
		/*  
		 * sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序  
		 * 存储起来。  
		 */  
		StringBuffer sb = new StringBuffer();   
		while (matcher.find()) {   
			String name = matcher.group(1);//键名   
			String value = null == data.get(name) ? "" : data.get(name).toString();//键值   
			if (value == null) {   
				value = "";   
			} else {    
				value = value.replaceAll("\\$", "\\\\\\$");
				//value = value.replaceAll("\\", "\\\\");				
			}   
 
			matcher.appendReplacement(sb, value);   
		}   
		   
		matcher.appendTail(sb);   
		return sb.toString();   
	}
}
