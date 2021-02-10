package pl.streamsoft.currencyexchange.newservice;

import java.util.Date;

public interface DataReader {

	public boolean isDateValid(Date date);

	public String getExchangeRateBody(String currencyCode, Date date);
}
