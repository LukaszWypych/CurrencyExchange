package pl.streamsoft.currencyexchange.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRateEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	private String code;

	@Column(name = "rate", precision = 19, scale = 4)
	private BigDecimal rate;

	@Temporal(TemporalType.DATE)
	private Date date;

	@ManyToMany
	private Set<CountryEntity> countries;

	public ExchangeRateEntity() {

	}

	public ExchangeRateEntity(String code, BigDecimal rate, Date date) {
		this.code = code;
		this.rate = rate;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public BigDecimal getValue() {
		return rate;
	}

	public Date getDate() {
		return date;
	}

	public Set<CountryEntity> getCountry() {
		return countries;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setValue(BigDecimal value) {
		this.rate = value;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setCountry(Set<CountryEntity> countries) {
		this.countries = countries;
	}
}
