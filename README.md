# HTTPicnic

<img src="https://img.shields.io/maven-metadata/v/https/repo1.maven.org/maven2/org/henrya/httpicnic/maven-metadata.xml.svg?label=Maven"> <a href="https://github.com/henry-anderson/HTTPicnic/blob/master/LICENSE"><img src="https://img.shields.io/badge/License-MIT-blue.svg"></a>

HTTPicnic is a Java library that greatly simplifies the process of sending HTTP requests and handling their responses.

[View the Javadocs here](https://henry-anderson.github.io/HTTPicnic/)

## Maven
```xml
<dependency>
  <groupId>org.henrya</groupId>
  <artifactId>httpicnic</artifactId>
  <version>1.0.0</version>
</dependency>
```

### HttpRequest
The HttpRequest class is an abstract class that is used to send basic HTTP requests.

When creating a new request, the URL that the request will be sent to is defined in the constructor.

```java
HttpRequest request = new HttpPost("https://example.com/login");
```

Headers and parameters can then added to the request.

```java
HttpRequest request = new HttpRequest("http://example.com/login");
request.addHeader(HttpHeader.ACCEPT, "text/html");
request.addParameter("username", "henry");
request.addParameter("password", "password123");
HttpResponse response = request.sendPOST();
System.out.println(response.getContent();
```

The HttpResponse object contains all the data that the web server replied with.

### PicnicClient
The PicnicClient class is used to send multiple HTTP requests. It handles the cookies from each request and is useful in cases where cookies need to be stored and sent in every request.

```java
PicnicClient client = new PicnicClient();
client.addParameter("username", "henry");
client.addParameter("password", "password123");
client.sendPOST("http://example.com/login");
HttpResponse response = client.sendGET("http://example.com/control_panel");
System.out.println(response.getContent());
```

In this scenario the goal would be to access a control panel that requires the user to be logged in to view. To accomplish this we would send a POST request to the login page which would respond with a session cookie. The PicnicClient class handles  the session cookie and sends it with the next GET request to the user's control panel to show the server that the user is logged in.
