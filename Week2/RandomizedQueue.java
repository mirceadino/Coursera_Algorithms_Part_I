import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] stack;
	private int size;

	public RandomizedQueue() {
		stack = (Item[]) new Object[2];
		size = 0;
	}

	public boolean isEmpty() {
		return (size == 0);
	}

	public int size() {
		return size;
	}

	private void resize(int capacity) {
		assert capacity >= size;
		Item[] temp = (Item[]) new Object[capacity];
		for (int i = 0; i < size; i++) {
			temp[i] = stack[i];
		}
		stack = temp;
	}

	public void enqueue(Item item) {
		if (item == null)
			throw new NullPointerException();

		if (size == stack.length)
			resize(2 * stack.length);

		stack[size] = item;
		size++;
	}

	private int randomPosition() {
		return StdRandom.uniform(size);
	}

	private void swap(int i, int j) {
		Item auxiliary;
		auxiliary = stack[i];
		stack[i] = stack[j];
		stack[j] = auxiliary;
	}

	public Item dequeue() {
		if (isEmpty())
			throw new NoSuchElementException();

		int position = randomPosition();
		Item item;

		swap(position, size - 1);
		item = stack[size - 1];
		stack[size - 1] = null;
		size--;

		if (size > 0 && size == stack.length / 4)
			resize(stack.length / 2);

		return item;
	}

	public Item sample() {
		if (isEmpty())
			throw new NoSuchElementException();

		int position = randomPosition();
		Item item;

		swap(position, size - 1);
		item = stack[size - 1];

		return item;
	}

	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

	private class RandomizedQueueIterator implements Iterator<Item> {
		private int current;
		private int[] order;

		public RandomizedQueueIterator() {
			current = 0;
			order = new int[size];

			for (int i = 0; i < size; i++)
				order[i] = i;

			if (size >= 1)
				StdRandom.shuffle(order, 0, size - 1);
		}

		public boolean hasNext() {
			return (current < size);
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();

			Item item = stack[order[current]];
			current++;
			return item;
		}
	}

	public static void main(String[] args) {

	}
}