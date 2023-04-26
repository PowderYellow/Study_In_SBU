from unittest import TestCase
from sorter import ShapeSorter
from rectangle import Rectangle
from quadrilateral import Quadrilateral


class TestSorter(TestCase):

    def test_sort(self):
        r1 = Rectangle(0, 4, 6, 4, 6, 0, 0, 0)
        r2 = Rectangle(1, 4, 6, 4, 6, 0, 1, 0)
        r3 = Rectangle(2, 4, 6, 4, 6, 0, 2, 0)
        q1 = Quadrilateral(-1, 4, 6, 4, 6, 0, -1, 0)
        self.assertEqual(ShapeSorter.sort(r1, r2, r3, q1), [q1, r1, r2, r3])
        self.assertEqual(ShapeSorter.sort(r1), [r1])
        self.assertEqual(ShapeSorter.sort(), [])
        self.assertNotEqual(ShapeSorter.sort(r2, r3, r1, q1), [r2, r3, r1, q1])
