public class ArrayDeque<Item> {
    private Item[] items;
    private int size;
    private int nextLast;
    private int nextFirst;
    private static int RFACTOR = 2;

    public ArrayDeque() {
        size = 0;
        nextLast = 0;
        nextFirst = 7;
        items = (Item[]) new Object[8];
    }

    private int backOne(int marker) {
        if (marker == 0) {
            return items.length - 1;
        }
        else {
            return marker - 1;
        }
    }

    private int upOne(int marker) {
        if (marker == items.length - 1) {
            return 0;
        }
        else {
            return marker + 1;
        }
    }

    private void resize(int capacity) {
        Item[] a = (Item[]) new Object[capacity];
        if (nextLast > nextFirst) {
            System.arraycopy(items, upOne(nextFirst), a, 0, size); 
        }
        else {
            System.arraycopy(items, nextFirst + 1, a, 0, items.length - nextFirst - 1);
            System.arraycopy(items, 0, a, items.length - nextFirst - 1, size - items.length + nextFirst + 1);
        }
        items = a;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    private void checksizeUp() {
        if (nextFirst == nextLast) {
            resize(items.length * RFACTOR);
        }
    }

    private void checksizeDown() {
        double R = (double)size / (double)items.length;
        if (items.length > 8 && R < 0.25) {
            int C = items.length / 2;
            resize(C);
        }
    }

    public void addFirst(Item x) {
        size += 1;
        items[nextFirst] = x;
        nextFirst = backOne(nextFirst);
        checksizeUp();
    }

    public void addLast(Item x) {
        size += 1;
        items[nextLast] = x;
        nextLast = upOne(nextLast);
        checksizeUp();
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public Item removeFirst() {
        if (size > 0) {
            nextFirst = upOne(nextFirst);
            Item itemToReturn = items[nextFirst];
            size = size - 1;
            checksizeDown();
            return itemToReturn;
        }
        else {
            return null;
        }
    }

    public Item removeLast() {
        if (size > 0) {
            nextLast = backOne(nextLast);
            Item itemToReturn = items[nextLast];
            size = size - 1;
            checksizeDown();
            return itemToReturn;
        }
        else {
            return null;
        }
    }

    public int size() {
        return size;        
    }

    public Item get(int i) {
        if (i < items.length && i >= 0 && i < size) {
            int adjustIndex = nextFirst + 1 + i;
            if (adjustIndex >= items.length) {
                return items[adjustIndex - items.length];
            }
            else {
                return items[adjustIndex];
            }
        }
        else {
            return null;
        }
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
    }
}
