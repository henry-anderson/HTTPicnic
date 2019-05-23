package anderson.henry.httppicnic.examples.webdkp;

public class Main {
	
	public static void main(String[] args) {
		WebDKPAccount account = new WebDKPAccount("username123", "password123", "https://webdkp.com/dkp/Aegwynn/ronintest/");
		if(account.login()) {
			System.out.println("Logged in!");
			account.loadPlayers();
			System.out.println("Players loaded!");
			if(account.playerExists("Player123")) {
				account.addPoints(account.getPlayer("Player123"), 50, "This is an example", "Henry", "Wisconsin");
			}
			else {
				account.createPlayer("Player123", 50);
			}
		}
		else {
			System.out.println("Could not login!");
		}
	}
}
