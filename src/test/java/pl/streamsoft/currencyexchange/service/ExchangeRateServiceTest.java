package pl.streamsoft.currencyexchange.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.streamsoft.currencyexchange.entity.CountryEntity;
import pl.streamsoft.currencyexchange.entity.CurrencyEntity;
import pl.streamsoft.currencyexchange.repository.CountryRepository;
import pl.streamsoft.currencyexchange.repository.CurrencyRepository;
import pl.streamsoft.currencyexchange.repository.ExchangeRateRepository;

public class ExchangeRateServiceTest {

	private CountryRepository countryRepository;

	private CurrencyRepository currencyRepository;

	private ExchangeRateRepository exchangeRateRepository;

	private ExchangeRateService exchangeRateService;

	@BeforeEach
	void setup() {
		countryRepository = mock(CountryRepository.class);
		currencyRepository = mock(CurrencyRepository.class);
		exchangeRateRepository = mock(ExchangeRateRepository.class);
		exchangeRateService = new ExchangeRateService(currencyRepository, exchangeRateRepository, countryRepository);
	}

	@Test
	void shouldAddNewCountryToCurrencyWhenEmpty() {
		// given
		String countryName = "Name";
		CurrencyEntity currencyEntity = new CurrencyEntity();
		when(countryRepository.getCountryByName(countryName)).thenReturn(null);

		// when
		exchangeRateService.addCountryToCurrency(currencyEntity, countryName);

		// then
		assertThat(currencyEntity.getCountries()).anySatisfy(c -> c.getName().equals(countryName));
	}

	@Test
	void shouldAddExistingCountryToCurrencyWhenEmpty() {
		// given
		CountryEntity countryEntity = new CountryEntity();
		countryEntity.setName("Name");
		CurrencyEntity currencyEntity = new CurrencyEntity();
		when(countryRepository.getCountryByName(countryEntity.getName())).thenReturn(countryEntity);

		// when
		exchangeRateService.addCountryToCurrency(currencyEntity, countryEntity.getName());

		// then
		assertThat(currencyEntity.getCountries()).anySatisfy(c -> c.getName().equals(countryEntity.getName()));
	}

	@Test
	void shouldAddNewCountryToCurrencyWithValues() {
		// given
		String countryName = "Name";
		CountryEntity countryEntity = new CountryEntity();
		countryEntity.setName("Name2");
		CurrencyEntity currencyEntity = new CurrencyEntity();
		currencyEntity.setCountries(new HashSet<>());
		currencyEntity.getCountries().add(countryEntity);
		when(countryRepository.getCountryByName(countryName)).thenReturn(null);

		// when
		exchangeRateService.addCountryToCurrency(currencyEntity, countryName);

		// then
		assertThat(currencyEntity.getCountries()).hasSize(2);
		assertThat(currencyEntity.getCountries()).anySatisfy(c -> c.getName().equals(countryName));
	}
}
