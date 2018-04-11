import java.util.Iterator;

public class Bag<Item> implements Iterable<Item> {
    private Node first; // top of bag
    private int n; // number of items

    private class Node {     // nested class to define nodes
        Item item;
        Node next;
    }

    public boolean isEmpty() { return first == null; }

    public int size() { return n; }

    public void add(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    public class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public void remove() {}
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
