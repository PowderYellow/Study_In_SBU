from two_d_point import TwoDPoint
from typing import Tuple


class Quadrilateral:

    def __init__(self, *floats) -> None:
        points = TwoDPoint.from_coordinates(list(floats))
        self.__vertices = tuple(points[0:4])
        if not self.__is_member():
            raise TypeError("Not a quadrilateral")

    def __is_member(self) -> bool:
        if len(self.vertices) != 4:
            return False
        side_lengths_list = self.side_lengths()
        if len(side_lengths_list) != 4:
            return False
        x0 = self.__vertices[0].x
        y0 = self.__vertices[0].y
        x1 = self.__vertices[1].x
        y1 = self.__vertices[1].y
        x2 = self.__vertices[2].x
        y2 = self.__vertices[2].y
        x3 = self.__vertices[3].x
        y3 = self.__vertices[3].y
        if (x0 == x1 and x1 == x2 and x2 == x3) or (y0 == y1 and y1 == y2 and y2 == y3):
            return False
        return True

    @property
    def vertices(self) -> tuple:
        return self.__vertices

    def side_lengths(self) -> tuple:
        """Returns a tuple of four floats, each denoting the length of a side of this quadrilateral. The value must be
        ordered clockwise, starting from the top left corner."""
        list_of_sides = [];
        list_of_sides.append(abs(self.__vertices[0].x - self.__vertices[1].x))
        list_of_sides.append(abs(self.__vertices[1].y - self.__vertices[2].y))
        list_of_sides.append(abs(self.__vertices[2].x - self.__vertices[3].x))
        list_of_sides.append(abs(self.__vertices[0].y - self.__vertices[3].y))
        return tuple(list_of_sides)  # TODO

    def smallest_x(self) -> float:
        """Returns the x-coordinate of the vertex with the smallest x-value of the four vertices of this
        quadrilateral."""
        min_x = self.__vertices[0].x
        for point in self.__vertices:
            if point.x < min_x:
                min_x = point.x
        return min_x  # TODO

    def __str__(self) -> str:
        s1 = "Quadrilateral: "
        for point in self.vertices:
            s1 += point.__str__() + " "
        return s1

    def __eq__(self, other: object) -> bool:
        if not isinstance(other, Quadrilateral):
            return False
        for i in range(len(self.vertices)):
            if not self.vertices[i].__eq__(other.vertices[i]):
                return False
        return True




