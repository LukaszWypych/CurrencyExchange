package pl.streamsoft.currencyexchange.service.repository;

import java.util.Date;
import java.util.List;

import pl.streamsoft.currencyexchange.ExchangeRate;

public interface Repository {

	public void addExchangeRate(ExchangeRate rate);

	public ExchangeRate getExchangeRate(String currencyCode, Date date);

	public List<ExchangeRate> getAllExchangeRates(Date date);

	public void updateExchangeRate(ExchangeRate rate);

	public void deleteExchangeRate(Long id);
}
