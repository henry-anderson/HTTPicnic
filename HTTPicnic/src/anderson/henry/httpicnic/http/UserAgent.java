package anderson.henry.httpicnic.http;

public enum UserAgent {
	GOOGLE_CHROME("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
	
	private String value;
	
	UserAgent(String value) {
		this.value = value;
	}
	
	public String getText() {
		return this.value;
	}
}
