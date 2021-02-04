package pl.streamsoft.currencyexchange.exception;

public class GettingExchangeRateTimeoutException extends ExchangeCurrencyHttpException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8788270218780114782L;

	public GettingExchangeRateTimeoutException(String message) {
		super(message);
	}

}
