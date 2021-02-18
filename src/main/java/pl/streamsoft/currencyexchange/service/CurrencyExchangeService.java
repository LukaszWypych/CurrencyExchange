package pl.streamsoft.currencyexchange.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

import pl.streamsoft.currencyexchange.ExchangedCurrency;
import pl.streamsoft.currencyexchange.entity.ExchangeRateEntity;
import pl.streamsoft.currencyexchange.exception.CurrencyNotFoundException;
import pl.streamsoft.currencyexchange.service.converter.Converter;
import pl.streamsoft.currencyexchange.service.datareader.DataReader;

public class CurrencyExchangeService {

	private ExchangeRateService exchangeRateService;

	private Set<DataReader> dataReaders;

	private Converter converter;

	public CurrencyExchangeService(Set<DataReader> dataReaders, Converter converter,
			ExchangeRateService exchangeRateService) {
		this.dataReaders = dataReaders;
		this.converter = converter;
		this.exchangeRateService = exchangeRateService;
	}

	public ExchangedCurrency exchangeCurrencyToPLN(String currencyCode, Date date, BigDecimal value) {
		handleInputArguments(currencyCode, date, value);
		ExchangeRateEntity rate = exchangeRateService.getExchangeRateByCode(currencyCode, date);
		if (rate == null) {
			date = getLastDateWithRate(date);
			rate = exchangeRateService.getExchangeRateByCode(currencyCode, date);
			if (rate == null) {
				String body = getBodyFromReaders(date, currencyCode);
				rate = converter.getExchangeRateFromBody(body);
				exchangeRateService.addExchangeRate(rate, currencyCode);
			}
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
			for (DataReader d : dataReaders) {
				if (d.isDateValid(c.getTime())) {
					return c.getTime();
				}
			}
			c.add(Calendar.DATE, -1);
		}
	}

	private String getBodyFromReaders(Date date, String currencyCode) {
		String body = null;
		for (DataReader d : dataReaders) {
			try {
				body = d.getExchangeRateBody(currencyCode, date);
				break;
			} catch (CurrencyNotFoundException e) {
				continue;
			}
		}
		if (body == null) {
			throw new CurrencyNotFoundException("Currency not found");
		}
		return body;
	}
}
