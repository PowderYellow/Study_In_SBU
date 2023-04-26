from unittest import TestCase
from square import Square
from quadrilateral import Quadrilateral

class TestSquare(TestCase):

    def test_snap(self):
        s1 = Square(0, 2.7, 2.7, 2.7, 2.7, 0, 0, 0)
        s2 = Square(0, 0.1, 0.1, 0.1, 0.1, 0, 0, 0)
        self.assertEqual(s1.snap(), Quadrilateral(0, 3, 3, 3, 3, 0, 0, 0))
        self.assertEqual(s2.snap(), s2)

    def test___str(self):
        s1 = Square(0, 10, 10, 10, 10, 0, 0, 0)
        self.assertEqual(s1.__str__(), "Square: (0, 10) (10, 10) (10, 0) (0, 0) ")

    def test__is_member(self):
        self.assertRaises(TypeError, (Square, *(0, 11, 10, 10, 10, 0, 0, 0)))
        self.assertRaises(TypeError, (Square, *()))

    def test__eq(self):
        s1 = Square(0, 10, 10, 10, 10, 0, 0, 0)
        s2 = Square(0, 10, 10, 10, 10, 0, 0, 0)
        s3 = Square(0, 2, 2, 2, 2, 0, 0, 0)
        self.assertTrue(s1.__eq__(s2))
        self.assertFalse(s1.__eq__(s3))

