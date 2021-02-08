package pl.streamsoft.currencyexchange.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import pl.streamsoft.currencyexchange.ExchangeRate;
import pl.streamsoft.currencyexchange.ExchangeRateUtils;
import pl.streamsoft.currencyexchange.exception.CurrencyNotFoundException;
import pl.streamsoft.currencyexchange.exception.ParsingExchangeRateException;

public class CurrencyExchangeServiceFileJson extends CurrencyExchangeServiceFile {

	protected ExchangeRate getExchangeRateFromFile(File file) {
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader(file)) {
			JSONObject json = (JSONObject) jsonParser.parse(reader);
			return ExchangeRateUtils.getExchangeRateFromJson(json);
		} catch (ParseException e) {
			throw new ParsingExchangeRateException(e.getMessage());
		} catch (FileNotFoundException e) {
			throw new CurrencyNotFoundException(e.getMessage());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	protected String getFormat() {
		return "json";
	}
}
