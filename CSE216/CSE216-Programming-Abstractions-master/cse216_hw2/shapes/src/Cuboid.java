import java.util.ArrayList;
import java.util.List;

// TODO : a missing interface method must be implemented in this class to make it compile. This must be in terms of volume().
public class Cuboid implements ThreeDShape, SurfaceAreaHelper {

    private final ThreeDPoint[] vertices = new ThreeDPoint[8];

    /**
     * Creates a cuboid out of the list of vertices. It is expected that the vertices are provided in
     * the order as shown in the figure given in the homework document (from v0 to v7).
     * 
     * @param vertices the specified list of vertices in three-dimensional space.
     */
    public Cuboid(List<ThreeDPoint> vertices) {
        if (vertices.size() != 8)
            throw new IllegalArgumentException(String.format("Invalid set of vertices specified for %s",
                                                             this.getClass().getName()));
        int n = 0;
        for (ThreeDPoint p : vertices) this.vertices[n++] = p;
    }

    @Override
    public double volume() {
        double l, w, h = 0;

        l = Math.abs(vertices[2].getX() - vertices[3].getX());
        w = Math.abs(vertices[3].getY() - vertices[4].getY());
        h = Math.abs(vertices[1].getZ() - vertices[2].getZ());

        return (l*w*h);
        // TODO
    }

    @Override
    public ThreeDPoint center() {
        ThreeDPoint v1 = this.vertices[2];
        ThreeDPoint v2 = this.vertices[5];

        ThreeDPoint center = new ThreeDPoint((v1.getX() + v2.getX())/2,
                                             (v1.getY() + v2.getY())/2,
                                             (v1.getZ() + v2.getZ())/2);
        return center; // TODO
    }

    @Override
    public int compareTo(ThreeDShape o) {
        if(this.volume() == o.volume())
            return 0;
        else if(this.volume() > o.volume())
            return 1;
        else
            return -1;
    }



    public static Cuboid random(){
        List<ThreeDPoint> newVertices = new ArrayList<>();
        ThreeDPoint zeroPoint = new ThreeDPoint(Math.random()*101, Math.random()*101, Math.random()*101);
        newVertices.add(zeroPoint);
        ThreeDPoint onePoint = new ThreeDPoint(zeroPoint.getX() - Math.random()*101, zeroPoint.getY(), zeroPoint.getZ());
        newVertices.add(onePoint);
        ThreeDPoint twoPoint = new ThreeDPoint(onePoint.getX(), onePoint.getY(), onePoint.getZ()  - Math.random()*101);
        newVertices.add(twoPoint);
        ThreeDPoint threePoint = new ThreeDPoint(zeroPoint.getX(), twoPoint.getY(), twoPoint.getZ());
        newVertices.add(threePoint);
        ThreeDPoint fourPoint = new ThreeDPoint(threePoint.getX(), threePoint.getY() + Math.random()*101, threePoint.getZ());
        newVertices.add(fourPoint);
        ThreeDPoint fivePoint = new ThreeDPoint(fourPoint.getX(), fourPoint.getY(), fourPoint.getZ() + Math.random() * 101);
        newVertices.add(fivePoint);
        ThreeDPoint sixPoint = new ThreeDPoint(onePoint.getX(), fivePoint.getY() , fivePoint.getZ());
        newVertices.add(sixPoint);
        ThreeDPoint sevenPoint = new ThreeDPoint(sixPoint.getX(), fourPoint.getY(), fourPoint.getZ());
        newVertices.add(sevenPoint);

        return new Cuboid(newVertices);
    }



    @Override
    public double surfaceArea() {
        double l = Math.abs(vertices[2].getX() - vertices[3].getX());
        double w = Math.abs(vertices[3].getY() - vertices[4].getY());
        double h = Math.abs(vertices[1].getZ() - vertices[2].getZ());

        return 2 * ((l*w) + (w*h) + (l*h));
    }
}
