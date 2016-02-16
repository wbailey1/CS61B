public class LinkedListDeque<Item> implements Deque<Item> {
	private class Node {
		public Item item;     /* Equivalent of first */
		public Node next; /* Equivalent of rest */
		public Node prev;
		public Node(Item i, Node h, Node p) {
			item = i;
			next = h;	
			prev = p;	
		}
	} 

	private Node sentinel;
	private int size; 

	public LinkedListDeque() {
		size = 0;
		sentinel = new Node(null, null, null);
		sentinel.prev = sentinel;
		sentinel.next = sentinel;
	}

    @Override
	public void addFirst(Item x) {
		Node oldFrontNode = sentinel.next;
		Node newNode = new Node(x, oldFrontNode, sentinel);
		oldFrontNode.prev = newNode;
		sentinel.next = newNode;
		size += 1;
	}

    @Override
	public void addLast(Item x) {
		Node oldBackNode = sentinel.prev;
		Node newNode = new Node(x, sentinel, oldBackNode);
		oldBackNode.next = newNode;
		sentinel.prev = newNode;
		size += 1;
	}

    @Override
	public boolean isEmpty() {
		if (sentinel.next == sentinel) {
			return true;
		}
		else {
			return false;
		}
	}

    @Override
	public int size() {
		return size;
	}

    @Override
	public Item removeFirst() {
		Node oldFrontNode = sentinel.next;
		sentinel.next = oldFrontNode.next;
		sentinel.next.prev = sentinel;
		size -= 1;
		return oldFrontNode.item;
	}

    @Override
	public Item removeLast() {
		Node oldBackNode = sentinel.prev;
		sentinel.prev = oldBackNode.prev;
		sentinel.prev.next = sentinel;
		size -= 1;
		return oldBackNode.item;
	}

    @Override
	public Item get(int index) {
		int i = 0;
		Node currentNode = sentinel;
		while (i < index + 1) {
			if (currentNode.next == sentinel) {
				return null;
			}
			currentNode = currentNode.next;
			i += 1;
		}
		return currentNode.item;
	}

	private Item getRecursiveHelper(int index, Node currentNode) {
		if (currentNode.next == sentinel) {
			return null;
		}
		if (index == 0) {
			return currentNode.next.item;
		}
		return getRecursiveHelper(index - 1, currentNode.next);
	}

	public Item getRecursive(int index) {
		Node currentNode = sentinel;
		return getRecursiveHelper(index, currentNode);
	}

    @Override
	public void printDeque() {
		Node currentNode = sentinel;
		while (currentNode.next != sentinel) {
			System.out.print(currentNode.next.item + " ");
			currentNode = currentNode.next;
		}
	}
} 