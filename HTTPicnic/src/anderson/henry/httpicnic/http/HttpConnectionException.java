package anderson.henry.httpicnic.http;

/**
 * An exception that is thrown when there is a problem connecting to the server
 * @author Henry Anderson
 */
public class HttpConnectionException extends Exception {
	private static final long serialVersionUID = -5210465527460214727L;
	private String url;
	
	/**
	 * Constructs a new HttpConnectionException
	 * @param url The URL that is causing the exception to be thrown
	 * @param message A message explaining why the exception was thrown
	 */
	public HttpConnectionException(String url, String message) {
		super(message);
		this.url = url;
	}
	
	/**
	 * Returns the URL that caused the exception to be thrown
	 * @return The URL
	 */
	public String getURL() {
		return this.url;
	}
}
