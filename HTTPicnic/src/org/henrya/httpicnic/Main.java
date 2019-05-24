package org.henrya.httpicnic;

import org.henrya.httpicnic.http.HttpConnectionException;
import org.henrya.httpicnic.http.HttpResponse;

public class Main {
	public static void main(String[] args) {
		try {
			PicnicClient client = new PicnicClient();
			HttpResponse response = client.sendOPTIONS("https://ww.google.com/");
			System.out.println(response.getStatusCode() + " " + response.getStatusMessage());
			for(String field : response.getHeaderFields()) {
				System.out.println(field + ": " + response.getHeaderValue(field));
			}
			System.out.println(response.getContent());
		} catch(HttpConnectionException e) {
			e.printStackTrace();
		}
	}
}