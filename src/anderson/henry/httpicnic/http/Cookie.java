package anderson.henry.httpicnic.http;

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
	
	public Cookie(String name, String value) {
		this.name = name;
		this.value = value;
	}
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
		/* System.out.println("Name: " + this.name);
		System.out.println("Value: " + this.value);
		System.out.println("Secure: " + this.secure);
		System.out.println("Http Only: " + this.httpOnly);
		System.out.println("Expires: " + this.expires);
		System.out.println("Max-Age: " + this.maxAge);
		System.out.println("Domain: " + this.domain);
		System.out.println("Path: " + this.path);
		System.out.println("Same Site: " + this.sameSite);
		System.out.println(); */
	}
	
	public String getRawCookie() {
		return this.raw;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isSecure() {
		return this.secure;
	}
	
	public boolean isHttpOnly() {
		return this.httpOnly;
	}
	
	public String getExpires() {
		return this.expires;
	}

	public int getMaxAge() {
		return this.maxAge;
	}
	
	public String getDomain() {
		return this.domain;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String getSameSite() {
		return this.sameSite;
	}
}
