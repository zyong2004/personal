package security;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.codec.binary.Base64OutputStream;

import jodd.util.Base64;

public class Base64Util {

	
	public static void main(String[] args) {

		Base64 b = new Base64();
		System.out.println(Base64.encodeToString("中国哦随即大幅拉升解放了的距离"));
		System.out.println(Base64.decodeToString("5Lit5Zu9"));
		
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
	}

}
