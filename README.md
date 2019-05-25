<img src="https://img.shields.io/maven-metadata/v/https/repo1.maven.org/maven2/org/henrya/httpicnic/maven-metadata.xml.svg?label=Maven">


# HTTPicnic
HTTPicnic is a Java library that greatly simplifies the process of sending HTTP requests and handling their responses.

[View the Javadocs here](https://henry-anderson.github.io/HTTPicnic/)

<h3>HttpRequest</h3>
The HttpRequest class is an abstract class that is used to send basic HTTP requests.

When creating a new request, the URL that the request will be sent to is defined in the constructor.

<pre>
HttpRequest request = new HttpPost("https://example.com/login");
</pre>

Headers and parameters can then added to the request.

<pre>
HttpRequest request = new HttpRequest("http://example.com/login");
request.addHeader(HttpHeader.ACCEPT, "text/html");
request.addParameter("username", "henry");
request.addParameter("password", "password123");
HttpResponse response = request.sendPOST();
System.out.println(response.getContent();
</pre>

The HttpResponse object contains all the data that the web server replied with.

<h3>PicnicClient</h3>
The PicnicClient class is used to send multiple HTTP requests. It handles the cookies from each request and is useful in cases where cookies need to be stored and sent in every request.

<pre>
PicnicClient client = new PicnicClient();
client.addParameter("username", "henry");
client.addParameter("password", "password123");
client.sendPOST("http://example.com/login");
HttpResponse response = client.sendGET("http://example.com/control_panel");
System.out.println(response.getContent());
</pre>

In this scenario the goal would be to access a control panel that requires the user to be logged in to view. To accomplish this we would send a POST request to the login page which would respond with a session cookie. The PicnicClient class handles  the session cookie and sends it with the next GET request to the user's control panel to show the server that the user is logged in.
