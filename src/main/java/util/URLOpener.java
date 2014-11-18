package util;

import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Set;

/**
 * java调用系统默认浏览器打开连接url
 * @author zhangyong
 *
 */
public class URLOpener {

	public boolean openUrl(String url){
		String osName= System.getProperty("os.name");
		try {
		if(osName.startsWith("Mac OS")){
			Class fileMgr = Class.forName("com.apple.eio.FileManager");
			Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[]{String.class});
			openURL.invoke(null, new Object[]{url});
			
		}else if(osName.startsWith("Windows")){
			Runtime.getRuntime().exec( "rundll32 url.dll,FileProtocolHandler " + url); 
		}else{
			String[]browsers ={"firefox","opera","konqueror","epiphany","mozilla","netscape"};
			String brower=null;
			for(int i= 0;i<browsers.length&&brower==null;i++){
				if(Runtime.getRuntime().exec(new String[]{"which",browsers[i]}).waitFor()==0){
					brower = browsers[i];
				}
				if(brower!=null){
					Runtime.getRuntime().exec(new String[]{brower,url});
				}
				
			}
			return true;
			
		}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	public static void main(String[] args) {
		System.out.println(System.getenv().toString());
		Properties p = System.getProperties();
		Set s = 	p.entrySet();
		for (Object object : s) {
			System.out.println(object);
		}
	}
}
