package pl.streamsoft.currencyexchange.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import pl.streamsoft.currencyexchange.ExchangeRate;
import pl.streamsoft.currencyexchange.exception.CurrencyNotFoundException;

public class CurrencyExchangeServiceNBP extends CurrencyExchangeService {

	private static final String NBP_URL = "http://api.nbp.pl/api/exchangerates/rates/A/";
	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	@Override
	protected ExchangeRate getExchangeRate(String currencyCode, BigDecimal value)
			throws ParseException, IOException, CurrencyNotFoundException, HttpTimeoutException, HttpException, JSONException, java.text.ParseException {
		CloseableHttpResponse response = getResponseFromNBP(currencyCode);
		handleResponseCode(response.getStatusLine().getStatusCode());
		return getExchangeRateFromEntity(response.getEntity());
	}

	private CloseableHttpResponse getResponseFromNBP(String currencyCode) throws ClientProtocolException, IOException {
		HttpGet request = new HttpGet(NBP_URL + currencyCode);
		request.addHeader("Accept", "application/json");
		CloseableHttpResponse response = httpClient.execute(request);
		return response;
	}

	private void handleResponseCode(int responseCode)
			throws CurrencyNotFoundException, HttpTimeoutException, HttpException {
		switch (responseCode) {
		case HttpStatus.SC_OK:
			break;
		case HttpStatus.SC_NOT_FOUND:
			throw new CurrencyNotFoundException("Currency not found");
		case HttpStatus.SC_REQUEST_TIMEOUT:
			throw new HttpTimeoutException("Couldn't get a response from NBP server");
		default:
			throw new HttpException("Problem occurred during getting response from the server");
		}
	}

	private ExchangeRate getExchangeRateFromEntity(HttpEntity entity) throws ParseException, IOException, JSONException, java.text.ParseException {
		String body = EntityUtils.toString(entity);
		JSONObject bodyJson = new JSONObject(body);
		JSONObject ratesJson = new JSONObject(bodyJson.getJSONArray("rates").get(0).toString());
		String code = bodyJson.get("code").toString();
		BigDecimal rate = new BigDecimal(ratesJson.get("mid").toString());
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(ratesJson.get("effectiveDate").toString());
		ExchangeRate exchangeRate = new ExchangeRate(code,rate,date);
		return exchangeRate;
	}
}
