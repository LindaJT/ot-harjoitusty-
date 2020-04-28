
package goalplanner.domain;

import goalplanner.dao.GoalDao;
import goalplanner.dao.UserDao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sovelluslogiikasta vastaava luokka 
 */

public class GoalPlannerService {
    
    private GoalDao goalDao;
    private UserDao userDao;
    private User loggedIn;
    
    public GoalPlannerService(UserDao userDao, GoalDao goalDao) {
        this.userDao = userDao;
        this.goalDao = goalDao;
    }
    
    /**
    * Uuden käyttäjän luominen
    *
    * @param   name   luotavan käyttäjän nimi
    * 
    * @param    username    luotavan käyttäjän käyttäjänimi
    */
    
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
    
    /**
     * Uuden tavoitteen luominen käyttäjälle
     * 
     * @param name tavoitteen nimi
     * @param date tavoitteen tavoitepäivämäärä
     * 
     */
    
    public boolean createGoal(String name, LocalDate date) {
        Goal goal = new Goal(name, date, loggedIn);
        try {
            goalDao.create(goal);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /**
     * Käyttäjän sisäänkirjautuminen
     * 
     * @param username käyttäjänimi, jolla sisäänkirjautumista yritetään
     * 
     */
    
    public boolean login(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return false;
        }
        
        loggedIn = user;
        
        return true;
    }
    
    /**
     * Metodi palauttaa sisäänkirjautuneen käyttäjän
     * 
     * @return sisäänkirjautunut käyttäjä
     */
    public User getLoggedUser() {
        return loggedIn;
    }

    /**
     * Käyttäjän uloskirjautuminen
     */
    
    public void logout() {
        loggedIn = null;  
    }
    
    /**
     * Käyttäjän vielä saavuttamattomat tavoitteet
     * 
     * @return lista käyttäjän saavuttamattomista tavoitteista
     */
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
    
    /**
     * Metodi asettaa tavoitteen saavutetuksi
     * 
     * @param id tavoitteen id 
     */
    
    public void setAchieved(int id) {
        try {
            goalDao.setAchieved(id);
        } catch (Exception e) {
            
        }
    }
    
}
