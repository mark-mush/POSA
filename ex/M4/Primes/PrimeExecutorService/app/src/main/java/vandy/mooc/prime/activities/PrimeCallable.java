package vandy.mooc.prime.activities;

import android.util.Log;

import java.util.concurrent.Callable;

/**
 * Uses a brute-force algorithm to determine if a given number is
 * prime or not.
 */
public class PrimeCallable
       implements Callable<PrimeCallable.PrimeResult> {
    /**
     * Debugging tag used by the Android logger.
     */
    private final String TAG =
        getClass().getSimpleName();

    /** 
     * Number to evaluate for "primality".
     */
    private final long mPrimeCandidate;

    /**
     * The result returned via the future.
     */
    public static class PrimeResult {
        /**
         * Value that was evaluated for primality.
         */
        long mPrimeCandidate;
        
        /**
         * Result of the isPrime() method.
         */
        long mSmallestFactor;
        
        /**
         * Constructor initializes the fields.
         */
        PrimeResult(long primeCandidate, long smallestFactor) {
            mPrimeCandidate = primeCandidate;
            mSmallestFactor = smallestFactor;
        }
    }

    /**
     * Constructor initializes the fields.
     */
    public PrimeCallable(long primeCandidate) {
        mPrimeCandidate = primeCandidate;
    }
    
    /**
     * This method provides a brute-force determination of whether
     * number @a n is prime.  Returns 0 if it is prime, or the
     * smallest factor if it is not prime. 
     */
    private long isPrime(long n) {
        if (n > 3)
            for (long factor = 2;
                 factor <= n / 2; 
                 ++factor)
                if ((factor % (n / 10)) == 0
                    && Thread.interrupted()) {
                    Log.d(TAG,
                          "Thread interrupted "
                          + Thread.currentThread());
                    break;
                } else if (n / factor * factor == n)
                    return factor;

        return 0;
    }

    /**
     * Hook method that determines if a given number is prime.
     * Returns 0 if it is prime or the smallest factor if it is not
     * prime.
     */
    public PrimeResult call() {
        // Return a PrimeResult containing the prime candidate and the
        // result of checking this number for primality.
        return new PrimeResult(mPrimeCandidate,
                               // Determine if mPrimeCandidate is
                               // prime or not.
                               isPrime(mPrimeCandidate));
    }
}
