package edu.escuelaing.arep.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpStockServiceImp implements HttpStockService {
	private String url = "http://api.openweathermap.org/data/2.5";
	private String key = "cacc365971fdc4c009bca29a9d6850cb";

	public String getWeather(String ciudad) throws Exception {

		try {

			URL obj = new URL(url + "/weather?q=" + ciudad + "&appid=" + key);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");
			StringBuffer response = null;
			int responseCode = con.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
					System.out.println("Entra al response");
					System.out.println(inputLine);
				}
				in.close();
			} 
			return String.valueOf(response);

		} catch (IOException ex) {
			throw new Exception(ex.getMessage());
		}
	}
}
