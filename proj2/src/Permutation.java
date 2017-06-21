/* --------------------------------------------
 * File Name : Permutation.java
 * Purpose :
 * Creation Date : 06-11-2017
 * Last Modified : Mon Jun 12 19:06:34 2017
 * Created By : QI ZHANG 
 * -------------------------------------------- */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        // Reservoir sampling
        int k = Integer.parseInt(args[0]);
        for (int i = 1; !StdIn.isEmpty(); i++) {
            String s = StdIn.readString();
            if (i <= k) {
                rq.enqueue(s);
            } else if (StdRandom.uniform() < (double) k/i) {
                rq.dequeue();
                rq.enqueue(s);
            }
        }
 
        while (!rq.isEmpty()) {
            System.out.println(rq.dequeue());
        }
    }
}
