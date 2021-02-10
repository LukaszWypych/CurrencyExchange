package pl.streamsoft.currencyexchange.newservice;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Date;

import pl.streamsoft.currencyexchange.exception.CurrencyNotFoundException;
import pl.streamsoft.currencyexchange.exception.ParsingExchangeRateException;

public class DataReaderFile implements DataReader {

	private String folderPath;

	private String extention;

	public DataReaderFile(String folderPath, String extention) {
		this.folderPath = folderPath;
		this.extention = extention;
	}

	@Override
	public String getExchangeRateBody(String currencyCode, Date date) {
		File file = getFile(date);
		try {
			return Files.readString(file.toPath(), StandardCharsets.US_ASCII);
		} catch (IOException e) {
			throw new ParsingExchangeRateException("Couldn't read file");
		}
	}

	private File getFile(Date date) {

		File file = new File(folderPath + date.toString() + "." + extention);
		if (!file.exists()) {
			throw new CurrencyNotFoundException("Currency file not found");
		}
		return file;
	}

	@Override
	public boolean isDateValid(Date date) {
		File file = new File(folderPath + date.toString() + "." + extention);
		return file.exists();
	}
}
