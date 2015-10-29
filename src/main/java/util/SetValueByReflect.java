package util;

import java.lang.reflect.Method;

public class SetValueByReflect {

	
	public static void main(String[] args) {
		
		User u = new User();
		Class<?> classType = u.getClass();
		
	
		try {
			Object o = classType.newInstance();
			Method m = classType.getMethod("setAge", int.class);
			
			
			Object oo = m.invoke(o, 1);
			System.out.println(o);
			System.out.println(oo);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
}
