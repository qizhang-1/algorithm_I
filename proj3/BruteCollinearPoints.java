/* --------------------------------------------
 * File Name : BruteCollinearPoints.java
 * Purpose :
 * Creation Date : 06-13-2017
 * Last Modified : Thu Jun 15 01:26:25 2017
 * Created By : QI ZHANG 
 * -------------------------------------------- */
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points2) {
        Point[] points = Arrays.copyOf(points2, points2.length);
        checkDuplicatedEntries(points);
        checkDuplicatedEntries(points);
        Arrays.sort(points);
        int n = points.length;
        ArrayList<LineSegment> ans = new ArrayList<>();
        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    for (int l = k + 1; l < n; l++) {
                        Point p = points[i], q = points[j], r = points[k], s = points[l];
                        double k1 = p.slopeTo(q);
                        double k2 = p.slopeTo(r);
                        double k3 = p.slopeTo(s);
                        if (k1 == k2 && k1 == k3) {
                            LineSegment ls = new LineSegment(p, s);
                            ans.add(ls);
                        }
                    }
                }
            }
        }
        segments = ans.toArray(new LineSegment[ans.size()]);
//        System.out.println("ans SIZE = " +  ans.size());
    }
    
    // the number of line segments
    public  int numberOfSegments() {
        return segments.length;
    }
    
    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numberOfSegments());
    }
    
    private void checkDuplicatedEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicated entries in given points.");
                }
            }
        }
    }
    
}
