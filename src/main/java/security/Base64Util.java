package security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;

import com.mybatis.bean.User;

import jodd.util.Base64;

public class Base64Util {

	
	public static void main(String[] args) throws Exception {
		/*File file = new File("D:\\opt\\1.jpg");
		
		FileInputStream fis = new  FileInputStream(file);
		int i=0;
		byte[] bt = new byte[fis.available()];
		
		fis.read(bt);
		fis.close();
		System.out.println(Base64.encodeToString(bt));
		
		
		Base64 b = new Base64();
		System.out.println(Base64.encodeToString("中国哦随即大幅拉升解放了的距离"));
		System.out.println(Base64.decodeToString("5Lit5Zu9"));*/
		
/*		Base64OutputStream bout = null;
		
		try {
			bout = new Base64OutputStream(new FileOutputStream("d:\\bugreport1.txt"));
			bout.write("dddd".getBytes());
			bout.flush();
			bout.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Base64InputStream bb = null;
		try {
			byte[] bt = new byte[1024];
			bb = new Base64InputStream(new FileInputStream("d:\\bugreport1.txt"));
			int i =-1;
			StringBuilder sb = new StringBuilder();
			while ((i=bb.read(bt))!=-1) {
				sb.append(new String(bt,0,i));
			}
			System.out.println(sb.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		reflect();
	}
	
	public static void reflect(){
		
		
		Class clazz = User.class;
		clazz.getAnnotations();
	 Field [] fields =	clazz.getDeclaredFields();
		for (Field field : fields) {
			
			System.out.println(field.getName()+"-"+field.getType());
		}
		
		
	 Method methods []=	clazz.getDeclaredMethods();
		for (Method method : methods) {
		}
		
	}

}
