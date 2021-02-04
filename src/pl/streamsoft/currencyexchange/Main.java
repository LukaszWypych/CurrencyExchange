package pl.streamsoft.currencyexchange;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import pl.streamsoft.currencyexchange.service.CurrencyExchangeServiceNBP;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Input currency code");
		String currencyCode = sc.nextLine();
		BigDecimal value;
		while(true) {
			try {
				System.out.println("Input value to exchange");
				value = new BigDecimal(sc.nextLine());
				if(value.compareTo(BigDecimal.ZERO)>=0) {
					break;
				}
				System.out.println("Wrong input");
			} catch(NumberFormatException e) {
				System.out.println("Wrong input");
			}
		}
		sc.close();
		CurrencyExchangeServiceNBP cef = new CurrencyExchangeServiceNBP();
			ExchangedCurrency exchangedCurrency = cef.exchangeCurrencyToPLN(currencyCode, new Date(), value);
			String pattern = "dd-MM-yyyy";
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			String date = dateFormat.format(exchangedCurrency.getDate());
			System.out.println("Currency exchanged with the rate on the date "+ date
			+" is "+exchangedCurrency.getValue());
	}
}