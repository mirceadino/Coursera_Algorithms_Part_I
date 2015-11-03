import java.util.Comparator;

public class Solver {
	private Board initial;
	private boolean solvable;
	private Stack<Board> solution;

	/**
	 * node class to define a state in the search algorithm
	 */

	private class Node {
		public int steps;
		public Node father;
		public Board current;
		public final Comparator<Node> HAMMING = new HammingOrder();
		public final Comparator<Node> MANHATTAN = new ManhattanOrder();
		public boolean fromInitial;

		public Node(Board current, boolean isReal) {
			this.steps = 1;
			this.father = this;
			this.current = current;
			this.fromInitial = isReal;
		}

		public Node(int steps, Node father, Board current, boolean isReal) {
			this.steps = steps;
			this.father = father;
			this.current = current;
			this.fromInitial = isReal;
		}

		private class HammingOrder implements Comparator<Node> {
			public int compare(Node A, Node B) {
				int a = A.steps + A.current.hamming();
				int b = B.steps + B.current.hamming();

				if (a < b)
					return -1;
				if (a > b)
					return +1;
				return 0;
			}
		}

		private class ManhattanOrder implements Comparator<Node> {
			public int compare(Node A, Node B) {
				int a = A.steps + A.current.manhattan();
				int b = B.steps + B.current.manhattan();

				if (a < b)
					return -1;
				if (a > b)
					return +1;
				return HAMMING.compare(A, B);
			}
		}
	}

	/**
	 * find a solution to the initial board (using the A* algorithm)
	 */

	public Solver(Board initial) {
		if (initial == null)
			throw new NullPointerException();

		Board twin;
		MinPQ<Node> PQ;
		Node A = null;
		Node auxiliary = new Node(initial, false);

		this.initial = initial;
		twin = this.initial.twin();

		PQ = new MinPQ<Node>(auxiliary.MANHATTAN);

		PQ.insert(new Node(initial, true));
		PQ.insert(new Node(twin, false));

		while (!PQ.isEmpty()) {
			A = PQ.delMin();

			if (A.current.isGoal()) {
				this.solvable = A.fromInitial;
				break;
			}

			for (Board neighbor : A.current.neighbors())
				if (!neighbor.equals(A.father.current))
					PQ.insert(new Node(A.steps + 1, A, neighbor, A.fromInitial));
		}

		if (this.solvable) {
			int numberOfSteps = A.steps;
			solution = new Stack<Board>();

			while (numberOfSteps != 0) {
				solution.push(A.current);
				A = A.father;
				numberOfSteps--;
			}
		}
	}

	/**
	 * is the initial board solvable?
	 */

	public boolean isSolvable() {
		return solvable;
	}

	/**
	 * min number of moves to solve initial board; -1 if unsolvable
	 */

	public int moves() {
		if (!isSolvable())
			return -1;
		return this.solution.size() - 1;
	}

	/**
	 * sequence of boards in a shortest solution; null if unsolvable
	 */

	public Iterable<Board> solution() {
		return this.solution;
	}

	/**
	 * solve a slider puzzle (given below)
	 */

	public static void main(String[] args) {
		// create initial board from file
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}