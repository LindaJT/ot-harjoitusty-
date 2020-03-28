
import java.util.Scanner;


public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GoalplannerService service = new GoalplannerService();

        GoalplannerApp app = new GoalplannerApp(scanner, service);
        app.start();
    }
    
}
