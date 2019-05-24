package org.henrya.httpicnic.http;

/**
 * Represents an HTTP cookie
 * @author Henry Anderson
 */
public class Cookie {
	private String raw;
	private String name;
	private String value;
	private String expires;
	private int maxAge;
	private String domain;
	private String path;
	private String sameSite;
	private boolean secure;
	private boolean httpOnly;
	
	/**
	 * @param name The name of the cookie
	 * @param value The value of the cookie
	 */
	public Cookie(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 * Parses a cookie represented by a string
	 * @param raw The raw, unparsed cookie
	 */
	public Cookie(String raw) {
		this.raw = raw;
		String[] fields = raw.split("\\s*;\\s*");
		String[] nameAndValue = fields[0].split("=", 2);
		this.name = nameAndValue[0];
		this.value = nameAndValue[1];
		
		for(int i = 0; i < fields.length; i++) {
			if(fields[i].equalsIgnoreCase("secure")) {
				this.secure = true;
			}
			else if(fields[i].equalsIgnoreCase("httpOnly")) {
				this.httpOnly = true;
			}
			else if(fields[i].indexOf("=") > 0) {
				String[] split = fields[i].split("=");
				if(split[0].equalsIgnoreCase("expires")) {
					this.expires = split[1];
				}
				else if(split[0].equalsIgnoreCase("max-age")) {
					this.maxAge = Integer.parseInt(split[1]);
				}
				else if(split[0].equalsIgnoreCase("domain")) {
					this.domain = split[1];
				}
				else if(split[0].equalsIgnoreCase("path")) {
					this.path = split[1];
				}
				else if(split[0].equalsIgnoreCase("samesite")) {
					this.sameSite = split[1];
				}
				
			}
		}
	}
	
	/**
	 * Returns the raw, unparsed form of the cookie
	 * @return Returns a String
	 */
	public String getRawCookie() {
		return this.raw;
	}
	
	/**
	 * Returns the name of the cookie
	 * @return Returns a String
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the value of the cookie
	 * @return Returns a String
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Sets the value of the cookie
	 * @param value The value of the cookie
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Returns whether the cookie is secure
	 * @return Returns a boolean
	 */
	public boolean isSecure() {
		return this.secure;
	}
	
	/**
	 * Returns whether the cookie is http only
	 * @return Returns a boolean
	 */
	public boolean isHttpOnly() {
		return this.httpOnly;
	}
	
	/**
	 * Returns the expiration date of the cookie
	 * @return Returns a String
	 */
	public String getExpires() {
		return this.expires;
	}

	/**
	 * Returns the maximum age of the cookie
	 * @return Returns an int
	 */
	public int getMaxAge() {
		return this.maxAge;
	}
	
	/**
	 * Returns the domain of the cookie
	 * @return Returns a String
	 */
	public String getDomain() {
		return this.domain;
	}
	
	/**
	 * Returns the path of the cookie
	 * @return Returns a boolean
	 */
	public String getPath() {
		return this.path;
	}
	
	/**
	 * Returns the same site attribute of the cookie
	 * @return Returns a String
	 */
	public String getSameSite() {
		return this.sameSite;
	}
}
