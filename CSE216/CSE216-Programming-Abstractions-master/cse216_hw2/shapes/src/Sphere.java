public class Sphere implements ThreeDShape, SurfaceAreaHelper{
    private double radius;
    private ThreeDPoint center;

    public Sphere(double x, double y, double z, double radius){
        this.center = new ThreeDPoint(x, y, z);
        this.radius = radius;
    }

    @Override
    public Point center() {
        return this.center;
    }

    @Override
    public double volume() {
        return (4.0/3.0)*(Math.PI)*(Math.pow(this.radius, 3));
    }

    @Override
    public int compareTo(ThreeDShape o) {
        if(this.volume() > o.volume())
            return 1;
        else if(this.volume() == o.volume())
            return 0;
        else
            return -1;
    }

    public static Sphere random(){
        double radius = Math.random();
        return new Sphere(Math.random(), Math.random(), Math.random(), radius);
    }

    @Override
    public double surfaceArea() {
        return 4.0 * (Math.PI) * Math.pow(this.radius, 2);
    }
}


