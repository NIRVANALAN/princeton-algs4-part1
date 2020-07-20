// package stack_and_queue_week2;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // construct an empty randomized queue

    // node
    private Node first, last;

    private class Node { // first private
        Item item;
        Node next;
    }

    public RandomizedQueue() {
        last = first = null;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        int n = 0;
        for (Item item : this) {
            n++;
            // StdOut.println(item);
        }
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        var oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty())
            first = last; // !
        else
            oldLast.next = last;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        int k = StdRandom.uniform(this.size());
        var node = first;
        Item item;
        if (k == 0) {
            item = first.item;
            first = first.next;
        } else {
            for (int i = 0; i < k - 1; i++) {
                node = node.next;
            }
            item = node.next.item; // ? will GC work
            node.next = node.next.next;
            if (node.next == null) // ! avoid last reference lost
                last = node;
        }
        // var item = first.item;
        // first = first.next;
        if (isEmpty())
            last = null;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        int k = StdRandom.uniform(this.size());
        var node = first;
        for (int i = 0; i < k; i++) {
            node = node.next;
        }
        return node.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            var item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        var que = new RandomizedQueue<Integer>();
        for (int i = 0; i < 5; i++) {
            que.enqueue(i);
        }
        while (!que.isEmpty()) {
            StdOut.print(que.dequeue());
        }
    }
}