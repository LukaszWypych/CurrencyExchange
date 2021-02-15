package pl.streamsoft.currencyexchange.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import pl.streamsoft.currencyexchange.entity.CurrencyEntity;

public class CurrencyRepositoryImpl implements CurrencyRepository {

	private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("pgsql");

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	@Override
	public void addCurrency(CurrencyEntity currency) {
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(currency);
		entityManager.getTransaction().commit();
	}

	@Override
	public CurrencyEntity getCurrencyByCode(String code) {
		EntityManager entityManager = getEntityManager();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<CurrencyEntity> cq = cb.createQuery(CurrencyEntity.class);
		Root<CurrencyEntity> root = cq.from(CurrencyEntity.class);
		cq.select(root).where(cb.equal(root.get("code"), code));
		TypedQuery<CurrencyEntity> query = entityManager.createQuery(cq);
		try {
			CurrencyEntity result = query.getSingleResult();
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<CurrencyEntity> getAllCurrencies() {
		EntityManager entityManager = getEntityManager();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<CurrencyEntity> cq = cb.createQuery(CurrencyEntity.class);
		Root<CurrencyEntity> rootEntry = cq.from(CurrencyEntity.class);
		CriteriaQuery<CurrencyEntity> all = cq.select(rootEntry);
		TypedQuery<CurrencyEntity> allQuery = entityManager.createQuery(all);
		return allQuery.getResultList();
	}

	@Override
	public CurrencyEntity updateCurrency(CurrencyEntity currency) {
		EntityManager entityManager = getEntityManager();
		return entityManager.merge(currency);

	}

}
