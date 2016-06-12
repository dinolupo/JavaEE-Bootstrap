/**
 * 
 */
package io.github.dinolupo.javaee;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dinolupo.github.io
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyInject {
	
	DayTime value(); //default DayTime.MORNING;
	
	enum DayTime {
		MORNING,
		EVENING
	}
}
