/* --------------------------------------------
 * File Name : SampleClient.java
 * Purpose :
 * Creation Date : 06-15-2017
 * Last Modified : Thu Jun 15 01:00:19 2017
 * Created By : QI ZHANG 
 * -------------------------------------------- */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class SampleClient2 {
    
    public static void main(String[] args) {
        // read the N points from a file
        Point[] points = getPointsFromTestFile(args[0]);
        
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
    
    public static Point[] getPointsFromTestFile(String fileName) {
        In in = new In("test-input/" + fileName);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        return points;
    }
    
}
