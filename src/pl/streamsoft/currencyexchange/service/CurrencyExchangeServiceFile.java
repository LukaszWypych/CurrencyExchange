package pl.streamsoft.currencyexchange.service;

import java.io.File;
import java.io.FileReader;
import java.util.Date;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import pl.streamsoft.currencyexchange.ExchangeRate;
import pl.streamsoft.currencyexchange.ExchangeRateUtils;
import pl.streamsoft.currencyexchange.exception.CurrencyNotFoundException;

public class CurrencyExchangeServiceFile extends CurrencyExchangeService {

	@Override
	protected ExchangeRate getExchangeRate(String currencyCode, Date date) {
		File file = getFile(currencyCode, date);
		JSONObject json = getJsonFromFile(file);
		return ExchangeRateUtils.getExchangeRateFromJson(json);
	}

	private File getFile(String currencyCode, Date date) {
		File file = new File(date.toString() + currencyCode + ".json");
		if (!file.exists()) {
			throw new CurrencyNotFoundException("Currency file not found");
		}
		return file;
	}

	private JSONObject getJsonFromFile(File file) {
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader(file)) {
			// Read JSON file
			return (JSONObject) jsonParser.parse(reader);
		} catch (Exception e) {
			return null;
		}
	}
}
