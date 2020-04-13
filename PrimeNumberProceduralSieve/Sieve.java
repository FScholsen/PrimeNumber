package PrimeNumberProceduralSieve;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.util.Arrays;
import PrimeNumber.PrimeNumber;

public class Sieve {

	public static final String RESULT_FILE_NAME = "result_sieve.txt";
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		PrimeNumber.writeToFile("", false, Sieve.RESULT_FILE_NAME);
		
		int m, k;
		// For the first 1000 prime numbers use 7920 because the 1000th prime number is 7919 
		int nlimit = 7920;
		int klimit = (int) Math.sqrt((double) nlimit) + 1;

		/* Create an array of boolean values all set to true (here : 0 = true = prime, other than 0 is false = not prime = -1)*/
		boolean[] mark = new boolean[nlimit];
		Arrays.fill(mark, true);
		
		/* Mark the number 1 with -1, meaning that it is not a prime number */
		mark[1] = false;
		
		for (k = 1; k < klimit; k++) {
			if (mark[k]) {
				for(m = k * k; m < nlimit; m += k)
					mark[m] = false;
			}
		}
		
		for(m = 1; m < nlimit; m++) {
			//System.out.println(m + " : " + mark[m]);
			if(mark[m]) {
				Sieve.writeToFile(m, true);
			}
		}
		mark = null;
		
		PrimeNumber.writeToFile(PrimeNumber.elapsedTime(start), true, Sieve.RESULT_FILE_NAME);
		System.out.println(PrimeNumber.elapsedTime(start));
	}
	
	public static boolean writeToFile(int _number, boolean _append) {
		BufferedWriter out = null;
		boolean fileAppended = true;
		try {
			FileWriter fstream = new FileWriter(RESULT_FILE_NAME, _append);
			out = new BufferedWriter(fstream);
			out.write(Integer.toString(_number));
			if (_append) out.write("\n");
		} catch (IOException e) {
			fileAppended = false;
			System.err.println("Error: " + e.getMessage());
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					fileAppended = false;
					e.printStackTrace();
				}
			}
		}
		return fileAppended;
	}

}
