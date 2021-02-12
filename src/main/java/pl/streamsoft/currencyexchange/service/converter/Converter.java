package pl.streamsoft.currencyexchange.service.converter;

import pl.streamsoft.currencyexchange.entity.ExchangeRateEntity;

public interface Converter {

	public ExchangeRateEntity getExchangeRateFromBody(String body);
}
