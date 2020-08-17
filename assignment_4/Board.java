package assignment_4;
import java.util.ArrayList;
import java.util.Arrays;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private int[][] tiles;
    private int manhattanDistance = -1;
    private int hammingDistance = -1;

    public Board(int[][] tiles) {
        // this.tiles = tiles.clone(); // shallow copy
        assert tiles.length == tiles[0].length;
        this.tiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            this.tiles[i] = Arrays.copyOf(tiles[i], tiles.length);
        }
    }

    // string representation of this board
    public String toString() {
        var string = new StringBuilder();
        // string.concat(Integer.toString(dimension()) + "\n");
        string.append(dimension() + "\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                string.append(" " + tiles[i][j]);
            }
            string.append("\n");
        }
        return string.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        if (hammingDistance == -1) {
            hammingDistance = 0;
            for (int i = 0; i < dimension(); i++) {
                for (int j = 0; j < dimension(); j++) {
                    if (tiles[i][j] != 0 && tiles[i][j] != i * dimension() + j + 1)
                        hammingDistance++;
                }
            }
        }
        return hammingDistance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattanDistance == -1) {
            manhattanDistance = 0;
            int x, y;
            for (int i = 0; i < dimension(); i++) {
                for (int j = 0; j < dimension(); j++) {
                    if (tiles[i][j] != 0) {
                        x = (tiles[i][j] - 1) / dimension();
                        y = (tiles[i][j] - 1) % dimension();
                        manhattanDistance += (Math.abs(x - i) + Math.abs(y - j));
                    }
                }
            }
        }
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() { //!
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (tiles[i][j] != i * dimension() + j + 1 && (i != dimension() - 1
                        || j != dimension() - 1)) // not finished, fuck
                    return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || getClass() != y.getClass()) return false;
        return this.toString().equals(y.toString());
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int i0 = 0, j0 = 0;
        isFindBlank:
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] == 0) {
                    i0 = i;
                    j0 = j;
                    break isFindBlank;
                }
            }
        }
        ArrayList<Board> ans = new ArrayList<>();
        int[] xCords = { 0, 1, 0, -1 }, yCords = { 1, 0, -1, 0 };
        int x, y;
        for (int k = 0; k < xCords.length; k++) {
            x = i0 + xCords[k];
            y = j0 + yCords[k];
            if (x >= dimension() || x < 0 || y >= dimension()
                    || y < 0) continue;
            var neighborBoardTiles = new Board(tiles);
            neighborBoardTiles.swap(i0, j0, x, y);
            ans.add(neighborBoardTiles);
        }
        return ans;
    }

    private void swap(int x1, int y1, int x2, int y2) {
        assert x1 >= 0 && x1 < tiles.length && x2 >= 0 && x2 < tiles.length;
        int tmp = tiles[x1][y1];
        tiles[x1][y1] = tiles[x2][y2];
        tiles[x2][y2] = tmp;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        var twinTiles = new Board(tiles); // deep copy
        for (int i = 0; i < 2; i++) {
            if (tiles[0][i] != 0 && tiles[1][i] != 0) {
                twinTiles.swap(0, i, 1, i);
                return twinTiles;
            }
        }
        throw new IllegalArgumentException();
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    } // 1h15min

}

