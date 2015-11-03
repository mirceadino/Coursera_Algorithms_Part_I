import java.util.Arrays;

public class Fast {
	private static boolean goodPoints(Point origin, Point A, Point B, Point C) {
		if (origin.slopeTo(A) == origin.slopeTo(B)
				&& origin.slopeTo(B) == origin.slopeTo(C))
			return true;
		return false;
	}

	private static boolean bestPoints(Point origin, Point A, Point B, Point C) {
		if (goodPoints(origin, A, B, C) && origin.compareTo(A) < 0
				&& A.compareTo(B) < 0 && B.compareTo(C) < 0)
			return true;
		return false;
	}

	private static void print(Point[] S, int counter) {
		int i;

		for (i = 0; i + 1 < counter; i++) {
			if (S[i].compareTo(S[i + 1]) == 0)
				continue;

			StdOut.print(S[i].toString() + " -> ");
		}

		StdOut.println(S[i].toString());

		S[0].drawTo(S[counter - 1]);
	}

	public static void main(String[] args) {
		In FIn = new In(args[0]);

		// rescale coordinates and turn on animation mode
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.show(0);
		StdDraw.setPenRadius(0.0025); // make the points a bit larger

		// read

		int N = FIn.readInt();
		Point[] P = new Point[N];

		for (int i = 0; i < N; i++) {
			int x = FIn.readInt();
			int y = FIn.readInt();

			P[i] = new Point(x, y);

			P[i].draw();
		}

		// solve

		for (int i = 0; i < N; i++) {
			Point origin = P[i];
			Point[] Q = P.clone();

			Arrays.sort(Q);
			Arrays.sort(Q, origin.SLOPE_ORDER);

			int j, k, l;

			for (j = 1; j < N; j = k) {
				for (k = j + 1; k + 1 < N; k++)
					if (!goodPoints(origin, Q[k - 1], Q[k], Q[k + 1]))
						break;

				if (k - j + 1 >= 3) {
					Point[] S = new Point[k - j + 2];

					S[0] = origin;

					for (l = j; l <= k; l++)
						S[l - j + 1] = Q[l];

					for (l = 1; l + 2 < (k - j + 2); l++)
						if (!bestPoints(S[0], S[l], S[l + 1], S[l + 2]))
							break;

					if (l + 2 == (k - j + 2))
						print(S, k - j + 2);
				}
			}
		}

		// display to screen all at once
		StdDraw.show(0);

		// reset the pen radius
		StdDraw.setPenRadius();
	}
}