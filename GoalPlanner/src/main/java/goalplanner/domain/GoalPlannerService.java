
package goalplanner.domain;

import goalplanner.dao.UserDao;


public class GoalPlannerService {
    
    private UserDao userDao;
    private User loggedIn;
    
    public GoalPlannerService(UserDao userDao) {
        this.userDao = userDao;
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
    
}
