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

public class HttpRequest {
	private String url;
	private UserAgent userAgent;
	private Map<String, String> headers = new HashMap<String, String>();
	private Map<String, String> parameters = new HashMap<String, String>();
	private List<Cookie> cookies = new ArrayList<Cookie>();
	
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
	
	public HttpResponse sendPOST(Map<String, String> headers, Map<String, String> parameters, List<Cookie> cookies) {
		return this.send("POST", headers, parameters, cookies);
	}
	public HttpResponse sendPOST() {
		return this.send("POST", this.headers, this.parameters, this.cookies);
	}
	public HttpResponse sendGET(Map<String, String> headers, Map<String, String> parameters, List<Cookie> cookies) {
		return this.send("GET", headers, parameters, cookies);
	}
	public HttpResponse sendGET() {
		return this.send("GET", this.headers, this.parameters, this.cookies);
	}
	public HttpResponse sendHEAD(Map<String, String> headers, Map<String, String> parameters, List<Cookie> cookies) {
		return this.send("HEAD", headers, parameters, cookies);
	}
	public HttpResponse sendHEAD() {
		return this.send("HEAD", this.headers, this.parameters, this.cookies);
	}
	public void setHeader(HttpHeader field, String value) {
		this.headers.put(field.getText(), value);
	}
	public void setHeader(String field, String value) {
		this.headers.put(field, value);
	}
	public void addParameter(String key, String value) {
		this.parameters.put(key, value);
	}

	public Cookie getCookie(String name) {
		for(Cookie cookie : this.cookies) {
			if(cookie.getName().equals(name)) {
				return cookie;
			}
		}
		return null;
	}
	public void addCookie(Cookie cookie) {
		this.addCookie(cookie.getName(), cookie.getValue());
	}
	public void addCookie(String name, String value) {
		if(this.containsCookie(name)) {
			this.getCookie(name).setValue(value);
		}
		else {
			this.cookies.add(new Cookie(name, value));
		}
	}
	public boolean containsCookie(String name) {
		for(Cookie cookie : this.cookies) {
			if(cookie.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	public void setUserAgent(UserAgent userAgent) {
		this.userAgent = userAgent;
	}
}
