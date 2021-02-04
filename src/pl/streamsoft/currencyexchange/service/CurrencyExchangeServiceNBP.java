package pl.streamsoft.currencyexchange.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import pl.streamsoft.currencyexchange.ExchangeRate;
import pl.streamsoft.currencyexchange.ExchangeRateUtils;
import pl.streamsoft.currencyexchange.exception.CurrencyNotFoundException;
import pl.streamsoft.currencyexchange.exception.ExchangeCurrencyHttpException;
import pl.streamsoft.currencyexchange.exception.GettingExchangeRateTimeoutException;
import pl.streamsoft.currencyexchange.exception.ParsingExchangeRateException;

public class CurrencyExchangeServiceNBP extends CurrencyExchangeService {

	private static final String RATE_URL = "http://api.nbp.pl/api/exchangerates/rates/A/";
	private static final String DATE_URL = "http://api.nbp.pl/api/exchangerates/tables/A/";
	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	protected ExchangeRate getExchangeRate(String currencyCode, Date date) {
		CloseableHttpResponse response = getResponseFromNBP(currencyCode, date);
		handleResponseCode(response.getStatusLine().getStatusCode());
		return getExchangeRateFromEntity(response.getEntity());
	}

	private Date getLastDateWithRate(Date date) throws IOException {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		while (true) {
			HttpGet request = new HttpGet(DATE_URL + simpleDateFormat.format(c.getTime()));
			request.addHeader("Accept", "application/json");
			CloseableHttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return c.getTime();
			}
			response.close();
			c.add(Calendar.DATE, -1);
		}
	}

	private CloseableHttpResponse getResponseFromNBP(String currencyCode, Date date) {
		try {
			date = getLastDateWithRate(date);
			HttpGet request = new HttpGet(RATE_URL + currencyCode + "/" + simpleDateFormat.format(date));
			request.addHeader("Accept", "application/json");
			CloseableHttpResponse response = httpClient.execute(request);
			return response;
		} catch (IOException e) {
			throw new UncheckedIOException("Executing http request failed", e);
		}

	}

	private void handleResponseCode(int responseCode) {
		switch (responseCode) {
		case HttpStatus.SC_OK:
			break;
		case HttpStatus.SC_NOT_FOUND:
			throw new CurrencyNotFoundException("Currency not found");
		case HttpStatus.SC_REQUEST_TIMEOUT:
			throw new GettingExchangeRateTimeoutException("Couldn't get a response from NBP server");
		default:
			throw new ExchangeCurrencyHttpException("Problem occurred during getting response from the server");
		}
	}

	private ExchangeRate getExchangeRateFromEntity(HttpEntity entity) {
		try {
			String body = EntityUtils.toString(entity);
			JSONObject bodyJson = new JSONObject(body);
			return ExchangeRateUtils.getExchangeRateFromJson(bodyJson);
		} catch (IOException e) {
			throw new ParsingExchangeRateException(e.getMessage());
		}

	}
}
