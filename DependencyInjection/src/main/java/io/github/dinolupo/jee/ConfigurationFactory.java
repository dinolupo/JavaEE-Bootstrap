/**
 * 
 */
package io.github.dinolupo.jee;

/**
 * @author dinolupo.github.io
 *
 */
public class ConfigurationFactory {
	private final static ConfigurationFactory INSTANCE = new ConfigurationFactory();
	
	// pre-cache Configuration product 
	private Object product;
	
	private ConfigurationFactory() {
		// STEP 4: ConfigurationFactory is now generic and can be put outside in a framework
		// and this is what JEE Application Servers do
		
		String classToInject = "io.github.dinolupo.jee.SystemPropertyConfiguration";
		try {
			this.product = Class.forName(classToInject).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new IllegalStateException("Product not found: " + classToInject, e);
		}
		
	}
	
	public static final ConfigurationFactory getInstance() {
		return INSTANCE;
	} 
	
	public Object create(){
		return this.product; 
	}
	
}
