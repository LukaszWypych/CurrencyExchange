package pl.streamsoft.currencyexchange.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.streamsoft.currencyexchange.entity.CountryEntity;

@Repository
@Transactional
public class CountryRepositoryImpl implements CountryRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addCountry(CountryEntity country) {
		entityManager.persist(country);
	}

	@Override
	public CountryEntity getCountryByName(String name) {
		TypedQuery<CountryEntity> query = entityManager.createNamedQuery("Country.getByName", CountryEntity.class);
		query.setParameter("name", name);
		try {
			CountryEntity result = query.getSingleResult();
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<CountryEntity> getAllCountries() {
		TypedQuery<CountryEntity> query = entityManager.createNamedQuery("Country.getAll", CountryEntity.class);
		List<CountryEntity> resultList = query.getResultList();
		return resultList;
	}

	public List<CountryEntity> getCountriesWithMultipleCurrencies(int amount) {
		TypedQuery<CountryEntity> query = entityManager.createNamedQuery("Country.getByCurrenciesAmount",
				CountryEntity.class);
		query.setParameter("amount", amount);
		List<CountryEntity> result = query.getResultList();
		return result;
	}
}
