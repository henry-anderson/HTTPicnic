package org.henrya.httpicnic.requests;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;

import org.henrya.httpicnic.http.HttpConnectionException;
import org.henrya.httpicnic.http.HttpRequest;
import org.henrya.httpicnic.http.HttpResponse;

import org.henrya.httpicnic.utils.PicnicUtils;

/**
 * A class for sending PATCH requests
 * @author Henry Anderson
 */
public class HttpPatch extends HttpRequest {

	/**
	 * Constructs a new PATCH request
	 * @param url The URL the PATCH request will be sent to
	 */
	public HttpPatch(String url) {
		super(url);
	}

	/**
	 * Sends the HTTP PATCH request using new headers, new parameters, and new cookies
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
			conn.setRequestMethod("PATCH");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if(rawParams != null && !rawParams.isEmpty()) {
				conn.setDoOutput(true);
				DataOutputStream output = new DataOutputStream(conn.getOutputStream());
				output.writeBytes(rawParams);
				output.flush();
				output.close();
			}
			responseCode = conn.getResponseCode();
			responseMessage = conn.getResponseMessage();
			String responseContent = PicnicUtils.parseContent(new BufferedReader(new InputStreamReader(conn.getInputStream())));
			return new HttpResponse(PicnicUtils.parseHeaders(conn.getHeaderFields()), PicnicUtils.parseCookies(conn.getHeaderFields()), responseContent, conn.getResponseCode(), conn.getResponseMessage());
		} catch(ConnectException | UnknownHostException e) {
			throw new HttpConnectionException(this.getURL(), e.getMessage());
		} catch(IOException e) {
			return new HttpResponse(PicnicUtils.parseHeaders(conn.getHeaderFields()), PicnicUtils.parseCookies(conn.getHeaderFields()), null, responseCode, responseMessage);
		}
	}
}