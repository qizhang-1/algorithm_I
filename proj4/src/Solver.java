/* --------------------------------------------
 * File Name : Solver.java
 * Purpose :
 * Creation Date : 06-18-2017
 * Last Modified : Mon Jun 19 00:10:23 2017
 * Created By : QI ZHANG 
 * -------------------------------------------- */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.lang.*;
import java.util.*;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)
    private class Move implements Comparable<Move> {
        private Move previous;
        private Board board;
        private int numMoves;
        public Move(Board board) {
            numMoves = 0;
            this.board = board;
        }
        
        public Move(Board board, Move previous){
            this.board = board;
            this.previous = previous;
            this.numMoves = previous.numMoves + 1;
        }
        
        public int compareTo(Move move) {
            return (this.board.manhattan() - move.board.manhattan()) + (this.numMoves - move.numMoves);
        }
    }
    private Move lastMove;
    public Solver(Board initial) {
        MinPQ<Move> moves = new MinPQ<>();
        moves.insert(new Move(initial));

        MinPQ<Move> twinMoves = new MinPQ<>();
        twinMoves.insert(new Move(initial.twin()));
        
        while (true) {
            lastMove = expand(moves);
            if (lastMove != null || expand(twinMoves) != null)
                return;
        }
    }
    private Move expand(MinPQ<Move> moves) {
        if (moves.isEmpty())    return null;
        Move bestMove = moves.delMin();
        if (bestMove.board.isGoal()) return bestMove;
        for (Board nb: bestMove.board.neighbors()) {
            if (bestMove.previous == null || !nb.equals(bestMove.previous.board)) {
                moves.insert(new Move(nb, bestMove));
            }
        }
        return null;
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return lastMove != null;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? lastMove.numMoves : - 1;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())  return null;
        //Stack<Board> moves = new Stack<>();
        ArrayDeque<Board> moves = new ArrayDeque<Board>();
        while (lastMove != null) {
            moves.push(lastMove.board);
            lastMove = lastMove.previous;
        }
        
        return new ArrayDeque<Board>(moves);
    }
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        for (String filename : args) {
            
            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }
            Board initial = new Board(tiles);
            Solver solver = new Solver(initial);
            System.out.println("moves " + solver.moves());
            System.out.println("moves " + solver.moves());
            System.out.println("moves " + solver.moves());
            System.out.println("solvability " + solver.isSolvable());
            System.out.println("solvability " + solver.isSolvable());
            System.out.println("solvability " + solver.isSolvable());
            Iterable<Board> item = solver.solution();
            int ii = 0;
            Iterator<Board> it  = item.iterator();
            while (it.hasNext()) {
                it.next();
                ii++;
            }
            System.out.println("len 1 = " + ii);
            ii = 0;
            Iterable<Board> item2 = solver.solution();
            Iterator<Board> it2 = item2.iterator();
            while (it2.hasNext()) {
                it2.next();
                ii++;
           }
            System.out.println("len 2 = " + ii);
        }
    }
}
