package pl.streamsoft.currencyexchange.repository;

import java.util.List;

import pl.streamsoft.currencyexchange.entity.CurrencyEntity;

public interface CurrencyRepository {

	public void addCurrency(CurrencyEntity currency);

	public CurrencyEntity getCurrencyByCode(String code);

	public List<CurrencyEntity> getAllCurrencies();

	public CurrencyEntity updateCurrency(CurrencyEntity currency);
}
