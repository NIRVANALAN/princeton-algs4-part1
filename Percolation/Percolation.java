
import edu.princeton.cs.algs4.StdRandom;
// import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // creates n-by-n grid, with all sites initially blocked
    private boolean[] grid;
    private final int n;
    private final byte[] x = { 1, -1, 0, 0 };
    private final byte[] y = { 0, 0, 1, -1 };
    private final WeightedQuickUnionUF uf;
    /*
    final 用来修饰一个引用
    如果引用为基本数据类型，则该引用为常量，该值无法修改；
    如果引用为引用数据类型，比如对象、数组，则该对象、数组本身可以修改，但指向该对象或数组的地址的引用不能修改。
    如果引用时类的成员变量，则必须当场赋值，否则编译会报错。
    */
    public Percolation(int n) { // after static but before methods
        if (n <= 0)
            throw new IllegalArgumentException();
        grid = new boolean[n * n + 2];
        this.n = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 1; i <= n; i++) {
            uf.union(0, id(1,i));
            uf.union(n*n+1, id(n, i)); // !no constant here...
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int centerId = id(row, col);
        if (!grid[centerId]) {
            for (int i = 0; i < x.length; i++) {
                int newRow = row + x[i];
                int newCol = col + y[i];
                try {
                    int currentId = id(newRow, newCol);  
                    if (grid[currentId]) // !
                        uf.union(centerId, currentId);
                } catch (IllegalArgumentException e) {
                    continue;
                }
            }
            grid[centerId] = true;
        }
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
        if (uf.find(0) == uf.find(n * n + 1)) // ! no for loop
            return true;
        return false;
    }

    //! private helper method
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