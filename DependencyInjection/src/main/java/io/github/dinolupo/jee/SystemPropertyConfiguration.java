/**
 * 
 */
package io.github.dinolupo.jee;

/**
 * @author dinolupo.github.io
 *
 */
public class SystemPropertyConfiguration implements Configuration {

	/* (non-Javadoc)
	 * @see io.github.dinolupo.jee.Configuration#getConfig(java.lang.String)
	 */
	@Override
	public String getConfig(String key) {
		return System.getProperty(key);
	}

}
