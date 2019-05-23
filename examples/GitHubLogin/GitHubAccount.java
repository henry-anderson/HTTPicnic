package anderson.henry.githublogin;

import anderson.henry.httpicnic.PicnicClient;
import anderson.henry.httpicnic.http.HttpHeader;
import anderson.henry.httpicnic.http.HttpResponse;

public class GitHubAccount {
	private PicnicClient client;
	private String username;
	private String password;
	
	/**
	 * Creates a new GitHubAccount instance representing a user on GitHub
	 * @param username The GitHub account's username
	 * @param password The GitHub account's password
	 */
	public GitHubAccount(String username, String password) {
		this.username = username;
		this.password = password;
		this.client = new PicnicClient();
	}
	
	/**
	 * Logs into GitHub.com
	 * Note: Before sending a POST request to login you must get an "authenticity token" from a form on the login page
	 * @return a boolean representing whether the login was successful
	 */ 
	public boolean login() {
		HttpResponse response = client.sendGET("https://github.com/login");
		String token = response.getContent().split("name=\"authenticity_token\" value=\"")[1].split("\" />")[0];
		client.setHeader(HttpHeader.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36");
		client.addParameter("commit", "Sign in");
		client.addParameter("utf8", "&#x2713;");
		client.addParameter("authenticity_token", token);
		client.addParameter("login", this.username);
		client.addParameter("password", this.password);
		client.addParameter("webauthn-support", "supported");
		response = this.client.sendPOST("https://github.com/session");
		return client.getCookie("logged_in").getValue().equalsIgnoreCase("yes");
	}
}
