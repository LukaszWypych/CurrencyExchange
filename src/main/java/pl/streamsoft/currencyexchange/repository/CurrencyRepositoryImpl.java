package pl.streamsoft.currencyexchange.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.streamsoft.currencyexchange.entity.CurrencyEntity;

@Repository
@Transactional
public class CurrencyRepositoryImpl implements CurrencyRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addCurrency(CurrencyEntity currency) {
		entityManager.persist(currency);
	}

	@Override
	public CurrencyEntity getCurrencyByCode(String code) {
		TypedQuery<CurrencyEntity> query = entityManager.createNamedQuery("Currency.getByCode", CurrencyEntity.class);
		query.setParameter("code", code);
		try {
			CurrencyEntity result = query.getSingleResult();
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<CurrencyEntity> getAllCurrencies() {
		TypedQuery<CurrencyEntity> query = entityManager.createNamedQuery("Currency.getAll", CurrencyEntity.class);
		List<CurrencyEntity> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public CurrencyEntity updateCurrency(CurrencyEntity currency) {
		return entityManager.merge(currency);
	}

	public CurrencyEntity getCurrencyWithHighestRateDifferenceInPeriod(Date from, Date to) {
		Query query = entityManager.createNamedQuery("Currency.getByHighestRateDifferenceInPeriod");
		query.setParameter("from", from);
		query.setParameter("to", to);
		CurrencyEntity result = (CurrencyEntity) query.getSingleResult();
		return result;
	}
}
