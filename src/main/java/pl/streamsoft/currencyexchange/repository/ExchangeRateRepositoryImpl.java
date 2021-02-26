package pl.streamsoft.currencyexchange.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.streamsoft.currencyexchange.entity.ExchangeRateEntity;

@Repository
@Transactional
public class ExchangeRateRepositoryImpl implements ExchangeRateRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addExchangeRate(ExchangeRateEntity rate) {
		entityManager.persist(rate);
	}

	@Override
	public ExchangeRateEntity getExchangeRateByCode(String currencyCode, Date date) {
		TypedQuery<ExchangeRateEntity> query = entityManager.createNamedQuery("ExchangeRate.getByCode",
				ExchangeRateEntity.class);
		query.setParameter("code", currencyCode);
		query.setParameter("date", date);
		try {
			ExchangeRateEntity result = query.getSingleResult();
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<ExchangeRateEntity> getAllExchangeRates(Date date) {
		TypedQuery<ExchangeRateEntity> query = entityManager.createNamedQuery("ExchangeRate.getAll",
				ExchangeRateEntity.class);
		List<ExchangeRateEntity> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public ExchangeRateEntity updateExchangeRate(ExchangeRateEntity rate) {
		return entityManager.merge(rate);
	}

	@Override
	public void deleteExchangeRate(Long id) {
		ExchangeRateEntity rate = entityManager.find(ExchangeRateEntity.class, id);
		entityManager.remove(rate);
	}

	public BigDecimal getMaxRateFromPeriodForCurrency(String currencyCode, Date from, Date to) {
		TypedQuery<BigDecimal> query = entityManager.createNamedQuery("ExchangeRate.getMaxRateFromPeriodForCurrency",
				BigDecimal.class);
		query.setParameter("code", currencyCode);
		query.setParameter("from", from);
		query.setParameter("to", to);
		BigDecimal result = query.getSingleResult();
		return result;
	}

	public BigDecimal getMinRateFromPeriodForCurrency(String currencyCode, Date from, Date to) {
		TypedQuery<BigDecimal> query = entityManager.createNamedQuery("ExchangeRate.getMinRateFromPeriodForCurrency",
				BigDecimal.class);
		query.setParameter("code", currencyCode);
		query.setParameter("from", from);
		query.setParameter("to", to);
		BigDecimal result = query.getSingleResult();
		return result;
	}

	public List<BigDecimal> getMaxRatesForCurrency(String currencyCode, int limit) {
		TypedQuery<BigDecimal> query = entityManager.createNamedQuery("ExchangeRate.getMaxRatesForCurrency",
				BigDecimal.class);
		query.setMaxResults(limit);
		query.setParameter("code", currencyCode);
		List<BigDecimal> result = query.getResultList();
		return result;
	}

	public List<BigDecimal> getMinRatesForCurrency(String currencyCode, int limit) {
		TypedQuery<BigDecimal> query = entityManager.createNamedQuery("ExchangeRate.getMinRatesForCurrency",
				BigDecimal.class);
		query.setMaxResults(limit);
		query.setParameter("code", currencyCode);
		List<BigDecimal> result = query.getResultList();
		return result;
	}
}
