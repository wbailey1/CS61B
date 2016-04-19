package hw4.puzzle;

public class Board {
    private int[][] board;
    public Board(int[][] tiles) {
        board = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                board[i][j] = tiles[i][j];
            }
        }
    }
    public int tileAt(int i, int j) {
        if (i < 0 || i >= size() || j < 0 || j >= size()) {
            throw new NullPointerException();
        }
        return board[i][j];
    }
    public int size() {
        return board.length;
    }
    public int hamming() {
        int count = 0;
        for (int i = 0; i < size(); i++) {
            for (int x = 0; x < size(); x++) {
                if (board[i][x] != 0 && board[i][x] != i * size() + x + 1) {
                    count++;
                }
            }
        }
        return count;
    }
    public int manhattan() {
        int count = 0;
        int xpos;
        int ypos;
        for (int i = 0; i < size(); i++) {
            for (int x = 0; x < size(); x++) {
                if (board[i][x] != 0 && board[i][x] != i * size() + x + 1) {
                    xpos = board[i][x] % size();
                    if (xpos == 0) {
                        xpos = size() - 1;
                    }
                    ypos = (board[i][x] - 1) / size();
                    count = count + Math.abs(i - ypos) + Math.abs(x - xpos);
                }
            }
        }
        return count;
    }
    public boolean isGoal() {
        return hamming() == 0;
    }
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;
        return this.board == that.board;
    }
    public int hashCode() {
        return 25;
    }
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
