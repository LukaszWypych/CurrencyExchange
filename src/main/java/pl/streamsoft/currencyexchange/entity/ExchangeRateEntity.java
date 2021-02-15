package pl.streamsoft.currencyexchange.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRateEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(name = "rate", scale = 4)
	private BigDecimal rate;

	@Temporal(TemporalType.DATE)
	private Date date;

	@ManyToOne
	private CurrencyEntity currency;

	public ExchangeRateEntity() {

	}

	public ExchangeRateEntity(BigDecimal rate, Date date) {
		this.rate = rate;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public BigDecimal getValue() {
		return rate;
	}

	public Date getDate() {
		return date;
	}

	public CurrencyEntity getCurrency() {
		return currency;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setValue(BigDecimal value) {
		this.rate = value;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setCurrency(CurrencyEntity currency) {
		this.currency = currency;
	}
}
