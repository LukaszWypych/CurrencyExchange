package pl.streamsoft.currencyexchange;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import pl.streamsoft.currencyexchange.repository.CountryRepository;
import pl.streamsoft.currencyexchange.repository.CountryRepositoryImpl;
import pl.streamsoft.currencyexchange.repository.CurrencyRepository;
import pl.streamsoft.currencyexchange.repository.CurrencyRepositoryImpl;
import pl.streamsoft.currencyexchange.repository.ExchangeRateRepository;
import pl.streamsoft.currencyexchange.repository.ExchangeRateRepositoryImpl;
import pl.streamsoft.currencyexchange.service.CurrencyExchangeService;
import pl.streamsoft.currencyexchange.service.ExchangeRateService;
import pl.streamsoft.currencyexchange.service.converter.Converter;
import pl.streamsoft.currencyexchange.service.converter.ConverterJson;
import pl.streamsoft.currencyexchange.service.datareader.DataReader;
import pl.streamsoft.currencyexchange.service.datareader.DataReaderNBP;

public class Main {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepositoryImpl();
		CurrencyRepository currencyRepository = new CurrencyRepositoryImpl();
		CountryRepository countryRepository = new CountryRepositoryImpl();
		ExchangeRateService service = new ExchangeRateService(currencyRepository, exchangeRateRepository,
				countryRepository);
		DataReader dataReader = new DataReaderNBP("json");
		Converter converter = new ConverterJson();
		CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService(dataReader, converter, service);
		ExchangedCurrency exchangedCurrency = currencyExchangeService.exchangeCurrencyToPLN("EUR",
				simpleDateFormat.parse("2021-2-9"), new BigDecimal("100"));
		System.out.println("Currency exchanged with the rate on the date "
				+ simpleDateFormat.format(exchangedCurrency.getDate()) + " is " + exchangedCurrency.getValue());
	}
}