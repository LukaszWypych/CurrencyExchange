package pl.streamsoft.currencyexchange.service.converter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import pl.streamsoft.currencyexchange.ExchangeRate;
import pl.streamsoft.currencyexchange.exception.ParsingExchangeRateException;

public class ConverterDb implements Converter {

	@Override
	public ExchangeRate getExchangeRateFromBody(String body) {
		JSONObject bodyJson = new JSONObject(body);
		return getExchangeRateFromJson(bodyJson);
	}

	private ExchangeRate getExchangeRateFromJson(JSONObject json) {
		try {
			String code = json.get("code").toString();
			BigDecimal rate = new BigDecimal(json.get("value").toString());
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(json.get("date").toString());
			ExchangeRate exchangeRate = new ExchangeRate(code, rate, date);
			return exchangeRate;
		} catch (ParseException | JSONException e) {
			throw new ParsingExchangeRateException(e.getMessage());
		}
	}
}
