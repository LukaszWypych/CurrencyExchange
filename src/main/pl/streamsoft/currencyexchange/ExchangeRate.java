package pl.streamsoft.currencyexchange;

import java.math.BigDecimal;
import java.util.Date;

public class ExchangeRate {
	
	private String code;
	
	private BigDecimal value;
	
	private Date date;
	
	public ExchangeRate(String code, BigDecimal value, Date date) {
		this.code=code;
		this.value=value;
		this.date=date;
	}

	public String getCode() {
		return code;
	}

	public BigDecimal getValue() {
		return value;
	}

	public Date getDate() {
		return date;
	}
}
