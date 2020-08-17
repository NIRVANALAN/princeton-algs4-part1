package assignment_2;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueueLinkedListVersion<Item> implements Iterable<Item> {
    // construct an empty randomized queue

    // node
    private Node first, last;
    private int n = 0; // ! necessary?

    private class Node { // first private
        Item item;
        Node next;
    }

    public RandomizedQueueLinkedListVersion() {
        first = null; // Inner assignments should be avoided
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty())
            first = last; // !
        else
            oldLast.next = last;
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        int k = StdRandom.uniform(this.size());
        Node node = first;
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
        n--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        int k = StdRandom.uniform(this.size());
        Node node = first;
        for (int i = 0; i < k; i++) {
            node = node.next;
        }
        return node.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new linkedListRandomIterator(this.size());
    }

    private class linkedListRandomIterator implements Iterator<Item> {

        private Node current = first;
        private int id = 0;
        int[] perm;

        public linkedListRandomIterator(int n) {
            perm = StdRandom.permutation(n);
        }

        @Override
        public boolean hasNext() {
            return id != perm.length;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            for (int i = 0; i < perm[id]; i++) {
                current = current.next;
            }
            Item item = current.item;
            current = first;
            id++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueueLinkedListVersion<Integer> que = new RandomizedQueueLinkedListVersion<>();
        for (int i = 0; i < 5; i++) {
            que.enqueue(i);
        }
        // while (!que.isEmpty()) {
        // StdOut.print(que.dequeue());
        // }
        for (Integer item : que) {
            StdOut.println(item);
        }
    }
}
