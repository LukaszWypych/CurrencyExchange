package pl.streamsoft.currencyexchange.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pl.streamsoft.currencyexchange.entity.CurrencyEntity;

public class CurrencyRepositoryImpl implements CurrencyRepository {

	private EntityManager getEntityManager() {
		return EntityManagerFactoryHelper.getFactory().createEntityManager();
	}

	@Override
	public void addCurrency(CurrencyEntity currency) {
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(currency);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public CurrencyEntity getCurrencyByCode(String code) {
		EntityManager entityManager = getEntityManager();
		TypedQuery<CurrencyEntity> query = entityManager.createNamedQuery("Currency.getByCode", CurrencyEntity.class);
		query.setParameter("code", code);
		CurrencyEntity result = query.getSingleResult();
		entityManager.close();
		return result;
	}

	@Override
	public List<CurrencyEntity> getAllCurrencies() {
		EntityManager entityManager = getEntityManager();
		TypedQuery<CurrencyEntity> query = entityManager.createNamedQuery("Currency.getAll", CurrencyEntity.class);
		List<CurrencyEntity> resultList = query.getResultList();
		entityManager.close();
		return resultList;
	}

	@Override
	public CurrencyEntity updateCurrency(CurrencyEntity currency) {
		EntityManager entityManager = getEntityManager();
		currency = entityManager.merge(currency);
		entityManager.close();
		return currency;
	}

	public CurrencyEntity getCurrencyWithBiggestRateDifferenceInPeriod(Date from, Date to) {
		EntityManager entityManager = getEntityManager();
		TypedQuery<CurrencyEntity> query = entityManager.createNamedQuery("Currency.getByBiggestRateDifferenceInPeriod",
				CurrencyEntity.class);
		query.setMaxResults(1);
		query.setParameter("from", from);
		query.setParameter("to", to);
		CurrencyEntity result = query.getSingleResult();
		entityManager.close();
		return result;
	}
}
