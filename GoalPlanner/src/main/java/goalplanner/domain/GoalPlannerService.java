
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
     * @param category tavoitteen kategoria
     * 
     * @return luotu tavoite
     * 
     */
    
    public Goal createGoal(String name, LocalDate date, String category) {
        Goal goal = new Goal(name, date, loggedIn, category);
        try {
            Goal newGoal = goalDao.create(goal);
            return newGoal;
        } catch (Exception e) {
            return null;
        }
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
    
    public List<Goal> getTodaysGoals() {
        List<Goal> unachieved = this.getUnachieved();
        List<Goal> todaysGoals = new ArrayList<>();
        for (Goal goal : unachieved) {
            if (LocalDate.now().equals(goal.getGoalDate())) {
                todaysGoals.add(goal);
            }
        }
        return todaysGoals;
    }
    
    public List<Goal> getWeeklyGoals() {
        List<Goal> unachieved = this.getUnachieved();
        List<Goal> weeklyGoals = new ArrayList<>();
        for (Goal goal : unachieved) {
            if (goal.getGoalDate().isBefore(LocalDate.now().plusDays(6)) && goal.getGoalDate().isAfter(LocalDate.now().minusDays(1))) {
                weeklyGoals.add(goal);
            }
        }
        return weeklyGoals;
    }
    
    public List<Goal> getMonthGoals() {
        List<Goal> unachieved = this.getUnachieved();
        List<Goal> monthGoals = new ArrayList<>();
        for (Goal goal : unachieved) {
            if (goal.getGoalDate().getMonthValue() == LocalDate.now().getMonthValue() && goal.getGoalDate().getYear() == LocalDate.now().getYear()) {
                monthGoals.add(goal);
            }
        }
        return monthGoals;
    }
    
    public List<Goal> getYearGoals() {
        List<Goal> unachieved = this.getUnachieved();
        List<Goal> yearGoals = new ArrayList<>();
        for (Goal goal : unachieved) {
            if (goal.getGoalDate().getYear() == LocalDate.now().getYear()) {
                yearGoals.add(goal);
            }
        }
        return yearGoals;
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
    
    public void repeatDaily(int id, int times) {
        try {
            Goal goal = goalDao.findById(id);
            String name = goal.getName();
            LocalDate date = goal.getGoalDate();
            String category = goal.getCategory();
            for (int i = 0; i < times; i++) {
                createGoal(name, date.plusDays(i + 1), category);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }
    
    public void repeatWeekly(int id, int times) {
        try {
            Goal goal = goalDao.findById(id);
            String name = goal.getName();
            LocalDate date = goal.getGoalDate();
            String category = goal.getCategory();
            for (int i = 0; i < times; i++) {
                createGoal(name, date.plusDays((i + 1) * 7), category);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }
    
    public void repeatMonthly(int id, int times) {
        try {
            Goal goal = goalDao.findById(id);
            String name = goal.getName();
            LocalDate date = goal.getGoalDate();
            String category = goal.getCategory();
            for (int i = 0; i < times; i++) {
                createGoal(name, date.plusMonths(i + 1), category);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }
    
    public void repeatYearly(int id, int times) {
        try {
            Goal goal = goalDao.findById(id);
            String name = goal.getName();
            LocalDate date = goal.getGoalDate();
            String category = goal.getCategory();
            for (int i = 0; i < times; i++) {
                createGoal(name, date.plusYears(i + 1), category);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }
}
