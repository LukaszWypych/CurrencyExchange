package pl.streamsoft.currencyexchange.repository;

import java.util.Date;
import java.util.List;

import pl.streamsoft.currencyexchange.entity.ExchangeRateEntity;

public interface Repository {

	public void addExchangeRate(ExchangeRateEntity rate);

	public ExchangeRateEntity getExchangeRateByCode(String currencyCode, Date date);

	public ExchangeRateEntity getExchangeRateByCountry(String country, Date date);

	public List<ExchangeRateEntity> getAllExchangeRates(Date date);

	public void updateExchangeRate(ExchangeRateEntity rate);

	public void deleteExchangeRate(Long id);
}
