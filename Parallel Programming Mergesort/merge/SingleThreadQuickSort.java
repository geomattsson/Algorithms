package merge;

import java.util.Random;

/**
 * Created by Yousif on 2018-01-23.
 */
public class SingleThreadQuickSort {
    public static void quickSort(float[] array){
        quickSort(array,0,array.length-1);
    }

    private static void quickSort(float[] array, int low, int high) {
        if (low < high) {
            int partition = partition(array,low,high);
            quickSort(array,low,partition);
            quickSort(array,partition+1,high);
        }
    }

    // the algorithm for QuickSort partitioning is written using the pseudocode in
    // https://en.wikipedia.org/wiki/Quicksort#Hoare_partition_scheme
    public static int partition(float[] array, int low, int high) {
        medianOfThree(array,low,high);
        float pivot = array[low];
        int i = low - 1;
        int j = high + 1;
        while (true) {
            do {
                i++;
            }
            while (array[i] < pivot);

            do {
                j--;
            }
            while (array[j] > pivot);

            if (i >= j) {
                return j;
            }
            swap(array, i, j);
        }
    }

    private int getPivot(int low, int high) {
        Random rand = new Random();
        return rand.nextInt((high - low) + 1) + low;
    }

    private static void swap(float[] array, int i, int j) {
        float tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public static float medianOfThree(float[] intArray, int first, int last) {
        int middle = (first + last) / 2;
        if (intArray[first] > intArray[middle])
            swap(intArray, first, middle);
        if (intArray[first] > intArray[last])
            swap(intArray, first, last);
        if (intArray[middle] > intArray[last])
            swap(intArray, middle, last);
        swap(intArray, middle, first);
        return intArray[first];
    }
}
