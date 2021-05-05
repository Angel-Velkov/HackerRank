package primeChecker;

public class Prime {
    public void checkPrime(int... nums) {
        for (int currentNum : nums) {
            if (isPrime(currentNum)) {
                System.out.print(currentNum + " ");
            }
        }
        System.out.println();
    }

    private boolean isPrime(int currentNum) {
        if (currentNum < 2) {
            return false;
        }

        for (int i = 2; i < currentNum; i++) {
            if (currentNum % i == 0) {
                return false;
            }
        }

        return true;
    }
}
