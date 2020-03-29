
package goalplanner.domain;

import goalplanner.dao.FileUserDao;
import goalplanner.dao.UserDao;


public class GoalPlannerService {
    
    private UserDao userDao;
    
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
        } catch(Exception e) {
            return false;
        }

        return true;
    }
    
}
