package pl.streamsoft.currencyexchange.repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryHelper {

	private static EntityManagerFactory factory;

	static {
		factory = Persistence.createEntityManagerFactory("pgsql");
	}

	public static EntityManagerFactory getFactory() {
		return factory;
	}

}
