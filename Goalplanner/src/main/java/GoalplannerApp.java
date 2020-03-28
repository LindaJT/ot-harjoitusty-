
import java.util.Scanner;


public class GoalplannerApp {
    private Scanner scanner;
    private GoalplannerService service;
    
    public GoalplannerApp(Scanner scanner, GoalplannerService service) {
        this.scanner = scanner;
        this.service = service;
        service = new GoalplannerService();
        
    }
    
    public void start() {
        System.out.println("Hello world");
    }
    
}
