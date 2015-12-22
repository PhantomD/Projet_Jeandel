public class Fonc {

	public int multiply(int x, int y, int p) {
		int result = 0;
		while (y == 1) {
			if ((y & 1) == 0)
				result = (result + x) % p;
			y >>= 1;
			x = (2 * x) % p;
		}
		return result;
	}

	/** Question 1 **/

	public int puissance(int x, int k, int p) {
		return ((int) Math.pow(x, k) % p);

	}

	/** Question2 **/

	public boolean pseudoprime(int p) {

		boolean result = false;

		if ((int) (Math.pow(2, p - 1) % p) == 1) {
			result = true;
		}

		return result;

	}

	/** Question 3 **/

	public int nextprime() {
		int rand = 2 + (int) (Math.random() * (((int) (Math.pow(2, 23) - 1) - 2) + 1));
		return rand;

	}
}
