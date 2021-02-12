package pl.streamsoft.currencyexchange.service.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import pl.streamsoft.currencyexchange.ExchangeRate;

public class RepositoryHibernate implements Repository {

	private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

	public void addExchangeRate(ExchangeRate rate) {
		Session session = factory.openSession();
		session.beginTransaction();
		session.save(rate);
		session.getTransaction().commit();
		session.close();
	}

	public ExchangeRate getExchangeRate(String currencyCode, Date date) {
		Session session = factory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ExchangeRate> cq = cb.createQuery(ExchangeRate.class);
		Root<ExchangeRate> root = cq.from(ExchangeRate.class);
		cq.select(root).where(cb.equal(root.get("date"), date), cb.equal(root.get("code"), currencyCode));
		Query<ExchangeRate> query = session.createQuery(cq);

		return query.uniqueResult();
	}

	public List<ExchangeRate> getAllExchangeRates(Date date) {
		Session session = factory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ExchangeRate> cq = cb.createQuery(ExchangeRate.class);
		Root<ExchangeRate> root = cq.from(ExchangeRate.class);
		CriteriaQuery<ExchangeRate> all = cq.select(root).where(cb.equal(root.get("date"), date));
		Query<ExchangeRate> query = session.createQuery(all);

		return query.getResultList();
	}

	public void updateExchangeRate(ExchangeRate rate) {
		Session session = factory.openSession();
		session.beginTransaction();
		session.update(rate);
		session.getTransaction().commit();
		session.close();
	}

	public void deleteExchangeRate(Long id) {
		Session session = factory.openSession();
		session.beginTransaction();
		Object persistentInstance = session.load(ExchangeRate.class, id);
		if (persistentInstance != null) {
			session.delete(persistentInstance);
		}
		session.getTransaction().commit();
		session.close();
	}
}
