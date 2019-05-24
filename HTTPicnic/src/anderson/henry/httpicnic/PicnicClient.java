package anderson.henry.httpicnic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anderson.henry.httpicnic.http.Cookie;
import anderson.henry.httpicnic.http.HttpConnectionException;
import anderson.henry.httpicnic.http.HttpHeader;
import anderson.henry.httpicnic.http.HttpResponse;
import anderson.henry.httpicnic.requests.HttpGet;
import anderson.henry.httpicnic.requests.HttpHead;
import anderson.henry.httpicnic.requests.HttpPost;

/**
 * A wrapper class for sending multiple HTTP requests and handling response cookies
 * @author Henry Anderson
 */
public class PicnicClient {
	private Map<String, String> headers = new HashMap<String, String>();
	private Map<String, String> parameters = new HashMap<String, String>();
	private List<Cookie> cookies = new ArrayList<Cookie>();
	
	/**
	 * Sends a GET request to the URL using the current headers, parameters, and cookies
	 * @param url The URL to send the request
	 * @return Returns a HttpResponse instance
	 */
	public HttpResponse sendGET(String url) throws HttpConnectionException {
		HttpGet request = new HttpGet(url);
		HttpResponse response =  request.send(this.headers, this.parameters, this.cookies);
		this.addNewCookies(response.getCookies());
		this.parameters.clear();
		return response;
	}
	
	/**
	 * Sends a HEAD request to the URL using the current headers, parameters, and cookies
	 * @param url The URL to send the request
	 * @return Returns a HttpResponse instance
	 */
	public HttpResponse sendHEAD(String url) throws HttpConnectionException {
		HttpHead request = new HttpHead(url);
		HttpResponse response =  request.send(this.headers, this.parameters, this.cookies);
		this.addNewCookies(response.getCookies());
		this.parameters.clear();
		return response;
	}
	
	/**
	 * Sends a POST request to the URL using the current headers, parameters, and cookies
	 * @param url The URL to send the request
	 * @return Returns a HttpResponse instance
	 */
	public HttpResponse sendPOST(String url) throws HttpConnectionException{
		HttpPost request = new HttpPost(url);
		HttpResponse response =  request.send(this.headers, this.parameters, this.cookies);
		this.addNewCookies(response.getCookies());
		this.parameters.clear();
		return response;
	}
	
	/**
	 * Returns the current list of headers
	 * @return headers
	 */
	public Map<String, String> getHeaders() {
		return this.headers;
	}
	
	/**
	 * Sets the headers that will be sent with the request
	 * @param headers The headers
	 */
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	
	/**
	 * Returns the headers value
	 * @param field The field of the header
	 * @return The value of the header
	 */
	public String getHeaderValue(String field) {
		return this.getHeaders().get(field);
	}
	
	/**
	 * Returns the headers value
	 * @param field The field of the header
	 * @return The value of the header
	 */
	public String getHeaderValue(HttpHeader field) {
		return this.getHeaders().get(field.getText());
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
	 * Returns whether the specified header will be sent with the next request
	 * @param field The field of the header
	 * @return A boolean
	 */
	public boolean containsHeader(String field) {
		return (this.getHeaderValue(field) != null) ? false : true;
	}
	
	/**
	 * Returns whether the specified header will be sent with the next request
	 * @param field The field of the header
	 * @return A boolean
	 */
	public boolean containsHeader(HttpHeader field) {
		return (this.getHeaderValue(field) != null) ? false : true;
	}
	
	/**
	 * Returns the current list of parameters
	 * @return parameters
	 */
	public Map<String, String> getParameters() {
		return this.parameters;
	}
	
	/**
	 * Sets the parameters that will be sent with the request
	 * @param parameters The parameters
	 */
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
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
	 * Removes a parameter
	 * @param key The parameter's key
	 */
	public void removeParameter(String key) {
		this.parameters.remove(key);
	}
	
	/**
	 * Returns the current list of cookies
	 * @return cookies
	 */
	public List<Cookie> getCookies() {
		return this.cookies;
	}
	
	/**
	 * Sets the cookies that will be sent with the request
	 * @param cookies The cookies
	 */
	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}
	
	/**
	 * Returns a Cookie instance from its name
	 * @param name The name of the cookie
	 * @return The cookie
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
	 * Adds new cookies to the client
	 * @param newCookies The List of new cookies
	 */
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
}