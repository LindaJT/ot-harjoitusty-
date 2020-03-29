
package goalplanner.ui;

import goalplanner.dao.FileUserDao;
import goalplanner.domain.GoalPlannerService;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;


public class GoalPlannerUi extends Application {
    
    private GoalPlannerService service;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void init() {
        FileUserDao userDao = new FileUserDao("users.txt");

        service = new GoalPlannerService(userDao);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello world!");
        primaryStage.show();
    }
    
}
