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
	private Configuration product;
	
	private ConfigurationFactory() {
		// STEP 1: the implementation is in the factory now, and so we use the class loader to avoid
		// a recompilation of the Client class
		// this.product = new SystemPropertyConfiguration();
		
		// STEP 2:
		String classToInject = "io.github.dinolupo.jee.SystemPropertyConfiguration";
		try {
			this.product = (Configuration) Class.forName(classToInject).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new IllegalStateException("Product not found: " + classToInject, e);
		}
		
		// STEP 3: 
		// the String classToInject can be retrieved from a configuration file (property file, xml, etc.)
		// we skip this part because it is not relevant to JEE topic
	}
	
	public static final ConfigurationFactory getInstance() {
		return INSTANCE;
	} 
	
	public Configuration create(){
		return this.product; 
	}
	
}
