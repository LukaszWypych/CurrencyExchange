package pl.streamsoft.currencyexchange.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import com.neovisionaries.i18n.CountryCode;
import com.neovisionaries.i18n.CurrencyCode;

import pl.streamsoft.currencyexchange.entity.CountryEntity;
import pl.streamsoft.currencyexchange.entity.CurrencyEntity;
import pl.streamsoft.currencyexchange.entity.ExchangeRateEntity;
import pl.streamsoft.currencyexchange.exception.CurrencyNotFoundException;
import pl.streamsoft.currencyexchange.repository.CountryRepository;
import pl.streamsoft.currencyexchange.repository.CurrencyRepository;
import pl.streamsoft.currencyexchange.repository.ExchangeRateRepository;

public class ExchangeRateService {

	private CurrencyRepository currencyRepository;

	private ExchangeRateRepository exchangeRateRepository;

	private CountryRepository countryRepository;

	public ExchangeRateService(CurrencyRepository currencyRepository, ExchangeRateRepository exchangeRateRepository,
			CountryRepository countryRepository) {
		this.currencyRepository = currencyRepository;
		this.exchangeRateRepository = exchangeRateRepository;
		this.countryRepository = countryRepository;
	}

	public void addExchangeRate(ExchangeRateEntity rate, String currencyCode) {
		if (rate.getCurrency() == null) {
			addCurrencyToRate(rate, currencyCode.toUpperCase());
		}
		exchangeRateRepository.addExchangeRate(rate);
	}

	public ExchangeRateEntity getExchangeRateByCode(String currencyCode, Date date) {
		try {
			return exchangeRateRepository.getExchangeRateByCode(currencyCode.toUpperCase(), date);
		} catch (NoResultException e) {
			return null;
		}
	}

	public void updateExchangeRate(ExchangeRateEntity rate) {
		exchangeRateRepository.updateExchangeRate(rate);
	}

	public void addCurrency(CurrencyEntity currencyEntity) {
		currencyRepository.addCurrency(currencyEntity);
	}

	public CurrencyEntity getCurrencyByCode(String currencyCode) {
		try {
			return currencyRepository.getCurrencyByCode(currencyCode.toUpperCase());
		} catch (NoResultException e) {
			return null;
		}
	}

	public void addCountry(CountryEntity countryEntity) {
		countryRepository.addCountry(countryEntity);
	}

	public CountryEntity getCountryByName(String name) {
		try {
			return countryRepository.getCountryByName(name);
		} catch (NoResultException e) {
			return null;
		}
	}

	public void addCurrencyToRate(ExchangeRateEntity rate, String currencyCode) {
		CurrencyEntity currencyEntity = getCurrencyByCode(currencyCode);
		if (currencyEntity == null) {
			CurrencyCode currency = CurrencyCode.getByCode(currencyCode);
			currencyEntity = new CurrencyEntity();
			currencyEntity.setCode(currencyCode.toUpperCase());
			currencyEntity.setName(currency.getName());
			try {
				CountryCode country = currency.getCountryList().get(0);
				addCountryToCurrency(currencyEntity, country.getName());
				addCurrency(currencyEntity);
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new CurrencyNotFoundException("Currency not found");
			}
		}
		rate.setCurrency(currencyEntity);
	}

	public void addCountryToCurrency(CurrencyEntity currencyEntity, String country) {
		CountryEntity countryEntity = getCountryByName(country);
		if (countryEntity == null) {
			countryEntity = new CountryEntity();
			countryEntity.setName(country);
			addCountry(countryEntity);
		}
		currencyEntity.getCountries().add(countryEntity);
	}

	public BigDecimal getMaxRateFromPeriodForCurrency(String currencyCode, Date from, Date to) {
		return exchangeRateRepository.getMaxRateFromPeriodForCurrency(currencyCode.toUpperCase(), from, to);
	}

	public BigDecimal getMinRateFromPeriodForCurrency(String currencyCode, Date from, Date to) {
		return exchangeRateRepository.getMinRateFromPeriodForCurrency(currencyCode.toUpperCase(), from, to);
	}

	public List<BigDecimal> getMaxRatesForCurrency(String currencyCode, int limit) {
		return exchangeRateRepository.getMaxRatesForCurrency(currencyCode.toUpperCase(), limit);
	}

	public List<BigDecimal> getMinRatesForCurrency(String currencyCode, int limit) {
		return exchangeRateRepository.getMinRatesForCurrency(currencyCode.toUpperCase(), limit);
	}

	public List<CountryEntity> getCountriesWithMultipleCurrencies(int amountOfCurrencies) {
		return countryRepository.getCountriesWithMultipleCurrencies(amountOfCurrencies);
	}

	public CurrencyEntity getCurrencyWithHighestRateDifferenceInPeriod(Date from, Date to) {
		return currencyRepository.getCurrencyWithHighestRateDifferenceInPeriod(from, to);
	}
}
