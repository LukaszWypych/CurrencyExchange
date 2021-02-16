package pl.streamsoft.currencyexchange.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "countries")
public class CountryEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	private String name;

	@ManyToMany(mappedBy = "countries")
	private Set<CurrencyEntity> currencies;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Set<CurrencyEntity> getCurrencies() {
		return currencies;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCurrencies(Set<CurrencyEntity> currencies) {
		this.currencies = currencies;
	}

}
