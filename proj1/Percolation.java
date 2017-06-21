/* --------------------------------------------
 * File Name : Percolation.java
 * Purpose :
 * Creation Date : 05-21-2017
 * Last Modified : Sun May 21 22:47:01 2017
 * Created By : QI ZHANG 
 * -------------------------------------------- */
/****************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:  java Percolation < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *
 ****************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class Percolation {
    
    private boolean[][] grid;
    private int gridSize;
    private WeightedQuickUnionUF unionUF;
    private WeightedQuickUnionUF backWash;
    private final int top;
    private final int bottom;
    private int count;    
    /**
     * Construction Method, declare two WQUUF, and two virtual node
     *
     * @param N the scale of the input
     * @throws java.lang.IllegalArgumentException if N < 0
     */
    public Percolation(int N) {               // create N-by-N grid, with all sites blocked
        if (N <= 0) {
            throw new IllegalArgumentException("The input N is illegal!");
        }
        grid = new boolean[N][N];
        gridSize = N;
        top = 0;
        bottom = N * N + 1;
        unionUF = new WeightedQuickUnionUF(N * N + 1);
        backWash = new WeightedQuickUnionUF(N * N + 2);
    }
    
    /**
     * @param i the row of the input matrix, index from 1 to N
     * @param j the column of the input matrix, index form 1 to N
     */
    public void open(int i, int j) {         // open site (row i, column j) if it is not open already
        validateArray(i, j);

        if (grid[i - 1][j - 1]) return;
        grid[i - 1][j - 1] = true;
        count++;
        if (i == 1) {
            unionUF.union(top, xyTo1D(i, j));
            backWash.union(top, xyTo1D(i, j));
        }
        
        if (i == gridSize) {
            backWash.union(xyTo1D(i, j), bottom);
        }
        
        if (i > 1 && isOpen(i - 1, j)) {
            unionUF.union(xyTo1D(i, j), xyTo1D(i - 1, j));
            backWash.union(xyTo1D(i, j), xyTo1D(i - 1, j));
        }
        
        if (i < gridSize && isOpen(i + 1, j)) {
            unionUF.union(xyTo1D(i, j), xyTo1D(i + 1, j));
            backWash.union(xyTo1D(i, j), xyTo1D(i + 1, j));
        }
        
        if (j > 1 && isOpen(i, j - 1)) {
            unionUF.union(xyTo1D(i, j), xyTo1D(i, j - 1));
            backWash.union(xyTo1D(i, j), xyTo1D(i, j - 1));
        }
        
        if (j < gridSize && isOpen(i, j + 1)) {
            unionUF.union(xyTo1D(i, j), xyTo1D(i, j + 1));
            backWash.union(xyTo1D(i, j), xyTo1D(i, j + 1));
        }
    }
    
    public int numberOfOpenSites(){
        return count;
    } 
    
    public boolean isOpen(int i, int j) {    // is site (row i, column j) open?
        validateArray(i, j);
        return grid[i - 1][j - 1];
    }
    
    public boolean isFull(int i, int j) {    // is site (row i, column j) full?
        validateArray(i, j);
        return unionUF.connected(xyTo1D(i, j), top);
    }
    
    public boolean percolates() {            // does the system percolate?
        return backWash.connected(top, bottom);
    }
    
    private int xyTo1D(int i, int j) {
        return (i - 1) * gridSize + j;
    }
    
    private void validateArray(int i, int j) {
        if (i <= 0 || j <= 0 || i > gridSize || j > gridSize) {
            throw new IndexOutOfBoundsException("index: (" + i + ", " + j + ") are out of bound!");
        }
    }
    
    public static void main(String[] args) {  // test client (optional)
        Percolation percolation = new Percolation(5);
        percolation.open(1, 1);
        percolation.open(3, 3);
        percolation.open(5, 5);
        percolation.open(5, 1);
        percolation.open(4, 3);
        percolation.open(4, 1);
        percolation.open(3, 1);
        percolation.open(2, 2);
        percolation.open(1, 2);
        percolation.open(2, 3);
        percolation.open(4, 4);
        percolation.open(4, 5);
        
        StdOut.println("is (5,1) full?:" + percolation.isFull(5, 1));
        StdOut.println("is (5,5) full?:" + percolation.isFull(5, 5));
        StdOut.println("is percolation?:" + percolation.percolates());
    }
    
}

