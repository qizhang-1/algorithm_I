/* --------------------------------------------
 * File Name : FastCollinearPoints.java
 * Purpose :
 * Creation Date : 06-13-2017
 * Last Modified : Thu Jun 15 01:23:01 2017
 * Created By : QI ZHANG 
 * -------------------------------------------- */
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points2) {
        Point[] points = Arrays.copyOf(points2, points2.length);
        checkDuplicatedEntries(points);
        ArrayList<LineSegment> ans = new ArrayList<>();
        int n = points.length;
        if (n <= 3) return;
        for (int i = 0; i < n; i++) {
            Point base = points[i];
//            System.out.println("Dealing with Point: " + i);
            Point[] others = new Point[n - 1];
            for (int j = 0; j < n; j++) {
                if (j < i) others[j] = points[j];
                if (j > i) others[j - 1] = points[j];
            }
            Arrays.sort(others, base.slopeOrder());
            int cnt = 0;
            int index = 0;
            double tempSlope = base.slopeTo(others[0]);
            for (int j = 0; j < n - 1; j++) {
                if (tempSlope ==  base.slopeTo(others[j])) {
                    cnt++;
                    continue;
                }
                else {
                    if (cnt >= 3) {
                        if (others[index].compareTo(base) >= 0){
                            LineSegment ls = new LineSegment(base, others[j - 1]);
                            ans.add(ls);
                        }
                    }
                    cnt = 1;
                    index = j;
                    tempSlope = base.slopeTo(others[j]);
                }
            }
            if (cnt >= 3) {
                if (others[index].compareTo(base) >= 0){
                    LineSegment ls = new LineSegment(base, others[index]);
                    ans.add(ls);
                }
            }
        }
        System.out.println("ANS size: " + ans.size());
        segments = ans.toArray(new LineSegment[ans.size()]);
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
