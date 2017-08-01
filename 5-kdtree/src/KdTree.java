
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

    class Node {
        Point2D p;          // the splitting point, null if the rectange hasn't been split yet
        RectHV rect;        // enclosing rectangle of the node
        boolean isVert;     // is the split vertical (by x)?
        Node left, right;   // the halves if the node was split

        Node(boolean v, RectHV r) {
            isVert = v;
            rect = r;
        }

        // split the node by the given point
        void split(Point2D p) {
            assert this.p == null;
            assert rect.contains(p);

            this.p = p;
            RectHV l, r;
            if (isVert) {
                l = new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax());
                r = new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax());
            } else {
                l = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
                r = new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
            }
            left = new Node(!isVert, l);
            right = new Node(!isVert, r);
        }

        // Is given point in the left subtree?
        boolean isLeft(Point2D p) {
            if (isVert) {
                return 0 > Double.compare(p.y(), this.p.y());
            }
            return 0 > Double.compare(p.x(), this.p.x());
        }

        // Is splitting point equal to the given one?
        boolean isEqual(Point2D p) {
            return p.equals(this.p);
        }
    }

    public KdTree() {                              // construct an empty set of points
        root = new Node(true, new RectHV(0,0,1,1));
        size = 0;
    }
    
    public boolean isEmpty() {                      // is the set empty?
        return root.p == null;
    }
    
    public int size() {                         // number of points in the set
        return size;
    }
    
    public void insert(Point2D p) {              // add the point to the set (if it is not already in the set)
        if (p == null)
            throw new IllegalArgumentException();

        Node n = root;
        while (n.p != null) {
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
        n.p.draw();
        if (n.left != null)  draw(n.left);
        if (n.right != null)  draw(n.right);
    }

    public void draw() {                         // draw all points to standard draw
        if (root != null)
            draw(root);
    }
    
    private void range(Node node, RectHV rect, List<Point2D> res) {
        if (node.rect.intersects(rect))
            return;
        if (node.p == null)
            return;
        if (rect.contains(node.p))
            res.add(node.p);
        range(node.left, rect, res);
        range(node.right, rect, res);
    }

    public Iterable<Point2D> range(RectHV rect) {             // all points that are inside the rectangle (or on the boundary)
        if (rect == null)
            throw new IllegalArgumentException();

        List<Point2D> res = new ArrayList<>();
        range(root, rect, res);
        return res;
    }
    
    private void nearest(Node node, Point2D p, Double minD, Point2D minP) {
        // If no point in the node, nothing to search here
        if (node.p == null)
            return;
        // We'll be operating squared distances to avoid computation of square roots
        double d = p.distanceSquaredTo(node.p);
        // If the point is closer than known so far, update the result
        if (d < minD) {
            minD = d;
            minP = node.p;
        }

        // Distances to subrectangles
        double dl = node.left.rect.distanceSquaredTo(p);
        double dr = node.right.rect.distanceSquaredTo(p);

        // Choose the closer one first, and prune the farther one.
        if (dl < dr) {
            if (dl < minD) nearest(node.left, p, minD, minP);
            if (dr < minD) nearest(node.right, p, minD, minP);
        } else {
            if (dr < minD) nearest(node.right, p, minD, minP);
            if (dl < minD) nearest(node.left, p, minD, minP);
        }
    }

    public Point2D nearest(Point2D p) {             // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null)
            throw new IllegalArgumentException();
        Double minD = Double.POSITIVE_INFINITY;
        Point2D minP = null;
        nearest(root, p, minD, minP);
        return minP;
    }
    
    public static void main(String[] args) {                  // unit testing of the methods (optional)
    }
    
}