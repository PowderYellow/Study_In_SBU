from unittest import TestCase
from quadrilateral import Quadrilateral


class TestQuadrilateral(TestCase):

    def test_side_lengths(self):
        q1 = Quadrilateral(0, 4, 6, 4, 6, 0, 0, 0)
        self.assertTrue(q1.side_lengths() == (6, 4, 6, 4))

    def test_smallest_x(self):
        q1 = Quadrilateral(0, 4, 6, 4, 6, 0, 0, 0)
        self.assertTrue(q1.smallest_x() == 0)

    def test___is_member(self):
        self.assertRaises(TypeError, (Quadrilateral, *(1, 2)))
        self.assertRaises(TypeError, (Quadrilateral, *(1, 0, 1, 3, 1, 5, 1, 7)))
        self.assertRaises(TypeError, (Quadrilateral, *(0, 0, 0, 0, 0, 0, 0, 0)))
        self.assertNotEqual((Quadrilateral, *(1, 2)), None)

    def test_eq_(self):
        q1 = Quadrilateral(0, 4, 6, 4, 6, 0, 0, 0)
        q2 = Quadrilateral(0, 4, 6, 4, 6, 0, 0, 0)
        q3 = Quadrilateral(0, 4, 6, 5, 6, 0, 0, 0)
        self.assertTrue(q1.__eq__(q2))
        self.assertFalse(q1.__eq__(q3))

    def test__str(self):
        q1 = Quadrilateral(0, 4, 6, 4, 6, 0, 0, 0)
        self.assertEqual(q1.__str__(), "Quadrilateral: (0, 4) (6, 4) (6, 0) (0, 0) ")


