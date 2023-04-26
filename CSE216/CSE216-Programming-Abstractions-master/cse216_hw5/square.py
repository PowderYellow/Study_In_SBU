from rectangle import Rectangle
from quadrilateral import Quadrilateral
from two_d_point import TwoDPoint


class Square(Rectangle):

    def __init__(self, *floats):
        super().__init__(*floats)
        if not self.__is_member():
            raise TypeError("A square cannot be formed by the given coordinates.")

    def snap(self):
        """Snaps the sides of the square such that each corner (x,y) is modified to be a corner (x',y') where x' is the
        integer value closest to x and y' is the integer value closest to y. This, of course, may change the shape to a
        general quadrilateral, hence the return type. The only exception is when the square is positioned in a way where
        this approximation will lead it to vanish into a single point. In that case, a call to snap() will not modify
        this square in any way."""
        x0 = round(self.vertices[0].x)
        y0 = round(self.vertices[0].y)
        x1 = round(self.vertices[1].x)
        y1 = round(self.vertices[1].y)
        x2 = round(self.vertices[2].x)
        y2 = round(self.vertices[2].y)
        x3 = round(self.vertices[3].x)
        y3 = round(self.vertices[3].y)

        if x0 == x1 and x1 == x2 and x2 == x3 and y0 == y1 and y1 == y2 and y2 == y3:
            return self
        return Quadrilateral(x0, y0, x1, y1, x2, y2, x3, y3)
        # TODO

    def __is_member(self):
        if len(set(self.side_lengths())) != 1:
            return False
        else:
            return True

    def __str__(self):
        s1 = "Square: "
        for point in self.vertices:
            s1 += point.__str__() + " "
        return s1

    def __eq__(self, other):
        return super().__eq__(other)

