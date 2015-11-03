public class Percolation {
	private int gridDimension;
	private boolean[][] grid;
	private WeightedQuickUnionUF dsuP;
	private WeightedQuickUnionUF dsuF;
	private int top;
	private int bottom;

	private boolean outOfTheBounds(int i) {
		if (1 <= i && i <= gridDimension)
			return false;
		else
			return true;
	}

	private int twoToOne(int i, int j) {
		if (i < 1)
			return top;

		if (i > gridDimension)
			return bottom;

		return (i - 1) * gridDimension + j;
	}

	public Percolation(int N) {
		if (N <= 0)
			throw new IllegalArgumentException();

		gridDimension = N;
		grid = new boolean[gridDimension + 5][gridDimension + 5];
		dsuP = new WeightedQuickUnionUF(gridDimension * gridDimension + 5);
		dsuF = new WeightedQuickUnionUF(gridDimension * gridDimension + 5);
		top = 0;
		bottom = gridDimension * gridDimension + 1;

		/* block all the grid */

		for (int i = 0; i <= gridDimension + 1; i++)
			for (int j = 0; j <= gridDimension + 1; j++)
				grid[i][j] = false;

		/* open the top and the bottom of the grid */

		for (int j = 1; j <= gridDimension; j++) {
			grid[0][j] = true;
			grid[gridDimension + 1][j] = true;
		}
	}

	public void open(int i, int j) {
		if (outOfTheBounds(i) || outOfTheBounds(j))
			throw new IndexOutOfBoundsException();

		grid[i][j] = true;

		/* merge with neighboring open cells */

		if (grid[i - 1][j] == true) {
			dsuP.union(twoToOne(i, j), twoToOne(i - 1, j));
			dsuF.union(twoToOne(i, j), twoToOne(i - 1, j));
		}

		if (grid[i + 1][j] == true) {
			dsuP.union(twoToOne(i, j), twoToOne(i + 1, j));
			if (i + 1 <= gridDimension)
				dsuF.union(twoToOne(i, j), twoToOne(i + 1, j));
		}

		if (grid[i][j - 1] == true) {
			dsuP.union(twoToOne(i, j), twoToOne(i, j - 1));
			dsuF.union(twoToOne(i, j), twoToOne(i, j - 1));
		}

		if (grid[i][j + 1] == true) {
			dsuP.union(twoToOne(i, j), twoToOne(i, j + 1));
			dsuF.union(twoToOne(i, j), twoToOne(i, j + 1));
		}
	}

	public boolean isOpen(int i, int j) {
		if (outOfTheBounds(i) || outOfTheBounds(j))
			throw new IndexOutOfBoundsException("Cell " + i + " " + j);
		return grid[i][j];
	}

	public boolean isFull(int i, int j) {
		if (outOfTheBounds(i) || outOfTheBounds(j))
			throw new IndexOutOfBoundsException();
		return (isOpen(i, j) && dsuF.connected(twoToOne(i, j), top));
	}

	public boolean percolates() {
		return (dsuP.connected(top, bottom));
	}

	public static void main(String[] args) {
		
	}
}