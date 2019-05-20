# HTTPicnic
A Java library that greatly simplifies the process of sending HTTP requests and handling their responses.

[View the Javadocs here](https://henry-anderson.github.io/HTTPicnic/)

<h3>Example Usage</h3>
<pre>
PicnicClient client = new PicnicClient();
client.addParameter("siteUserEvent", "login");
client.addParameter("username", "username123");
client.addParameter("password", "password123");
HttpResponse response = client.sendPOST("https://webdkp.com/login");
System.out.println(response.getContent());
 </pre>
