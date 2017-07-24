
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sakhnik
 */
public class FastCollinearPoints {
    private final ArrayList<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {     // finds all line segments containing 4 or more points
        if (points == null)
            throw new IllegalArgumentException();
        for (Point p : points) {
            if (p == null)
                throw new IllegalArgumentException();
        }

        Point[] field = points.clone();

        for (Point origin : points) {

            Arrays.sort(field, origin.slopeOrder());
            assert origin == field[0];
            if (field.length > 1 && origin.equals(field[1]))
                throw new IllegalArgumentException();

            // Check for equal adjacent groups
            int start = 0;
            double slope = Double.NEGATIVE_INFINITY;
            for (int i = 1; i < field.length; ++i) {
                if (Double.compare(slope, origin.slopeTo(field[i])) == 0)
                    continue;
                if (i - start > 2) {
                    addSegment(origin, field, start, i);
                }
                slope = origin.slopeTo(field[i]);
                start = i;
            }
            if (field.length - start > 2) {
                addSegment(origin, field, start, field.length);
            }
        }
    }
    
    private void addSegment(Point origin, Point[] field, int start, int end) {
        Point maxP = origin;
        while (start != end) {
            Point p = field[start++];
            // Avoid duplicates (if segment doesn't start from the lowest point)
            if (p.compareTo(origin) < 0)
                return;
            if (p.compareTo(maxP) > 0)
                maxP = p;
        }

        segments.add(new LineSegment(origin, maxP));
    }

    public           int numberOfSegments() {        // the number of line segments
        return segments.size();
    }
    
    public LineSegment[] segments() {                // the line segments
        LineSegment[] s = new LineSegment[segments.size()];
        return segments.toArray(s);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
