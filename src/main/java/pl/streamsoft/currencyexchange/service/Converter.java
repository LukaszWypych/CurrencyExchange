package pl.streamsoft.currencyexchange.service;

import pl.streamsoft.currencyexchange.ExchangeRate;

public interface Converter {

	public ExchangeRate getExchangeRateFromBody(String body);
}
