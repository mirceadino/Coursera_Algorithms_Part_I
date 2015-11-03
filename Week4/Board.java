public class Board {
	private int N;
	private int[][] blocks;

	/**
	 * construct a board from an N-by-N array of blocks (where blocks[i][j] =
	 * block in row i, column j)
	 */

	public Board(int[][] blocks) {
		this.N = blocks.length;
		this.blocks = new int[N][N];

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				this.blocks[i][j] = blocks[i][j];
	}

	/**
	 * board dimension N
	 */

	public int dimension() {
		return N;
	}

	/**
	 * exchange the values of blocks (i0, j0) and (i1, j1)
	 */

	private void exchange(int i0, int j0, int i1, int j1) {
		blocks[i0][j0] = blocks[i0][j0] + blocks[i1][j1];
		blocks[i1][j1] = blocks[i0][j0] - blocks[i1][j1];
		blocks[i0][j0] = blocks[i0][j0] - blocks[i1][j1];
	}

	/**
	 * value of the block in the goal board of line i, column j
	 */

	private int blockValue(int i, int j) {
		if (i == N - 1 && j == N - 1)
			return 0;

		return i * N + j + 1;
	}

	/**
	 * position-line of the block in the goal board of value v
	 */

	private int blockLine(int v) {
		if (v == 0)
			return N - 1;
		return (v - 1 - blockColumn(v)) / N;
	}

	/**
	 * position-column of the block in the goal board of value v
	 */

	private int blockColumn(int v) {
		if (v == 0)
			return N - 1;
		return (v - 1) % N;
	}

	/**
	 * number of blocks out of place
	 */

	public int hamming() {
		int number = 0;

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] == 0)
					continue;

				if (blockValue(i, j) != blocks[i][j])
					number++;
			}

		return number;
	}

	/**
	 * sum of Manhattan distances between blocks and goal
	 */

	public int manhattan() {
		int sumOfDistances = 0;
		int x, y;

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] == 0)
					continue;

				x = blockLine(blocks[i][j]);
				y = blockColumn(blocks[i][j]);

				sumOfDistances += Math.abs(i - x) + Math.abs(j - y);
			}

		return sumOfDistances;
	}

	/**
	 * is this board the goal board?
	 */

	public boolean isGoal() {
		return (hamming() == 0);
	}

	/**
	 * a board that is obtained by exchanging two adjacent blocks in the same
	 * row
	 */

	public Board twin() {
		Board twinBoard = new Board(this.blocks);

		for (int i = 0; i < N; i++)
			for (int j = 0; j + 1 < N; j++)
				if (this.blocks[i][j] != 0 && this.blocks[i][j + 1] != 0) {
					twinBoard.exchange(i, j, i, j + 1);
					return twinBoard;
				}

		return twinBoard;
	}

	/**
	 * does this board equal y?
	 */

	public boolean equals(Object y) {
		if (y == this)
			return true;
		if (y == null)
			return false;
		if (y.getClass() != this.getClass())
			return false;

		Board that = (Board) y;

		if (this.N != that.N)
			return false;

		for (int i = 0; i < this.N; i++)
			for (int j = 0; j < this.N; j++)
				if (this.blocks[i][j] != that.blocks[i][j])
					return false;

		return true;
	}

	/**
	 * all neighboring boards
	 */

	public Iterable<Board> neighbors() {
		Stack<Board> S = new Stack<Board>();
		int[] dx = { -1, 1, 0, 0 };
		int[] dy = { 0, 0, -1, 1 };
		int i0 = -1, j0 = -1;
		int x, y;

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (blocks[i][j] == 0) {
					i0 = i;
					j0 = j;
					break;
				}

		for (int i = 0; i < 4; i++) {
			x = i0 + dx[i];
			y = j0 + dy[i];

			if (0 <= x && x < N && 0 <= y && y < N) {
				Board neighbor = new Board(blocks);
				neighbor.exchange(i0, j0, x, y);
				S.push(neighbor);
			}
		}

		return S;
	}

	/**
	 * string representation of this board (in the output format specified
	 * below)
	 */

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", blocks[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	/**
	 * unit tests (not graded)
	 */

	public static void main(String[] args) {

	}
}