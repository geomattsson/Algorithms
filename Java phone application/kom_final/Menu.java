package kom_final;

/**
 * @author Gustav Mattsson
 */
public class Menu {
    private boolean faulty;
    
    public Menu(boolean faulty) {
        this.faulty = faulty;
    }
    
    public void printMenu() {
        System.out.println("1. Call");
        System.out.println("2. Answer");
        System.out.println("3. Hang up");
        System.out.println("4. Get Current State");
        if (faulty) {
            System.out.println("5. FaultCall");
            System.out.println("6. FaultAnswer");
            System.out.println("7. FaultHangup");
        }
        System.out.println("0 to exit.");
    }
}
