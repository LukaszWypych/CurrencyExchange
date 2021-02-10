package pl.streamsoft.currencyexchange.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import pl.streamsoft.currencyexchange.ExchangeRate;
import pl.streamsoft.currencyexchange.ExchangedCurrency;
import pl.streamsoft.currencyexchange.service.Converter;
import pl.streamsoft.currencyexchange.service.CurrencyExchangeService;
import pl.streamsoft.currencyexchange.service.DataReader;

public class CurrencyExchangeServiceTest {

	private CurrencyExchangeService currencyExchangeService;

	private DataReader dataReader;

	private Converter converter;

	private String currencyCode;

	private Date date;

	private Date newDate;

	private BigDecimal value;

	@BeforeEach
	void setup() {
		dataReader = mock(DataReader.class);
		converter = mock(Converter.class);
		currencyExchangeService = new CurrencyExchangeService(dataReader, converter);
		currencyCode = "usd";
		date = new Date();
		newDate = new Date();
		value = new BigDecimal("100");
	}

	@Test
	void shouldExchangeCurrencyOnGivenDay() {
		// given
		when(dataReader.isDateValid(any(Date.class))).thenReturn(true);
		Answer<String> dateAnswer = new Answer<String>() {
			public String answer(InvocationOnMock invocation) throws Throwable {
				newDate = invocation.getArgumentAt(1, Date.class);
				return "body";
			}
		};
		when(dataReader.getExchangeRateBody(any(String.class), any(Date.class))).then(dateAnswer);
		Answer<ExchangeRate> rateAnswer = new Answer<ExchangeRate>() {
			public ExchangeRate answer(InvocationOnMock invocation) throws Throwable {
				ExchangeRate exchangeRate = new ExchangeRate(currencyCode, new BigDecimal("2"), newDate);
				return exchangeRate;
			}
		};
		when(converter.getExchangeRateFromBody(any(String.class))).then(rateAnswer);

		// when
		ExchangedCurrency result = currencyExchangeService.exchangeCurrencyToPLN(currencyCode, date, value);

		// then
		assertThat(result.getValue().doubleValue()).isEqualTo(value.doubleValue() * 2);
		assertThat(result.getDate()).isEqualTo(date);
	}

	@Test
	void shouldExchangeCurrencyOnPreviousDay() {
		// given
		when(dataReader.isDateValid(any(Date.class))).thenReturn(false).thenReturn(true);
		Answer<String> dateAnswer = new Answer<String>() {
			public String answer(InvocationOnMock invocation) throws Throwable {
				newDate = invocation.getArgumentAt(1, Date.class);
				return "body";
			}
		};
		when(dataReader.getExchangeRateBody(any(String.class), any(Date.class))).then(dateAnswer);
		Answer<ExchangeRate> rateAnswer = new Answer<ExchangeRate>() {
			public ExchangeRate answer(InvocationOnMock invocation) throws Throwable {
				ExchangeRate exchangeRate = new ExchangeRate(currencyCode, new BigDecimal("2"), newDate);
				return exchangeRate;
			}
		};
		when(converter.getExchangeRateFromBody(any(String.class))).then(rateAnswer);

		// when
		ExchangedCurrency result = currencyExchangeService.exchangeCurrencyToPLN(currencyCode, date, value);

		// then
		assertThat(result.getValue().doubleValue()).isEqualTo(value.doubleValue() * 2);
		assertThat(result.getDate().before(date)).isEqualTo(true);
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
