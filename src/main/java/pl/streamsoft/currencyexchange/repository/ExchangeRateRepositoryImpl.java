package pl.streamsoft.currencyexchange.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import pl.streamsoft.currencyexchange.entity.CurrencyEntity;
import pl.streamsoft.currencyexchange.entity.ExchangeRateEntity;

public class ExchangeRateRepositoryImpl implements ExchangeRateRepository {

	private EntityManager getEntityManager() {
		return EntityManagerFactoryHelper.getFactory().createEntityManager();
	}

	@Override
	public void addExchangeRate(ExchangeRateEntity rate) {
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(rate);
		entityManager.getTransaction().commit();
	}

	@Override
	public ExchangeRateEntity getExchangeRateByCode(String currencyCode, Date date) {
		EntityManager entityManager = getEntityManager();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ExchangeRateEntity> cq = cb.createQuery(ExchangeRateEntity.class);
		Root<ExchangeRateEntity> root = cq.from(ExchangeRateEntity.class);
		Join<ExchangeRateEntity, CurrencyEntity> childJoin = root.join("currency");
		cq.select(root).where(cb.equal(root.get("date"), date), cb.equal(childJoin.get("code"), currencyCode));
		TypedQuery<ExchangeRateEntity> query = entityManager.createQuery(cq);
		ExchangeRateEntity result = query.getSingleResult();
		return result;
	}

	@Override
	public List<ExchangeRateEntity> getAllExchangeRates(Date date) {
		EntityManager entityManager = getEntityManager();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ExchangeRateEntity> cq = cb.createQuery(ExchangeRateEntity.class);
		Root<ExchangeRateEntity> rootEntry = cq.from(ExchangeRateEntity.class);
		CriteriaQuery<ExchangeRateEntity> all = cq.select(rootEntry);
		TypedQuery<ExchangeRateEntity> allQuery = entityManager.createQuery(all);
		return allQuery.getResultList();
	}

	@Override
	public ExchangeRateEntity updateExchangeRate(ExchangeRateEntity rate) {
		EntityManager entityManager = getEntityManager();
		return entityManager.merge(rate);
	}

	@Override
	public void deleteExchangeRate(Long id) {
		EntityManager entityManager = getEntityManager();
		ExchangeRateEntity rate = entityManager.find(ExchangeRateEntity.class, id);
		entityManager.getTransaction().begin();
		entityManager.persist(rate);
		entityManager.getTransaction().commit();
	}
}
