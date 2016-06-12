/**
 * 
 */
package io.github.dinolupo.javaee;

/**
 * @author dinolupo.github.io
 *
 */
public class Facade {

	@MyInject(MyInject.DayTime.EVENING)
	Service service;

	String somethingElse;
	
}

