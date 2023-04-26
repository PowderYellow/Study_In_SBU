from quadrilateral import Quadrilateral
from two_d_point import TwoDPoint


class Rectangle(Quadrilateral):

    def __init__(self, *floats) -> None:
        super().__init__(*floats)
        if not self.__is_member():
            raise TypeError("A rectangle cannot be formed by the given coordinates.")

    def __is_member(self) -> bool:
        """Returns True if the given coordinates form a valid rectangle, and False otherwise."""
        if len(self.vertices) != 4:
            return False
        side_lengths_list = self.side_lengths()
        if side_lengths_list[0] != side_lengths_list[2] or side_lengths_list[1] != side_lengths_list[3]:
            return False
        if self.vertices[0].y != self.vertices[1].y or self.vertices[2].y != self.vertices[3].y:
            return False
        if self.vertices[0].x != self.vertices[3].x or self.vertices[1].x != self.vertices[2].x:
            return False
        return True  # TODO

    def center(self) -> TwoDPoint:
        """Returns the center of this rectangle, calculated to be the point of intersection of its diagonals."""
        return TwoDPoint((self.vertices[0].x + self.vertices[2].x)/2, (self.vertices[0].y + self.vertices[2].y)/2)

    def area(self) -> float:
        """Returns the area of this rectangle. The implementation invokes the side_lengths() method from the superclass,
        and computes the product of this rectangle's length and width."""
        side_lengths = self.side_lengths()
        return side_lengths[0] * side_lengths[1]  # TODO

    def __str__(self) -> str:
        s1 = "Rectangle: "
        for point in self.vertices:
            s1 += point.__str__() + " "
        return s1

    def __eq__(self, other: object) -> bool:
        if isinstance(other, Rectangle):
            return super().__eq__(other)
        else:
            return False
