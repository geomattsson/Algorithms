package merge;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author Gustav Mattsson
 */
public class Test {

    private final float[] original;
    private final long[] avg;
    private final long[] min;
    private final long[] max;

    public Test(float[] list) {
        this.original = list;
        avg = new long[4];
        min = new long[4];
        max = new long[4];
        for (int i = 0; i < 4; i++) {
            min[i] = Long.MAX_VALUE;
            max[i] = Long.MIN_VALUE;
        }
    }

    public void testSingleMerge(int iterations) throws InterruptedException {
        long[] times = new long[iterations];

        System.out.println("Starting Single Merge Test");
        for (int i = 0; i < iterations; i++) {
            float[] list = Arrays.copyOf(original, original.length);
            float[] temp = new float[list.length];
            //MergeSort sort = new MergeSort(list, temp, 0, list.length - 1, 0);
            System.gc();
            Thread.sleep(1000);

            //float[] sorted = sort.sort();
            long start = System.nanoTime();
            SingleThreadQuickSort.quickSort(list);
            long elapsed = System.nanoTime() - start;
            times[i] = elapsed;

            System.out.print("Time: " + elapsed / 1.0E9 + " s - ");
            if (verify(list)) {
                System.out.println("Sorted");
                if (max[0] < elapsed) {
                    max[0] = elapsed;
                }
                if (min[0] > elapsed) {
                    min[0] = elapsed;
                }
            } else {
                System.out.println("Sorting failed");
            }
        }
        System.out.println("Finished Single Merge Test\n");
        avg[0] = calcAvg(times);
    }

    public void testParallelMerge(int cores, int threshold, int iterations) throws InterruptedException {
        long[] times = new long[iterations];

        ForkJoinPool pool = new ForkJoinPool(cores);
        System.out.println("ForkJoin, threads = " + cores);

        System.out.println("Starting Parallel Merge Test");
        for (int i = 0; i < iterations; i++) {
            float[] list = Arrays.copyOf(original, original.length);
            //float[] temp = new float[list.length];
            //MergeSort sort = new MergeSort(list, temp, 0, list.length - 1, threshold);
            SortTask sort = new SortTask(list, 0, list.length - 1, threshold);
            System.gc();
            Thread.sleep(1000);

            long start = System.nanoTime();
            pool.invoke(sort);
            long elapsed = System.nanoTime() - start;
            times[i] = elapsed;

            System.out.print("Time: " + elapsed / 1.0E9 + " s - ");
            if (verify(list)) {
                System.out.println("Sorted");
                if (max[1] < elapsed) {
                    max[1] = elapsed;
                }
                if (min[1] > elapsed) {
                    min[1] = elapsed;
                }
            } else {
                System.out.println("Sorting failed");
            }
        }

        System.out.println("Finished Parallel Merge Test\n");
        avg[1] = calcAvg(times);
    }

    public void testArraySort(int iterations) throws InterruptedException {
        long[] times = new long[iterations];

        System.out.println("Starting Array Sort Test");
        for (int i = 0; i < iterations; i++) {
            float[] list = Arrays.copyOf(original, original.length);
            System.gc();
            Thread.sleep(1000);

            long start = System.nanoTime();
            Arrays.sort(list);
            long elapsed = System.nanoTime() - start;
            times[i] = elapsed;

            System.out.print("Time: " + elapsed / 1.0E9 + " s - ");
            if (verify(list)) {
                System.out.println("Sorted");
                if (max[2] < elapsed) {
                    max[2] = elapsed;
                }
                if (min[2] > elapsed) {
                    min[2] = elapsed;
                }
            } else {
                System.out.println("Sorting failed");
            }
        }
        System.out.println("Finished Array Sort Test\n");
        avg[2] = calcAvg(times);
    }

    public void testParallelArraySort(int iterations) throws InterruptedException {
        long[] times = new long[iterations];    

        System.out.println("Starting Parallel Sort Test");
        for (int i = 0; i < iterations; i++) {
            float[] list = Arrays.copyOf(original, original.length);
            System.gc();
            Thread.sleep(1000);

            long start = System.nanoTime();
            Arrays.parallelSort(list);
            long elapsed = System.nanoTime() - start;
            times[i] = elapsed;

            System.out.print("Time: " + elapsed / 1.0E9 + " s - ");
            if (verify(list)) {
                System.out.println("Sorted");
                if (max[3] < elapsed) {
                    max[3] = elapsed;
                }
                if (min[3] > elapsed) {
                    min[3] = elapsed;
                }
            } else {
                System.out.println("Sorting failed");
            }
        }
        System.out.println("Finished Parallel Sort Test\n");
        avg[3] = calcAvg(times);
    }

    public void printStats() {
        System.out.println("SingleMerge Stats");
        System.out.println("Min: " + min[0] / 1.0E9 + " s");
        System.out.println("Average: " + avg[0] / 1.0E9 + " s");
        System.out.println("Max: " + max[0] / 1.0E9 + " s\n");
        printParallelStats();
        System.out.println("ArraySort Stats");
        System.out.println("Min: " + min[2] / 1.0E9 + " s");
        System.out.println("Average: " + avg[2] / 1.0E9 + " s");
        System.out.println("Max: " + max[2] / 1.0E9 + " s\n");
        System.out.println("ParallelSort Stats");
        System.out.println("Min: " + min[3] / 1.0E9 + " s");
        System.out.println("Average: " + avg[3] / 1.0E9 + " s");
        System.out.println("Max: " + max[3] / 1.0E9 + " s\n");
    }

    public void printParallelStats() {
        System.out.println("ParallelMerge Stats");
        System.out.println("Min: " + min[1] / 1.0E9 + " s");
        System.out.println("Average: " + avg[1] / 1.0E9 + " s");
        System.out.println("Max: " + max[1] / 1.0E9 + " s\n");
    }

    private long calcAvg(long[] list) {
        long avg = 0;
        for (int i = 0; i < list.length; i++) {
            avg += list[i];
        }
        avg = avg / list.length;
        return avg;
    }

    private boolean verify(float[] list) {
        for (int i = 0; i < list.length - 1; i++) {
            if (list[i] > list[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
