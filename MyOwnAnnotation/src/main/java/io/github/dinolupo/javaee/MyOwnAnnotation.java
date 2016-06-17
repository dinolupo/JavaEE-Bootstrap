/**
 * 
 */
package io.github.dinolupo.javaee;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

/**
 * @author dinolupo.github.io
 *
 */
public class MyOwnAnnotation {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> clazz = Class.forName("io.github.dinolupo.javaee.Facade");
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			MyInject myInject = field.getAnnotation(MyInject.class);
			if (myInject != null) {
				System.out.printf("Field %s is annotated with %s\n", field, myInject);
				Object facade = clazz.newInstance();
				Class<?> serviceType = field.getType();
				// To Inject interfaces we simulate a Scanner and retrieve an Implementation with suffix "Impl"
				String simpleName = serviceType.getSimpleName();
				String className = simpleName + "Impl";
				String packageName = serviceType.getPackage().getName();
				String fullName = packageName + "." + className;
				Object service = Class.forName(fullName).newInstance();
				field.setAccessible(true); // even if the field is private, access it
				field.set(facade, service);
				System.out.printf("%s\n", facade);
			} else {
				//System.out.printf("Field %s is not annotated", field);
			}
		}

	}

}
