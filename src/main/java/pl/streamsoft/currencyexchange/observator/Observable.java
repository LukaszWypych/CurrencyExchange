package pl.streamsoft.currencyexchange.observator;

public interface Observable {

	void subscribe(Observator observer);

	void unsubscribe(Observator observer);

	void notifyObservators();
}
