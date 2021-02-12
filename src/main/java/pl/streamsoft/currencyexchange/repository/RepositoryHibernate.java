package pl.streamsoft.currencyexchange.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import pl.streamsoft.currencyexchange.entity.ExchangeRateEntity;

public class RepositoryHibernate implements Repository {

	private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

	public void addExchangeRate(ExchangeRateEntity rate) {
		Session session = factory.openSession();
		session.beginTransaction();
		session.save(rate);
		session.getTransaction().commit();
		session.close();
	}

	public ExchangeRateEntity getExchangeRateByCode(String currencyCode, Date date) {
		Session session = factory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ExchangeRateEntity> cq = cb.createQuery(ExchangeRateEntity.class);
		Root<ExchangeRateEntity> root = cq.from(ExchangeRateEntity.class);
		cq.select(root).where(cb.equal(root.get("date"), date), cb.equal(root.get("code"), currencyCode));
		Query<ExchangeRateEntity> query = session.createQuery(cq);
		ExchangeRateEntity result = query.uniqueResult();
		session.close();
		return result;
	}

	public ExchangeRateEntity getExchangeRateByCountry(String country, Date date) {
		Session session = factory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ExchangeRateEntity> cq = cb.createQuery(ExchangeRateEntity.class);
		Root<ExchangeRateEntity> root = cq.from(ExchangeRateEntity.class);
		cq.select(root).where(cb.equal(root.get("date"), date), cb.equal(root.get("country"), country));
		Query<ExchangeRateEntity> query = session.createQuery(cq);

		ExchangeRateEntity result = query.uniqueResult();
		session.close();
		return result;
	}

	public List<ExchangeRateEntity> getAllExchangeRates(Date date) {
		Session session = factory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ExchangeRateEntity> cq = cb.createQuery(ExchangeRateEntity.class);
		Root<ExchangeRateEntity> root = cq.from(ExchangeRateEntity.class);
		CriteriaQuery<ExchangeRateEntity> all = cq.select(root).where(cb.equal(root.get("date"), date));
		Query<ExchangeRateEntity> query = session.createQuery(all);

		List<ExchangeRateEntity> result = query.getResultList();
		session.close();
		return result;
	}

	public void updateExchangeRate(ExchangeRateEntity rate) {
		Session session = factory.openSession();
		session.beginTransaction();
		session.update(rate);
		session.getTransaction().commit();
		session.close();
	}

	public void deleteExchangeRate(Long id) {
		Session session = factory.openSession();
		session.beginTransaction();
		Object persistentInstance = session.load(ExchangeRateEntity.class, id);
		if (persistentInstance != null) {
			session.delete(persistentInstance);
		}
		session.getTransaction().commit();
		session.close();
	}

}
