/**
 * 
 */
package io.github.dinolupo.jee;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author dinolupo.github.io
 *
 */
public class ConfigurationFactoryTest {

	ConfigurationFactory cut;
	
	@Before
	public void initialize() {
		this.cut = ConfigurationFactory.getInstance();
	}
	
	/**
	 * Test method for {@link io.github.dinolupo.jee.ConfigurationFactory#create()}.
	 */
	@Test
	public void testCreate() {
		Configuration product = cut.create();
		assertNotNull(product);
		
		
	}

}
