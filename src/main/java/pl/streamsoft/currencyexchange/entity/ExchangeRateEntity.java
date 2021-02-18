package pl.streamsoft.currencyexchange.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "exchange_rates")
@NamedQueries({
		@NamedQuery(name = "ExchangeRate.getByCode", query = "SELECT e FROM ExchangeRateEntity e JOIN e.currency c WHERE c.code = :code AND e.date = :date"),
		@NamedQuery(name = "ExchangeRate.getAll", query = "SELECT e FROM ExchangeRateEntity e"),
		@NamedQuery(name = "ExchangeRate.getMaxRateFromPeriodForCurrency", query = "SELECT MAX(e.rate) FROM ExchangeRateEntity e JOIN e.currency c WHERE c.code = :code AND e.date >= :from AND e.date <= :to"),
		@NamedQuery(name = "ExchangeRate.getMinRateFromPeriodForCurrency", query = "SELECT MIN(e.rate) FROM ExchangeRateEntity e JOIN e.currency c WHERE c.code = :code AND e.date >= :from AND e.date <= :to"),
		@NamedQuery(name = "ExchangeRate.getMaxRatesForCurrency", query = "SELECT e.rate FROM ExchangeRateEntity e JOIN e.currency c WHERE c.code = :code ORDER BY e.rate DESC"),
		@NamedQuery(name = "ExchangeRate.getMinRatesForCurrency", query = "SELECT e.rate FROM ExchangeRateEntity e JOIN e.currency c WHERE c.code = :code ORDER BY e.rate ASC") })
public class ExchangeRateEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(name = "rate", precision = 19, scale = 4)
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
