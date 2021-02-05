package pl.streamsoft.currencyexchange.service;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import pl.streamsoft.currencyexchange.ExchangeRate;
import pl.streamsoft.currencyexchange.exception.CurrencyNotFoundException;

public abstract class CurrencyExchangeServiceFile extends CurrencyExchangeService {

	@Override
	protected ExchangeRate getExchangeRate(String currencyCode, Date date) {
		File file = getFile(date);
		return getExchangeRateFromFile(file);
	}

	private File getFile(Date date) {

		File file = new File(date.toString() + "." + getExtension());
		if (!file.exists()) {
			throw new CurrencyNotFoundException("Currency file not found");
		}
		return file;
	}

	@Override
	protected Date getLastDateWithRate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		while (true) {
			File file = new File(date.toString() + "." + getExtension());
			if (file.exists()) {
				return date;
			}
			c.add(Calendar.DATE, -1);
		}
	}

	abstract protected ExchangeRate getExchangeRateFromFile(File file);

	abstract protected String getExtension();
}
