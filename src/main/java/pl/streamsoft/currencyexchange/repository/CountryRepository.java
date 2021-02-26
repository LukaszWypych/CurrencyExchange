package pl.streamsoft.currencyexchange.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import pl.streamsoft.currencyexchange.entity.CountryEntity;

@Repository
public interface CountryRepository {

	public void addCountry(CountryEntity country);

	public CountryEntity getCountryByName(String name);

	public List<CountryEntity> getAllCountries();

	public List<CountryEntity> getCountriesWithMultipleCurrencies(int amountOfCurrencies);
}
