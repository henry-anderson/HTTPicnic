package anderson.henry.httpicnic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anderson.henry.httpicnic.http.Cookie;
import anderson.henry.httpicnic.http.HttpHeader;
import anderson.henry.httpicnic.http.HttpRequest;
import anderson.henry.httpicnic.http.HttpResponse;
import anderson.henry.httpicnic.http.UserAgent;

/**
 * A wrapper class for sending multiple HTTP requests and handling response cookies
 * @author Henry Anderson
 * @author henryanderson1@hotmail.com
 */
public class PicnicClient {
	private UserAgent userAgent;
	private List<Cookie> cookies = new ArrayList<Cookie>();
	private Map<String, String> headers = new HashMap<String, String>();
	private Map<String, String> parameters = new HashMap<String, String>();
	
	/**
	 * Sends a POST request to the URL using the current headers, parameters, and cookies
	 * @param url The URL to send the request
	 * @return Returns a HttpResponse instance
	 */
	public HttpResponse sendPOST(String url) {
		HttpRequest request = new HttpRequest(url);
		request.setUserAgent(this.userAgent);
		HttpResponse response =  request.sendPOST(this.headers, this.parameters, this.cookies);
		this.addNewCookies(response.getCookies());
		this.parameters.clear();
		return response;
	}
	
	/**
	 * Sends a GET request to the URL using the current headers, parameters, and cookies
	 * @param url The URL to send the request
	 * @return Returns a HttpResponse instance
	 */
	public HttpResponse sendGET(String url) {
		HttpRequest request = new HttpRequest(url);
		request.setUserAgent(this.userAgent);
		HttpResponse response =  request.sendGET(this.headers, this.parameters, this.cookies);
		this.addNewCookies(response.getCookies());
		this.parameters.clear();
		return response;
	}
	
	/**
	 * Sends a HEAD request to the URL using the current headers, parameters, and cookies
	 * @param url The URL to send the request
	 * @return Returns a HttpResponse instance
	 */
	public HttpResponse sendHEAD(String url) {
		HttpRequest request = new HttpRequest(url);
		request.setUserAgent(this.userAgent);
		HttpResponse response =  request.sendHEAD(this.headers, this.parameters, this.cookies);
		this.addNewCookies(response.getCookies());
		this.parameters.clear();
		return response;
	}
	
	private void addNewCookies(List<Cookie> newCookies) {
		for(Cookie cookie : newCookies) {
			if(this.containsCookie(cookie.getName())) {
				this.getCookie(cookie.getName()).setValue(cookie.getValue());
			}
			else {
				this.cookies.add(cookie);
			}
		}
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
	 * Adds a cookie to be sent with the next request
	 * @param cookie The cookie to be added
	 */
	public void addCookie(Cookie cookie) {
		this.addCookie(cookie.getName(), cookie.getValue());
	}
	
	/**
	 * Adds a cookie to be sent with the next request
	 * @param name The name of the cookie
	 * @param value The value of the cookie
	 */
	public void addCookie(String name, String value) {
		if(this.containsCookie(name)) {
			this.getCookie(name).setValue(value);
		}
		else {
			this.cookies.add(new Cookie(name, value));
		}
	}
	
	/**
	 * Checks whether a cookie currently exists
	 * @param name The name of the cookie
	 * @return Returns a boolean value
	 */
	public boolean containsCookie(String name) {
		for(Cookie cookie : this.cookies) {
			if(cookie.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Sets a header to be sent in the next request
	 * @param field The field of the header
	 * @param value The value of the header
	 */
	public void setHeader(HttpHeader field, String value) {
		this.headers.put(field.getText(), value);
	}
	
	/**
	 * Sets a header to be sent in the next request
	 * @param field The field of the header
	 * @param value The value of the header
	 */
	public void setHeader(String field, String value) {
		this.headers.put(field, value);
	}
	
	/**
	 * Removes a header that is queued for the next request
	 * @param field The field of the header
	 */
	public void removeHeader(String field) {
		this.headers.remove(field);
	}
	
	/**
	 * Removes a header that is queued for the next request
	 * @param field The field of the header
	 */
	public void removeHeader(HttpHeader field) {
		this.headers.remove(field.getText());
	}
	
	/**
	 * Adds a parameter to be sent in the next request
	 * @param key The parameter's key
	 * @param value The parameter's value
	 */
	public void addParameter(String key, String value) {
		this.parameters.put(key, value);
	}
	
	/**
	 * Sets the user agent to be sent in the next request
	 * @param userAgent The user agent
	 */
	public void setUserAgent(UserAgent userAgent) {
		this.userAgent = userAgent;
	}
	
	/**
	 * Returns the current list of cookies
	 * @return Returns a List
	 */
	public List<Cookie> getCookies() {
		return this.cookies;
	}
}