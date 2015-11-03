/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

	// compare points by slope
	public final Comparator<Point> SLOPE_ORDER = new BySlope();

	private final int x; // x coordinate
	private final int y; // y coordinate

	// create the point (x, y)
	public Point(int x, int y) {
		/* DO NOT MODIFY */
		this.x = x;
		this.y = y;
	}

	// plot this point to standard drawing
	public void draw() {
		/* DO NOT MODIFY */
		StdDraw.point(x, y);
	}

	// draw line between this point and that point to standard drawing
	public void drawTo(Point that) {
		/* DO NOT MODIFY */
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	// slope between this point and that point
	public double slopeTo(Point that) {
		double slope;

		if (this.x == that.x && this.y == that.y)
			slope = Double.NEGATIVE_INFINITY;
		else if (this.x == that.x)
			slope = Double.POSITIVE_INFINITY;
		else if (this.y == that.y)
			slope = +0.0;
		else
			slope = (this.y - that.y) * 1.0 / (this.x - that.x);

		return slope;
	}

	private class BySlope implements Comparator<Point> {
		public int compare(Point A, Point B) {
			double m1 = slopeTo(A);
			double m2 = slopeTo(B);

			if (m1 < m2)
				return -1;

			if (m1 > m2)
				return +1;

			return 0;
		}
	}

	// is this point lexicographically smaller than that one?
	// comparing y-coordinates and breaking ties by x-coordinates
	public int compareTo(Point that) {
		if (this.y == that.y) {
			if (this.x < that.x)
				return -1;
			if (this.x > that.x)
				return +1;
		} else if (this.y < that.y)
			return -1;
		else if (this.y > that.y)
			return +1;
		return 0;
	}

	// return string representation of this point
	public String toString() {
		/* DO NOT MODIFY */
		return "(" + x + ", " + y + ")";
	}

	// unit test
	public static void main(String[] args) {
		int N = StdIn.readInt();

		Point[] P = new Point[N];
		int poz = 0;

		for (int i = 0; i < N; i++) {
			int x = StdIn.readInt();
			int y = StdIn.readInt();

			P[i] = new Point(x, y);

			if (P[poz].compareTo(P[i]) > 0)
				poz = i;
		}

		Point aux = P[poz];

		Arrays.sort(P, aux.SLOPE_ORDER);

		for (int i = 0; i < N; i++)
			StdOut.print(P[i].toString() + " ");
		StdOut.println();

		for (int i = 0; i < N; i++)
			StdOut.print(aux.slopeTo(P[i]) + " ");
		StdOut.println();
	}
}