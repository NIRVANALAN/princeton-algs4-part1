
import edu.princeton.cs.algs4.StdRandom;
// import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
    private boolean[] grid;
    private int n;
    private int[] x = { 1, -1, 0, 0 };
    private int[] y = { 0, 0, 1, -1 };
    private WeightedQuickUnionUF uf;

    private boolean validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            return false;
        return true;
    }

    private int id(int row, int col) {
        if (validate(row, col))
            return (row - 1) * n + col;
        else
            throw new IllegalArgumentException();
    }

    public Percolation(int n) {
        if (n < 0)
            throw new IllegalArgumentException();
        grid = new boolean[n * n + 1];
        this.n = n;
        uf = new WeightedQuickUnionUF(n * n + 1);
        for (int i = 1; i <= n; i++) {
            uf.union(0, i);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int centerId = id(row, col);
        if (!grid[centerId]) {
            for (int i = 0; i < 4; i++) {
                int newRow = row + x[i];
                int newCol = col + y[i];
                try {
                    int currentId = id(newRow, newCol);  
                    if (grid[currentId]) // !
                        uf.union(centerId, currentId);
                } catch (Exception e) {
                    continue;
                }
            }
        }
        grid[centerId] = true;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[id(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        int id = id(row, col);
        if (grid[id] && uf.find(id) == uf.find(0))
            return true;
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int sum = 0;
        for (int i = 1; i < grid.length; i++) {
            if (grid[i])
                sum += 1;
        }
        return sum;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 1; i <= n; i++) {
            if (isFull(n, i))
                return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 10;
        var tites = new Percolation(n);
        System.out.println(tites.percolates());
        while (!tites.percolates()) {
            int x = StdRandom.uniform(n) + 1;
            int y = StdRandom.uniform(n) + 1;
            // System.out.printf("%d %d\n", x, y);
            tites.open(x, y);
        }
        System.out.println(tites.percolates());
        System.out.println(tites.numberOfOpenSites());
    }
}