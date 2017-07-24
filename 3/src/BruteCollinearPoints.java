
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sakhnik
 */
public class BruteCollinearPoints {

    private final ArrayList<LineSegment> segments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
        if (points == null)
            throw new IllegalArgumentException();
        for (Point p : points)
            if (p == null)
                throw new IllegalArgumentException();

        Point[] field = points.clone();
        Arrays.sort(field);
 
        for (int i1 = 0, in = field.length - 3; i1 < in; ++i1) {
            Point p1 = field[i1];
            for (int i2 = i1+1, jn = field.length - 2; i2 < jn; ++i2) {
                Point p2 = field[i2];
                if (p1.equals(p2))
                    throw new IllegalArgumentException();
                double s2 = p1.slopeTo(p2);

                for (int i3 = i2+1, kn = field.length - 1; i3 < kn; ++i3) {
                    Point p3 = field[i3];
                    double s3 = p1.slopeTo(p3);
                    if (Double.compare(s2, s3) != 0)
                        continue;


                    for (int i4 = i3+1; i4 < field.length; ++i4) {
                        Point p4 = field[i4];
                        double s4 = p1.slopeTo(p4);
                        if (Double.compare(s2, s4) == 0) {
                            segments.add(new LineSegment(p1, p4));
                        }
                    }
                }
            }
        }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
