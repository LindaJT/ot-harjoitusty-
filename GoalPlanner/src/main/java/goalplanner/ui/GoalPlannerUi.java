
package goalplanner.ui;

import goalplanner.dao.FileGoalDao;
import goalplanner.dao.FileUserDao;
import goalplanner.domain.Goal;
import goalplanner.domain.GoalPlannerService;
import java.time.LocalDate;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
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
    
    private VBox todayNodes;
    private VBox weekNodes;
    private VBox monthNodes;
    private VBox yearNodes;
    
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
        Label loginLabel = new Label("Username");
        TextField usernameInput = new TextField();
        
        inputPane.getChildren().addAll(loginLabel, usernameInput);
        Label loginMessage = new Label();
        
        Button loginButton = new Button("Login");
        Button createButton = new Button("Create new user");
        loginButton.setOnAction(e-> {
            String username = usernameInput.getText();
            menuLabel.setText("Welcome to GoalPlanner " + username + "!");
            if (service.login(username)) {
                loginMessage.setText("");
                redrawGoals();
                primaryStage.setScene(goalScene);  
                usernameInput.setText("");
            } else {
                loginMessage.setText("User does not exist");
                loginMessage.setTextFill(Color.RED);
            }      
        });  
        
        createButton.setOnAction(e-> {
            usernameInput.setText("");
            primaryStage.setScene(newUserScene);   
        });  
        
        loginPane.getChildren().addAll(loginMessage, inputPane, loginButton, createButton);       
        
        logInScene = new Scene(loginPane, 700, 700);    
   
        // new createNewUserScene
        
        VBox newUserPane = new VBox(10);
        
        HBox newUsernamePane = new HBox(10);
        newUsernamePane.setPadding(new Insets(10));
        TextField newUsernameInput = new TextField(); 
        Label newUsernameLabel = new Label("Username");
        newUsernameLabel.setPrefWidth(100);
        newUsernamePane.getChildren().addAll(newUsernameLabel, newUsernameInput);
     
        HBox newNamePane = new HBox(10);
        newNamePane.setPadding(new Insets(10));
        TextField newNameInput = new TextField();
        Label newNameLabel = new Label("Name");
        newNameLabel.setPrefWidth(100);
        newNamePane.getChildren().addAll(newNameLabel, newNameInput);        
        
        Label userCreationMessage = new Label();
        
        Button createNewUserButton = new Button("Create");
        createNewUserButton.setPadding(new Insets(10));

        createNewUserButton.setOnAction(e-> {
            String username = newUsernameInput.getText();
            String name = newNameInput.getText();
   
            if (username.length() < 2 || name.length() < 2) {
                userCreationMessage.setText("Username or name too short");
                userCreationMessage.setTextFill(Color.RED);              
            } else if (service.createUser(name, username)) {
                userCreationMessage.setText("");                
                loginMessage.setText("New user created");
                loginMessage.setTextFill(Color.GREEN);
                primaryStage.setScene(logInScene);      
            } else {
                userCreationMessage.setText("Username has to be unique");
                userCreationMessage.setTextFill(Color.RED);        
            }
 
        });  
        
        newUserPane.getChildren().addAll(userCreationMessage, newUsernamePane, newNamePane, createNewUserButton); 
        
        ScrollPane goalScroll = new ScrollPane();
        BorderPane mainPane = new BorderPane(goalScroll);
        
        goalScene = new Scene(mainPane, 700, 700);
        
        VBox createForm = new VBox(10);
        Button createGoal = new Button("Set");
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        Label name = new Label("Goal");
        TextField nameInput = new TextField("Name");
        Label categoryLabel = new Label("Choose category of the goal");
        ComboBox category = new ComboBox();
        String categories[] = {"Health", "Studies", "Work", "Money"};
        category.setItems(FXCollections .observableArrayList(categories));
        Label date = new Label("Set goal day, month and year");
        TextField dayInput = new TextField("DD");
        TextField monthInput = new TextField("MM");
        TextField yearInput = new TextField("YYYY");
        Label errorMessage = new Label();
        Label repeatQ = new Label("Do you want to repeat the goal? Write daily, weekly, monthly or yearly");
        TextField repeatA = new TextField();
        TextField times = new TextField("How many times?");
        createGoal.setOnAction(e-> {
            LocalDate createDate;
            createDate = LocalDate.of(Integer.parseInt(yearInput.getText()), Integer.parseInt(monthInput.getText()), Integer.parseInt(dayInput.getText()));
            LocalDate now = LocalDate.now();
            if (createDate.isBefore(now)) {
                errorMessage.setText("Goal date can not be in the past");
            } else if (nameInput.getText().length() < 3) {
                errorMessage.setText("Goal name has to be over 3 characters");
            } else {
                service.createGoal(nameInput.getText(), createDate, "category");
                int id = service.getUnachieved().size() +1;
                if (!repeatA.getText().isEmpty()) {
                    repeats(repeatA.getText(), Integer.parseInt(times.getText()), id);
                }
                nameInput.setText("Goal");
                dayInput.setText("DD");
                monthInput.setText("MM");
                yearInput.setText("YYYY");
                times.setText("How many times?");
                id = -1;
                primaryStage.setScene(goalScene); 
            }
        });
        
        createForm.getChildren().addAll(name, nameInput, categoryLabel, category, date, dayInput, monthInput, yearInput, repeatQ, repeatA, times, errorMessage, createGoal);
        
        Scene createGoalScene = new Scene(createForm, 700, 700);
       
        newUserScene = new Scene(newUserPane, 700, 700);
        
        
        VBox menuPane = new VBox(10);
        Button logoutButton = new Button("Logout");
        Button createGoalButton = new Button("Set a goal!");
        HBox calendarPane = new HBox(30);
        Label goalCalendar = new Label("Your goals: ");
        Button todayButton = new Button("TODAY");
        Button weekButton = new Button("THIS WEEK");
        Button monthButton = new Button("THIS MONTH");
        Button yearButton = new Button("LATER THAN THIS MONTH");
        calendarPane.getChildren().addAll(goalCalendar, todayButton, weekButton, monthButton, yearButton);
        menuPane.getChildren().addAll(menuLabel, logoutButton, createGoalButton, calendarPane);
        logoutButton.setOnAction(e->{
            service.logout();
            primaryStage.setScene(logInScene);
        });
        createGoalButton.setOnAction(e-> {
            primaryStage.setScene(createGoalScene);
        });
        
        todayNodes = new VBox(10);
        weekNodes = new VBox(10);
        monthNodes = new VBox(10);
        yearNodes = new VBox(10);
        todayNodes.setMaxWidth(280);
        todayNodes.setMinWidth(280);
        weekNodes.setMaxWidth(280);
        weekNodes.setMinWidth(280);
        monthNodes.setMaxWidth(280);
        monthNodes.setMinWidth(280);
        yearNodes.setMaxWidth(280);
        yearNodes.setMinWidth(280);
        
        todayButton.setOnAction(e -> {
            redrawGoals();
            goalScroll.setContent(todayNodes);
        });
        weekButton.setOnAction(e -> {
            redrawGoals();
            goalScroll.setContent(weekNodes);
        });
        monthButton.setOnAction(e -> {
            redrawGoals();
            goalScroll.setContent(monthNodes);
        });
        yearButton.setOnAction(e -> {
            redrawGoals();
            goalScroll.setContent(yearNodes);
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
    
    public Node createGoalNode(Goal goal) {
        HBox box = new HBox(10);
        Label label  = new Label(goal.getName());
        label.setMinHeight(28);
        int day = goal.getGoalDate().getDayOfMonth();
        int month = goal.getGoalDate().getMonthValue();
        int year = goal.getGoalDate().getYear();
        Label date = new Label(day + "/" + month + "/" + year);
        date.setMinHeight(28);
        Label category = new Label(goal.getCategory());
        category.setMinHeight(28);
        Button button = new Button("Achieved the goal!");
        button.setOnAction(e->{
            service.setAchieved(goal.getId());
            redrawGoals();
        });
                
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
    //    box.setPadding(new Insets(0,5,0,5));
        
        box.getChildren().addAll(label, date, spacer, category, button);
        return box;
    }
    
    public void redrawGoals() {
        todayNodes.getChildren().clear();     
        weekNodes.getChildren().clear();  
        monthNodes.getChildren().clear();  
        yearNodes.getChildren().clear();  

        List<Goal> todaysGoals = service.getTodaysGoals();
        todaysGoals.forEach(goal->{
            todayNodes.getChildren().add(createGoalNode(goal));  
        });
        List<Goal> weekGoals = service.getWeeklyGoals();
        weekGoals.forEach(goal->{
            weekNodes.getChildren().add(createGoalNode(goal));  
        });
        List<Goal> monthGoals = service.getMonthGoals();
        monthGoals.forEach(goal->{
            monthNodes.getChildren().add(createGoalNode(goal));  
        });
        List<Goal> yearGoals = service.getYearGoals();
        yearGoals.forEach(goal->{
            yearNodes.getChildren().add(createGoalNode(goal));  
        });
    }
    
    public void repeats(String type, int repeatTimes, int id) {
        if (type.equals("Daily")) {
            service.repeatDaily(repeatTimes, id);
        } else if (type.equals("Weekly")) {
            service.repeatWeekly(repeatTimes, id);
        } else if (type.equals("Monthly")) {
            service.repeatMonthly(repeatTimes, id);
        } else {
            service.repeatYearly(repeatTimes, id);
        }
    }
    
    @Override
    public void stop() {
      System.out.println("app closing");
    } 
    
}
