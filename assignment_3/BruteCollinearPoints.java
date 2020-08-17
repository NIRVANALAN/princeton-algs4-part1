package assignment_3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[] pointSet, data;
    private ArrayList<LineSegment> sLineSegments;
    private LineSegment[] lineSegments;
    private int r = 4;

    public BruteCollinearPoints(Point[] points) // finds all line segments containing 4 points
    {
        validate(points);
        this.pointSet = points.clone(); // ! not mutatable
        data = new Point[4];
        sLineSegments = new ArrayList<>();
        combinationUtil(pointSet, data, 0, pointSet.length - 1, 0, r); // * move to this.points
        // lineSegments = sLineSegments.toArray(new Collinear_points_week3.LineSegment[0]); // !
        // StdOut.print(numberOfSegments());
    }

    public int numberOfSegments() // the number of line segments
    {
        if (sLineSegments != null)
            return sLineSegments.size();
        return 0;
    }

    public LineSegment[] segments() // the line segments
    {
        // ===
        // p q r s
        // return lineSegments.clone(); // * do not return reference
        if (numberOfSegments() > 0)
            return sLineSegments.toArray(new LineSegment[0]);
        return new LineSegment[0];
    }

    private void combinationUtil(Point[] pointSet, Point[] data, int start, int end, int index, int r) { // ! timing
                                                                                                         // standard not
                                                                                                         // meet
        if (index == r) {
            var dataCopy = data.clone(); // ! sort will change the order of data, leading to duplicate elements during
            Arrays.sort(dataCopy);// ! make sure the array is in order
            double slope = dataCopy[0].slopeTo(dataCopy[1]), currentSlope;
            for (int i = 1; i < data.length - 1; i++) {
                currentSlope = dataCopy[i].slopeTo(dataCopy[i + 1]);
                if (currentSlope != slope)
                    return;
            } // ? flag
            sLineSegments.add(new LineSegment(dataCopy[0], dataCopy[r - 1]));
            return;// ! or add else alter
        }
        for (int i = start; i <= end && end - i >= r - (index + 1); i++) {
            data[index] = pointSet[i];
            combinationUtil(pointSet, data, i + 1, end, index + 1, r);
        }
    }

    private void validate(Point[] pointSet) {
        if (pointSet == null)
            throw new IllegalArgumentException();
        for (Point point : pointSet) {
            if (point == null)
                throw new IllegalArgumentException();
        }
        // Arrays.sort(points);
        for (int i = 0; i < pointSet.length; i++) {
            for (int j = i + 1; j < pointSet.length; j++) {
                if (pointSet[i].equals(pointSet[j]))
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

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        // Collinear_points_week3.FastCollinearPoints collinear = new Collinear_points_week3.FastCollinearPoints(points);
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}