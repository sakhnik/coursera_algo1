
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
    private final Point[] sortBuffer;

    public FastCollinearPoints(Point[] points) {     // finds all line segments containing 4 or more points
        if (points == null)
            throw new IllegalArgumentException();
        sortBuffer = new Point[points.length];

        Point[] field = points.clone();
        for (Point origin : points) {
            if (origin == null)
                throw new IllegalArgumentException();

            Arrays.sort(field, origin.slopeOrder());
            assert origin == field[0];
            if (field.length > 1 && origin.equals(field[1]))
                throw new IllegalArgumentException();

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
        sortBuffer[0] = origin;
        int i = 1;
        while (start != end) {
            sortBuffer[i++] = field[start++];
        }
        Arrays.sort(sortBuffer, 0, i);
        LineSegment segment = new LineSegment(sortBuffer[0], sortBuffer[i-1]);
        String ss = segment.toString();
        for (LineSegment s : segments)
            if (ss.equals(s.toString()))
                return;
        segments.add(segment);
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
