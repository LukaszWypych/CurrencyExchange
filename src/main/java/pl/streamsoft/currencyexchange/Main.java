package pl.streamsoft.currencyexchange;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.streamsoft.currencyexchange.repository.Repository;
import pl.streamsoft.currencyexchange.repository.RepositoryHibernate;
import pl.streamsoft.currencyexchange.service.CurrencyExchangeService;
import pl.streamsoft.currencyexchange.service.ExchangeRateService;
import pl.streamsoft.currencyexchange.service.converter.Converter;
import pl.streamsoft.currencyexchange.service.converter.ConverterJson;
import pl.streamsoft.currencyexchange.service.datareader.DataReader;
import pl.streamsoft.currencyexchange.service.datareader.DataReaderNBP;

public class Main {

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = simpleDateFormat.parse("2021-2-5");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		Repository repository = new RepositoryHibernate();
		ExchangeRateService exchangeRateService = new ExchangeRateService(repository);

//		ExchangeRateEntity rate = exchangeRateService.getExchangeRateByCode("usd", date);
//		rate.setValue(new BigDecimal("2"));
//		exchangeRateService.updateExchangeRate(rate);
//		ExchangeRateEntity exchangeRate = new ExchangeRateEntity("usd", new BigDecimal("2"), date);
//		exchangeRateService.addExchangeRate(exchangeRate);

		exchangeRateService.getAllExchangeRates(date)
				.forEach(r -> System.out.println(r.getCode() + "," + r.getValue()));
		DataReader dataReader = new DataReaderNBP("json");
		Converter converter = new ConverterJson();
		CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService(dataReader, converter,
				repository);
		ExchangedCurrency exchangedCurrency = currencyExchangeService.exchangeCurrencyToPLN("USD",
				simpleDateFormat.parse("2021-2-7"), new BigDecimal("100"));
		System.out.println("Currency exchanged with the rate on the date "
				+ simpleDateFormat.format(exchangedCurrency.getDate()) + " is " + exchangedCurrency.getValue());
	}
}