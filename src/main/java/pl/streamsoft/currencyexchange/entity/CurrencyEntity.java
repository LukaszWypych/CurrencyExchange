package pl.streamsoft.currencyexchange.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "currencies")
public class CurrencyEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	private String name;

	private String code;

	@ManyToMany
	private Set<CountryEntity> countries;

	@OneToMany(mappedBy = "currency")
	private Set<ExchangeRateEntity> rates;

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public Set<CountryEntity> getCountries() {
		return countries;
	}

	public Set<ExchangeRateEntity> getRates() {
		return rates;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCountries(Set<CountryEntity> countries) {
		this.countries = countries;
	}

	public void setRates(Set<ExchangeRateEntity> rates) {
		this.rates = rates;
	}
}
