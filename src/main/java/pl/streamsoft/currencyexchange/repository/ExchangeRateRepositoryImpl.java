package pl.streamsoft.currencyexchange.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import pl.streamsoft.currencyexchange.entity.ExchangeRateEntity;

@Repository
public class ExchangeRateRepositoryImpl implements ExchangeRateRepository {

//	private EntityManager getEntityManager() {
//		return EntityManagerFactoryHelper.getFactory().createEntityManager();
//	}

	@PersistenceContext(unitName = "pgsqll")
	private EntityManager entityManager;

	@Override
	public void addExchangeRate(ExchangeRateEntity rate) {
		// EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(rate);
		entityManager.getTransaction().commit();
		// entityManager.close();
	}

	@Override
	public ExchangeRateEntity getExchangeRateByCode(String currencyCode, Date date) {
		// EntityManager entityManager = getEntityManager();
		TypedQuery<ExchangeRateEntity> query = entityManager.createNamedQuery("ExchangeRate.getByCode",
				ExchangeRateEntity.class);
		query.setParameter("code", currencyCode);
		query.setParameter("date", date);
		ExchangeRateEntity result = query.getSingleResult();
		// entityManager.close();
		return result;
	}

	@Override
	public List<ExchangeRateEntity> getAllExchangeRates(Date date) {
		// EntityManager entityManager = getEntityManager();
		TypedQuery<ExchangeRateEntity> query = entityManager.createNamedQuery("ExchangeRate.getAll",
				ExchangeRateEntity.class);
		List<ExchangeRateEntity> resultList = query.getResultList();
		// entityManager.close();
		return resultList;
	}

	@Override
	public ExchangeRateEntity updateExchangeRate(ExchangeRateEntity rate) {
		// EntityManager entityManager = getEntityManager();
		rate = entityManager.merge(rate);
		// entityManager.close();
		return rate;
	}

	@Override
	public void deleteExchangeRate(Long id) {
		// EntityManager entityManager = getEntityManager();
		ExchangeRateEntity rate = entityManager.find(ExchangeRateEntity.class, id);
		entityManager.getTransaction().begin();
		entityManager.persist(rate);
		entityManager.getTransaction().commit();
		// entityManager.close();
	}

	public BigDecimal getMaxRateFromPeriodForCurrency(String currencyCode, Date from, Date to) {
		// EntityManager entityManager = getEntityManager();
		TypedQuery<BigDecimal> query = entityManager.createNamedQuery("ExchangeRate.getMaxRateFromPeriodForCurrency",
				BigDecimal.class);
		query.setParameter("code", currencyCode);
		query.setParameter("from", from);
		query.setParameter("to", to);
		BigDecimal result = query.getSingleResult();
		// entityManager.close();
		return result;
	}

	public BigDecimal getMinRateFromPeriodForCurrency(String currencyCode, Date from, Date to) {
		// EntityManager entityManager = getEntityManager();
		TypedQuery<BigDecimal> query = entityManager.createNamedQuery("ExchangeRate.getMinRateFromPeriodForCurrency",
				BigDecimal.class);
		query.setParameter("code", currencyCode);
		query.setParameter("from", from);
		query.setParameter("to", to);
		BigDecimal result = query.getSingleResult();
		// entityManager.close();
		return result;
	}

	public List<BigDecimal> getMaxRatesForCurrency(String currencyCode, int limit) {
		// EntityManager entityManager = getEntityManager();
		TypedQuery<BigDecimal> query = entityManager.createNamedQuery("ExchangeRate.getMaxRatesForCurrency",
				BigDecimal.class);
		query.setMaxResults(limit);
		query.setParameter("code", currencyCode);
		List<BigDecimal> result = query.getResultList();
		// entityManager.close();
		return result;
	}

	public List<BigDecimal> getMinRatesForCurrency(String currencyCode, int limit) {
		// EntityManager entityManager = getEntityManager();
		TypedQuery<BigDecimal> query = entityManager.createNamedQuery("ExchangeRate.getMinRatesForCurrency",
				BigDecimal.class);
		query.setMaxResults(limit);
		query.setParameter("code", currencyCode);
		List<BigDecimal> result = query.getResultList();
		// entityManager.close();
		return result;
	}
}
