package uppgift3;

/**
 * Tenta written by Gustav Mattsson
 */
public class Main {
    
    private static int walk(int ns, int ew) { // ns = northsouth - ew = eastwest
        int[][] grid = new int[ns+1][ew+1];
        printGrid(ns, ew, grid);
        int count = walk(ns, ew, grid);
        printGrid(ns, ew, grid);
        return count;
    }
    
    private static int walk(int ns, int ew, int[][] grid) {
        // Base condiions
        if(ns == 0 && ew == 0) return 1;
        if(grid[ns][ew] != 0) return grid[ns][ew];
        // Recursion steps
        int count = 0;
        if(ns > 0) count += walk(ns-1, ew, grid);
        if(ew > 0) count += walk(ns, ew-1, grid);
        
        // Adding count to grid - Dynamic programming
        grid[ns][ew] = count;
        return count;
    }
    
    private static void printGrid(int ns, int ew, int[][] grid) {
        for(int i = 0; i < ns; i++) {
            for(int j = 0; j < ew; j++)
                System.out.print(grid[i][j] + " ");
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        System.out.println(walk(6, 3));
    }
}
