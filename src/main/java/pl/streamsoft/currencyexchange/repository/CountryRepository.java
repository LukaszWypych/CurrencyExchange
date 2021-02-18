package pl.streamsoft.currencyexchange.repository;

import java.util.List;

import pl.streamsoft.currencyexchange.entity.CountryEntity;

public interface CountryRepository {

	public void addCountry(CountryEntity country);

	public CountryEntity getCountryByName(String name);

	public List<CountryEntity> getAllCountries();

	public List<CountryEntity> getCountriesWithCurrencies(int currencies);
}
