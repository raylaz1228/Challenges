/**
 * Write a function that takes a positive integer, N, and returns the maximal product of a set of
 * positive integers whose sum is N. Invalid inputs should return 0, that is non-positive integers or
 * any integer where no set of at least 2 positive addends exists.
 * For example: Given 8, the result is 18
 * 8 can be written as 2*2*2*2 whose product is 16. However, it can also be written as 3*3*2
 * whose product is 18.
 */
public class Challenge2 {

    /**
     * This method finds the maximum product that can be achieved at each index upto n.
     * We store the result in a dp array which is used for finding the maximum product of the subsequent numbers.
     * @param n number that needs to be summed to.
     * @return long max product
     */
    private static long getMaximalProductForSum(int n) {
        if (n <= 0) {
            return 0;
        }

        if (n <= 4) {
            return n;
        }

        long[] dp = new long[n+1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 3;
        dp[4] = 4;

        for (int i=5;i<=n;i++) {

            /**
             * At each step, we find the maximum we can get by processing each index with either 2 or 3.
             * For example, for n=8, we can do Math.max(dp[5]*dp[3], dp[6]*dp[2]) which gives us 18.
             * Both (5,3) and (6,2) would add upto 8.
             * But before we process (5,3) and (6,2) we make sure we have the results for the individual indices in dp array.
             * We compute the prior values using bottom up dynamic programming approach.
             */
            dp[i] = Math.max(dp[i-2] * dp[i-(i-2)], dp[i-3] * dp[i-(i-3)]);
        }
        return dp[n];
    }



    /**
     * Assertion method to check the test cases.
     * In the event of difference in value, this method would throw an exception.
     * @param n1
     * @param n2
     * @return boolean true or exception
     */
    private static boolean assertEquals(long n1, long n2) {
        if (n1 == n2) {
            return true;
        } else {
            throw new RuntimeException("Assertion failed as two values are different.");
        }
    }

    /**
     * Entry point method for standalone class.
     * @param args none needed.
     * @throws Exception if any
     */
    public static void main(String[] args) throws Exception {
        assertEquals(getMaximalProductForSum(20), 1458);
        assertEquals(getMaximalProductForSum(1), 1);
        assertEquals(getMaximalProductForSum(8), 18);
        assertEquals(getMaximalProductForSum(0), 0);
        // The below test case for N=40, gets a maximum with 3 ^ 12 * 2 ^ 2 which is 2125764.
        assertEquals(getMaximalProductForSum(40), 2125764);
        assertEquals(getMaximalProductForSum(Integer.MIN_VALUE), 0);

        System.out.println("All cases passed!");
        System.out.println("Time Complexity is O(n) as we compute max product for all indices upto N.");
        System.out.println("Space Complexity is O(n) to store all max products for indices upto N.");
    }
}
