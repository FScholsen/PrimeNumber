package PrimeNumber;

public class Number {
	
	/**
	 * @value Integer The decimal integer value
	 */
	private Integer value;
	
	/**
	 * @value boolean Flag for the prime numbers 
	 */
	private boolean isPrime = false;

	protected Number(Integer value) {
		this.value = value;
	}

	protected Integer getValue() {
		return value;
	}

	protected void setValue(Integer value) {
		this.value = value;
	}

	protected boolean getPrime() {
		return isPrime;
	}

	protected void setPrime(boolean isPrime) {
		this.isPrime = isPrime;
	}
	
	// TODO change implementation
	protected boolean isPrime() {
		int i;
		int number = this.getValue();

		if (number <= 1)
			return false;

		for (i = 2; i * i <= number; i++) {
			if (number % i == 0)
				return false;
		}

		return true;
	}

}
