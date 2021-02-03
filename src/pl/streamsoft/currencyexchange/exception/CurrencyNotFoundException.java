package pl.streamsoft.currencyexchange.exception;

import org.apache.http.HttpException;

public class CurrencyNotFoundException extends HttpException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 298309177675058637L;

	public CurrencyNotFoundException(String message) {
		super(message);
	}
}
