import java.util.Arrays;
import java.util.List;

public class Quadrilateral implements Positionable, TwoDShape {

    private final TwoDPoint[] vertices = new TwoDPoint[4];

    public Quadrilateral(double... vertices) { 
        this(TwoDPoint.ofDoubles(vertices));
    }

    public Quadrilateral(List<TwoDPoint> vertices) {
//        System.out.println(vertices);
        int n = 0;
        for (TwoDPoint p : vertices) this.vertices[n++] = p;
        if (!isMember(vertices))
            throw new IllegalArgumentException(String.format("Invalid set of vertices specified for %s",
                                                             this.getClass().getCanonicalName()));
    }

    /**
     * Given a list of four points, adds them as the four vertices of this quadrilateral in the order provided in the
     * list. This is expected to be a counterclockwise order of the four corners.
     *
     * @param points the specified list of points.
     * @throws IllegalStateException if there are more than four vertices provided as input.
     */
    @Override
    public void setPosition(List<? extends Point> points) {
        // TODO
        if(points.size() != 4)
            throw new IllegalStateException();

        for(int i = 0; i < points.size(); i++){
            vertices[i] = (TwoDPoint)points.get(i);
        }
    }

    @Override
    public List<TwoDPoint> getPosition() {
        return Arrays.asList(vertices);
    }

    /**
     * @return the lengths of the four sides of the quadrilateral. Since the setter {@link Quadrilateral#setPosition(List)}
     *         expected the corners to be provided in a counterclockwise order, the side lengths are expected to be in
     *         that same order.
     */
    protected double[] getSideLengths() {
        double[] sideLengths = new double[4];
        double side1 = Math.abs(vertices[1].getX() - vertices[0].getX());
        sideLengths[0] = side1;
        double side2 = Math.abs(vertices[1].getY() - vertices[2].getY());
        sideLengths[1] = side2;
        double side3 = Math.abs(vertices[3].getX() - vertices[2].getX());
        sideLengths[2] = side3;
        double side4 = Math.abs(vertices[0].getY() - vertices[3].getY());
        sideLengths[3] = side4;
//        System.out.println(side1 + " " + side2 + " " + side3 + " " + side4);

        return sideLengths;
        // TODO
    }

    @Override
    public int numSides() { return 4; }

    @Override
    public boolean isMember(List<? extends Point> vertices) { return vertices.size() == 4; }
}
