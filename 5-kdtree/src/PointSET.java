
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sakhnik
 */
public class PointSET {
    
    private final Set<Point2D> points;

    public         PointSET() {                               // construct an empty set of points
        points = new TreeSet<>();
    }
    
    public           boolean isEmpty() {                      // is the set empty?
        return points.isEmpty();
    }
    
    public               int size() {                         // number of points in the set
        return points.size();
    }
    
    public              void insert(Point2D p) {              // add the point to the set (if it is not already in the set)
        if (p == null)
            throw new IllegalArgumentException();
        points.add(p);
    }
    
    public           boolean contains(Point2D p) {            // does the set contain point p?
        if (p == null)
            throw new IllegalArgumentException();
        return points.contains(p);
    }
    
    public              void draw() {                         // draw all points to standard draw
        for (Point2D p : points)
            p.draw();
    }
    
    public Iterable<Point2D> range(RectHV rect) {             // all points that are inside the rectangle (or on the boundary)
        if (rect == null)
            throw new IllegalArgumentException();
        List<Point2D> res = new ArrayList<>();
        for (Point2D p : points)
            if (rect.contains(p))
                res.add(p);
        return res;
    }
    
    public           Point2D nearest(Point2D p) {             // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null)
            throw new IllegalArgumentException();

        double minD = Double.POSITIVE_INFINITY;
        Point2D minP = null;
        for (Point2D i : points) {
            double d = p.distanceSquaredTo(i);
            if (d < minD) {
                minD = d;
                minP = i;
            }
        }
        return minP;
    }
    
    public static void main(String[] args) {                  // unit testing of the methods (optional)
        
    }
}