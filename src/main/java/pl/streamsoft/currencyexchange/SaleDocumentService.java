package pl.streamsoft.currencyexchange;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.streamsoft.currencyexchange.newservice.Converter;
import pl.streamsoft.currencyexchange.newservice.ConverterJson;
import pl.streamsoft.currencyexchange.newservice.DataReader;
import pl.streamsoft.currencyexchange.newservice.DataReaderNBP;
import pl.streamsoft.currencyexchange.newservice.CurrencyExchangeService;
import pl.streamsoft.currencyexchange.service.CurrencyExchangeServiceNBPJson;

public class SaleDocumentService {

	private static void insert()  {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = simpleDateFormat.parse("2021-2-7");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		DataReader dataReader = new DataReaderNBP("json");
		Converter converter = new ConverterJson();
		CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService(dataReader, converter);
//		CurrencyExchangeService currencyExchangeService = new CurrencyExchangeServiceNBPJson();
		ExchangedCurrency exchangedCurrency = currencyExchangeService.exchangeCurrencyToPLN("usd", date,
				new BigDecimal("100"));
		System.out.println("Currency exchanged with the rate on the date "
				+ simpleDateFormat.format(exchangedCurrency.getDate()) + " is " + exchangedCurrency.getValue());
	}

	public static void main(String[] args)  {
		insert();
	}
}
