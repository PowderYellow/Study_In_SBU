import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {

    @Test
    void sorted() {
        assertTrue(MergeSort.sorted(new int[] {}));
        assertTrue(MergeSort.sorted(new int[] {1, 2, 3, 4, 5, 6}));
        assertFalse(MergeSort.sorted(new int[] {9, 8, 7, 4, 2, 1}));
        assertTrue(MergeSort.sorted(new int[] {0}));
        assertFalse(MergeSort.sorted(new int[] {0, 1, 2, 3, 9, 8, 7, 3}));
        assertThrows(NullPointerException.class, () -> MergeSort.sorted(null));
    }
}

