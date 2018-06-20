package merge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author Gustav Mattsson
 */
public class Main {

    private static final int NUMBER_OF_ELEMENTS = (int) 1E8;
    private static final int ITERATIONS = 20;
    private static final int THRESHOLD = (int) 100;
    private static final int CORES = 4;
    
    private static boolean base = false;
    private static boolean c = true;
    private static boolean t = false;

    public static void main(String[] args) throws Exception {
        float[] list = getGenerated();
        Test test = new Test(list);

        // Warmup
        System.out.println("Warmup.");
        test.testSingleMerge(2);
        System.out.println("Warmup complete.\n\n");
        
        // Base test
        if (base) {
            test.testSingleMerge(ITERATIONS);
            //test.testArraySort(ITERATIONS);
            //test.testParallelMerge(CORES, THRESHOLD, ITERATIONS);
            //test.testParallelArraySort(ITERATIONS);
            test.printStats();
        }
        //Core Tests 1-7
        if (c) {
            test = new Test(list);
            test.testParallelMerge(CORES - 1, THRESHOLD, ITERATIONS);
            test.printParallelStats();
            test = new Test(list);
            test.testParallelMerge(CORES - 2, THRESHOLD, ITERATIONS);
            test.printParallelStats();
        }
        
        if(t) {
            test = new Test(list);
            test.testParallelMerge(CORES, 100, ITERATIONS);
            test.printParallelStats();
            test = new Test(list);
            test.testParallelMerge(CORES, 1000, ITERATIONS);
            test.printParallelStats();
            test = new Test(list);
            test.testParallelMerge(CORES, 10000, ITERATIONS);
            test.printParallelStats();
            test = new Test(list);
            test.testParallelMerge(CORES, 50000, ITERATIONS);
            test.printParallelStats();
            test = new Test(list);
            test.testParallelMerge(CORES, 100000, ITERATIONS);
            test.printParallelStats();
            test = new Test(list);
            test.testParallelMerge(CORES, 1000000, ITERATIONS);
            test.printParallelStats();
            test = new Test(list);
            test.testParallelMerge(CORES, 10000000, ITERATIONS);
            test.printParallelStats();
        }
    }

    private static boolean verify(float[] list) {
        for (int i = 0; i < list.length - 1; i++) {
            if (list[i] > list[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public static float[] generate() {

        // Generate data
        System.out.println("Generation begins.");
        final int SIZE = NUMBER_OF_ELEMENTS;
        float[] arr = new float[SIZE];
        Random rand = new Random();
        for (int i = 0; i < SIZE; i++) {
            arr[i] = rand.nextFloat();
        }
        System.out.println("Generation done.");
        return arr;
    }

    private static float[] getGenerated() {
        float[] list = new float[NUMBER_OF_ELEMENTS];
        File f = new File("values.txt");
        if (f.isFile()) {
            BufferedReader reader = null;
            try {
                System.out.println("Begin reading values.txt");
                reader = new BufferedReader(new FileReader("values.txt"));
                for (int i = 0; i < list.length; i++) {
                    list[i] = Float.valueOf(reader.readLine());
                }
                System.out.println("Finished reading values.txt");
            } catch (Exception e) {

            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ex) {
                    }
                }
            }
        } else {
            list = generate();
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter("values.txt"));
                for (int i = 0; i < list.length; i++) {
                    writer.write(String.valueOf(list[i]));
                    writer.newLine();
                    writer.flush();
                }
            } catch (Exception e) {
                System.out.println("Write to file exception");
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException ex) {
                    }
                }
            }

        }
        return list;
    }
}
