// package stack_and_queue_week2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        var queue = new RandomizedQueue<String>();
        int k = StdIn.readInt();
        while (!StdIn.isEmpty()) {
            var string = StdIn.readString();
            if (queue.size() == k) {
                queue.dequeue();
            }
            queue.enqueue(string);
        }

        while (!queue.isEmpty()) {
            StdOut.println(queue.dequeue());
        }
    }
}