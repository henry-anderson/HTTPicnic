package org.henrya.httpicnic.examples.github;

import org.henrya.httpicnic.http.HttpConnectionException;

public class Main {
	
	/**
	 * Logs into http://github.com and follows the user "henry-anderson"
	 */
	public static void main(String[] args) {
		try {
			GitHubAccount account = new GitHubAccount("GitHubUsername", "password123");
			if(account.login()) {
				System.out.println("Logged in!");
				account.follow("henry-anderson");
			}
			else {
				System.out.println("Could not login!");
			}
		} catch(HttpConnectionException e) {
			System.out.println("Failed to connect to " + e.getURL());
		}
	}
}
