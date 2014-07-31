package com.mybatis.util;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class LogersUtil {

    /**
     * 反射输出实体类所有公共无入参的get和is方法名称和值
     * @author ZHANG Nan
     * @param object 实体类对象
     * @return object属性名称名称及属性值
     */
	public static String printParam(Object object){
		if(object==null){
			return "";
		}
		StringBuffer out=new StringBuffer();
		try {
			Class<?> clazz=object.getClass();
			out.append(clazz.getSimpleName());
			out.append(" [");
			Method[] methods=clazz.getMethods();
			for (Method method : methods) {
				if(method.getName().indexOf("get")>=0){
					Type [] types=method.getGenericParameterTypes();
					if(types==null || types.length==0){
						Method mtd = clazz.getMethod(method.getName(),new Class[]{});
						Object value = mtd.invoke(object);
						if(value!=null){
							out.append(method.getName()+"="+value.toString()+",");
						}
					}
				}
			}
			out.append("]");
		} catch (Exception e) {
		}
		return out.toString();
	}
}
