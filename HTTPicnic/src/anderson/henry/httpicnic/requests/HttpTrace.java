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
 * A class that represents a OPTIONS request
 * @author Henry Anderson
 */
public class HttpTrace extends HttpRequest {

	/**
	 * Constructs a new TRACE request
	 * @param url The URL the TRACE request will be sent to
	 */
	public HttpTrace(String url) {
		super(url);
	}
	
	/**
	 * Sends the HTTP TRACE request using new headers, new parameters, and new cookies
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
			urlObj = new URL(this.getURL());
			conn = PicnicUtils.prepareConnection(urlObj, headers, rawCookies);
			conn.setRequestMethod("TRACE");
			responseCode = conn.getResponseCode();
			responseMessage = conn.getResponseMessage();
			return new HttpResponse(PicnicUtils.parseHeaders(conn.getHeaderFields()), PicnicUtils.parseCookies(conn.getHeaderFields()), null, responseCode, responseMessage);
		} catch(ConnectException | UnknownHostException e) {
			throw new HttpConnectionException(this.getURL(), e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}