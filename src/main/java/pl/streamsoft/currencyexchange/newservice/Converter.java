package pl.streamsoft.currencyexchange.newservice;

import pl.streamsoft.currencyexchange.ExchangeRate;

public interface Converter {

	public ExchangeRate getExchangeRateFromBody(String body);
}
