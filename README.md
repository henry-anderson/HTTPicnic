# HTTPicnic
A Java library that greatly simplifies the process of sending HTTP requests and handling their responses.

[View the Javadocs here](https://henry-anderson.github.io/HTTPicnic/)

<h3>HttpRequest</h3>
The HttpRequest class is used to send basic HTTP requests. Currently only POST, GET, and HEAD requests are supported.

When creating a new HttpRequest instance, the URL that the request will be sent to is defined in the constructor.

<pre>
HttpRequest request = new HttpRequest("https://example.com/login");
</pre>

Headers and parameters can then added.

<pre>
HttpRequest request = new HttpRequest("https://example.com/login");
request.setUserAgent(UserAgent.GOOGLE_CHROME);
request.addHeader(HttpHeader.ACCEPT, "text/html");
request.addParameter("username", "henry");
request.addParameter("password", "password123");
HttpResponse response = request.sendPOST();

</pre>

<h3>Example Usage</h3>
<pre>
PicnicClient client = new PicnicClient();
client.addParameter("siteUserEvent", "login");
client.addParameter("username", "username123");
client.addParameter("password", "password123");
HttpResponse response = client.sendPOST("https://webdkp.com/login");
System.out.println(response.getContent());
 </pre>
