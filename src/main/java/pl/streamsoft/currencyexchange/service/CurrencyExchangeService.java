package pl.streamsoft.currencyexchange.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import pl.streamsoft.currencyexchange.ExchangeRate;
import pl.streamsoft.currencyexchange.ExchangedCurrency;
import pl.streamsoft.currencyexchange.service.converter.Converter;
import pl.streamsoft.currencyexchange.service.datareader.DataReader;
import pl.streamsoft.currencyexchange.service.repository.Repository;
import pl.streamsoft.currencyexchange.service.repository.RepositoryHibernate;

public class CurrencyExchangeService {

	private  Repository repository;

	private DataReader dataReader;

	private Converter converter;

	public CurrencyExchangeService(DataReader dataReader, Converter converter, Repository repository) {
		this.dataReader = dataReader;
		this.converter = converter;
		this.repository = repository;
	}
	
	public ExchangedCurrency exchangeCurrencyToPLN(String currencyCode, Date date, BigDecimal value) {
		handleInputArguments(currencyCode, date, value);
		ExchangeRate rate = repository.getExchangeRate(currencyCode, date);
		if (rate == null) {
			date = getLastDateWithRate(date);
			String body = dataReader.getExchangeRateBody(currencyCode, date);
			rate = converter.getExchangeRateFromBody(body);
			repository.addExchangeRate(rate);
		}
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

	private Date getLastDateWithRate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		while (true) {
			if (dataReader.isDateValid(c.getTime())) {
				return c.getTime();
			}
			c.add(Calendar.DATE, -1);
		}
	}
}
