package pl.streamsoft.currencyexchange.service;

import org.json.JSONObject;

import pl.streamsoft.currencyexchange.ExchangeRate;
import pl.streamsoft.currencyexchange.ExchangeRateUtils;

public class CurrencyExchangeServiceNBPJson extends CurrencyExchangeServiceNBP {

	@Override
	protected ExchangeRate getExchangeRateFromBody(String body) {
		JSONObject bodyJson = new JSONObject(body);
		return ExchangeRateUtils.getExchangeRateFromJson(bodyJson);
	}
}
