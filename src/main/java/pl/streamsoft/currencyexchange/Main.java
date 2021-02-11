package pl.streamsoft.currencyexchange;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import pl.streamsoft.currencyexchange.service.CurrencyExchangeService;
import pl.streamsoft.currencyexchange.service.converter.Converter;
import pl.streamsoft.currencyexchange.service.converter.ConverterDb;
import pl.streamsoft.currencyexchange.service.converter.ConverterJson;
import pl.streamsoft.currencyexchange.service.datareader.DataReader;
import pl.streamsoft.currencyexchange.service.datareader.DataReaderDb;
import pl.streamsoft.currencyexchange.service.datareader.DataReaderNBP;

public class Main {

	public static void main(String[] args) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = simpleDateFormat.parse("2021-2-10");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		ExchangeRate exchangeRate = new ExchangeRate("pln", new BigDecimal("2"), date);
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		ExchangeRate r = session.get(ExchangeRate.class, 1L);
		session.beginTransaction();
		if(r==null) {
			session.save(exchangeRate);
		} else {
			r.setCode(exchangeRate.getCode());
			r.setValue(exchangeRate.getValue());
			r.setDate(exchangeRate.getDate());
			session.update(r);
		}
		session.getTransaction().commit();
		//session.flush();
		session.close();
		DataReader dataReader = new DataReaderDb();
		Converter converter = new ConverterDb();
		CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService(dataReader, converter);
		ExchangedCurrency exchangedCurrency = currencyExchangeService.exchangeCurrencyToPLN("usd", new Date(),
				new BigDecimal("100"));
		System.out.println("Currency exchanged with the rate on the date "
				+ simpleDateFormat.format(exchangedCurrency.getDate()) + " is " + exchangedCurrency.getValue());
	}
}