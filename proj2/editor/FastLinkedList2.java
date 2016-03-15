package editor;
public class FastLinkedList2<Item> {
    public class Node {
        public Item item;     /* Equivalent of first */
        public Node next; /* Equivalent of rest */
        public Node prev;
        public Node(Item i, Node h, Node p) {
            item = i;
            next = h;
            prev = p;
        }
    }

    public Node sentinel;
    public int size;
    public Node currentNode;
    public int currentPos;
    public Node cyclePointer;

    public FastLinkedList2() {
        size = 0;
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        currentNode = sentinel;
        cyclePointer = sentinel;
        currentPos = 0;
    }

    public void addChar(Item x) {
        Node save = currentNode.next;
        currentNode.next = new Node(x, save, currentNode.prev.next);
        currentNode = currentNode.next;
        Editor.undoRedoObject.undoQueue.addItem(currentNode);
        Editor.undoRedoObject.undoActionQueue.addItem("delete");
        size ++;
        currentPos ++;
        cyclePointer = sentinel.next;
    }

    public void addItem(Item x) {
        Node save = currentNode.next;
        currentNode.next = new Node(x, save, currentNode.prev.next);
        currentNode = currentNode.next;
        size ++;
        currentPos ++;
        cyclePointer = sentinel.next;
    }

    public Node deleteChar() {
        if (currentPos != 0) {
            Node toReturn = currentNode;
            currentNode.prev.next = currentNode.next;
            currentNode = currentNode.prev;
            Editor.undoRedoObject.undoQueue.addItem(currentNode);
            Editor.undoRedoObject.undoActionQueue.addItem("add");
            size --;
            currentPos --;
            return toReturn;
        }
        else {
            return null;
        }
    }

    public Node deleteItem() {
        if (currentPos != 0) {
            Node toReturn = currentNode;
            currentNode.prev.next = currentNode.next;
            currentNode = currentNode.prev;
            size --;
            currentPos --;
            return toReturn;
        }
        else {
            return null;
        }
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node currentNode = sentinel;
        while (currentNode.next != sentinel) {
            System.out.print(currentNode.next.item + " ");
            currentNode = currentNode.next;
        }
    }
    public Node newNode(Item i, Node h, Node p) {
        return new Node(i, h, p);
    }
} 