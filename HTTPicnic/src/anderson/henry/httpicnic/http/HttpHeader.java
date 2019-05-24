package anderson.henry.httpicnic.http;

/**
 * Represents an HTTP header field
 * @author Henry Anderson
 */
public enum HttpHeader {

	/* Standard Request Headers */
	A_IM("A-IM"),
	ACCEPT("Accept"),
	ACCEPT_CHARSET("Accept-Charset"),
	ACCEPT_ENCODING("Accept-Encoding"),
	ACCEPT_LANGUAGE("Accept-Language"),
	ACCEPT_DATETIME("Accept-Datetime"),
	ACCESS_CONTROL_REQUEST_METHOD("Access-Control-Request-Method"),
	ACCESS_CONTROL_REQUEST_HEADERS("Access-Control-Request-Headers"),
	AUTHORIZATION("Authorization"),
	CACHE_CONTROL("Cache-Control"),
	CONNECTION("Connection"),
	CONTENT_LENGTH("Content-Length"),
	CONTENT_MD5("Content-MD5"),
	CONTENT_TYPE("Content-Type"),
	COOKIE("Cookie"),
	DATE("Date"),
	EXPECT("Expect"),
	FORWARDED("Forwarded"),
	FROM("From"),
	HOST("Host"),
	HTTP2_SETTINGS("HTTP2-Settings"),
	IF_MATCH("If-Match"),
	IF_MODIFIED_SINCE("If-Modified-Since"),
	IF_NONE_MATCH("If-None-Match"),
	IF_RANGE("If-Range"),
	IF_UNMODIFIED_SINCE("If-Unmodified-Since"),
	MAX_FORWARDS("Max-Forwards"),
	ORIGIN("Origin"),
	PRAGMA("Pragma"),
	PROXY_AUTHORIZATION("Proxy-Authorization"),
	RANGE("Range"),
	REFERER("Referer"),
	TE("TE"),
	USER_AGENT("User-Agent"),
	UPGRADE("Upgrade"),
	VIA("Via"),
	WARNING("Warning"),
	
	/* Non-Standard Request Headers */
	UPGRADE_INSECURE_REQUESTS("Upgrade-Insecure-Requests"),
	X_REQUESTED_WITH("X-Requested-With"),
	DNT("DNT"),
	X_FORWARDED_FOR("X-Forwarded-For"),
	X_FORWARDED_PROTO("X-Forwarded-Proto"),
	FRONT_END_HTTPS("Front-End-Https"),
	X_HTTP_METHOD_OVERRIDE("X-Http-Method-Override"),
	X_ATT_DEVICEID("X-ATT-DeviceId"),
	X_WAP_PROFILE("X-Wap-Profile"),
	PROXY_CONNECTION("Proxy-Connection"),
	X_UIDH("X-UIDH"),
	X_CSRF_TOKEN("X-Csrf-Token"),
	X_REQUEST_ID("X-Request-ID"),
	X_CORRELATION_ID("X-Correlation-ID"),
	SAVE_DATA("Save-Data"),
	
	/* Standard Response Headers */
	ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin"),
	ACCESS_CONTROL_ALLOW_CREDENTIALS("Access-Control-Allow-Credentials"),
	ACCESS_CONTROL_EXPOSE_HEADERS("Access-Control-Expose-Headers"),
	ACCESS_CONTROL_MAX_AGE("Access-Control-Max-Age"),
	ACCESS_CONTROL_ALLOW_METHODS("Access-Control-Allow-Methods"),
	ACCESS_CONTROL_ALLOW_HEADERS("Access-Control-Allow-Headers"),
	ACCEPT_PATCH("Accept-Patch"),
	ACCEPT_RANGES("Accept-Ranges"),
	AGE("Age"),
	ALLOW("Allow"),
	ALT_SVC("Alt-Svc"),
	CONTENT_DISPOSITION("Content-Disposition"),
	CONTENT_LANGUAGE("Content-Language"),
	CONTENT_LOCATION("Content-Location"),
	DELTA_BASE("Delta-Base"),
	ETAG("ETag"),
	EXPIRES("Expires"),
	IM("IM"),
	LAST_MODIFIED("Last-Modified"),
	LINK("Link"),
	LOCATION("Location"),
	P3P("P3P"),
	PROXY_AUTHENTICATE("Proxy-Authenticate"),
	PUBLIC_KEY_PINS("Public-Key-Pins"),
	RETRY_AFTER("Retry-After"),
	SERVER("Server"),
	SET_COOKIE("Set-Cookie"),
	STRICT_TRANSPORT_SECURITY("Strict-Transport-Security"),
	TRAILER("Trailer"),
	TRANSFER_ENCODING("Transfer-Encoding"),
	TK("Tk"),
	VARY("Vary"),
	WWW_AUTHENTICATE("WWW-Authenticate"),
	X_FRAME_OPTIONS("X-Frame-Options"),
	
	/* Non-Standard Response Headers */
	CONTENT_SECURITY_POLICY("Content-Security-Policy"),
	X_CONTENT_SECURITY_POLICY("X-Content-Security-Policy"),
	X_WEBKIT_CSP("X-WebKit-CSP"),
	REFRESH("Refresh"),
	STATUS("Status"),
	TIMING_ALLOW_ORIGIN("Timing-Allow-Origin"),
	X_CONTENT_DURATION("X-Content-Duration"),
	X_CONTENT_TYPE_OPTIONS("X-Content-Type-Options"),
	X_POWERED_BY("X-Powered-By"),
	X_UA_COMPATIBLE("X-UA-Compatible"),
	X_XSS_PROTECTION("X-XSS-Protection");
	
	private String text;
	
	HttpHeader(String text) {
		this.text = text;
	}
	
	/**
	 * Returns the String equivalent of the enum
	 * @return The text equivalent
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Returns the HttpHeader representation of the header field
	 * @param name The name of the header field
	 * @return The HttpHeader
	 */
	public static HttpHeader get(String name) {
		for(HttpHeader field : HttpHeader.values()) {
			if(field.getText() != null && field.getText().equalsIgnoreCase(name)) {
				return field;
			}
		}
		return null;
	}
}
