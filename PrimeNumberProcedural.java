/*
 * Single threaded version of PrimeNumber, using isPrime method of Number to determine prime numbers
 */
public class PrimeNumberProcedural {
	public static final String RESULT_FILE_NAME = "result_procedural.txt";
	
	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		PrimeNumber.writeToFile("", false, PrimeNumberProcedural.RESULT_FILE_NAME);
		
		int PRIMES_TO_FIND = 1000;
		
		int count = 0;
		int[] primes = new int[PRIMES_TO_FIND];
		
		
		for(int i = 1; count < primes.length; i++) {
			if(PrimeNumberProcedural.isPrime(i)) {
				PrimeNumber.writeToFile(Integer.toString(i), true, PrimeNumberProcedural.RESULT_FILE_NAME);
				count++;
			}
		}
		

		PrimeNumber.writeToFile(PrimeNumber.elapsedTime(start), true, PrimeNumberProcedural.RESULT_FILE_NAME);
		System.out.println(PrimeNumber.elapsedTime(start));
	}
	
	public static boolean isPrime(int _number) {
		int i;
		int number = _number;

		if (number <= 1)
			return false;

		for (i = 2; i * i <= number; i++) {
			if (number % i == 0)
				return false;
		}

		return true;
	}

}
