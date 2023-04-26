import java.util.Random;

public class MergeSort {

    private static final Random RNG = new Random(10982755L);
    private static final int LENGTH = 8192000;

    public static void main(String... args) {
        int[] arr = randomIntArray();
        long start = System.currentTimeMillis();
        concurrentMergeSort(arr);
        long end = System.currentTimeMillis();
        if (!sorted(arr)) {
            System.err.println("The final array is not sorted");
            System.exit(0);
        }
        System.out.printf("%10d numbers: %6d ms%n", LENGTH, end - start);
    }

    private static int[] randomIntArray() {
        int[] arr = new int[LENGTH];
        for (int i = 0; i < arr.length; i++)
            arr[i] = RNG.nextInt(LENGTH * 10);
        return arr;
    }

    public static boolean sorted(int[] arr) throws NullPointerException{
        //return !IntStream.range(1, arr.length)
                //.mapToObj(i -> arr[i - 1] > arr[i])
                //.findAny().orElse(false);

        if(arr == null) {
            throw new NullPointerException("Array is null");
        }

        else if(arr.length <= 1){
            return true;
        }

        else {

            for(int i = 1; i < arr.length; i++){
                if(arr[i] < arr[i-1])
                    return false;
            }


            return true;
        }
    }

    public static void concurrentMergeSort(int[] arr) {
        //int numThreads = 4;
        concurrentMergeSort(arr, Runtime.getRuntime().availableProcessors());
    }

    public static void concurrentMergeSort(int[] arr, int threadCount) {
        if(threadCount <= 1) {
            mergeSort(arr, arr.length);
            return;
        }

        int n = arr.length;
        int mid = n / 2;


        int[] l = new int[mid];
        int[] r = new int[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = arr[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = arr[i];
        }


        Thread leftSorter = new Thread(new Sorting(l, threadCount/2));
        Thread rightSorter = new Thread(new Sorting(r, threadCount/2));

        leftSorter.start();
        rightSorter.start();

        try{
            leftSorter.join();
            rightSorter.join();
        } catch (InterruptedException ie){
            ie.printStackTrace();
        }

        merge(arr, l, r, mid, n - mid);

    }


    public static void mergeSort(int[] a, int n) {
        if (n < 2) {
            return;
        }

        int mid = n / 2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        merge(a, l, r, mid, n - mid);
    }

    public static void merge(
            int[] a, int[] l, int[] r, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i] <= r[j]) {
                a[k++] = l[i++];
            }
            else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }

}