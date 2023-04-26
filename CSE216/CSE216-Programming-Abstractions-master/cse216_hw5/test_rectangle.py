from unittest import TestCase
from rectangle import Rectangle
from two_d_point import TwoDPoint


class TestRectangle(TestCase):

    def test___is_member(self):
        self.assertRaises(TypeError, (Rectangle, *(0, 4, 6, 4, 7, 0, 0, 0)))
        self.assertRaises(TypeError, (Rectangle, *(1, 5, 10, 5, 10, 0, 1, 1)))
        self.assertRaises(TypeError, (Rectangle, *()))

    def test_center(self):
        r1 = Rectangle(0, 4, 6, 4, 6, 0, 0, 0)
        point = TwoDPoint(3, 2)
        self.assertTrue(r1.center().__eq__(point))

    def test_area(self):
        r1 = Rectangle(0, 4, 6, 4, 6, 0, 0, 0)
        self.assertTrue(r1.area() == 24)

    def test_eq(self):
        r1 = Rectangle(0, 4, 6, 4, 6, 0, 0, 0)
        r2 = Rectangle(0, 4, 6, 4, 6, 0, 0, 0)
        r3 = Rectangle(0, 7, 6, 7, 6, 0, 0, 0)
        self.assertTrue(r1.__eq__(r2))
        self.assertFalse(r1.__eq__(r3))

    def test__str(self):
        r1 = Rectangle(0, 4, 6, 4, 6, 0, 0, 0)
        self.assertEqual(r1.__str__(), "Rectangle: (0, 4) (6, 4) (6, 0) (0, 0) ")