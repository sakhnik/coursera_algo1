
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
        Arrays.sort(points);
 
        for (int i = 0, in = points.length - 3; i < in; ++i) {
            Point pi = points[i];
            for (int j = i+1, jn = points.length - 2; j < jn; ++j) {
                Point pj = points[j];
                double sj = pi.slopeTo(pj);

                for (int k = j+1, kn = points.length - 1; k < kn; ++k) {
                    Point pk = points[k];
                    double sk = pi.slopeTo(pk);
                    if (Double.compare(sj, sk) != 0)
                        continue;


                    for (int l = k+1; l < points.length; ++l) {
                        Point pl = points[l];
                        if (pi.equals(pl))
                            throw new IllegalArgumentException();
                        double sl = pi.slopeTo(pl);
                        if (Double.compare(sj, sl) == 0) {
                            segments.add(new LineSegment(pi, pl));
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