package percolation_week1;

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
    private int count = 0;

    /*
     * final 用来修饰一个引用 如果引用为基本数据类型，则该引用为常量，该值无法修改；
     * 如果引用为引用数据类型，比如对象、数组，则该对象、数组本身可以修改，但指向该对象或数组的地址的引用不能修改。
     * 如果引用时类的成员变量，则必须当场赋值，否则编译会报错。
     */
    public Percolation(int n) { // * after static but before methods
        if (n <= 0)
            throw new IllegalArgumentException();
        grid = new boolean[n * n + 2]; // * 2 virtual nodes
        this.n = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int centerId = id(row, col);
        if (!grid[centerId]) {
            grid[centerId] = true;
            count += 1;
            if (row == 1)
                uf.union(centerId, 0);
            if (row == n)
                uf.union(centerId, n * n + 1);
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
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[id(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) { // ! not applicable when percolates()==True
        // if (percolates())
        // return false; // ! fuck OJ
        int id = id(row, col);
        if (grid[id] && uf.find(id) == uf.find(0))
            return true;
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.count;
    }

    // does the system percolate?
    public boolean percolates() {
        if (uf.find(0) == uf.find(n * n + 1)) // ! no for loop
            return true;
        return false;
    }

    // ! private helper method
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
        int n = 5;
        var tites = new Percolation(n);
        for (int i = 1; i <= n; i++) {
            tites.open(i, 1);
        }
        System.out.println(tites.isFull(n, 2));
        System.out.println(tites.percolates());
        System.out.println(tites.numberOfOpenSites());
    }
}