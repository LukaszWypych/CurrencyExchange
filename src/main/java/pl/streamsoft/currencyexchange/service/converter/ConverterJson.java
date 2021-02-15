package pl.streamsoft.currencyexchange.service.converter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import pl.streamsoft.currencyexchange.entity.ExchangeRateEntity;
import pl.streamsoft.currencyexchange.exception.ParsingExchangeRateException;

public class ConverterJson implements Converter {

	@Override
	public ExchangeRateEntity getExchangeRateFromBody(String body) {
		JSONObject bodyJson = new JSONObject(body);
		return getExchangeRateFromJson(bodyJson);
	}

	private ExchangeRateEntity getExchangeRateFromJson(JSONObject json) {
		try {
			JSONObject ratesJson = new JSONObject(json.getJSONArray("rates").get(0).toString());
			BigDecimal rate = new BigDecimal(ratesJson.get("mid").toString());
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(ratesJson.get("effectiveDate").toString());
			ExchangeRateEntity exchangeRate = new ExchangeRateEntity(rate, date);
			return exchangeRate;
		} catch (ParseException | JSONException e) {
			throw new ParsingExchangeRateException(e.getMessage());
		}
	}
}
