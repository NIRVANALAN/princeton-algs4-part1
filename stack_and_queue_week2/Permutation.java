import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]); // z=current processed string
        double probability, z = 0;
        while (k != 0 && !StdIn.isEmpty()) { // ? k=0 patch? border
            String string = StdIn.readString();
            z++;
            probability = k / z;
            if (probability >= 1) // z<=k
                queue.enqueue(string);
            else {
                if (StdRandom.uniform() <= probability) {
                    queue.dequeue();
                    queue.enqueue(string);
                }
            }
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(queue.dequeue());
        }
    }
}
