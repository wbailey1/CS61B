package hw2;                       

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] world;
    private int size;
    private int end;
    private WeightedQuickUnionUF union1, union2;
    private int count;
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        size = N;
        world = new boolean[N][N];
        end = N * N + 1;
        union1 = new WeightedQuickUnionUF(N * N + 2);
        union2 = new WeightedQuickUnionUF(N * N + 1);
        count = 0;
    }
    private int toInt(int row, int col) {
        return (size * row) + (col + 1);
    }
    public void open(int row, int col) {
        if (row < 0 || row > size || col < 0 || col > size) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (!isOpen(row, col)) {
            count++;
        }
        world[row][col] = true;

        int num = toInt(row, col);
        if (num - size > 0 && isOpen(row - 1, col)) {
            union1.union(num, toInt(row - 1, col));
            union2.union(num, toInt(row - 1, col));
        }
        if (num + size < end && isOpen(row + 1, col)) {
            union1.union(num, toInt(row + 1, col));
            union2.union(num, toInt(row + 1, col));
        }
        if ((num - 1) % size != 0 && isOpen(row, col - 1)) {
            union1.union(num, toInt(row, col - 1));
            union2.union(num, toInt(row, col - 1));
        }
        if (num % size != 0 && isOpen(row, col + 1)) {
            union1.union(num, toInt(row, col + 1));
            union2.union(num, toInt(row, col + 1));
        }
        if (row == 0) {
            union1.union(0, num);
            union2.union(0, num);
        }
        if (row == size - 1) {
            union1.union(num, end);
        }
    }
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > size || col < 0 || col > size) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return world[row][col];
    }
    public boolean isFull(int row, int col) {
        if (row < 0 || row > size || col < 0 || col > size) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int idx = toInt(row, col);
        return union1.connected(idx, 0) && union2.connected(idx, 0);
    }
    public int numberOfOpenSites() {
        return count;
    }
    public boolean percolates() {
        return union1.connected(0, end);
    }
    public static void main(String[] args) {

    }
}                       
