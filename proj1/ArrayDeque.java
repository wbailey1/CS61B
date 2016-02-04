public class ArrayDeque<Item> {
	private Item[] items;
	private int size;
	private static int RFACTOR = 2;

    public ArrayDeque() {
    	size = 0;
    	items = (Item[]) new Object[8];
    }

    public void addFirst(Item x) {
    	if (size == items.length) {
    		resize(size * RFACTOR);
    	}
		Item[] a = (Item[]) new Object[items.length];
    	a[0] = x;
    	System.arraycopy(items, 0, a, 1, size);
    	items = a;
    }

    private void resize(int capacity) {
    	Item[] a = (Item[]) new Object[capacity];
    	System.arraycopy(items, 0, a, 0, size);
    	items = a;    	
    }

    public void addLast(Item x) {    	
    	if (size == items.length) {
    		resize(size * RFACTOR);
    	}
    	items[size] = x;
    	size = size + 1;
    }

    public boolean isEmpty() {
    	if (items[0] == null) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    public Item removeFirst() {
    	Item itemToReturn = items[0];
    	size = size - 1;
    	Item[] a = (Item[]) new Object[items.length];
    	System.arraycopy(items, 1, a, 0, size);
    	checkSize();
    	return itemToReturn;
    }

    public Item removeLast() {
		Item itemToReturn = items[size - 1];
		items[size - 1] = null;
		size = size - 1;
		checkSize();
		return itemToReturn;
    }

    private void checkSize() {
    	if (size > 0) {
    		int R = size / items.length;
    		if (R < 0.25) {
    			resize(items.length / 2);
    			checkSize();
    		}	
    	}
    }

    public int size() {
        return size;        
    }

    public Item get(int i) {
        return items[i];
    }

    public void printDeque() {
    	for (int i = 0; i < size; i++) {
    		System.out.print(items[i] + " ");
    	}
    }
}
