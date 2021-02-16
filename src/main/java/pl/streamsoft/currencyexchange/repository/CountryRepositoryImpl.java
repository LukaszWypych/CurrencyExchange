package pl.streamsoft.currencyexchange.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
	}

	@Override
	public CountryEntity getCountryByName(String name) {
		EntityManager entityManager = getEntityManager();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<CountryEntity> cq = cb.createQuery(CountryEntity.class);
		Root<CountryEntity> root = cq.from(CountryEntity.class);
		cq.select(root).where(cb.equal(root.get("name"), name));
		TypedQuery<CountryEntity> query = entityManager.createQuery(cq);
		CountryEntity result = query.getSingleResult();
		return result;
	}

	@Override
	public List<CountryEntity> getAllCountries() {
		EntityManager entityManager = getEntityManager();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<CountryEntity> cq = cb.createQuery(CountryEntity.class);
		Root<CountryEntity> rootEntry = cq.from(CountryEntity.class);
		CriteriaQuery<CountryEntity> all = cq.select(rootEntry);
		TypedQuery<CountryEntity> allQuery = entityManager.createQuery(all);
		return allQuery.getResultList();
	}
}
