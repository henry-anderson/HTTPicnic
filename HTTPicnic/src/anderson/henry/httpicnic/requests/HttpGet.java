package anderson.henry.httpicnic.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;

import anderson.henry.httpicnic.http.HttpConnectionException;
import anderson.henry.httpicnic.http.HttpRequest;
import anderson.henry.httpicnic.http.HttpResponse;
import anderson.henry.httpicnic.utils.PicnicUtils;

/**
 * A class that represents a GET request
 * @author Henry Anderson
 */
public class HttpGet extends HttpRequest {

	/**
	 * Constructs a new GET request
	 * @param url The URL the GET request will be sent to
	 */
	public HttpGet(String url) {
		super(url);
	}
	
	/**
	 * Sends the HTTP GET request using new headers, new parameters, and new cookies
	 * @param headers The headers
	 * @param rawParams A String with the raw, unparsed parameters 
	 * @param rawCookies A String with the raw, unparsed cookies
	 * @return An HttpResponse instance
	 * @throws HttpConnectionException When the connection fails
	 */
	public HttpResponse sendRaw(Map<String, String> headers, String rawParams, String rawCookies) throws HttpConnectionException {
		URL urlObj = null;
		HttpURLConnection conn = null;
		int responseCode = -1;
		String responseMessage = null;
		try {
			urlObj = new URL(this.getURL() + (rawParams != null && !rawParams.isEmpty() ? "?" + rawParams : ""));
			conn = PicnicUtils.prepareConnection(urlObj, headers, rawCookies);
			conn.setRequestMethod("GET");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			responseCode = conn.getResponseCode();
			responseMessage = conn.getResponseMessage();
			String responseContent = PicnicUtils.parseContent(new BufferedReader(new InputStreamReader(conn.getInputStream())));
			return new HttpResponse(PicnicUtils.parseHeaders(conn.getHeaderFields()), PicnicUtils.parseCookies(conn.getHeaderFields()), responseContent, responseCode, responseMessage);
		} catch(ConnectException | UnknownHostException e) {
			throw new HttpConnectionException(this.getURL(), e.getMessage());
		} catch(IOException e) {
			return new HttpResponse(PicnicUtils.parseHeaders(conn.getHeaderFields()), PicnicUtils.parseCookies(conn.getHeaderFields()), null, responseCode, responseMessage);
		}
	}
}