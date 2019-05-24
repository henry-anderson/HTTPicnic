package anderson.henry.httpicnic.http;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents an HTTP response
 * @author Henry Anderson
 */
public class HttpResponse {
	private Map<String, String> headers;
	private List<Cookie> cookies;
	private String content;
	private int statusCode;
	private String statusMessage;
	
	/**
	 * Constructs a new HttpResponse
	 * @param headers The reponse headers
	 * @param cookies The response cookies
	 * @param content The content of the response
	 * @param statusCode The response status code
	 * @param statusMessage The response status message
	 */
	public HttpResponse(Map<String, String> headers, List<Cookie> cookies, String content, int statusCode, String statusMessage) {
		this.headers = headers;
		this.content = content;
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.cookies = cookies;
	}
	
	/**
	 * Returns the value of the header
	 * @param field The field of the header
	 * @return Returns a String
	 */
	public String getHeaderValue(String field) {
		return this.headers.get(field);
	}
	
	/**
	 * Returns the value of the header
	 * @param field The field of the header
	 * @return Returns a String
	 */
	public String getHeaderValue(HttpHeader field) {
		return this.headers.get(field.getText());
	}
	
	/**
	 * Returns the list of cookies
	 * @return Returns a List
	 */
	public List<Cookie> getCookies() {
		return this.cookies;
	}
	
	/**
	 * Returns the cookie with the specified name
	 * @param name The name of the cookie
	 * @return Returns a Cookie instance
	 */
	public Cookie getCookie(String name) {
		for(Cookie cookie : this.cookies) {
			if(cookie.getName().equals(name)) {
				return cookie;
			}
		}
		return null;
	}
	
	/**
	 * Checks whether a cookie currently exists
	 * @param name The name of the cookie
	 * @return Returns a boolean value
	 */
	public boolean containsCookie(String name) {
		return this.getCookie(name) != null ? true : false;
	}
	
	/**
	 * Checks whether the response contains a certain header
	 * @param field The name of the header
	 * @return Returns a boolean value
	 */
	public boolean containsHeader(String field) {
		return this.headers.containsKey(field);
	}
	
	/**
	 * Checks whether the response contains a certain header
	 * @param field The type of header
	 * @return Returns a boolean value
	 */
	public boolean containsHeader(HttpHeader field) {
		return this.headers.containsKey(field.getText());
	}
	
	/**
	 * Returns the list of headers
	 * @return Returns a List
	 */
	public Set<String> getHeaderFields() {
		Set<String> headerFields = new HashSet<String>();
		for(String header : this.headers.keySet()) {
			headerFields.add(header);
		}
		return headerFields;
	}
	
	/**
	 * Returns the content of the response
	 * @return Returns a String
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * Returns the status code of the response
	 * @return Returns an int
	 */
	public int getStatusCode() {
		return this.statusCode;
	}
	
	/**
	 * Returns the status message of the response
	 * @return Returns a String
	 */
	public String getStatusMessage() {
		return this.statusMessage;
	}
}