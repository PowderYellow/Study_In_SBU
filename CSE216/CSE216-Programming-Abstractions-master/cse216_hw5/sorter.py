from quadrilateral import Quadrilateral


class ShapeSorter:

    @staticmethod
    def sort(*args: Quadrilateral) -> list:
        shapes = []
        it = iter(args)
        for x in it:
            if not isinstance(x, Quadrilateral):
                raise Exception("hello world")
            shapes.append(x)
        for i in range(len(shapes)):
            # Last i elements are already in place
            for j in range(0, len(shapes) - i - 1):
                if shapes[j].smallest_x() > shapes[j + 1].smallest_x():
                    shapes[j], shapes[j + 1] = shapes[j + 1], shapes[j]
        return shapes
