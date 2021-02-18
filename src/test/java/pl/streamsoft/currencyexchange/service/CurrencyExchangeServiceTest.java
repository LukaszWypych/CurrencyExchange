package pl.streamsoft.currencyexchange.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import pl.streamsoft.currencyexchange.ExchangedCurrency;
import pl.streamsoft.currencyexchange.entity.ExchangeRateEntity;
import pl.streamsoft.currencyexchange.service.converter.Converter;
import pl.streamsoft.currencyexchange.service.datareader.DataReader;

public class CurrencyExchangeServiceTest {

	private CurrencyExchangeService currencyExchangeService;

	private DataReader dataReader;

	private Converter converter;

	private ExchangeRateService exchangeRateService;

	private String currencyCode;

	private Date date;

	private BigDecimal value;

	@BeforeEach
	void setup() {
		dataReader = mock(DataReader.class);
		converter = mock(Converter.class);
		exchangeRateService = mock(ExchangeRateService.class);
		LinkedHashSet<DataReader> dataReaders = new LinkedHashSet<>();
		dataReaders.add(dataReader);
		currencyExchangeService = new CurrencyExchangeService(dataReaders, converter, exchangeRateService);
		currencyCode = "usd";
		date = new Date();
		value = new BigDecimal("100");
		Whitebox.setInternalState(currencyExchangeService, "exchangeRateService", exchangeRateService);
	}

	@Test
	void shouldExchangeCurrencyOnGivenDayFromDatabase() {
		// given
		ExchangeRateEntity exchangeRate = new ExchangeRateEntity(new BigDecimal("2"), date);
		when(exchangeRateService.getExchangeRateByCode(currencyCode, date)).thenReturn(exchangeRate);

		// when
		ExchangedCurrency result = currencyExchangeService.exchangeCurrencyToPLN(currencyCode, date, value);

		// then
		assertThat(result.getValue().equals(value.multiply(exchangeRate.getValue())));
		assertThat(result.getDate()).isEqualTo(date);
	}

	@Test
	void shouldExchangeCurrencyOnPreviousDayFromDatabase() {
		// given
		ExchangeRateEntity exchangeRate = new ExchangeRateEntity(new BigDecimal("2"), null);
		when(dataReader.isDateValid(any(Date.class))).thenReturn(false).thenReturn(true);
		Answer<ExchangeRateEntity> dateAnswer = new Answer<ExchangeRateEntity>() {
			public ExchangeRateEntity answer(InvocationOnMock invocation) throws Throwable {
				exchangeRate.setDate(invocation.getArgumentAt(1, Date.class));
				return exchangeRate;
			}
		};
		when(exchangeRateService.getExchangeRateByCode(any(String.class), any(Date.class))).thenReturn(null)
				.then(dateAnswer);

		// when
		ExchangedCurrency result = currencyExchangeService.exchangeCurrencyToPLN(currencyCode, date, value);

		// then
		assertThat(result.getValue().equals(value.multiply(exchangeRate.getValue())));
		assertThat(result.getDate()).isBefore(date);
	}

	@Test
	void shouldExchangeCurrencyOnGivenDayFromExternalSource() {
		// given
		when(exchangeRateService.getExchangeRateByCode(currencyCode, date)).thenReturn(null);
		ExchangeRateEntity exchangeRate = new ExchangeRateEntity(new BigDecimal("2"), null);
		when(dataReader.isDateValid(any(Date.class))).thenReturn(true);
		Answer<String> dateAnswer = new Answer<String>() {
			public String answer(InvocationOnMock invocation) throws Throwable {
				exchangeRate.setDate(invocation.getArgumentAt(1, Date.class));
				return "body";
			}
		};
		when(dataReader.getExchangeRateBody(any(String.class), any(Date.class))).then(dateAnswer);
		when(converter.getExchangeRateFromBody(any(String.class))).thenReturn(exchangeRate);

		// when
		ExchangedCurrency result = currencyExchangeService.exchangeCurrencyToPLN(currencyCode, date, value);

		// then
		assertThat(result.getValue().equals(value.multiply(exchangeRate.getValue())));
		assertThat(result.getDate()).isEqualTo(date);
	}

