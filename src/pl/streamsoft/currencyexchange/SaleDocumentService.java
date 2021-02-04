package pl.streamsoft.currencyexchange;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.streamsoft.currencyexchange.service.CurrencyExchangeService;
import pl.streamsoft.currencyexchange.service.CurrencyExchangeServiceNBP;

public class SaleDocumentService {

	private static void insert() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = simpleDateFormat.parse("2021-01-31");
		CurrencyExchangeService currencyExchangeFacade = new CurrencyExchangeServiceNBP();
		ExchangedCurrency exchangedCurrency = currencyExchangeFacade.exchangeCurrencyToPLN("usd",date, new BigDecimal("100"));
		System.out.println("Currency exchanged with the rate on the date " +  simpleDateFormat.format(exchangedCurrency.getDate()) + " is "
				+ exchangedCurrency.getValue());
	}

	public static void main(String[] args) throws ParseException {
		insert();
	}
}
