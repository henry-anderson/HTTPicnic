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

public class PicnicClient {
	private UserAgent userAgent;
	private List<Cookie> cookies = new ArrayList<Cookie>();
	private Map<String, String> headers = new HashMap<String, String>();
	private Map<String, String> parameters = new HashMap<String, String>();
	
	public HttpResponse sendPOST(String url) {
		HttpRequest request = new HttpRequest(url);
		request.setUserAgent(this.userAgent);
		HttpResponse response =  request.sendPOST(this.headers, this.parameters, this.cookies);
		this.addNewCookies(response.getCookies());
		this.parameters.clear();
		return response;
	}
	
	public HttpResponse sendGET(String url) {
		HttpRequest request = new HttpRequest(url);
		request.setUserAgent(this.userAgent);
		HttpResponse response =  request.sendGET(this.headers, this.parameters, this.cookies);
		this.addNewCookies(response.getCookies());
		this.parameters.clear();
		return response;
	}
	
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
	public void setHeader(HttpHeader field, String value) {
		this.headers.put(field.getText(), value);
	}
	public void setHeader(String field, String value) {
		this.headers.put(field, value);
	}
	public void removeHeader(String field) {
		this.headers.remove(field);
	}
	public void removeHeader(HttpHeader field) {
		this.headers.remove(field.getText());
	}
	public void addParameter(String key, String value) {
		this.parameters.put(key, value);
	}
	public void setUserAgent(UserAgent userAgent) {
		this.userAgent = userAgent;
	}
	public List<Cookie> getCookies() {
		return this.cookies;
	}
}