	@Test
	void shouldExchangeCurrencyOnPreviousDayFromExterlanSource() {
		// given
		when(exchangeRateService.getExchangeRateByCode(currencyCode, date)).thenReturn(null);
		ExchangeRateEntity exchangeRate = new ExchangeRateEntity(new BigDecimal("2"), null);
		when(dataReader.isDateValid(any(Date.class))).thenReturn(false).thenReturn(true);
		Answer<String> dateAnswer = new Answer<String>() {
			public String answer(InvocationOnMock invocation) throws Throwable {
				exchangeRate.setDate(invocation.getArgumentAt(1, Date.class));
				return "body";
			}
		};
		when(dataReader.getExchangeRateBody(any(String.class), any(Date.class))).then(dateAnswer);
		when(converter.getExchangeRateFromBody(any(String.class))).thenReturn(exchangeRate);

		// when
		ExchangedCurrency result = currencyExchangeService.exchangeCurrencyToPLN(currencyCode, date, value);

		// then
		assertThat(result.getValue().equals(value.multiply(exchangeRate.getValue())));
		assertThat(result.getDate()).isBefore(date);
	}

	@Test
	void shouldThrowIllegalArgumentExceptionWhenCurrencyCodeIsNull() {
		// given
		currencyCode = null;

		// when
		IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
				() -> currencyExchangeService.exchangeCurrencyToPLN(currencyCode, date, value));

		// then
		assertThat(result.getMessage()).isEqualTo("Invalid currency code");
	}

	@Test
	void shouldThrowIllegalArgumentExceptionWhenCurrencyCodeLenghtNotEqualThree() {
		// given
		currencyCode = "ab";

		// when
		IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
				() -> currencyExchangeService.exchangeCurrencyToPLN(currencyCode, date, value));

		// then
		assertThat(result.getMessage()).isEqualTo("Invalid currency code");
	}

	@Test
	void shouldThrowIllegalArgumentExceptionWhenCurrencyCodeIsNotMatchingPattern() {
		// given
		currencyCode = "123";

		// when
		IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
				() -> currencyExchangeService.exchangeCurrencyToPLN(currencyCode, date, value));

		// then
		assertThat(result.getMessage()).isEqualTo("Invalid currency code");
	}

	@Test
	void shouldThrowIllegalArgumentExceptionWhenDateIsNull() {
		// given
		date = null;

		// when
		IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
				() -> currencyExchangeService.exchangeCurrencyToPLN(currencyCode, date, value));

		// then
		assertThat(result.getMessage()).isEqualTo("Invalid date");
	}

	@Test
	void shouldThrowIllegalArgumentExceptionWhenDateIsInFuture() {
		// given
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		date = c.getTime();

		// when
		IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
				() -> currencyExchangeService.exchangeCurrencyToPLN(currencyCode, date, value));

		// then
		assertThat(result.getMessage()).isEqualTo("Invalid date");
	}

	@Test
	void shouldThrowIllegalArgumentExceptionWhenValueIsNull() {
		// given
		value = null;

		// when
		IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
				() -> currencyExchangeService.exchangeCurrencyToPLN(currencyCode, date, value));

		// then
		assertThat(result.getMessage()).isEqualTo("Invalid value");
	}

	@Test
	void shouldThrowIllegalArgumentExceptionWhenValueIsNegative() {
		// given
		value = new BigDecimal("-1");

		// when
		IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
				() -> currencyExchangeService.exchangeCurrencyToPLN(currencyCode, date, value));

		// then
		assertThat(result.getMessage()).isEqualTo("Invalid value");
	}
}
