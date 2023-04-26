from two_d_point import TwoDPoint
from quadrilateral import Quadrilateral
from rectangle import Rectangle
from square import Square


if __name__ == '__main__':
    point1 = TwoDPoint(3, 2)
    print(point1.__str__())
    q1 = Quadrilateral(0, 4, 6, 4, 6, 0, 0, 0)
    print(q1.__str__())
    r1 = Rectangle(0, 4, 6, 4, 6, 0, 0, 0)
    print(r1.__str__())
    s1 = Square(0, 10, 10, 10, 10, 0, 0, 0)
    print(s1.__str__())
    print(r1._Rectangle__is_member())

