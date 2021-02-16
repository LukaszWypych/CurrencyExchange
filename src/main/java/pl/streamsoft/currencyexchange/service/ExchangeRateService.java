package pl.streamsoft.currencyexchange.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.NoResultException;

import com.neovisionaries.i18n.CountryCode;
import com.neovisionaries.i18n.CurrencyCode;

import pl.streamsoft.currencyexchange.entity.CountryEntity;
import pl.streamsoft.currencyexchange.entity.CurrencyEntity;
import pl.streamsoft.currencyexchange.entity.ExchangeRateEntity;
import pl.streamsoft.currencyexchange.repository.CountryRepository;
import pl.streamsoft.currencyexchange.repository.CurrencyRepository;
import pl.streamsoft.currencyexchange.repository.ExchangeRateRepository;

public class ExchangeRateService {

	private CurrencyRepository currencyRepository;

	public ExchangeRateService(CurrencyRepository currencyRepository, ExchangeRateRepository exchangeRateRepository,
			CountryRepository countryRepository) {
		this.currencyRepository = currencyRepository;
		this.exchangeRateRepository = exchangeRateRepository;
		this.countryRepository = countryRepository;
	}

	private ExchangeRateRepository exchangeRateRepository;

	private CountryRepository countryRepository;

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

	public CurrencyEntity getCurrencyByCode(String currencyCode) {
		try {
			return currencyRepository.getCurrencyByCode(currencyCode.toUpperCase());
		} catch (NoResultException e) {
			return null;
		}
	}

	public CountryEntity getCountryByName(String name) {
		try {
			return countryRepository.getCountryByName(name);
		} catch (NoResultException e) {
			return null;
		}
	}

	public void addCountry(CountryEntity countryEntity) {
		countryRepository.addCountry(countryEntity);
	}

	public void addCurrency(CurrencyEntity currencyEntity) {
		currencyRepository.addCurrency(currencyEntity);
	}

	private void addCurrencyToRate(ExchangeRateEntity rate, String currencyCode) {
		CurrencyEntity currencyEntity = getCurrencyByCode(currencyCode);
		if (currencyEntity == null) {
			CurrencyCode currency = CurrencyCode.getByCode(currencyCode);
			CountryCode country = currency.getCountryList().get(0);
			CountryEntity countryEntity = getCountryByName(country.getName());
			if (countryEntity == null) {
				countryEntity = new CountryEntity();
				countryEntity.setName(country.getName());
				addCountry(countryEntity);
			}
			currencyEntity = new CurrencyEntity();
			currencyEntity.setCode(currencyCode.toUpperCase());
			currencyEntity.setName(currency.getName());
			currencyEntity.setCountries(new HashSet<>(Arrays.asList(countryEntity)));
			addCurrency(currencyEntity);
		}
		rate.setCurrency(currencyEntity);
	}
}
