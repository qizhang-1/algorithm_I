/* --------------------------------------------
 * File Name : KdTree.java
 * Purpose :
 * Creation Date : 06-16-2017
 * Last Modified : Sun Jun 18 00:37:46 2017
 * Created By : QI ZHANG 
 * -------------------------------------------- */
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;
import java.util.List;


public class KdTree {
    private class Node {
        private final Point2D point;
        private final RectHV  rect;
        private final boolean isVertical;
        private Node left, right;
        private int size;
        
        // constructor
        public Node(final boolean isVertical, final Point2D point, final RectHV rect) {
            this.isVertical = isVertical;
            this.point = point;
            this.rect = rect;
            size = 1;
        }
        
        // compare if right distance
        public double compare(final Point2D p) {
            return isVertical ? p.x() - point.x() : p.y() - point.y();
        }
        
        // check if a rectangle intersects with a splitting line
        public boolean intersectWithSplittingLine(final RectHV r) {
            if (isVertical)
                return r.xmin() <= point.x() && r.xmax() >= point.x();
            else
                return r.ymin() <= point.y() && r.ymax() >= point.y();
        }
        
        // check if a point is right of a rectanglar box
        public boolean isRightOf(final RectHV r) {
            if (isVertical)
                return r.xmin() < point.x() && r.xmax() < point.x();
            else
                return r.ymin() < point.y() && r.ymax() < point.y();
        }
    }
    
    private Node root;
    
    public int size() {
        return getSize(root);
    }
    
    private int getSize(final Node node) {
        if (node == null)   return 0;
        return node.size;
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public void insert(final Point2D p) {
        checkNotNull(p, "Null point can not be inserted!!!");
        root = addNode(root, p, true, new RectHV(0, 0, 1, 1));
    }
    
    private Node addNode(final Node node, final Point2D p, boolean isVertical, RectHV r) {
        if (node == null) {
            return new Node(isVertical, p, r);
        }
        
        RectHV rectLeft  = null;
        RectHV rectRight = null;
        double cmp = node.compare(p);
        
        if (cmp < 0 && node.left == null) {
            double x_min = node.rect.xmin();
            double x_max = isVertical ? node.point.x() : node.rect.xmax();
            double y_min = node.rect.ymin();
            double y_max = isVertical ? node.rect.ymax() : node.point.y();
            rectLeft = new RectHV(x_min, y_min, x_max, y_max);
        }
        else if (cmp >= 0 && node.right == null){
            double x_min = isVertical ? node.point.x() : node.rect.xmin();
            double x_max = node.rect.xmax();
            double y_min = isVertical ? node.rect.ymin() : node.point.y();
            double y_max = node.rect.ymax();
            rectRight = new RectHV(x_min, y_min, x_max, y_max);
        }
        
        if (cmp < 0)
            node.left  = addNode(node.left,  p, !isVertical, rectLeft);
        else if (cmp > 0)
            node.right = addNode(node.right, p, !isVertical, rectRight);
        else if (!p.equals(node.point))
            node.right = addNode(node.right, p, !isVertical, rectRight);
        
        node.size = 1 + getSize(node.left) + getSize(node.right);
        return node;
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        checkNotNull(p, "Null point can not be in the set!!!");
        return find(root, p, true) != null;
    }
    
    private Point2D find(final Node node, final Point2D p, final boolean isVertical) {
        if (node == null)   return null;
        double cmp = node.compare(p);
        if (cmp < 0)         return find(node.left,  p, !isVertical);
        else if (cmp > 0)    return find(node.right, p, !isVertical);
        else if (!p.equals(node.point)) return find(node.right, p, !isVertical);
        else    return node.point;
    }
    
    
    // draw all points to standard draw
    public void draw() {
        draw(root);
    }
    private void draw(final Node node) {
        if (node == null)   return;
        StdDraw.point(node.point.x(), node.point.y());
        draw(node.left);
        draw(node.right);
    }
    
    public Iterable<Point2D> range(final RectHV r){
        checkNotNull(r, "Cannot use a null rectangle for search!!!");
        Queue<Point2D> q = new Queue<>();
        return findPoints(q, r, root);
    }
    
    private Queue<Point2D> findPoints(Queue<Point2D> q, RectHV r, Node node) {
        if (node == null || !r.intersects(node.rect))   return q;
        if (!r.contains(node.point))
            q.enqueue(node.point);
        findPoints(q, r, node.left);
        findPoints(q, r, node.right);
        return q;
    }
    
    
    public Point2D nearest(final Point2D p) {
        checkNotNull(p, "Cannot use a null point to calculate distance!!!");
        if (isEmpty())   return null;
        return findNearestHelper(p, root, root.point, Double.MAX_VALUE, true);
    }
    
    private Point2D findNearestHelper(final Point2D p_input, Node node, final Point2D root_point, final double d2_prev, boolean isVertical) {
        Point2D closest = root_point;
        if (node == null)   return closest;
        double d2_min = d2_prev;
        double d2_cur = node.point.distanceSquaredTo(p_input);
        if (d2_cur < d2_min) {
            d2_min = d2_cur;
            closest = node.point;
        }
        Node left = null, right = null;
        if (isVertical) {
            if (p_input.x() < node.point.x()) {
                left  = node.left;
                right = node.right;
            }
            else {
                left  = node.right;
                right = node.left;
            }
        }
        else {
            if (p_input.y() < node.point.y()) {
                left  = node.left;
                right = node.right;
            }
            else {
                left  = node.right;
                right = node.left;
            }
        
        }
        if (left != null && left.rect.distanceSquaredTo(p_input) < d2_min){
            closest = findNearestHelper(p_input, left, closest, d2_min, !isVertical);
            d2_min = closest.distanceSquaredTo(p_input);
        }
        if (right != null && right.rect.distanceSquaredTo(p_input) < d2_min) {
            closest = findNearestHelper(p_input, right, closest, d2_min, !isVertical);
        }
        
        return closest;
    }
    
    private static void checkNotNull(final Object obj, final String messageIfObjectIsNull) {
        if (obj == null) throw new NullPointerException(messageIfObjectIsNull);
    }
}
