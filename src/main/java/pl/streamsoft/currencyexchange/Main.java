package pl.streamsoft.currencyexchange;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;

import pl.streamsoft.currencyexchange.repository.CountryRepository;
import pl.streamsoft.currencyexchange.repository.CountryRepositoryImpl;
import pl.streamsoft.currencyexchange.repository.CurrencyRepository;
import pl.streamsoft.currencyexchange.repository.CurrencyRepositoryImpl;
import pl.streamsoft.currencyexchange.repository.ExchangeRateRepository;
import pl.streamsoft.currencyexchange.repository.ExchangeRateRepositoryImpl;
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
		LinkedHashSet<DataReader> dataReaders = new LinkedHashSet<>();
		dataReaders.add(dataReader);

//		countryRepository.getCountriesWithCurrencies(2).forEach(c -> System.out.println(c.getName()));

//		CurrencyEntity c = currencyRepository.getCurrencyWithBiggestRateDifferenceInPeriod(
//				simpleDateFormat.parse("2021-1-10"), simpleDateFormat.parse("2021-3-11"));
//		System.out.println(c.getName());

//		BigDecimal r = exchangeRateRepository.getMaxRateFromPeriodForCurrency("USD",
//				simpleDateFormat.parse("2021-1-10"), simpleDateFormat.parse("2021-3-11"));
//		BigDecimal r = exchangeRateRepository.getMinRateFromPeriodForCurrency("USD",
//				simpleDateFormat.parse("2021-1-10"), simpleDateFormat.parse("2021-3-11"));
//		System.out.println(r);
//		exchangeRateRepository.getMaxRatesForCurrency("EUR", 2).forEach(v -> System.out.println(v));
//		exchangeRateRepository.getMinRatesForCurrency("USD", 2).forEach(v -> System.out.println(v));

//		CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService(dataReaders, converter, service);
//		ExchangedCurrency exchangedCurrency = currencyExchangeService.exchangeCurrencyToPLN("usd",
//				simpleDateFormat.parse("2021-2-10"), new BigDecimal("100"));
//		System.out.println("Currency exchanged with the rate on the date "
//				+ simpleDateFormat.format(exchangedCurrency.getDate()) + " is " + exchangedCurrency.getValue());
//		service.getCountryByName("Ukraine").getCurrencies().forEach(c -> c.getRates()
//				.forEach(r -> System.out.println(c.getName() + "," + r.getValue() + "," + r.getDate())));
//		service.addExchangeRate(new ExchangeRateEntity(new BigDecimal("3"), new Date()), "XXX");
	}
}