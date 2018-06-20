package merge;

import java.util.concurrent.RecursiveTask;

/**
 * Created by Yousif on 2018-01-20.
 */
public class SortTask extends RecursiveTask<float[]> {
    public static int THRESHOLD = (int) 1E5;
    private static final long serialVersionUID = 1L;
    private float[] array;
    private final int low, high;

    public SortTask(float[] array, int low, int high, int threshold) {
        this.array = array;
        this.low = low;
        this.high = high;
        this.THRESHOLD = threshold;
    }

    @Override
    protected float[] compute() {
        if (low < high) {
            int partition = SingleThreadQuickSort.partition(array, low, high);
            if (high - low > THRESHOLD) {
                SortTask worker1 = new SortTask(array, low, partition, THRESHOLD);
                SortTask worker2 = new SortTask(array, partition + 1, high, THRESHOLD);
                worker1.fork();
                worker2.compute();
                worker1.join();
            } else {
                //Arrays.sort(array,low,high);
                array = new SortTask(array, low, partition, THRESHOLD).compute();
                array = new SortTask(array, partition + 1, high, THRESHOLD).compute();
            }
        }
        return array;
    }
}
