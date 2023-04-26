package geometry;

import java.util.*;

public class RadialGraph extends Shape {
    Point center;
    Point[] vertices = null; // i know it is bad without setting private,
    // but i really do not how to sovle the problem without adding new public methods...

    public RadialGraph(Point center, List<Point> neighbors) {

        this.center = center;
        if (isnotMember(neighbors)) {
            throw new IllegalArgumentException(String.format("Invalid set of vertices specified for %s",
                    this.getClass().getCanonicalName()));
        }
        this.vertices = new Point[neighbors.size()];
        int n = 0;
        for (Point p : neighbors) this.vertices[n++] = p;


    }


    public Point[] getPoints(Point[] vertices) {

        return vertices;

    }

    private boolean isnotMember(List<Point> vertices) {

        double len = Math.pow((vertices.get(0).getX() - center.getY()), 2)
                + Math.pow((vertices.get(0).getY() - center.getY()), 2);

        for (Point p : vertices) {

            double com_len = (Math.pow((p.getX() - center.getY()), 2)
                    + Math.pow((p.getY() - center.getY()), 2));
            if (Math.ceil(com_len) != Math.ceil(len)) {
                return true;
            }
        }
        return false;

    }


    public RadialGraph(Point center) {
        this.center = center;
    }


    @Override
    public RadialGraph rotateBy(int degrees) {
        Point east = new_Point_R("east", degrees);
        Point west = new_Point_R("west", degrees);
        Point north = new_Point_R("north", degrees);
        Point south = new_Point_R("south", degrees);
        return new RadialGraph(center, Arrays.asList(north, south, east, west));
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
    public RadialGraph translateBy(double x, double y) {
        this.center = new Point("center", center.getX() + x, center.getY() + y);

        Point east = new_Point_T("east", x, y);
        Point west = new_Point_T("west", x, y);
        Point north = new_Point_T("north", x, y);
        Point south = new_Point_T("south", x, y);
        return new RadialGraph(center, Arrays.asList(north, south, east, west));
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
        if (this.vertices == null)
            return "[" + center + "]";

        return "[" + center
                + FindPoint("east") + ";"
                + FindPoint("north") + ";"
                + FindPoint("west") + ";"
                + FindPoint("south") + "]";

    }

    @Override
    public Point center() {
        return center;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RadialGraph)) return false;
        RadialGraph rg = (RadialGraph) o;

        for (int i = 0; i < rg.vertices.length; i++) {
            double x = rg.FindPoint(this.vertices[i].getName()).getX();
            double x1 = this.vertices[i].getX();
            if (Double.compare(Math.round(x * 10000) / 10000,
                    Math.round(x1 * 10000) / 10000) != 0)
                return false;

            double y = rg.FindPoint(this.vertices[i].getName()).getY();
            double y1 = this.vertices[i].getY();

            if (Double.compare(Math.round(y * 10000) / 10000,
                    Math.round(y1 * 10000) / 10000) != 0)
                return false;
        }
        return true;
    }

    /* Driver method given to you as an outline for testing your code. You can modify this as you want, but please keep
     * in mind that the lines already provided here as expected to work exactly as they are (some lines have additional
     * explanation of what is expected) */
    public static void main(String... args) {
        Point center = new Point("center", 0, 0);
        Point east = new Point("east", 1, 0);
        Point west = new Point("west", -1, 0);
        Point north = new Point("north", 0, 1);
        Point south = new Point("south", 0, -1);
        Point toofarsouth = new Point("south", 0, -2);


        //1:done
        // A single node is a valid radial graph.
        RadialGraph lonely = new RadialGraph(center);


        //2:done
        // Must print: [(center, 0.0, 0.0)]
        System.out.println(lonely);


        //3.1:报错版本:done
        // This line must throw IllegalArgumentException, since the edges will not be of the same length
        // RadialGraph nope = new RadialGraph(center, Arrays.asList(north, toofarsouth, east, west));

        //3.2:正确版本:done
        Shape g = new RadialGraph(center, Arrays.asList(north, south, east, west));


        //4:done
        // Must follow the documentation in the Shape abstract class, and print the following string:
        // [(center, 0.0, 0.0); (east, 1.0, 0.0); (north, 0.0, 1.0); (west, -1.0, 0.0); (south, 0.0, -1.0)]
        System.out.println(g);


        //5：done
        // After this counterclockwise rotation by 90 degrees, "north" must be at (-1, 0), and similarly for all the
        // other radial points. The center, however, must remain exactly where it was.
        System.out.println("after first  rotate:90---------------------------------------------------");
        g = g.rotateBy(90);

        System.out.println(g);
        //6:done
        System.out.println(" after first translate:(1,1)---------------------------------------------------");
        // you should similarly add tests for the translateBy(x, y) method
        g = g.translateBy(1, 1);
        System.out.println(g);

        System.out.println("rotate again(90)---------------------------------------------------");
        g = g.rotateBy(90);
        System.out.println(g);

    }
}
