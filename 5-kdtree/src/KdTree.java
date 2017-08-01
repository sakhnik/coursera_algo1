
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sakhnik
 */
public class KdTree {

    private final Node root;
    private int size;

    // Accumulator of range of points
    private List<Point2D> range;

    // Accumulators to look for closest point
    private double minD;
    private Point2D minP;

    private static class Node {
        private Point2D point;          // the splitting point, null if the rectange hasn't been split yet
        private final RectHV rect;        // enclosing rectangle of the node
        private final boolean isVert;     // is the split vertical (by x)?
        private Node left, right;   // the halves if the node was split

        Node(boolean v, RectHV r) {
            isVert = v;
            rect = r;
        }

        // split the node by the given point
        void split(Point2D p) {
            assert point == null;
            assert rect.contains(p);

            point = p;
            if (isVert) {
                left = new Node(false, new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax()));
                right = new Node(false, new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax()));
            } else {
                left = new Node(true, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y()));
                right = new Node(true, new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax()));
            }
        }

        // Is given point in the left subtree?
        boolean isLeft(Point2D p) {
            if (isVert) {
                return 0 > Double.compare(p.x(), point.x());
            }
            return 0 > Double.compare(p.y(), point.y());
        }

        // Is splitting point equal to the given one?
        boolean isEqual(Point2D p) {
            return p.equals(point);
        }
    }

    public KdTree() {                              // construct an empty set of points
        root = new Node(true, new RectHV(0, 0, 1, 1));
        size = 0;
    }
    
    public boolean isEmpty() {                      // is the set empty?
        return root.point == null;
    }
    
    public int size() {                         // number of points in the set
        return size;
    }
    
    public void insert(Point2D p) {              // add the point to the set (if it is not already in the set)
        if (p == null)
            throw new IllegalArgumentException();

        Node n = root;
        while (n.point != null) {
            if (n.isEqual(p))
                return; // Such point exists already

            n = n.isLeft(p) ? n.left : n.right;
        }

        n.split(p);
        ++size;
    }
    
    public boolean contains(Point2D p) {            // does the set contain point p?
        if (p == null)
            throw new IllegalArgumentException();
        Node n = root;
        while (n != null) {
            if (n.isEqual(p))
                return true;
            n = n.isLeft(p) ? n.left : n.right;
        }
        return false;
    }
    
    private void draw(Node n) {
        n.point.draw();
        if (n.left != null)  draw(n.left);
        if (n.right != null)  draw(n.right);
    }

    public void draw() {                         // draw all points to standard draw
        if (root != null)
            draw(root);
    }
    
    private void range(Node node, RectHV rect) {
        if (node.rect.intersects(rect))
            return;
        if (node.point == null)
            return;
        if (rect.contains(node.point))
            range.add(node.point);
        range(node.left, rect);
        range(node.right, rect);
    }

    public Iterable<Point2D> range(RectHV rect) {             // all points that are inside the rectangle (or on the boundary)
        if (rect == null)
            throw new IllegalArgumentException();

        range = new ArrayList<>();
        range(root, rect);
        return range;
    }
 
    private void nearest(Node node, Point2D p) {
        // If no point in the node, nothing to search here
        if (node.point == null)
            return;
        // We'll be operating squared distances to avoid computation of square roots
        double d = p.distanceSquaredTo(node.point);
        // If the point is closer than known so far, update the result
        if (d < minD) {
            minD = d;
            minP = node.point;
        }

        // Distances to subrectangles
        double dl = node.left.rect.distanceSquaredTo(p);
        double dr = node.right.rect.distanceSquaredTo(p);

        // Choose the closer one first, and prune the farther one.
        if (dl < dr) {
            if (dl < minD) nearest(node.left, p);
            if (dr < minD) nearest(node.right, p);
        } else {
            if (dr < minD) nearest(node.right, p);
            if (dl < minD) nearest(node.left, p);
        }
    }

    public Point2D nearest(Point2D p) {             // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null)
            throw new IllegalArgumentException();
        minD = Double.POSITIVE_INFINITY;
        minP = null;
        nearest(root, p);
        return minP;
    }
    
    public static void main(String[] args) {                  // unit testing of the methods (optional)
    }
    
}