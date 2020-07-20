package percolation_week1;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WeightedQuickUnionUF {
    private int[] parent;
    private int[] sz;
    private int count;

    public WeightedQuickUnionUF(int n) {
        if (n < 0)
            throw new IllegalArgumentException();
        count = n;
        parent = new int[n];
        sz = new int[n];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
            sz[i] = 0;
        }
    }

    public int find(int p) {
        while (p != parent[p]) {
            parent[p] = parent[parent[p]]; // path compression by halving
            p = parent[p];
        }
        return p;
    }

    public int count() {
        return count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ)
            return;// !
        if (sz[p] < sz[q]) {
            parent[p] = rootQ;
            sz[q] += sz[p];
        } else {
            parent[q] = rootP;
            sz[p] += sz[q];
        }
        count--;// !
    }

    public void validate(int p) {
        if (p < 0 || p > parent.length) {
            throw new IllegalArgumentException("index " + p + "is not between 0 and " + parent.length);
        }
    }

    public static void main(String[] args) {
        // System.out.println("hello java again");
        int n = StdIn.readInt();
        var uf = new WeightedQuickUnionUF(n);
        while (!StdIn.isEmpty()) { // ! ctrl+d
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.connected(p, q))
                uf.union(p, q);
            // StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " componenct(s)");
    }
}