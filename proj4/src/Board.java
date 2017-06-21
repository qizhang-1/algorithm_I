/* --------------------------------------------
 * File Name : Board.java
 * Purpose :
 * Creation Date : 06-18-2017
 * Last Modified : Sun Jun 18 22:07:05 2017
 * Created By : QI ZHANG 
 * -------------------------------------------- */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Board {
    // data structures
    private final int N;
    private final int[][] blocks;
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = blocks.length;
        this.blocks = copy(blocks);
    }
    
    private int[][] swapBlocks(int[][] array, int i1, int j1, int i2, int j2){
        int[][] copy = copy(array);
        swap(copy, i1, j1, i2, j2);
        return copy;
    }
    
    private int[][] copy(int[][] array){
        int[][] copy = new int[array.length][];
        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i].clone();
        }
        return copy;
    }
    
    private void swap(int[][] blocks, int i1, int j1, int i2, int j2) {
        int tmp = blocks[i1][j1];
        blocks[i1][j1] = blocks[i2][j2];
        blocks[i2][j2] = tmp;
    }
    
    // board dimension n
    public int dimension() {
        return N;
    }
    
    // number of blocks out of place
    public int hamming() {
        int val = -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != i * N + j + 1)    val++;
            }
        }
        return val;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan(){
        int val = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int expected = i * N + j + 1;
                if (blocks[i][j] != expected && blocks[i][j] != 0){
                    int actual = blocks[i][j];
                    --actual;
                    int goalI = actual / N;
                    int goalJ = actual % N;
                    val += Math.abs(goalI - i) + Math.abs(goalJ - j);
                }
            }
        }
        return val;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }
    
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (blocks[i][j] != 0 && blocks[i][j + 1] != 0) {
                    return new Board(swapBlocks(blocks, i, j, i, j + 1));
                }
            }
        }
        throw new RuntimeException();
    }
    
    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (this == y)  return true;
        if (y == null || !(y instanceof Board))        return false;
        if (((Board) y).blocks == null)                return this.blocks == null;
        if (((Board) y).dimension() != dimension())    return false;
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++){
                if (blocks[i][j] != ((Board) y).blocks[i][j])  return false;
            }
        }
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();
        int[] location = getSpaceLocation();
        int row = location[0];
        int col = location[1];
        
        if (row != 0)       neighbors.add(new Board(swapBlocks(blocks, row, col, row - 1, col)));
        if (row != N - 1)   neighbors.add(new Board(swapBlocks(blocks, row, col, row + 1, col)));
        if (col != 0)       neighbors.add(new Board(swapBlocks(blocks, row, col, row, col - 1)));
        if (col != N - 1)   neighbors.add(new Board(swapBlocks(blocks, row, col, row, col + 1)));
        
        return neighbors;        
    }
    
    private int[] getSpaceLocation() {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        throw new RuntimeException();
    }
    
    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(" ");
                sb.append(blocks[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    // unit tests (not graded)
    public static void main(String[] args) {
    
    }
}
