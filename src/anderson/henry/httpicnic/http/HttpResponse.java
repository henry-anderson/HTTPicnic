package anderson.henry.httpicnic.http;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpResponse {
	private Map<String, String> headers;
	private List<Cookie> cookies;
	private String content;
	private int statusCode;
	private String statusMessage;
	
	public HttpResponse(Map<String, String> headers, List<Cookie> cookies, String content, int statusCode, String statusMessage) {
		this.headers = headers;
		this.content = content;
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.cookies = cookies;
	}
	
	public String getHeaderValue(String field) {
		return this.headers.get(field);
	}
	public String getHeaderValue(HttpHeader field) {
		return this.headers.get(field.getText());
	}
	
	public List<Cookie> getCookies() {
		return this.cookies;
	}
	
	public Cookie getCookie(String name) {
		for(Cookie cookie : this.cookies) {
			if(cookie.getName().equals(name)) {
				return cookie;
			}
		}
		return null;
	}
	
	public boolean containsCookie(String name) {
		return this.getCookie(name) != null ? true : false;
	}
	
	
	public boolean containsHeader(String field) {
		return this.headers.containsKey(field);
	}
	
	public Set<String> getHeaderFields() {
		Set<String> headerFields = new HashSet<String>();
		for(String header : this.headers.keySet()) {
			headerFields.add(header);
		}
		return headerFields;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public int getStatusCode() {
		return this.statusCode;
	}
	
	public String getStatusMessage() {
		return this.statusMessage;
	}
}