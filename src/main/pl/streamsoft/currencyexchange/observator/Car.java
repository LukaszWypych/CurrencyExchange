package pl.streamsoft.currencyexchange.observator;

import java.util.HashSet;
import java.util.Set;

public class Car implements Observable {

	private Set<Observator> observators;
	private String state;

	public Car() {
		observators = new HashSet<>();
		state = "stopped";
	}

	public String getState() {
		return state;
	}

	public void start() {
		state = "moving";
		notifyObservators();
	}

	public void stop() {
		state = "stopped";
		notifyObservators();
	}

	@Override
	public void subscribe(Observator observer) {
		observators.add(observer);
	}

	@Override
	public void unsubscribe(Observator observer) {
		observators.remove(observer);
	}

	@Override
	public void notifyObservators() {
		observators.forEach(o -> o.update());
	}

}
