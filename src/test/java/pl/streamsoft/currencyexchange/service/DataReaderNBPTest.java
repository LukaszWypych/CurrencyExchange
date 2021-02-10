package pl.streamsoft.currencyexchange.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.Whitebox;

import pl.streamsoft.currencyexchange.exception.CurrencyNotFoundException;
import pl.streamsoft.currencyexchange.exception.ExchangeCurrencyHttpException;
import pl.streamsoft.currencyexchange.exception.GettingExchangeRateTimeoutException;
import pl.streamsoft.currencyexchange.service.DataReader;
import pl.streamsoft.currencyexchange.service.DataReaderNBP;

public class DataReaderNBPTest {

	private DataReader dataReaderNBP;

	private String currencyCode;

	private Date date;

	private CloseableHttpClient httpClient;

	private CloseableHttpResponse response;

	private StatusLine statusLine;

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private HttpEntity httpEntity;

	@BeforeEach
	void setup() throws IOException {
		dataReaderNBP = new DataReaderNBP("json");
		currencyCode = "usd";
		date = new Date();
		httpClient = mock(CloseableHttpClient.class);
		statusLine = mock(StatusLine.class);
		response = mock(CloseableHttpResponse.class);
		httpEntity = mock(HttpEntity.class);
		Whitebox.setInternalState(dataReaderNBP, "simpleDateFormat", simpleDateFormat);
		Whitebox.setInternalState(dataReaderNBP, "httpClient", httpClient);
		when(httpClient.execute(any(HttpGet.class))).thenReturn(response);
		when(response.getStatusLine()).thenReturn(statusLine);
	}

	@Test
	void shouldReturnExchangeRate() throws ParseException, IOException {
		// given
		when(statusLine.getStatusCode()).thenReturn(200);
		when(response.getEntity()).thenReturn(httpEntity);

		// when
		String result = dataReaderNBP.getExchangeRateBody(currencyCode, date);

		// then
		assertThat(result).isEqualTo(EntityUtils.toString(response.getEntity()));
	}

	@Test
	void shouldThrowExchangeCurrencyHttpExceptionWhenGetCurrencyExecuteExceptionThrown() throws IOException {
		// given
		when(httpClient.execute(any(HttpGet.class))).thenThrow(new IOException());
		when(statusLine.getStatusCode()).thenReturn(408);

		// when
		ExchangeCurrencyHttpException result = assertThrows(ExchangeCurrencyHttpException.class,
				() -> dataReaderNBP.getExchangeRateBody(currencyCode, date));

		// then
		assertThat(result.getMessage()).isEqualTo("Problem occurred during getting response from the server");
	}

	@Test
	void shouldThrowCurrencyNotFoundExceptionWhenCurrencyNotFound() {
		// given
		when(statusLine.getStatusCode()).thenReturn(404);

		// when
		CurrencyNotFoundException result = assertThrows(CurrencyNotFoundException.class,
				() -> dataReaderNBP.getExchangeRateBody(currencyCode, date));

		// then
		assertThat(result.getMessage()).isEqualTo("Currency not found");
	}

	@Test
	void shouldThrowGettingExchangeRateTimeoutExceptionWhenGetCurrencyConnectionTimeout() {
		// given
		when(statusLine.getStatusCode()).thenReturn(408);

		// when
		GettingExchangeRateTimeoutException result = assertThrows(GettingExchangeRateTimeoutException.class,
				() -> dataReaderNBP.getExchangeRateBody(currencyCode, date));

		// then
		assertThat(result.getMessage()).isEqualTo("Request timeout - Couldn't get a response from NBP server");
	}

	@Test
	void shouldThrowExchangeCurrencyHttpExceptionWhenGetCurrencyOtherResponseCode() {
		// given
		when(statusLine.getStatusCode()).thenReturn(500);

		// when
		ExchangeCurrencyHttpException result = assertThrows(ExchangeCurrencyHttpException.class,
				() -> dataReaderNBP.getExchangeRateBody(currencyCode, date));

		// then
		assertThat(result.getMessage()).isEqualTo("Problem occurred during getting response from the server");
	}

	@Test
	void shouldReturnTrueOnValidDate() {
		// given
		when(statusLine.getStatusCode()).thenReturn(200);

		// when
		boolean result = dataReaderNBP.isDateValid(date);

		// then
		assertThat(result).isEqualTo(true);
	}

	@Test
	void shouldReturnFalseOnInvalidDate() {
		// given
		when(statusLine.getStatusCode()).thenReturn(404);

		// when
		boolean result = dataReaderNBP.isDateValid(date);

		// then
		assertThat(result).isEqualTo(false);
	}

	@Test
	void shouldThrowExchangeCurrencyHttpExceptionWhenValidateDateExecuteExceptionThrown() throws IOException {
		// given
		when(httpClient.execute(any(HttpGet.class))).thenThrow(new IOException());
		when(statusLine.getStatusCode()).thenReturn(408);

		// when
		ExchangeCurrencyHttpException result = assertThrows(ExchangeCurrencyHttpException.class,
				() -> dataReaderNBP.isDateValid(date));

		// then
		assertThat(result.getMessage()).isEqualTo("Problem occurred during getting response from the server");
	}

	@Test
	void shouldThrowGettingExchangeRateTimeoutExceptionWhenValidateDateConnectionTimeout() {
		// given
		when(statusLine.getStatusCode()).thenReturn(408);

		// when
		GettingExchangeRateTimeoutException result = assertThrows(GettingExchangeRateTimeoutException.class,
				() -> dataReaderNBP.isDateValid(date));

		// then
		assertThat(result.getMessage()).isEqualTo("Request timeout - Couldn't get a response from NBP server");
	}

	@Test
	void shouldThrowExchangeCurrencyHttpExceptionWhenValidateDateOtherResponseCode() {
		// given
		when(statusLine.getStatusCode()).thenReturn(500);

		// when
		ExchangeCurrencyHttpException result = assertThrows(ExchangeCurrencyHttpException.class,
				() -> dataReaderNBP.isDateValid(date));

		// then
		assertThat(result.getMessage()).isEqualTo("Problem occurred during getting response from the server");
	}
}
