package anderson.henry.httppicnic.examples.webdkp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import anderson.henry.httpicnic.PicnicClient;
import anderson.henry.httpicnic.http.HttpConnectionException;
import anderson.henry.httpicnic.http.HttpResponse;

/**
 * This class is referencing an account on https://webdkp.com
 * It has a number of different methods for programatically modifying DKP tables
 * This class uses HTTPicnic to handle HTTP requests
 * @author Henry Anderson
 */
public class WebDKPAccount {
	private PicnicClient client;
	private String username;
	private String password;
	private String tableUrl;
	private List<WebDKPPlayer> players = new ArrayList<WebDKPPlayer>();
	
	/**
	 * Creates a new WebDKPAccount instance representing an account on WebDKP
	 * @param username The GitHub account's username
	 * @param password The GitHub account's password
	 */
	public WebDKPAccount(String username, String password, String tableUrl) {
		this.username = username;
		this.password = password;
		this.tableUrl = tableUrl;
		this.client = new PicnicClient();
	}
	
	/**
	 * Logs into webdkp.com
	 * Note: Before sending a POST request to login you must get an "authenticity token" from a form on the login page
	 * @return a boolean representing whether the login was successful
	 * @throws HttpConnectionException Thrown if the connection fails
	 */ 
	public boolean login() throws HttpConnectionException {
		client.addParameter("siteUserEvent", "login");
		client.addParameter("username", this.username);
		client.addParameter("password", this.password);
		HttpResponse response = this.client.sendPOST("https://webdkp.com/login");
		return !response.getContent().contains("Incorrect");
	}
	
	/**
	 * Creates WebDKPPlayer instances of each player in the table
	 * The WebDKPAccount instance must be logged in before loading the players
	 * @throws HttpConnectionException Thrown if the connection fails
	 */
	public void loadPlayers() throws HttpConnectionException {
		boolean keepLoading = true;
		int page = 1;
		while(keepLoading) {
			String url = this.tableUrl.endsWith("/") ? this.tableUrl + "Admin/Manage/" + page + "/date/desc" : this.tableUrl + "/Admin/Manage/" + page + "/date/desc";
			HttpResponse response = this.client.sendGET(url);
			String content = response.getContent();
			if(content.contains("table.Add")) {
				String[] table = content.split("table.Add");
				for(String line : table) {
					if(line.length() > 2 && line.contains("userid")) {
						String jsonString = line.substring(1).split("\\);")[0].trim();
						JSONObject json = new JSONObject(jsonString);
						WebDKPPlayer player = new WebDKPPlayer(json.getString("player"), json.getInt("userid"), json.getDouble("dkp"), json.getDouble("lifetime"));
						this.players.add(player);
					}
				}
			}
			else {
				keepLoading = false;
			}
			page++;
		}
	}
	
	/**
	 * Adds points to an existing WebDKP player
	 * @param player The player to add points to
	 * @param amount The amount of points
	 * @param reason The reason
	 * @param awardedBy The person awarding the points
	 * @param location Location the points are being awarded from
	 * @throws HttpConnectionException Thrown if the connection fails
	 */
	public void addPoints(WebDKPPlayer player, int amount, String reason, String awardedBy, String location) throws HttpConnectionException {
		String url = this.tableUrl.endsWith("/") ? this.tableUrl + "Admin/CreateAward/" : this.tableUrl + "/Admin/CreateAward/";
		this.client.addParameter("ajax", "CreateAward");
		this.client.addParameter("playerids", String.valueOf(player.getID()));
		this.client.addParameter("cost", String.valueOf(amount));
		this.client.addParameter("reason", reason);
		this.client.addParameter("awardedby", awardedBy);
		this.client.addParameter("location", location);
		this.client.addParameter("_", "");
		this.client.sendPOST(url);
	}
	
	/**
	 * Creates a new WebDKP player
	 * @param name The name of the player
	 * @param amount The amount of points to add
	 * @throws HttpConnectionException Thrown if the connection fails
	 */
	public void createPlayer(String name, int amount) throws HttpConnectionException {
		String url = this.tableUrl.endsWith("/") ? this.tableUrl + "Admin/Manage/" : this.tableUrl + "/Admin/Manage/";
		this.client.addParameter("ajax", "AddPlayer");
		this.client.addParameter("name", name);
		this.client.addParameter("dkp", String.valueOf(amount));
		this.client.addParameter("_", "");
		this.client.sendPOST(url);
	}
	
	/**
	 * Returns a list of WebDKPPlayer instances
	 * @return The players
	 */
	public List<WebDKPPlayer> getPlayers() {
		return this.players;
	}
	
	/**
	 * Returns whether a player exists
	 * @param name The name of the player
	 * @return A boolean
	 */
	public boolean playerExists(String name) {
		for(WebDKPPlayer player : this.players) {
			if(player.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets a WebDKPPlayer from their name
	 * @param name The name of the player
	 * @return The WebDKPPlayer instance of the player
	 */
	public WebDKPPlayer getPlayer(String name) {
		for(WebDKPPlayer player : this.players) {
			if(player.getName().equalsIgnoreCase(name)) {
				return player;
			}
		}
		return null;
	}
}
