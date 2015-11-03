import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private Node first;
	private Node last;
	private int size;

	private class Node {
		Item item;
		Node prev;
		Node next;
	}

	public Deque() {
		first = null;
		last = null;
		size = 0;
	}

	public boolean isEmpty() {
		return (size() == 0);
	}

	public int size() {
		return size;
	}

	public void addFirst(Item item) {
		if (item == null)
			throw new NullPointerException();

		if (isEmpty()) {
			first = new Node();
			last = first;
			first.item = item;
		} else {
			first.prev = new Node();
			first.prev.next = first;
			first = first.prev;
			first.item = item;
		}

		size++;
	}

	public void addLast(Item item) {
		if (item == null)
			throw new NullPointerException();

		if (isEmpty()) {
			first = new Node();
			last = first;
			first.item = item;
		} else {
			last.next = new Node();
			last.next.prev = last;
			last = last.next;
			last.item = item;
		}

		size++;
	}

	public Item removeFirst() {
		if (isEmpty())
			throw new NoSuchElementException();

		Item item = first.item;

		if (size == 1) {
			first = null;
			last = null;
		} else {
			first = first.next;
			first.prev = null;
		}

		size--;

		return item;
	}

	public Item removeLast() {
		if (isEmpty())
			throw new NoSuchElementException();

		Item item = last.item;

		if (size == 1) {
			first = null;
			last = null;
		} else {
			last = last.prev;
			last.next = null;
		}

		size--;

		if (isEmpty()) {
			first = null;
			last = null;
		}

		return item;
	}

	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Item> {
		private Node current = first;

		public boolean hasNext() {
			return (current != null);
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();

			Item item = current.item;
			current = current.next;
			return item;
		}
	}

	public static void main(String[] args) {
		
	}
}