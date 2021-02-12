package pl.streamsoft.currencyexchange.service;

import java.util.Date;
import java.util.List;

import pl.streamsoft.currencyexchange.entity.ExchangeRateEntity;
import pl.streamsoft.currencyexchange.repository.Repository;

public class ExchangeRateService {

	private Repository repository;

	public ExchangeRateService(Repository repository) {
		this.repository = repository;
	}

	public void addExchangeRate(ExchangeRateEntity rate) {
		prepareExchangeRate(rate);
		repository.addExchangeRate(rate);
	}

	public ExchangeRateEntity getExchangeRateByCode(String currencyCode, Date date) {
		return repository.getExchangeRateByCode(currencyCode.toUpperCase(), date);
	}

//	public ExchangeRateEntity getExchangeRateByCountry(String country, Date date) {
//		return repository.getExchangeRateByCode(country.toUpperCase(), date);
//	}

	public List<ExchangeRateEntity> getAllExchangeRates(Date date) {
		return repository.getAllExchangeRates(date);
	}

	public void updateExchangeRate(ExchangeRateEntity rate) {
		prepareExchangeRate(rate);
		repository.updateExchangeRate(rate);
	}

	public void deleteExchangeRate(Long id) {
		repository.deleteExchangeRate(id);
	}

	private void prepareExchangeRate(ExchangeRateEntity exchangeRate) {
		exchangeRate.setCode(exchangeRate.getCode().toUpperCase());
	}
}
