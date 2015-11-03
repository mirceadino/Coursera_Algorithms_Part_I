public class PercolationStats {
	private int N;
	private int T;
	private double[] X;

	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0)
			throw new IllegalArgumentException();

		this.N = N;
		this.T = T;
		X = new double[T];

		for (int test = 0; test < T; test++) {
			Percolation A = new Percolation(N);
			int count = 0;

			while (!A.percolates()) {
				int i, j;

				do {
					i = StdRandom.uniform(1, N + 1);
					j = StdRandom.uniform(1, N + 1);
				} while (A.isOpen(i, j));

				A.open(i, j);
				count++;
			}

			X[test] = count * 1.0 / (this.N * this.N);
		}
	}

	public double mean() {
		return StdStats.mean(X);
	}

	public double stddev() {
		return StdStats.stddev(X);
	}

	public double confidenceLo() {
		return (mean() - 1.96 * stddev() / Math.sqrt(T));
	}

	public double confidenceHi() {
		return (mean() + 1.96 * stddev() / Math.sqrt(T));
	}

	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);

		PercolationStats A = new PercolationStats(N, T);

		System.out.println("mean                    = " + A.mean());
		System.out.println("stddev                  = " + A.stddev());
		System.out.println("95% confidence interval = " + A.confidenceLo()
				+ ", " + A.confidenceHi());
	}
}