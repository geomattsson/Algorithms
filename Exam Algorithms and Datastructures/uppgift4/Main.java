package uppgift4;

import java.util.Arrays;
/**
 * Tenta written by Gustav Mattsson
 */
public class Main {
    private static void flip(int[] v) {
        int temp;
        for(int i = 0; i < v.length/2; i++) {
            temp = v[i];
            v[i] = v[v.length-1-i];
            v[v.length-1-i] = temp;
        }
    }
    
    private static void heapSort(int[] v) {
        int heapSize = 1, temp = 0;
        while(heapSize < v.length) { // Adding to the heap
            add(v, heapSize);
            heapSize++;
        }
        while(heapSize > 0) {
            remove(v, heapSize);
            heapSize--;
            
        }
        flip(v);
    }
    
    private static void add(int[] v, int heapSize) {
        int index = heapSize, t;
        int p = (index-1)/2;
        while(v[index] < v[p] && index != 0) {
            t = v[p];
            v[p] = v[index];
            v[index] = t;
            index = p;
            p = (p-1)/2;
        }
    }
    
    private static void remove(int[] v, int heapSize) {
        int temp = 0, index = 0;
        temp = v[0];
        v[0] = v[heapSize-1];
        v[heapSize-1] = temp;
        while((2*index)+1 < heapSize-1) {
            System.out.println(Arrays.toString(v));
            int newIndex = 0;
            int left = (2*index)+1, right = (2*index)+2;
            int smallest = v[index];
            if(v[left] < smallest) {
                smallest = v[left];
                newIndex = left;
            }
            if(right < heapSize-1) {
                if(v[right] < smallest) {
                    smallest = v[right];
                    newIndex = right;
                }
            }
            if(smallest == v[index]) break;
            v[newIndex] = v[index];
            v[index] = smallest;
            index = newIndex;
        }
    }

    public static void main(String[] args) {
        int size = 10;
        int[] v = new int[size];
        for (int i = 0; i < v.length; i++) {
            v[i] = (int) (Math.random() * size * 10);
        }
        System.out.println(Arrays.toString(v));
        heapSort(v);
        System.out.println(Arrays.toString(v));
        for (int i = 0; i < v.length - 1; i++) {
            if (v[i] > v[i + 1]) {
                System.out.println("error");
                break;
            }
        }
    }

}
