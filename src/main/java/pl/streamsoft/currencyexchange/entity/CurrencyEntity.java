package pl.streamsoft.currencyexchange.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "currencies")
@NamedQueries({ @NamedQuery(name = "Currency.getByCode", query = "SELECT c FROM CurrencyEntity c WHERE c.code = :code"),
		@NamedQuery(name = "Currency.getAll", query = "SELECT c FROM CurrencyEntity c"),
		@NamedQuery(name = "Currency.getByBiggestRateDifferenceInPeriod", query = "SELECT c FROM CurrencyEntity c JOIN c.rates e WHERE e.date >= :from AND e.date <= :to GROUP BY c.id ORDER BY (max(e.rate) - min(e.rate)) DESC") })
public class CurrencyEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	private String name;

	private String code;

	@ManyToMany
	private Set<CountryEntity> countries = new HashSet<CountryEntity>();

	@OneToMany(mappedBy = "currency", cascade = CascadeType.REMOVE)
	private Set<ExchangeRateEntity> rates = new HashSet<ExchangeRateEntity>();

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