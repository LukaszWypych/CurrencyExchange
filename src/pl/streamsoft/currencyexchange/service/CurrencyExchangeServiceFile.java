package pl.streamsoft.currencyexchange.service;

import java.io.FileReader;
import java.util.Date;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import pl.streamsoft.currencyexchange.ExchangeRate;
import pl.streamsoft.currencyexchange.ExchangeRateUtils;

public class CurrencyExchangeServiceFile extends CurrencyExchangeService {

	@Override
	protected ExchangeRate getExchangeRate(String currencyCode, Date date) {
		JSONObject json = getJsonFromFile();
		return ExchangeRateUtils.getExchangeRateFromJson(json);
	}

	public JSONObject getJsonFromFile() {
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader("employees.json")) {
			// Read JSON file
			return (JSONObject) jsonParser.parse(reader);
		} catch (Exception e) {
			return null;
		}
	}
}
