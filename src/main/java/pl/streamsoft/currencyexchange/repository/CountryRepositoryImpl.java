package pl.streamsoft.currencyexchange.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pl.streamsoft.currencyexchange.entity.CountryEntity;

public class CountryRepositoryImpl implements CountryRepository {

	private EntityManager getEntityManager() {
		return EntityManagerFactoryHelper.getFactory().createEntityManager();
	}

	@Override
	public void addCountry(CountryEntity country) {
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(country);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public CountryEntity getCountryByName(String name) {
		EntityManager entityManager = getEntityManager();
		TypedQuery<CountryEntity> query = entityManager.createNamedQuery("Country.getByName", CountryEntity.class);
		query.setParameter("name", name);
		CountryEntity result = query.getSingleResult();
		entityManager.close();
		return result;
	}

	@Override
	public List<CountryEntity> getAllCountries() {
		EntityManager entityManager = getEntityManager();
		TypedQuery<CountryEntity> query = entityManager.createNamedQuery("Country.getAll", CountryEntity.class);
		List<CountryEntity> resultList = query.getResultList();
		entityManager.close();
		return resultList;
	}

	public List<CountryEntity> getCountriesWithAmountOfCurrencies(int amount) {
		EntityManager entityManager = getEntityManager();
		TypedQuery<CountryEntity> query = entityManager.createNamedQuery("Country.getByCurrenciesAmount",
				CountryEntity.class);
		query.setParameter("amount", amount);
		List<CountryEntity> result = query.getResultList();
		entityManager.close();
		return result;
	}
}
