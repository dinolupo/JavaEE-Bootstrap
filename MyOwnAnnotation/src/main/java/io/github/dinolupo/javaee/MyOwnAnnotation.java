/**
 * 
 */
package io.github.dinolupo.javaee;

import java.lang.reflect.Field;

/**
 * @author dinolupo.github.io
 *
 */
public class MyOwnAnnotation {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException {
		Class<?> clazz = Class.forName("io.github.dinolupo.javaee.Facade");
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			MyInject myInject = field.getAnnotation(MyInject.class);
			if (myInject != null) {
				System.out.printf("Field %s is annotated with %s\n", field, myInject);
			} else {
				System.out.printf("Field %s is not annotated", field);
			}
		}

	}

}
