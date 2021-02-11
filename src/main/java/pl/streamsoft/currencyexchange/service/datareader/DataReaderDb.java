package pl.streamsoft.currencyexchange.service.datareader;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.json.JSONObject;

import pl.streamsoft.currencyexchange.ExchangeRate;
import pl.streamsoft.currencyexchange.exception.CurrencyNotFoundException;

public class DataReaderDb implements DataReader {

	private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

	@Override
	public String getExchangeRateBody(String currencyCode, Date date) {
		Session session = factory.openSession();
//		Query query = session.createQuery("select e from ExchangeRate e where e.date = :date");
//		query.setParameter("date", date);
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ExchangeRate> cq = cb.createQuery(ExchangeRate.class);
		Root<ExchangeRate> root = cq.from(ExchangeRate.class);
        cq.select(root).where(cb.equal(root.get("date"), date), cb.equal(root.get("code"), currencyCode));
        Query<ExchangeRate> query = session.createQuery(cq);

        ExchangeRate result = query.uniqueResult();
        if(result==null) {
        	throw new CurrencyNotFoundException("Currency not found");
        }
        
		JSONObject bodyJson = new JSONObject(result);
		return bodyJson.toString();
	}

	@Override
	public boolean isDateValid(Date date) {
		Session session = factory.openSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<ExchangeRate> cq = cb.createQuery(ExchangeRate.class);
		Root<ExchangeRate> root = cq.from(ExchangeRate.class);
        cq.select(root).where(cb.equal(root.get("date"), date));
        Query<ExchangeRate> query = session.createQuery(cq);

		return query.getResultList() != null;
	}
}
