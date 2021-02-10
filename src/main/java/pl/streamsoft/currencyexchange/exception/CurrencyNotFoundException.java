package pl.streamsoft.currencyexchange.exception;

public class CurrencyNotFoundException extends ExchangeCurrencyHttpException {

	public CurrencyNotFoundException(String message) {
		super(message);
	}
}
