package uppgift5;

/**
 * Tenta written by Gustav Mattsson
 */
public class Main {
    
    private static int solve(int ns, int ew) { // ns = north-south | ew = east-west 
        int[][] streetH = new int[ns+1][ew];
        int[][] streetV = new int[ns][ew+1];
        printGrid(ns+1, ew, streetH);
        printGrid(ns, ew+1, streetV);
        return solve(ns, ew, ns, ew, streetH, streetV);
    }
    
    private static int solve(int ns, int ew, int pNS, int pEW, 
            int[][] streetH, int[][] streetV) {
        printGrid(ns+1, ew, streetH);
        printGrid(ns, ew+1, streetV);
        if(pNS == 0 & pEW == 0) return 1;
        int count = 0;
        if(pNS > 0) {
            //Check south
            if(streetV[pNS-1][pEW] == 0) {
                streetV[pNS-1][pEW] = 1;
                count += solve(ns, ew, pNS-1, pEW, streetH, streetV);
                streetV[pNS-1][pEW] = 0;
            }
        }
        if(pNS < ns) {
            //Check north
            if(streetV[pNS][pEW] == 0) {
                streetV[pNS][pEW] = 1;
                count += solve(ns, ew, pNS+1, pEW, streetH, streetV);
                streetV[pNS][pEW] = 0;
            }
        }
        if(pEW < ew) {
            //Check east
            if(streetH[pNS][pEW] == 0) {
                streetH[pNS][pEW] = 1;
                count += solve(ns, ew, pNS, pEW+1, streetH, streetV);
                streetH[pNS][pEW] = 0;
            }
        }
        if(pEW > 0) {
            // Check west
            if(streetH[pNS][pEW-1] == 0) {
                streetH[pNS][pEW-1] = 1;
                count += solve(ns, ew, pNS, pEW-1, streetH, streetV);
                streetH[pNS][pEW-1] = 0;
            }
        }
        
        return count;
    }
    
    private static void printGrid(int ns, int ew, int[][] grid) {
        for(int i = ns-1; i >= 0; i--) {
            for(int j = ew-1; j >= 0; j--)
                System.out.print(grid[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        System.out.println(solve(3,3));
    }
}
