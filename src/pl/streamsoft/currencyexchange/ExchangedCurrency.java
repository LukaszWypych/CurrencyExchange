package pl.streamsoft.currencyexchange;

import java.math.BigDecimal;
import java.util.Date;

public class ExchangedCurrency {
	
	private BigDecimal value;
	
	private Date date;

	public ExchangedCurrency(BigDecimal value, Date date) {
		this.value = value;
		this.date = date;
	}

	public BigDecimal getValue() {
		return value;
	}

	public Date getDate() {
		return date;
	}
}
