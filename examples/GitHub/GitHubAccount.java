package anderson.henry.httppicnic.examples.github;

import anderson.henry.httpicnic.PicnicClient;
import anderson.henry.httpicnic.http.HttpHeader;
import anderson.henry.httpicnic.http.HttpResponse;

/**
 * This class represents an account on https://github.com
 * It allows you to programmatically sign into GitHub and follow or unfollow a user
 * I didn't use the GitHub API for this class to get around rate limits
 * This class uses HTTPicnic to handle HTTP requests
 * @author Henry Anderson
 */
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
	 * Note: Before sending the POST request to login you must get an "authenticity token" from a form on the login page
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
	
	/**
	 * Follows a user on GitHub
	 * Note: Before sending the POST request to follow a user you must get an "authenticity token" from a form on their page
	 * @param user The name of the user to follow
	 */
	public void follow(String user) {
		HttpResponse response = client.sendGET("https://github.com/" + user);
		String token = response.getContent().split("name=\"authenticity_token\" value=\"")[4].split("\" />")[0];
		client.addParameter("utf-8", "&#x2713;");
		client.addParameter("authenticity_token", token);
		client.sendPOST("https://github.com/users/follow?target=" + user);
	}
	
	/**
	 * Unfollows a user on GitHub
	 * Note: Before sending the POST request to unfollow a user you must get an "authenticity token" from a form on their page
	 * @param user The name of the user to unfollow
	 */
	public void unfollow(String user) {
		HttpResponse response = client.sendGET("https://github.com/" + user);
		String token = response.getContent().split("name=\"authenticity_token\" value=\"")[5].split("\" />")[0];
		client.addParameter("utf-8", "&#x2713;");
		client.addParameter("authenticity_token", token);
		client.sendPOST("https://github.com/users/unfollow?target=" + user);
	}
}
