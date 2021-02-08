package pl.streamsoft.currencyexchange.observator;

public class Passenger implements Observator {

	private Car car;
	private String carState;
	
	public Passenger(Car car) {
		this.car = car;
		carState = car.getState();
	}

	@Override
	public void update() {
		carState = car.getState();
		System.out.println("Car changed state. New state: "+carState);
	}

}
