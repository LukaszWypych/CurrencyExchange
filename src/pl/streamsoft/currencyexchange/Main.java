package pl.streamsoft.currencyexchange;

import java.math.BigDecimal;

public class Main {

	public static void main(String[] args) {
		CurrencyExchangeFacade cef = new CurrencyExchangeFacade();
		BigDecimal result = cef.exchangeCurrencyToPLN("USD", new BigDecimal("2.20"));
		System.out.println(result);
	}
}
