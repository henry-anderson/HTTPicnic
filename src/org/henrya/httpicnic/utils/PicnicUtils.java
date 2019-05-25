package org.henrya.httpicnic.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.henrya.httpicnic.http.Cookie;
import org.henrya.httpicnic.http.HttpHeader;

/**
 * @author Henry Anderson
 */
public final class PicnicUtils {
	public static Map<String, String> parseHeaders(Map<String, List<String>> headers) {
		Map<String, String> parsedHeaders = new HashMap<String, String>();
		for(String headerField : headers.keySet()) {
			if(headerField != null) {
				String parsedValues = "";
				List<String> values = headers.get(headerField);
				for(int i = 0; i < values.size(); i++) {
					String value = values.get(i);
					parsedValues = parsedValues + value + (i == (values.size() - 1) ? "" : "; ");
				}
				parsedHeaders.put(headerField, parsedValues);
			}
		}
		return parsedHeaders;
	}
	public static List<Cookie> parseCookies(Map<String, List<String>> headers) {
		List<Cookie> cookies = new ArrayList<Cookie>();
		for(String headerField : headers.keySet()) {
			if(headerField != null && headerField.equalsIgnoreCase("Set-Cookie")) {
				for(String rawCookie : headers.get(headerField)) {
					cookies.add(new Cookie(rawCookie));
				}
			}
		}
		return cookies;
	}
	public static String toRawCookies(List<Cookie> cookies) {
		String rawCookies = "";
		for(int i = 0; i < cookies.size(); i++) {
			Cookie cookie = cookies.get(i);
			rawCookies = rawCookies + cookie.getName() + "=" + cookie.getValue() + (i == (cookies.size() - 1) ? "" : "; ");
		}
		return rawCookies;
	}
	public static String toRawParameters(Map<String, String> parameters) {
		try {
			String parsedParams = "";
			Object[] params = parameters.keySet().toArray();
			for(int i = 0; i < parameters.size(); i++) {
				String value = URLEncoder.encode(parameters.get(params[i]), StandardCharsets.UTF_8.toString());
				parsedParams = parsedParams + params[i] + "=" + value + (i == (parameters.size() - 1) ? "" : "&");
			}
			return parsedParams;
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String parseContent(BufferedReader reader) throws IOException {
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = reader.readLine()) != null) {
			response.append(inputLine + "\n");
		}
		reader.close();

		return response.toString();
	}
	
	public static HttpURLConnection prepareConnection(URL urlObj, Map<String, String> headers, String rawCookies) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
		conn.setInstanceFollowRedirects(false);
		if(rawCookies != null && !rawCookies.isEmpty()) {
			conn.setRequestProperty(HttpHeader.COOKIE.getText(), rawCookies);
		}
		for(String field : headers.keySet()) {
			conn.setRequestProperty(field, headers.get(field));
		}
		return conn;
	}
}
