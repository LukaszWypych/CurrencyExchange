package pl.streamsoft.currencyexchange.exception;

public class GettingExchangeRateTimeoutException extends ExchangeCurrencyHttpException {

	public GettingExchangeRateTimeoutException(String message) {
		super(message);
	}

}
