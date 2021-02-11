package pl.streamsoft.currencyexchange.service.converter;

import pl.streamsoft.currencyexchange.ExchangeRate;

public interface Converter {

	public ExchangeRate getExchangeRateFromBody(String body);
}
