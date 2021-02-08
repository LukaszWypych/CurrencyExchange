package pl.streamsoft.currencyexchange.service;

import java.io.IOException;
import java.io.UncheckedIOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import pl.streamsoft.currencyexchange.ExchangeRate;

public class CurrencyExchangeServiceNBPXml extends CurrencyExchangeServiceNBP {

	@Override
	protected ExchangeRate getExchangeRateFromBody(String body) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(body);
			return null;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected String getFormat() {
		return "xml";
	}
}
