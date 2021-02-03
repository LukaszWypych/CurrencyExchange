package pl.streamsoft.currencyexchange;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpTimeoutException;

import org.apache.http.HttpException;
import org.apache.http.ParseException;
import org.json.JSONException;

import pl.streamsoft.currencyexchange.exception.CurrencyNotFoundException;
import pl.streamsoft.currencyexchange.service.CurrencyExchangeService;
import pl.streamsoft.currencyexchange.service.CurrencyExchangeServiceNBP;

public class SaleDocumentService {

	private static void insert() {
		CurrencyExchangeService currencyExchangeFacade = new CurrencyExchangeServiceNBP();
			ExchangedCurrency exchangedCurrency;
			try {
				exchangedCurrency = currencyExchangeFacade.exchangeCurrencyToPLN("DOLAR", BigDecimal.ONE);
				System.out.println("Currency exchanged with the rate on the date "+exchangedCurrency.getDate()
				+" is "+exchangedCurrency.getValue());
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CurrencyNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (HttpTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		insert();
	}
}
