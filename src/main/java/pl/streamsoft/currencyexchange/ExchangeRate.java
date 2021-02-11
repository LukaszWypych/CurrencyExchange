package pl.streamsoft.currencyexchange;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
	private Long id;
	
	private String code;
	
	private BigDecimal value;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	public ExchangeRate() {
		
	}
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
