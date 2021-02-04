package pl.streamsoft.currencyexchange.exception;

public class CurrencyNotFoundException extends ExchangeCurrencyHttpException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 298309177675058637L;

	public CurrencyNotFoundException(String message) {
		super(message);
	}
}
