package pl.streamsoft.currencyexchange.integration.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.streamsoft.currencyexchange.entity.CountryEntity;
import pl.streamsoft.currencyexchange.entity.CurrencyEntity;
import pl.streamsoft.currencyexchange.repository.CountryRepository;
import pl.streamsoft.currencyexchange.repository.CountryRepositoryImpl;
import pl.streamsoft.currencyexchange.repository.CurrencyRepository;
import pl.streamsoft.currencyexchange.repository.CurrencyRepositoryImpl;
import pl.streamsoft.currencyexchange.repository.EntityManagerFactoryHelper;
import pl.streamsoft.currencyexchange.repository.ExchangeRateRepository;
import pl.streamsoft.currencyexchange.repository.ExchangeRateRepositoryImpl;
import pl.streamsoft.currencyexchange.service.ExchangeRateService;

public class ExchangeRateServiceTest {

	private static EntityManagerFactory factory;

	private CountryRepository countryRepository;

	private CurrencyRepository currencyRepository;

	private ExchangeRateRepository exchangeRateRepository;

	private ExchangeRateService exchangeRateService;

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@BeforeAll
	static void init() {
		factory = Persistence.createEntityManagerFactory("test");
		EntityManagerFactoryHelper.setFactory(factory);
	}

	@BeforeEach
	void setup() {
		countryRepository = new CountryRepositoryImpl();
		currencyRepository = new CurrencyRepositoryImpl();
		exchangeRateRepository = new ExchangeRateRepositoryImpl();
		exchangeRateService = new ExchangeRateService(currencyRepository, exchangeRateRepository, countryRepository);
	}

	@Test
	void shouldGetMaxRateFromPeriodForCurrency() throws ParseException {
		// given
		String currencyCode = "JPY";
		Date from = simpleDateFormat.parse("2021-02-10");
		Date to = simpleDateFormat.parse("2021-02-13");
		BigDecimal maxRateForJPY = new BigDecimal("5.0010");

		// when
		BigDecimal result = exchangeRateService.getMaxRateFromPeriodForCurrency(currencyCode, from, to);

		// then
		assertThat(result).isEqualTo(maxRateForJPY);
	}

	@Test
	void shouldGetMinRateFromPeriodForCurrency() throws ParseException {
		// given
		String currencyCode = "JPY";
		Date from = simpleDateFormat.parse("2021-02-10");
		Date to = simpleDateFormat.parse("2021-02-13");
		BigDecimal minRateForJPY = new BigDecimal("5.0000");

		// when
		BigDecimal result = exchangeRateService.getMinRateFromPeriodForCurrency(currencyCode, from, to);

		// then
		assertThat(result).isEqualTo(minRateForJPY);
	}

	@Test
	void shouldGetMaxRatesForCurrency() {
		// given
		String currencyCode = "USD";
		int limit = 2;
		BigDecimal maxUSDValue = new BigDecimal("1.4321");
		BigDecimal midUSDValue = new BigDecimal("1.2222");

		// when
		List<BigDecimal> result = exchangeRateService.getMaxRatesForCurrency(currencyCode, limit);

		// then
		assertThat(result).hasSize(limit);
		assertThat(result.get(0)).isEqualTo(maxUSDValue);
		assertThat(result.get(1)).isEqualTo(midUSDValue);
	}

	@Test
	void shouldGetMinRatesForCurrency() {
		// given
		String currencyCode = "USD";
		int limit = 2;
		BigDecimal minUSDValue = new BigDecimal("1.1234");
		BigDecimal midUSDValue = new BigDecimal("1.2222");

		// when
		List<BigDecimal> result = exchangeRateService.getMinRatesForCurrency(currencyCode, limit);

		// then
		assertThat(result).hasSize(limit);
		assertThat(result.get(0)).isEqualTo(minUSDValue);
		assertThat(result.get(1)).isEqualTo(midUSDValue);
	}

	@Test
	void shouldGetCountriesWithMultipleCurrencies() {
		// given
		int amountOfCurrenciesInCountry = 2;
		int amountOfCountriesWithMultipleCurrencies = 2;

		// when
		List<CountryEntity> result = exchangeRateService
				.getCountriesWithMultipleCurrencies(amountOfCurrenciesInCountry);

		// then
		assertThat(result).hasSize(amountOfCountriesWithMultipleCurrencies);
		assertThat(result).allMatch(c -> c.getCurrencies().size() >= amountOfCurrenciesInCountry);
	}

	@Test
	void shouldGetCurrencyWithHighestRateDifferenceInPeriod() throws ParseException {
		// given
		Date from = simpleDateFormat.parse("2021-02-10");
		Date to = simpleDateFormat.parse("2021-02-13");
		String codeOfCurrencyWithHighestRateDiffrence = "EUR";

		// when
		CurrencyEntity result = exchangeRateService.getCurrencyWithHighestRateDifferenceInPeriod(from, to);

		// then
		assertThat(result.getCode()).isEqualTo(codeOfCurrencyWithHighestRateDiffrence);
	}
}