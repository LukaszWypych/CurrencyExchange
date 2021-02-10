package pl.streamsoft.currencyexchange.service;

import java.io.File;
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

		File file = new File(date.toString() + "." + getFormat());
		if (!file.exists()) {
			throw new CurrencyNotFoundException("Currency file not found");
		}
		return file;
	}

	@Override
	protected boolean isDateValid(Date date) {
		File file = new File(date.toString() + "." + getFormat());
		return file.exists();
	}

	abstract protected ExchangeRate getExchangeRateFromFile(File file);

	abstract protected String getFormat();
}
