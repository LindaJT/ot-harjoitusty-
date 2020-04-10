
package goalplanner.domain;

import goalplanner.dao.GoalDao;
import goalplanner.dao.UserDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class GoalPlannerService {
    
    private GoalDao goalDao;
    private UserDao userDao;
    private User loggedIn;
    
    public GoalPlannerService(UserDao userDao, GoalDao goalDao) {
        this.userDao = userDao;
        this.goalDao = goalDao;
    }
    
    public boolean createUser(String name, String username)  {   
        if (userDao.findByUsername(username) != null) {
            return false;
        }
        User user = new User(name, username);
        try {
            userDao.create(user);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
    
    public boolean createGoal(String name, Date date) {
        Goal goal = new Goal(name, date, loggedIn);
        try {
            goalDao.create(goal);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public boolean login(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return false;
        }
        
        loggedIn = user;
        
        return true;
    }
    
    public User getLoggedUser() {
        return loggedIn;
    }

    
    public void logout() {
        loggedIn = null;  
    }
    
    public List<Goal> getUnachieved() {
        if (loggedIn == null) {
            return new ArrayList<>();
        }
        return goalDao.getAll()
                .stream()
                .sorted()
                .filter(goal->goal.getUser().equals(loggedIn))
                .filter(goal->!goal.getAchieved())
                .collect(Collectors.toList());
    }
    
    public void setAchieved(int id) {
        try {
            goalDao.setAchieved(id);
        } catch (Exception e) {
            
        }
    }
    
}
