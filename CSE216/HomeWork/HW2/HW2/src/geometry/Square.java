package geometry;

import java.util.Arrays;
import java.util.Objects;

public class Square extends Shape {

    Point center;
    Point[] vertices;// i know it is bad without setting private,
    // but i really do not how to sovle the problem without adding new public methods...

    public Square(Point a, Point b, Point c, Point d) {
        if (isvertical(a, b, c) && isvertical(b, c, d)
                && islenequal(a, b, c) && islenequal(b, c, d)) {

            this.vertices = new Point[4];
            int n = 0;
            for (Point p : Arrays.asList(a, b, c, d)) this.vertices[n++] = p;

            double x = (vertices[0].getX() + vertices[2].getX()) / 2;
            double y = (vertices[0].getY() + vertices[2].getY()) / 2;
            this.center = new Point("center", x, y);

        } else {
            throw new IllegalArgumentException(String.format("Invalid set of vertices specified for %s",
                    this.getClass().getCanonicalName()));
        }

    }

    private boolean islenequal(Point a, Point b, Point c) {
        double line_one = getlensquare(a, b);
        double line_two = getlensquare(b, c);
        return line_one == line_two;
    }

    private boolean isvertical(Point a, Point b, Point c) {
        double line_one = getlensquare(a, b);
        double line_two = getlensquare(b, c);

        double line_three = getlensquare(a, c);
        return line_one + line_two == line_three;
    }

    private double getlensquare(Point a, Point b) {
        double subx = a.getX() - b.getX();
        double suby = a.getY() - b.getY();
        return (double) Math.round((Math.pow(subx, 2) + Math.pow(suby, 2)) * 100) / 100;
    }


    @Override
    public Point center() {
        return center;

    }

    @Override
    public Square rotateBy(int degrees) {

        Point a = new_Point_R(vertices[0].getName(), degrees);
        Point b = new_Point_R(vertices[1].getName(), degrees);
        Point c = new_Point_R(vertices[2].getName(), degrees);
        Point d = new_Point_R(vertices[3].getName(), degrees);
        return new Square(a, b, c, d);

    }

    private Point new_Point_R(String name, int degrees) {
        Point new_p = FindPoint(name);

        if (new_p == null) {
            return null;
        }
        double x = new_p.getX() - center().getX();
        double y = new_p.getY() - center().getY();

        double new_x = x * Math.cos(Math.PI * degrees / 180) - y * Math.sin(Math.PI * degrees / 180);
        double new_y = x * Math.sin(Math.PI * degrees / 180) + y * Math.cos(Math.PI * degrees / 180);

        return new Point(name, new_x + center().getX(), new_y + center().getY());
    }

    @Override
    public Shape translateBy(double x, double y) {
        this.center = new Point("center", center.getX() + x, center.getY() + y);

        Point a = new_Point_T(vertices[0].getName(), x, y);
        Point b = new_Point_T(vertices[1].getName(), x, y);
        Point c = new_Point_T(vertices[2].getName(), x, y);
        Point d = new_Point_T(vertices[3].getName(), x, y);
        return new Square(a, b, c, d);
    }

    private Point new_Point_T(String name, double x, double y) {
        Point new_p = FindPoint(name);
        if (new_p == null) {
            return null;
        }
        double new_x = new_p.getX() + x;
        double new_y = new_p.getY() + y;
        return new Point(name, new_x, new_y);
    }


    private Point FindPoint(String NAME) {
        for (Point p : this.vertices) {
            if (Objects.equals(p.getName(), NAME)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public String toString() {

        return "[" +
                vertices[0] + ";"
                + vertices[1] + ";"
                + vertices[2] + ";"
                + vertices[3] + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Square)) return false;
        Square rg = (Square) o;

        for (int i = 0; i < 4; i++) {

            if (Double.compare(rg.FindPoint(this.vertices[i].getName()).getX(), this.vertices[i].getX()) != 0)
                return false;
            if (Double.compare(rg.FindPoint(this.vertices[i].getName()).getY(), vertices[i].getY()) != 0) return false;
        }

        return true;
    }


    //(3, 3) (2, 3), (2, 2), and (3, 2)
    public static void main(String... args) {
        Point east = new Point("a", 3, 3);
        Point west = new Point("b", 2, 3);
        Point north = new Point("c", 2, 2);
        Point south = new Point("d", 3, 2);
        Point longsouth = new Point("d", 3, 3);

        //1:right version

        Square sq = new Square(east, west, north, south);


        //1.1:throw err:done

        //Square sq2 = new Square(east, west, south, north);
        //Square sq3 = new Square(east, west, north, longsouth);

        //2:done

        //   Point center = sq.center();
        //  System.out.println(center);


        //3:
        System.out.println(sq);

        System.out.println("after first  rotate:90---------------------------------------------------");
        sq = sq.rotateBy(90);

        System.out.println(sq);

        //6:done
        System.out.println(" after first translate:(1,1)---------------------------------------------------");

        sq = (Square) sq.translateBy(1, 1);
        System.out.println(sq);
        System.out.println("rotate again(90)---------------------------------------------------------------");
        sq = sq.rotateBy(90);
        System.out.println(sq);

    }


}
