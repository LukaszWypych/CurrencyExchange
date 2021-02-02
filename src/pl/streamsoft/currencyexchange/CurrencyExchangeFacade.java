package pl.streamsoft.currencyexchange;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class CurrencyExchangeFacade {

	private static final String GET_URL = "http://api.nbp.pl/api/exchangerates/rates/A/";
	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	public BigDecimal exchangeCurrencyToPLN(String currencyCode, BigDecimal value) throws IOException {
		currencyCode = currencyCode.toUpperCase();
		BigDecimal rate = getExchangeRateForCurrency(currencyCode);
		if (rate == null) {
			throw new IOException("Getting exchange rate failed");
		}
		return value.multiply(rate).setScale(2, RoundingMode.HALF_UP);
	}

	private BigDecimal getExchangeRateForCurrency(String currencyCode) throws IOException {
		BigDecimal rate = null;
		HttpGet request = new HttpGet(GET_URL + currencyCode);
		request.addHeader("Accept", "application/json");
		CloseableHttpResponse response = httpClient.execute(request);
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new IOException("Request failed. Status code: " + response.getStatusLine().getStatusCode());
		}
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String body = EntityUtils.toString(entity);
			JSONObject bodyJson = new JSONObject(body);
			JSONObject ratesJson = new JSONObject(bodyJson.getJSONArray("rates").get(0).toString());
			rate = new BigDecimal(ratesJson.get("mid").toString());
		}
		return rate;
	}
}
