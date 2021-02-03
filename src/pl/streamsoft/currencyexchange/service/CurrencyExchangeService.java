package pl.streamsoft.currencyexchange.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.http.HttpTimeoutException;

import org.apache.http.HttpException;
import org.apache.http.ParseException;
import org.json.JSONException;

import pl.streamsoft.currencyexchange.ExchangeRate;
import pl.streamsoft.currencyexchange.ExchangedCurrency;
import pl.streamsoft.currencyexchange.exception.CurrencyNotFoundException;

public abstract class CurrencyExchangeService {

	public ExchangedCurrency exchangeCurrencyToPLN(String currencyCode, BigDecimal value) throws IllegalArgumentException, ParseException, CurrencyNotFoundException,IOException,  HttpTimeoutException, HttpException, JSONException, java.text.ParseException {
		handleInputArguments(currencyCode, value);
		ExchangeRate rate = getExchangeRate(currencyCode, value);
		BigDecimal exchangedValue = value.multiply(rate.getValue()).setScale(2, RoundingMode.HALF_UP);
		ExchangedCurrency exchangedCurrency = new ExchangedCurrency(exchangedValue, rate.getDate());
		return exchangedCurrency;
	}

	private void handleInputArguments(String currencyCode, BigDecimal value) {
		if (currencyCode.length() != 3 || currencyCode.contains("/") || currencyCode.contains("\\")) {
			throw new IllegalArgumentException("Invalid currency code");
		}
		if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Value should be a positive number");
		}
	}

	abstract protected ExchangeRate getExchangeRate(String currencyCode, BigDecimal value)
			throws ParseException, CurrencyNotFoundException,IOException,  HttpTimeoutException, HttpException, JSONException, java.text.ParseException;
}
