import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Rectangle extends Quadrilateral implements SymmetricTwoDShape {
    private final TwoDPoint[] vertices = new TwoDPoint[4];

    public Rectangle(double... vertices){
        this(TwoDPoint.ofDoubles(vertices));
    }

    public Rectangle(List<TwoDPoint> vertices){
        super(vertices);
    }

    /**
     * The center of a rectangle is calculated to be the point of intersection of its diagonals.
     *
     * @return the center of this rectangle.
     */
    @Override
    public Point center() {
        TwoDPoint center = new TwoDPoint((this.vertices[0].getX()+this.vertices[2].getX())/2,
                                         (this.vertices[0].getY()+this.vertices[2].getY())/2);
        return center;
        // TODO
    }

    @Override
    public boolean isMember(List<? extends Point> vertices) {

        boolean xDifference = false;
        boolean yDifference = false;
        if(vertices.size() == 4){
            if(Math.abs(((TwoDPoint)vertices.get(0)).getX() - ((TwoDPoint)vertices.get(1)).getX()) ==
                    Math.abs(((TwoDPoint)vertices.get(3)).getX() - ((TwoDPoint)vertices.get(2)).getX())){
                xDifference = true;
            }
            else
                xDifference = false;

            if(Math.abs(((TwoDPoint)vertices.get(1)).getY() - ((TwoDPoint)vertices.get(2)).getY()) ==
                    Math.abs(((TwoDPoint)vertices.get(0)).getY() - ((TwoDPoint)vertices.get(3)).getY())){
                yDifference = true;
            }
            else
                yDifference = false;

        }


        return vertices.size() == 4 &&
                DoubleStream.of(getSideLengths()).boxed().collect(Collectors.toSet()).size() == 2
                && xDifference && yDifference;
        // TODO
    }

    @Override
    public double area() {
        double[] sideLengths = super.getSideLengths();

        return sideLengths[0]*sideLengths[1];
        // TODO
    }

}
