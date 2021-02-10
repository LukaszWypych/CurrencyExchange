package pl.streamsoft.currencyexchange.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import pl.streamsoft.currencyexchange.ExchangeRate;
import pl.streamsoft.currencyexchange.exception.CurrencyNotFoundException;
import pl.streamsoft.currencyexchange.exception.ExchangeCurrencyHttpException;
import pl.streamsoft.currencyexchange.exception.GettingExchangeRateTimeoutException;
import pl.streamsoft.currencyexchange.exception.ParsingExchangeRateException;

public abstract class CurrencyExchangeServiceNBP extends CurrencyExchangeService {

	private static final String RATE_URL = "http://api.nbp.pl/api/exchangerates/rates/A/";
	private static final String DATE_URL = "http://api.nbp.pl/api/exchangerates/tables/A/";
	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	protected ExchangeRate getExchangeRate(String currencyCode, Date date) {
		CloseableHttpResponse response = getResponseFromNBP(currencyCode, date);
		handleResponseCode(response.getStatusLine().getStatusCode());
		try {
			return getExchangeRateFromBody(EntityUtils.toString(response.getEntity()));
		} catch (IOException e) {
			throw new ParsingExchangeRateException(e.getMessage());
		}
	}

	private CloseableHttpResponse getResponseFromNBP(String currencyCode, Date date) {
		try {
			HttpGet request = new HttpGet(RATE_URL + currencyCode + "/" + simpleDateFormat.format(date));
			request.addHeader("Accept", "application/" + getFormat());
			CloseableHttpResponse response = httpClient.execute(request);
			return response;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	protected boolean isDateValid(Date date) {
		try {
			HttpGet request = new HttpGet(DATE_URL + simpleDateFormat.format(date));
			request.addHeader("Accept", "application/" + getFormat());
			CloseableHttpResponse response = httpClient.execute(request);
			int status = response.getStatusLine().getStatusCode();
			response.close();
			return isDateResponseValid(status);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private void handleResponseCode(int responseCode) {
		switch (responseCode) {
		case HttpStatus.SC_OK:
			break;
		case HttpStatus.SC_NOT_FOUND:
			throw new CurrencyNotFoundException("Currency not found");
		default:
			handleErrorResponse(responseCode);
		}
	}

	private boolean isDateResponseValid(int responseCode) {
		switch (responseCode) {
		case HttpStatus.SC_OK:
			return true;
		case HttpStatus.SC_NOT_FOUND:
			return false;
		default:
			handleErrorResponse(responseCode);
			return false;
		}
	}

	private void handleErrorResponse(int reponseCode) {
		switch (reponseCode) {
		case HttpStatus.SC_REQUEST_TIMEOUT:
			throw new GettingExchangeRateTimeoutException("Couldn't get a response from NBP server");
		default:
			throw new ExchangeCurrencyHttpException("Problem occurred during getting response from the server");
		}
	}

	abstract protected ExchangeRate getExchangeRateFromBody(String body);

	abstract protected String getFormat();
}
