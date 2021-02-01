package pl.streamsoft.currencyexchange;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Input currency code");
		String currencyCode = sc.nextLine().toUpperCase();
		System.out.println("Input value to exchange");
		String value = sc.nextLine().toUpperCase();
		sc.close();
		CurrencyExchangeFacade cef = new CurrencyExchangeFacade();
		BigDecimal result = cef.exchangeCurrencyToPLN(currencyCode, new BigDecimal(value));
		System.out.println(result);
	}
}
