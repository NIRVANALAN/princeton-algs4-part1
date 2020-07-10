import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    // perform independent trials on an n-by-n grid
    private final double[] results;
    private final int trials;

    private static final double CONFIDENCE = 1.96;

    public PercolationStats(int n, int trials) {
        validateHelper(n);
        validateHelper(trials);
        this.trials = trials;
        this.results = new double[trials];
        for (int i = 0; i < trials; i++) {
            var tites = new Percolation(n);
            while (!tites.percolates()) {
                int x = StdRandom.uniform(n) + 1;
                int y = StdRandom.uniform(n) + 1;
                // System.out.printf("%d %d\n", x, y);
                tites.open(x, y);
            }
            results[i] = Double.valueOf(tites.numberOfOpenSites()) / (n * n);
            // System.out.println(tites.numberOfOpenSites());
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE * stddev() / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE * stddev() / Math.sqrt(trials));
    }
    private void validateHelper(int x) {
        if (x <= 0)
            throw new IllegalArgumentException();
    }
    // test client (see below)
    public static void main(String[] args) {
        // args = new String[2];
        // args[0] = "10";
        // args[1] = "10";
        var stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.printf("mean\t\t\t = %f\n", stats.mean());
        StdOut.printf("stddev\t\t\t = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval\t = [%f, %f]\n", stats.confidenceLo(), stats.confidenceHi());
    }

}