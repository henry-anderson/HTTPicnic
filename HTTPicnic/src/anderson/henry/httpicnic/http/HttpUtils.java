package anderson.henry.httpicnic.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtils {
	protected static Map<String, String> parseHeaders(Map<String, List<String>> headers) {
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
	protected static List<Cookie> parseCookies(Map<String, List<String>> headers) {
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
	protected static String toRawCookies(List<Cookie> cookies) {
		String rawCookies = "";
		for(int i = 0; i < cookies.size(); i++) {
			Cookie cookie = cookies.get(i);
			rawCookies = rawCookies + cookie.getName() + "=" + cookie.getValue() + (i == (cookies.size() - 1) ? "" : "; ");
		}
		return rawCookies;
	}
	protected static String parseParameters(Map<String, String> parameters) {
		String parsedParams = "";
		Object[] params = parameters.keySet().toArray();
		for(int i = 0; i < parameters.size(); i++) {
			parsedParams = parsedParams + params[i] + "=" + parameters.get(params[i]) + (i == (parameters.size() - 1) ? "" : "&");
		}
		return parsedParams;
	}
	
	protected static String parseContent(BufferedReader reader) throws IOException {
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = reader.readLine()) != null) {
			response.append(inputLine);
		}
		reader.close();

		return response.toString();
	}
}
