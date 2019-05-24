package anderson.henry.httpicnic.requests;

import java.io.IOException;
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
 * A class for sending HEAD requests
 * @author Henry Anderson
 */
public class HttpHead extends HttpRequest {
	
	/**
	 * Constructs a new HEAD request
	 * @param url The URL the HEAD request will be sent to
	 */
	public HttpHead(String url) {
		super(url);
	}
	
	/**
	 * Sends the HTTP HEAD request using new headers, new parameters, and new cookies
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
			conn.setRequestMethod("HEAD");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			responseCode = conn.getResponseCode();
			responseMessage = conn.getResponseMessage();
			return new HttpResponse(PicnicUtils.parseHeaders(conn.getHeaderFields()), PicnicUtils.parseCookies(conn.getHeaderFields()), null, responseCode, responseMessage);
		} catch(ConnectException | UnknownHostException e) {
			throw new HttpConnectionException(this.getURL(), e.getMessage());
		} catch(IOException e) {
			return new HttpResponse(PicnicUtils.parseHeaders(conn.getHeaderFields()), PicnicUtils.parseCookies(conn.getHeaderFields()), null, responseCode, responseMessage);
		}
	}
}