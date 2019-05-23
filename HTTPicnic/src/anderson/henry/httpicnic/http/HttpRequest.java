package anderson.henry.httpicnic.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class for sending HTTP requests
 */
public class HttpRequest {
	private String url;
	private UserAgent userAgent;
	private Map<String, String> headers = new HashMap<String, String>();
	private Map<String, String> parameters = new HashMap<String, String>();
	private List<Cookie> cookies = new ArrayList<Cookie>();
	
	/**
	 * @param url The path to the new, or existing file excluding the extension
	 */
	public HttpRequest(String url) {
		this.url = url;
	}
	
	private HttpResponse send(String method, Map<String, String> headers, Map<String, String> parameters, List<Cookie> cookies) {
		try {
			switch(method) {
				case "POST": {
					URL urlObj = new URL(this.url);
					HttpURLConnection conn = this.prepareURL(urlObj, headers, cookies);
					conn.setRequestMethod("POST");
					if(parameters != null && !parameters.isEmpty()) {
						String urlParameters = HttpUtils.parseParameters(parameters);
						conn.setDoOutput(true);
						DataOutputStream output = new DataOutputStream(conn.getOutputStream());
						output.writeBytes(urlParameters);
						output.flush();
						output.close();
					}
					return new HttpResponse(HttpUtils.parseHeaders(conn.getHeaderFields()), HttpUtils.parseCookies(conn.getHeaderFields()), HttpUtils.parseContent(new BufferedReader(new InputStreamReader(conn.getInputStream()))), conn.getResponseCode(), conn.getResponseMessage());
				}
				case "GET": {
					URL urlObj = new URL(this.url + "?" + HttpUtils.parseParameters(parameters));
					HttpURLConnection conn = this.prepareURL(urlObj, headers, cookies);
					conn.setRequestMethod("GET");
					return new HttpResponse(HttpUtils.parseHeaders(conn.getHeaderFields()), HttpUtils.parseCookies(conn.getHeaderFields()), HttpUtils.parseContent(new BufferedReader(new InputStreamReader(conn.getInputStream()))), conn.getResponseCode(), conn.getResponseMessage());
				}
				case "HEAD": {
					URL urlObj = new URL(this.url + "?" + HttpUtils.parseParameters(parameters));
					HttpURLConnection conn = this.prepareURL(urlObj, headers, cookies);
					conn.setRequestMethod("HEAD");
					return new HttpResponse(HttpUtils.parseHeaders(conn.getHeaderFields()), HttpUtils.parseCookies(conn.getHeaderFields()), null, conn.getResponseCode(), conn.getResponseMessage());
				}
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private HttpURLConnection prepareURL(URL urlObj, Map<String, String> headers, List<Cookie> cookies) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
		if(this.userAgent != null) {
			conn.setRequestProperty(HttpHeader.USER_AGENT.getText(), this.userAgent.getText());
		}
		if(cookies != null && !cookies.isEmpty()) {
			String rawCookies = HttpUtils.toRawCookies(cookies);
			conn.setRequestProperty(HttpHeader.COOKIE.getText(), rawCookies);
		}
		
		for(String field : headers.keySet()) {
			conn.setRequestProperty(field, headers.get(field));
		}
		return conn;
	}
	
	/**
	 * Sends a POST request using the current headers, parameters, and cookies
	 * @param headers The headers to send
	 * @param parameters The parameters to send
	 * @param cookies The cookies to send
	 * @return Returns a HttpResponse instance
	 */
	public HttpResponse sendPOST(Map<String, String> headers, Map<String, String> parameters, List<Cookie> cookies) {
		return this.send("POST", headers, parameters, cookies);
	}
	
	/**
	 * Sends a POST request using the current headers, parameters, and cookies
	 * @return Returns a HttpResponse instance
	 */
	public HttpResponse sendPOST() {
		return this.send("POST", this.headers, this.parameters, this.cookies);
	}
	
	/**
	 * Sends a GET request using the current headers, parameters, and cookies
	 * @param headers The headers to send
	 * @param parameters The parameters to send
	 * @param cookies The cookies to send
	 * @return Returns a HttpResponse instance
	 */
	public HttpResponse sendGET(Map<String, String> headers, Map<String, String> parameters, List<Cookie> cookies) {
		return this.send("GET", headers, parameters, cookies);
	}
	
	/**
	 * Sends a GET request using the current headers, parameters, and cookies
	 * @return Returns a HttpResponse instance
	 */
	public HttpResponse sendGET() {
		return this.send("GET", this.headers, this.parameters, this.cookies);
	}
	
	/**
	 * Sends a HEAD request using the current headers, parameters, and cookies
	 * @param headers The headers to send
	 * @param parameters The parameters to send
	 * @param cookies The cookies to send
	 * @return Returns a HttpResponse instance
	 */
	public HttpResponse sendHEAD(Map<String, String> headers, Map<String, String> parameters, List<Cookie> cookies) {
		return this.send("HEAD", headers, parameters, cookies);
	}
	
	/**
	 * Sends a HEAD request using the current headers, parameters, and cookies
	 * @return Returns a HttpResponse instance
	 */
	public HttpResponse sendHEAD() {
		return this.send("HEAD", this.headers, this.parameters, this.cookies);
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
	 * Adds a parameter to be sent
	 * @param key The parameter's key
	 * @param value The parameter's value
	 */
	public void addParameter(String key, String value) {
		this.parameters.put(key, value);
	}

	/**
	 * Returns the current list of cookies
	 * @param name The name of the cookie
	 * @return Returns a List
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
	 * Sets the user agent to be sent in the next request
	 * @param userAgent The user agent
	 */
	public void setUserAgent(UserAgent userAgent) {
		this.userAgent = userAgent;
	}
}
