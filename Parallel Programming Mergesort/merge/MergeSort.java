package merge;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

/**
 * @author Gustav Mattsson
 */
public class MergeSort extends RecursiveAction {

    private int low;
    private int high;
    private int threshold;
    private float[] list;
    private float[] temp;
    
    public MergeSort(float[] list, float[] temp, int low, int high, int threshold) {
        this.low = low;
        this.high = high;
        this.list = list;
        this.threshold = threshold;
        this.temp = temp;
    }
    
    public float[] sort() {
        split(list, temp, low, high);
        return list;
    }
    
    private void split(float[] list, float[] temp, int begin, int end) {
        if ((end - begin) == 0) {
            return;
        }
        int mid = begin + (end - begin) / 2;
        split(list, temp, begin, mid);
        split(list, temp, mid+1, end);
        merge(list, temp, begin, mid, end);
    }
    
    @Override
    protected void compute() {
        pSplit(list, temp, low, high);
    }
    
    private void pSplit(float[] list, float[] temp, int begin, int end) {
        if ((end - begin) == 0) {
            return;
        }
        int mid = begin + (end - begin) / 2;
        if((end - begin) < threshold) {
            //Arrays.sort(list, begin, end+1);
            split(list, temp, begin, end);
        } else {
            MergeSort worker1 = new MergeSort(list, temp, begin, mid, threshold);
            MergeSort worker2 = new MergeSort(list, temp, mid+1, end, threshold);
            worker1.fork();
            worker2.compute();
            worker1.join();
            merge(list, temp, begin, mid, end);
        }
    }
    
    private void merge(float[] list, float[] temp, int low, int middle, int high) {
        for (int i = low; i <= high; i++) {
            temp[i] = list[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;
        while (i <= middle && j <= high) {
            if (temp[i] <= temp[j]) {
                list[k] = temp[i];
                i++;
            } else {
                list[k] = temp[j];
                j++;
            }
            k++;
        }
        while (i <= middle) {
            list[k] = temp[i];
            k++;
            i++;
        }
    }
}


