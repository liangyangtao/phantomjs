package com.ylx;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Tes {
	public static void main(String[] args) {
		// new WChangeTo3().update();
		int i = 0;
		while (i < 10) {
			try {
				String url_str = "http://192.168.1.95:8080/sfd/HelloWorld.action?id=" + i;
				URL url = new URL(url_str);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.connect();
				connection.getResponseCode();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}

	}

}
