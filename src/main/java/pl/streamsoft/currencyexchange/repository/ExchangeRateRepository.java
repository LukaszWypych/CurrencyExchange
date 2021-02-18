package pl.streamsoft.currencyexchange.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import pl.streamsoft.currencyexchange.entity.ExchangeRateEntity;

public interface ExchangeRateRepository {

	public void addExchangeRate(ExchangeRateEntity rate);

	public ExchangeRateEntity getExchangeRateByCode(String currencyCode, Date date);

	public List<ExchangeRateEntity> getAllExchangeRates(Date date);

	public ExchangeRateEntity updateExchangeRate(ExchangeRateEntity rate);

	public void deleteExchangeRate(Long id);

	public BigDecimal getMaxRateFromPeriodForCurrency(String currencyCode, Date from, Date to);

	public BigDecimal getMinRateFromPeriodForCurrency(String currencyCode, Date from, Date to);

	public List<BigDecimal> getMaxRatesForCurrency(String currencyCode, int limit);

	public List<BigDecimal> getMinRatesForCurrency(String currencyCode, int limit);
}
