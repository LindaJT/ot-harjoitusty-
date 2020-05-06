
package goalplanner.domain;

import java.time.LocalDate;
import java.util.Objects;

/**
 * 
 * Tavoitetta vastaava luokka
 */
public class Goal implements Comparable<Goal> {
    
    private int id;
    private String name;
    private LocalDate goalDate;
    private Boolean achieved;
    private User user;
    private String category;
    
    public Goal(int id, String name, LocalDate goalDate, Boolean achieved, User user, String category) {
        this.id = id;
        this.name = name;
        this.goalDate = goalDate;
        this.achieved = achieved;
        this.user = user;
        this.category = category;
        
    }
    
    public Goal(String name, LocalDate goalDate, User user, String category) {
        this.name = name;
        this.goalDate = goalDate;
        this.achieved = false;
        this.user = user;
        this.category = category;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getGoalDate() {
        return goalDate;
    }


    public Boolean getAchieved() {
        return achieved;
    }

    public void setAchieved() {
        this.achieved = true;
    }

    public User getUser() {
        return user;
    }
    
    public String getCategory() {
        return category;
    }


    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Goal other = (Goal) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    /**
     * Järjestää tavoitteet päivämäärän mukaan niin, että uudempi tavoite on viimeisenä
     * 
     * @param goal tavoite
     * @return vertailuarvo
     */
    @Override
    public int compareTo(Goal goal) {
        if (this.goalDate.equals(goal.getGoalDate())) {
            return 0;
        } else if (this.goalDate.isAfter(goal.getGoalDate())) {
            return 1;
        } else {
            return -1;
        }
    }
    
    
    
    
}
