package geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * * @Date: 4/8/23 6:12 PM
 */
public class SquareSymmetries {

    boolean areSymmetric(Square g1, Square g2) {

        List<Square> squares = symmetriesOf(g1);
        for (Square sq : squares) {
            if (sq.equals(g2)) {
                return true;
            }
        }

        return false;
    }

    List<Square> symmetriesOf(Square g1) {

        List<Square> sq = new ArrayList<>();
        sq.add(g1.rotateBy(90));
        sq.add(g1.rotateBy(180));
        sq.add(g1.rotateBy(270));
        sq.add(g1.rotateBy(360));

        Point point = getPoint(g1.vertices[0].getName(), 0, g1, 0, 1);

        sq.add(
                new Square(
                        getPoint(g1.vertices[0].getName(), 0, g1, 0, 1),
                        getPoint(g1.vertices[1].getName(), 1, g1, 0, 1),
                        getPoint(g1.vertices[2].getName(), 2, g1, 0, 1),
                        getPoint(g1.vertices[3].getName(), 3, g1, 0, 1)
                ));

        sq.add(
                new Square(
                        getPoint(g1.vertices[0].getName(), 0, g1, 1, 2),
                        getPoint(g1.vertices[1].getName(), 1, g1, 1, 2),
                        getPoint(g1.vertices[2].getName(), 2, g1, 1, 2),
                        getPoint(g1.vertices[3].getName(), 3, g1, 1, 2)
                ));

        sq.add(
                new Square(
                        getPoint(g1.vertices[0].getName(), 0, g1, 0, 2),
                        getPoint(g1.vertices[1].getName(), 1, g1, 0, 2),
                        getPoint(g1.vertices[2].getName(), 2, g1, 0, 2),
                        getPoint(g1.vertices[3].getName(), 3, g1, 0, 2)
                ));


        sq.add(
                new Square(
                        getPoint(g1.vertices[0].getName(), 0, g1, 1, 3),
                        getPoint(g1.vertices[1].getName(), 1, g1, 1, 3),
                        getPoint(g1.vertices[2].getName(), 2, g1, 1, 3),
                        getPoint(g1.vertices[3].getName(), 3, g1, 1, 3)
                ));

        return sq;
    }

    private Point getPoint(String name, int i, Square g1, int first_index, int second_index) {
        double x = g1.vertices[i].getX();
        double y = g1.vertices[i].getY();
        return getcorrespondingpoint(name, x, y, GetVerticalBisection(g1.vertices[first_index], g1.vertices[second_index]));
    }


    private Point getcorrespondingpoint(String name, double x1, double y1, double[] fig) {
        //getFootOfPerpendicular
        double A = fig[0], B = fig[1], C = fig[2];
        if (A * A + B * B < 1e-13)
            return null;
        if (Math.abs(A * x1 + B * y1 + C) < 1e-13) {
            return new Point(name, x1, y1);
        } else {
            double newX = (B * B * x1 - A * B * y1 - A * C) / (A * A + B * B);
            double newY = (-A * B * x1 + A * A * y1 - B * C) / (A * A + B * B); //getFootOfPerpendicular
            return new Point(name, 2 * newX - x1, 2 * newY - y1);
        }
    }

    private double[] GetVerticalBisection(Point a, Point b) {
        double x_x = (a.getX() + b.getX()) / 2;
        double y_y = (a.getY() + b.getY()) / 2;

        if ((b.getY() - a.getY()) == 0) {
            return new double[]{-1, 0, x_x};
        }
        double slope = (a.getX() - b.getX()) / (b.getY() - a.getY());
        return new double[]{slope, Double.valueOf(-1), y_y - slope * x_x};
    }


}

