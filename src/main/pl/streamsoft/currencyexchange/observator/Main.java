package pl.streamsoft.currencyexchange.observator;

public class Main {

	public static void main(String[] args) {
		Car car = new Car();
		Passenger passenger = new Passenger(car);
		car.subscribe(passenger);
		car.start();
		car.stop();
	}

}
