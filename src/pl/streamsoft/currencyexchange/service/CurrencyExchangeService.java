package pl.streamsoft.currencyexchange.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.regex.Pattern;

import pl.streamsoft.currencyexchange.ExchangeRate;
import pl.streamsoft.currencyexchange.ExchangedCurrency;

public abstract class CurrencyExchangeService {

	public ExchangedCurrency exchangeCurrencyToPLN(String currencyCode, Date date, BigDecimal value) {
		handleInputArguments(currencyCode, date, value);
		date = getLastDateWithRate(date);
		ExchangeRate rate = getExchangeRate(currencyCode, date);
		BigDecimal exchangedValue = value.multiply(rate.getValue()).setScale(2, RoundingMode.HALF_UP);
		ExchangedCurrency exchangedCurrency = new ExchangedCurrency(exchangedValue, rate.getDate());
		return exchangedCurrency;
	}

	private void handleInputArguments(String currencyCode, Date date, BigDecimal value)
			throws IllegalArgumentException {
		if (currencyCode == null || currencyCode.length() != 3 || !Pattern.matches("[a-zA-Z]+", currencyCode)) {
			throw new IllegalArgumentException("Invalid currency code");
		}
		if (date == null || date.after(new Date())) {
			throw new IllegalArgumentException("Invalid date");
		}
		if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Invalid value");
		}
	}

	abstract protected Date getLastDateWithRate(Date date);

	abstract protected ExchangeRate getExchangeRate(String currencyCode, Date date);
}
