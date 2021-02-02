package pl.streamsoft.currencyexchange;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Input currency code");
		String currencyCode = sc.nextLine();
		System.out.println("Input value to exchange");
		BigDecimal value = new BigDecimal(sc.nextLine());
		sc.close();
		CurrencyExchangeFacade cef = new CurrencyExchangeFacade();
		try {
			BigDecimal result = cef.exchangeCurrencyToPLN(currencyCode, value);
			System.out.println("Your value in PLN: " + result);
		} catch (IOException e) {
			System.out.println("Getting information from NBP failed. Message: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exchanging currency failed. Message: " + e.getMessage());
		}
	}
}