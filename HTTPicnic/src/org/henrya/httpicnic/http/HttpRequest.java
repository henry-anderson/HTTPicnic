package org.henrya.httpicnic.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.henrya.httpicnic.utils.PicnicUtils;

/**
 * A class for sending HTTP requests
 * @author Henry Anderson
 */
public abstract class HttpRequest {
	private String url;
	private Map<String, String> headers = new HashMap<String, String>();
	private Map<String, String> parameters = new HashMap<String, String>();
	private List<Cookie> cookies = new ArrayList<Cookie>();
	
	/**
	 * @param url The path to the new, or existing file excluding the extension
	 */
	public HttpRequest(String url) {
		this.url = url;
	}
	
	/**
	 * Sends the HTTP request using new headers, new parameters, and new cookies
	 * @param headers The headers
	 * @param rawParams A String with the raw, unparsed parameters 
	 * @param rawCookies A String with the raw, unparsed cookies
	 * @return An HttpResponse instance
	 * @throws HttpConnectionException When the connection fails
	 */
	public abstract HttpResponse sendRaw(Map<String, String> headers, String rawParams, String rawCookies) throws HttpConnectionException;
	
	/**
	 * Sends the HTTP request using new headers, new parameters, and new cookies
	 * @param headers The headers
	 * @param parameters The parameters
	 * @param cookies The cookies
	 * @return An HttpResponse instance
	 * @throws HttpConnectionException When the connection fails
	 */
	public HttpResponse send(Map<String, String> headers, Map<String, String> parameters, List<Cookie> cookies) throws HttpConnectionException {
		return this.sendRaw(headers, PicnicUtils.toRawParameters(parameters), PicnicUtils.toRawCookies(cookies));
	}
	
	/**
	 * Sends the HTTP request
	 * @return An HttpResponse instance
	 * @throws HttpConnectionException When the connection fails
	 */
	public HttpResponse send() throws HttpConnectionException{
		return this.sendRaw(this.getHeaders(), PicnicUtils.toRawParameters(this.getParameters()), PicnicUtils.toRawCookies(this.getCookies()));
	}
	
	/**
	 * Sends the HTTP request with new, unparsed parameters and cookies
	 * @param rawParams The raw, unparsed parameters
	 * @param rawCookies The raw, unparsed cookies
	 * @return An HttpResponse instance
	 * @throws HttpConnectionException When the connection fails
	 */
	public HttpResponse sendRaw(String rawParams, String rawCookies) throws HttpConnectionException {
		return this.sendRaw(this.getHeaders(), rawParams, rawCookies);
	}
	
	/**
	 * Sends the HTTP request with new, unparsed parameters
	 * @param rawParams The raw, unparsed parameters
	 * @return An HttpResponse instance
	 * @throws HttpConnectionException When the connection fails
	 */
	public HttpResponse sendRawParams(String rawParams) throws HttpConnectionException {
		return this.sendRaw(this.getHeaders(), rawParams, PicnicUtils.toRawCookies(this.getCookies()));
	}
	
	/**
	 * Sends the HTTP request with new, unparsed cookies
	 * @param rawCookies The raw, unparsed cookies
	 * @return An HttpResponse instance
	 * @throws HttpConnectionException When the connection fails
	 */
	public HttpResponse sendRawCookies(String rawCookies) throws HttpConnectionException {
		return this.sendRaw(this.getHeaders(), PicnicUtils.toRawParameters(this.getParameters()), rawCookies);
	}
	
	/**
	 * Returns the URL that the request will be sent to
	 * @return The URL
	 */
	public String getURL() {
		return this.url;
	}
	
	/**
	 *  Sets the URL that the request will be sent to
	 *  @param url The URL
	 */
	public void setURL(String url) {
		this.url = url;
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
	 * Sets a header to be sent
	 * @param field The field of the header
	 * @param value The value of the header
	 */
	public void setHeader(HttpHeader field, String value) {
		this.headers.put(field.getText(), value);
	}
	
	/**
	 * Sets a header to be sent
	 * @param field The field of the header
	 * @param value The value of the header
	 */
	public void setHeader(String field, String value) {
		this.headers.put(field, value);
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
	 * Adds a parameter to be sent
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
}
