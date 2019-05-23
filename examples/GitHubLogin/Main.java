package anderson.henry.httppicnic.examples.githublogin;

public class Main {
	
	/**
	 * Logs into http://github.com
	 */
	public static void main(String[] args) {
		GitHubAccount account = new GitHubAccount("henry-anderson", "Hheennrryy1");
		if(account.login())
			System.out.println("Logged in!");
		else
			System.out.println("Could not login!");
	}
}
