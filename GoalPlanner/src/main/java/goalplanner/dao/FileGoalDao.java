
package goalplanner.dao;

import goalplanner.domain.Goal;
import goalplanner.domain.User;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileGoalDao implements GoalDao {
    
    public List<Goal> goals;
    private String file;
    
    public FileGoalDao(String file, UserDao users) throws Exception {
        this.goals = new ArrayList<>();
        this.file = file;
        try {
            Scanner reader = new Scanner(new File(file));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(";");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                Date goalDate = new Date(parts[3]);
                User user = users.getAll().stream().filter(u->u.getUsername().equals(parts[5])).findFirst().orElse(null);
                Goal goal = new Goal(name, goalDate, user);
                goals.add(goal);
            }
        } catch (Exception e) {
            FileWriter writer = new FileWriter(new File(file));
            writer.close();
        }
    }
    
    private void save() throws Exception {
        try (FileWriter writer = new FileWriter(new File(file))) {
            for (Goal goal : goals) {
                writer.write(goal.getId() + ";" + goal.getName() + ";" + goal.getGoalDate() + ";" + goal.getAchieved() + ";" + goal.getUser().getUsername() + "\n");
            }
        }
    }    
    
    private int generateId() {
        return goals.size() + 1;
    }
    
    public List<Goal> getAll() {
        return goals;
    }
    
    public Goal create(Goal goal) throws Exception {
        goal.setId(generateId());
        goals.add(goal);
        save();
        return goal;
    }   
    
    public void setAchieved(int id) throws Exception {
        for (Goal goal : goals) {
            if (goal.getId() == id) {
                goal.setAchieved();
            }
        }
        save();
    }  
    
}
