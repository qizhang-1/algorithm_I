/* --------------------------------------------
 * File Name : PointSET.java
 * Purpose :
 * Creation Date : 06-16-2017
 * Last Modified : Fri Jun 16 00:00:42 2017
 * Created By : QI ZHANG 
 * -------------------------------------------- */
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.lang.NullPointerException;

public class PointSET {
    private TreeSet<Point2D> tree;
    // construct an empty set of points
    public PointSET() {
        tree = new TreeSet<>();
    }
    
    // is the set empty?
    public boolean isEmpty() {
        return tree.isEmpty();
    }
    
    // number of points in the set
    public int size() {
        return tree.size();
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null)  throw new NullPointerException("Does not support null point insertion!");
        tree.add(p);
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null)  throw new NullPointerException("No null points!");
        return tree.contains(p);
    }
    
    // draw all points to standard draw
    public void draw() {
        for (Point2D point : tree) {
            StdDraw.point(point.x(), point.y());
        }
    }
    
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)   throw new NullPointerException("Can't calculate range when rect is null!");
        List<Point2D> list = new ArrayList<>();
        for (Point2D p : tree) {
            if (rect.contains(p))
                list.add(p);
        }
        return list;
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null)  throw new NullPointerException("Can't calculate nearest point to a point with value null!");
        Point2D nearestPt = null;
        for (Point2D point : tree) {
            if (nearestPt == null || point.distanceSquaredTo(p) < point.distanceSquaredTo(p)){
                nearestPt = point;
            }
        }
        return nearestPt;
            
    }
    
    // unit testing of the methods (optional)
    public static void main(String[] args) {
        
    }
}
