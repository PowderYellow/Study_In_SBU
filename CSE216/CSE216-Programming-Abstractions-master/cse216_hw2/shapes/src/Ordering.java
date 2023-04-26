import java.util.*;

public class Ordering {

    //FIX
    static class XLocationComparator implements Comparator<TwoDShape> {
        @Override public int compare(TwoDShape o1, TwoDShape o2) {

            if(o1 instanceof Quadrilateral && o2 instanceof Quadrilateral){
                if(((Quadrilateral) o1).getPosition().get(2).getX() == ((Quadrilateral) o2).getPosition().get(2).getX())
                    return 0;
                else if(((Quadrilateral) o1).getPosition().get(2).getX() > ((Quadrilateral) o2).getPosition().get(2).getX())
                    return 1;
                else
                    return -1;
            }

            else if(o1 instanceof Quadrilateral && o2 instanceof Circle){
                if(((Quadrilateral) o1).getPosition().get(2).getX() == ((Circle) o2).getCenter().getX() - ((Circle) o2).getRadius())
                    return 0;
                else if(((Quadrilateral) o1).getPosition().get(2).getX() > ((Circle) o2).getCenter().getX() - ((Circle) o2).getRadius())
                    return 1;
                else
                    return 0;
            }

            else if(o1 instanceof Circle && o2 instanceof Quadrilateral){
                if((((Circle) o1).getCenter().getX() - ((Circle) o1).getRadius()) == ((Quadrilateral) o2).getPosition().get(2).getX())
                    return 0;
                else if( (((Circle) o1).getCenter().getX() - ((Circle) o1).getRadius()) > ((Quadrilateral) o2).getPosition().get(2).getX())
                    return 1;
                else
                    return -1;
            }

            else if(o1 instanceof Circle && o2 instanceof Circle){
                if((((Circle) o1).getCenter().getX() - ((Circle) o1).getRadius()) == (((Circle) o2).getCenter().getX() - ((Circle) o2).getRadius()))
                    return 1;
                else if( (((Circle) o1).getCenter().getX() - ((Circle) o1).getRadius()) > (((Circle) o2).getCenter().getX() - ((Circle) o2).getRadius()))
                    return 0;
                else
                    return -1;
            }

            return -1;
            // TODO
        }
    }

    static class AreaComparator implements Comparator<SymmetricTwoDShape> {
        @Override public int compare(SymmetricTwoDShape o1, SymmetricTwoDShape o2) {
            if(o1.area() > o2.area())
                return 1;
            else if(o1.area() == o2.area())
                return 0;
            else
                return -1;
            // TODO
        }
    }

    static class SurfaceAreaComparator implements Comparator<ThreeDShape> {
        @Override public int compare(ThreeDShape o1, ThreeDShape o2) {

            if(o1 instanceof Cuboid && o2 instanceof Cuboid){
                if(((Cuboid) o1).surfaceArea() == ((Cuboid) o2).surfaceArea())
                    return 0;
                else if(((Cuboid) o1).surfaceArea() > ((Cuboid) o2).surfaceArea())
                    return 1;
                else
                    return -1;
            }
            else if(o1 instanceof Sphere && o2 instanceof Sphere){
                if(((Sphere) o1).surfaceArea() == ((Sphere) o2).surfaceArea())
                    return 0;
                else if(((Sphere) o1).surfaceArea() > ((Sphere) o2).surfaceArea())
                    return 1;
                else
                    return -1;
            }
            else if(o1 instanceof Cuboid && o2 instanceof Sphere){
                if(((Cuboid) o1).surfaceArea() == ((Sphere) o2).surfaceArea())
                    return 0;
                else if(((Cuboid) o1).surfaceArea() > ((Sphere) o2).surfaceArea())
                    return 1;
                else
                    return -1;
            }
            else if(o1 instanceof Sphere && o2 instanceof Cuboid){
                if(((Sphere) o1).surfaceArea() == ((Cuboid) o2).surfaceArea())
                    return 0;
                else if(((Sphere) o1).surfaceArea() > ((Cuboid) o2).surfaceArea())
                    return 1;
                else
                    return -1;
            }

            return -1;
            // TODO
        }
    }

    // TODO: there's a lot wrong with this method. correct it so that it can work properly with generics.
    static <T> void copy(Collection<? extends T> source, Collection<T> destination) {
        destination.addAll(source);
    }

    public static void main(String[] args) {
        List<TwoDShape>          shapes          = new ArrayList<>();
        List<SymmetricTwoDShape> symmetricshapes = new ArrayList<>();
        List<ThreeDShape>        threedshapes    = new ArrayList<>();

        /*
         * uncomment the following block and fill in the "..." constructors to create actual instances. If your
         * implementations are correct, then the code should compile and yield the expected results of the various
         * shapes being ordered by their smallest x-coordinate, area, volume, surface area, etc. */


        symmetricshapes.add(new Rectangle(new double[] {3, 1, -4, 1, -4, -3, 3, -3}));
        symmetricshapes.add(new Square(new double[] {7, 8, 1, 8, 1, 2, 7, 2}));
        symmetricshapes.add(new Circle(0, 0, 5));

        copy(symmetricshapes, shapes); // note-1 //
//        shapes.add(new Quadrilateral(new ArrayList<>()));


        // sorting 2d shapes according to various criteria
        shapes.sort(new XLocationComparator());
        symmetricshapes.sort(new XLocationComparator());
        symmetricshapes.sort(new AreaComparator());

        // sorting 3d shapes according to various criteria
        Collections.sort(threedshapes);
        threedshapes.sort(new SurfaceAreaComparator());

        /*
         * if your changes to copy() are correct, uncommenting the following block will also work as expected note that
         * copy() should work for the line commented with 'note-1' while at the same time also working with the lines
         * commented with 'note-2' and 'note-3'. */


        List<Number> numbers = new ArrayList<>();
        List<Double> doubles = new ArrayList<>();
        Set<Square>        squares = new HashSet<>();
        Set<Quadrilateral> quads   = new LinkedHashSet<>();

        copy(doubles, numbers); // note-2 //
        copy(squares, quads);   // note-3 //
    }
}
