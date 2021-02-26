package pl.streamsoft.currencyexchange;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pl.streamsoft.currencyexchange.service.CurrencyExchangeService;
import pl.streamsoft.currencyexchange.service.ExchangeRateService;
import pl.streamsoft.currencyexchange.service.converter.Converter;
import pl.streamsoft.currencyexchange.service.converter.ConverterJson;
import pl.streamsoft.currencyexchange.service.datareader.DataReader;
import pl.streamsoft.currencyexchange.service.datareader.DataReaderFile;
import pl.streamsoft.currencyexchange.service.datareader.DataReaderNBP;

public class SaleDocumentService {

	private static void insert() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = simpleDateFormat.parse("2021-2-15");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		ExchangeRateService service = context.getBean(ExchangeRateService.class);

		DataReader dataReader = new DataReaderNBP("json");
		DataReader dataReader2 = new DataReaderFile("C", "json");
		Converter converter = new ConverterJson();
		LinkedHashSet<DataReader> dataReaders = new LinkedHashSet<>();
		dataReaders.add(dataReader2);
		dataReaders.add(dataReader);
		CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService(dataReaders, converter, service);
		ExchangedCurrency exchangedCurrency = currencyExchangeService.exchangeCurrencyToPLN("jpy", date,
				new BigDecimal("100"));
		System.out.println("Currency exchanged with the rate on the date "
				+ simpleDateFormat.format(exchangedCurrency.getDate()) + " is " + exchangedCurrency.getValue());
		context.close();
	}

	public static void main(String[] args) {
		insert();
	}
}
