import unittest
from unittest import TestCase
from two_d_point import TwoDPoint


class TestTwoDPoint(TestCase):

    def test_from_coordinates(self):
        list_of_points = [TwoDPoint(0, 4), TwoDPoint(6, 4), TwoDPoint(6, 0), TwoDPoint(0, 0)]
        self.assertEqual(TwoDPoint.from_coordinates([0, 4, 6, 4, 6, 0, 0, 0]), list_of_points)  # TODO

    def test_eq(self):
        point1 = TwoDPoint(3, 2)
        point2 = TwoDPoint(3, 2)
        point3 = TwoDPoint(2, 3)
        self.assertTrue(point1.__eq__(point2))
        self.assertFalse(point1.__eq__(point3))

    def test__ne(self):
        point1 = TwoDPoint(3, 2)
        point2 = TwoDPoint(2, 3)
        self.assertTrue(point1.__ne__(point2))

    def test__add(self):
        point1 = TwoDPoint(3, 2)
        point2 = TwoDPoint(3, 2)
        self.assertTrue(point1.__add__(point2).__eq__(TwoDPoint(6, 4)))

    def test__sub(self):
        point1 = TwoDPoint(3, 2)
        point2 = TwoDPoint(3, 2)
        self.assertTrue(point1.__sub__(point2).__eq__(TwoDPoint(0, 0)))

    def test__str(self):
        point1 = TwoDPoint(3,2)
        self.assertEqual(point1.__str__(), "(3, 2)")
