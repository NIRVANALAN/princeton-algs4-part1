import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // generics: Discover type mismatch errors at compile-time instead of run-time

    private Item[] s;
    private int tail = 0, head = 0; // next insert position
    private int n = 0;

    // construct an empty deque
    public Deque() {
        s = (Item[]) new Object[1];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return head == tail; // ?
    }

    // return the number of items on the deque
    public int size() {
        // int n = 0;
        // for (Item item : this) { // ! must call i.next()
        // n++;
        // } //! non-constant
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (this.size() == s.length - 1) { // !
            resize(s.length * 2);
        }
        head = (head - 1 + s.length) % s.length; // -1 first
        s[head] = item;
        n++;
    }

    // add the item to the back
    public void addLast(Item item) { // ! resize() may cause non-constant memory change(over 128B). LinkedList
                                     // implementation should solve this
        if (item == null)
            throw new IllegalArgumentException();
        if (this.size() == s.length - 1) {
            resize(s.length * 2);
        }
        s[tail] = item;
        tail = (tail + 1) % s.length;
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.size() == 0)
            throw new NoSuchElementException();
        if (this.size() == s.length / 4)
            this.resize(s.length / 2);
        Item item = s[head];
        s[head] = null;
        head = (head + 1) % s.length; //
        n--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() { // loitering
        if (this.size() == 0)
            throw new NoSuchElementException();
        tail = (tail - 1 + s.length) % s.length; // -1 first
        Item item = s[tail];
        s[tail] = null;
        if (this.size() == s.length / 4)
            this.resize(s.length / 2);
        n--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = head;

        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return i != tail;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item ret = s[i];// ?
            i = (i + 1) % s.length;
            return ret;
        }

    }

    // resize
    private void resize(int capacity) {
        // 1. copy ole elements into resized array
        Item[] copy = (Item[]) new Object[capacity];
        int j = 0;
        for (Item item : this) { // !
            copy[j] = item;
            j++;
        }
        // 2. update head, tail
        head = 0;
        tail = j;
        s = copy;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> que = new Deque<>();
        que.addLast(1);
        que.addFirst(2);
        que.addLast(3);
        que.addFirst(4);
        // StdOut.println(que.size());
        // que.removeFirst();
        // que.removeLast();
        // que.removeLast();

        // StdOut.print(que.size());
        for (Integer integer : que) {
            StdOut.println(integer);
        }
        StdOut.println(que.size());
    }

}
