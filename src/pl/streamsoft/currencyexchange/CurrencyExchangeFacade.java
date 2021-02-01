package pl.streamsoft.currencyexchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class CurrencyExchangeFacade {

	private static final String GET_URL = "http://api.nbp.pl/api/exchangerates/rates/A/";

	public BigDecimal exchangeCurrencyToPLN(String currency, BigDecimal value) {
		BigDecimal rate = getExchangeRate(currency);
		return value.multiply(rate).setScale(2,RoundingMode.HALF_UP);
	}

	private BigDecimal getExchangeRate(String currency) {
		BigDecimal rate = null;
		try {
			URL url = new URL(GET_URL + currency);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				JSONObject response = new JSONObject(in.readLine());
				JSONObject ratesObject = new JSONObject(response.getJSONArray("rates").get(0).toString());
				rate = new BigDecimal(ratesObject.get("mid").toString());
				in.close();
			} else {
				System.out.println("GET request not worked");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rate;
	}
}
