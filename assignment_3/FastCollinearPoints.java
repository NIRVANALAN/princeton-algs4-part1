package assignment_3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private Point[] PointSet;
    private ArrayList<LineSegment> sLineSegments;
    private LineSegment[] lineSegments;
    private int r = 4;

    public FastCollinearPoints(Point[] points) // finds all line segments containing 4 or more points
    {
        validate(points);
        // ! make sure the array is in order
        if (points.length < r)
            return;
        PointSet = points.clone(); // !
        sLineSegments = new ArrayList<>();
        int numOfPoints = points.length;
        for (int i = 0; i < points.length; i++) {
            Point[] ps = points.clone();
            Arrays.sort(ps, ps[i].slopeOrder());
            var anchor = ps[0]; // ps[0]=points[i]
            double slopeSlow = anchor.slopeTo(ps[1]), slopeFast;
            // ===
            // if (0 == anchor.compareTo(new Point(2682, 14118))) {
            // for (Point point : ps) {
            // StdOut.print(point);
            // }
            // StdOut.println();
            // }
            // ===
            for (int idxLeft = 1, idxRight; idxLeft < numOfPoints - 2; idxLeft = idxRight, slopeSlow = slopeFast) {
                idxRight = idxLeft + 1;

                slopeFast = anchor.slopeTo(ps[idxRight]);
                while (slopeSlow == slopeFast && idxRight < numOfPoints - 1) {
                    slopeFast = anchor.slopeTo(ps[++idxRight]);
                }
                // TODO rewrite with count(my version)

                // Check if any 3 or more adjacent points in the sorted order
                // have equal slopes with respect to p.
                // If so, these points, together with p, are collinear.
                int numOfAdjacentPoint = idxRight - idxLeft;
                if (idxRight == numOfPoints - 1 && slopeFast == slopeSlow) // ! trigger condition matters!
                    numOfAdjacentPoint++;
                // if (anchor.compareTo(tmp) == 0 && idxLeft == numOfPoints - 3)
                // StdOut.print(idxRight + " " + numOfAdjacentPoint);
                if (numOfAdjacentPoint >= 3) {
                    // sort the array as previous sort is unstable
                    Point[] segment = new Point[numOfAdjacentPoint + 1];
                    segment[0] = anchor;
                    System.arraycopy(ps, idxLeft, segment, 1, numOfAdjacentPoint);
                    Arrays.sort(segment);
                    // make sure no duplicate subsegment
                    if (segment[0] == anchor) {
                        sLineSegments.add(new LineSegment(anchor, segment[numOfAdjacentPoint]));
                    }
                }
            }

        }
        // ? if sLIneSegments.size()==0?
        // lineSegments = sLineSegments.toArray(new Collinear_points_week3.LineSegment[0]);
    }

    public int numberOfSegments() // the number of line segments
    {
        if (sLineSegments != null)
            return sLineSegments.size();
        return 0;
    }

    public LineSegment[] segments() // the line segments
    {
        // return lineSegments.clone(); // * do not return reference
        if (sLineSegments != null)
            return sLineSegments.toArray(new LineSegment[0]);
        return new LineSegment[0];
    }

    private void validate(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        for (Point point : points) {
            if (point == null)
                throw new IllegalArgumentException();
        }
        // Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].equals(points[j]))
                    throw new IllegalArgumentException();
            }
        }
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

        StdOut.println();

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
        // Collinear_points_week3.BruteCollinearPoints collinear = new Collinear_points_week3.BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}