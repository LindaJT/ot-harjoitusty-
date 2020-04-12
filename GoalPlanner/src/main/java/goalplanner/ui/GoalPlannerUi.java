
package goalplanner.ui;

import goalplanner.dao.FileGoalDao;
import goalplanner.dao.FileUserDao;
import goalplanner.domain.GoalPlannerService;
import java.util.Date;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;


public class GoalPlannerUi extends Application {
    
    private GoalPlannerService service;
    private Label menuLabel = new Label();
    private Scene goalScene;
    private Scene newUserScene;
    private Scene logInScene;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void init() throws Exception {
        FileUserDao userDao = new FileUserDao("users.txt");
        FileGoalDao goalDao = new FileGoalDao("goals.txt", userDao);

        service = new GoalPlannerService(userDao, goalDao);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox loginPane = new VBox(10);
        HBox inputPane = new HBox(10);
        loginPane.setPadding(new Insets(10, 10, 10, 10));
        Label loginLabel = new Label("username");
        TextField usernameInput = new TextField();
        
        inputPane.getChildren().addAll(loginLabel, usernameInput);
        Label loginMessage = new Label();
        
        Button loginButton = new Button("login");
        Button createButton = new Button("create new user");
        loginButton.setOnAction(e-> {
            String username = usernameInput.getText();
            menuLabel.setText(username + " logged in...");
            if (service.login(username)) {
                loginMessage.setText("");
                primaryStage.setScene(goalScene);  
                usernameInput.setText("");
            } else {
                loginMessage.setText("user does not exist");
                loginMessage.setTextFill(Color.RED);
            }      
        });  
        
        createButton.setOnAction(e-> {
            usernameInput.setText("");
            primaryStage.setScene(newUserScene);   
        });  
        
        loginPane.getChildren().addAll(loginMessage, inputPane, loginButton, createButton);       
        
        logInScene = new Scene(loginPane, 300, 250);    
   
        // new createNewUserScene
        
        VBox newUserPane = new VBox(10);
        
        HBox newUsernamePane = new HBox(10);
        newUsernamePane.setPadding(new Insets(10));
        TextField newUsernameInput = new TextField(); 
        Label newUsernameLabel = new Label("username");
        newUsernameLabel.setPrefWidth(100);
        newUsernamePane.getChildren().addAll(newUsernameLabel, newUsernameInput);
     
        HBox newNamePane = new HBox(10);
        newNamePane.setPadding(new Insets(10));
        TextField newNameInput = new TextField();
        Label newNameLabel = new Label("name");
        newNameLabel.setPrefWidth(100);
        newNamePane.getChildren().addAll(newNameLabel, newNameInput);        
        
        Label userCreationMessage = new Label();
        
        Button createNewUserButton = new Button("create");
        createNewUserButton.setPadding(new Insets(10));

        createNewUserButton.setOnAction(e-> {
            String username = newUsernameInput.getText();
            String name = newNameInput.getText();
   
            if (username.length() == 2 || name.length() < 2) {
                userCreationMessage.setText("username or name too short");
                userCreationMessage.setTextFill(Color.RED);              
            } else if (service.createUser(username, name)) {
                userCreationMessage.setText("");                
                loginMessage.setText("new user created");
                loginMessage.setTextFill(Color.GREEN);
                primaryStage.setScene(logInScene);      
            } else {
                userCreationMessage.setText("username has to be unique");
                userCreationMessage.setTextFill(Color.RED);        
            }
 
        });  
        
        newUserPane.getChildren().addAll(userCreationMessage, newUsernamePane, newNamePane, createNewUserButton); 
        
        ScrollPane goalScroll = new ScrollPane();
        BorderPane mainPane = new BorderPane(goalScroll);
        
        goalScene = new Scene(mainPane, 300, 250);
        
        VBox createForm = new VBox(10);
        Button createGoal = new Button("Set");
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        Label name = new Label("Goal name");
        TextField nameInput = new TextField();
        Label date = new Label("Set goal day, month and year");
        TextField dayInput = new TextField();
        TextField monthInput = new TextField();
        TextField yearInput = new TextField();
        createGoal.setOnAction(e-> {
            Date createDate;
            createDate = new Date(Integer.parseInt(yearInput.getText()), Integer.parseInt(monthInput.getText()), Integer.parseInt(dayInput.getText()));
            service.createGoal(nameInput.getText(), createDate);
            primaryStage.setScene(goalScene);
        });
        
        createForm.getChildren().addAll(name, nameInput, spacer, date, dayInput, monthInput, yearInput, createGoal);
        
        Scene createGoalScene = new Scene(createForm, 300, 200);
       
        newUserScene = new Scene(newUserPane, 300, 250);
        
        
        VBox menuPane = new VBox(10);
        Button logoutButton = new Button("Logout");
        Button createGoalButton = new Button("Set a goal!");
        menuPane.getChildren().addAll(menuLabel, logoutButton, createGoalButton);
        logoutButton.setOnAction(e->{
            service.logout();
            primaryStage.setScene(logInScene);
        });
        createGoalButton.setOnAction(e-> {
            primaryStage.setScene(createGoalScene);
        });
        
        mainPane.setTop(menuPane);
        
        primaryStage.setTitle("Welcome to Goal Planner!");
        primaryStage.setScene(logInScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e-> {
            System.out.println("closing");
            System.out.println(service.getLoggedUser());
            if (service.getLoggedUser() != null) {
                e.consume();   
            }
            
        });
    }
    
}
