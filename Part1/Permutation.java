import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);
        while (k != 0 && !StdIn.isEmpty()) { // ? k=0 patch? border
            String string = StdIn.readString();
            // if (queue.size() == k) { // ? how
            // }
            // if (StdRandom.bernoulli())
            // continue;
            // queue.dequeue();
            // }
            queue.enqueue(string);
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(queue.dequeue());
        }
    }
}
