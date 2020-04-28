
package goalplanner.domain;

import java.util.Date;
import java.util.Objects;

public class Goal implements Comparable<Goal> {
    
    private int id;
    private String name;
    private Date goalDate;
    private Boolean achieved;
    private User user;
    
    public Goal(int id, String name, Date goalDate, User user) {
        this.id = id;
        this.name = name;
        this.goalDate = goalDate;
        this.achieved = false;
        this.user = user;
        
    }
    
    public Goal(String name, Date goalDate, User user) {
        this.name = name;
        this.goalDate = goalDate;
        this.achieved = false;
        this.user = user;
        
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

    public Date getGoalDate() {
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


    @Override
    public int compareTo(Goal goal) {
        if (this.goalDate.equals(goal.getGoalDate())) {
            return 0;
        } else if (this.goalDate.after(goal.getGoalDate())) {
            return 1;
        } else {
            return -1;
        }
    }
    
    
    
    
}
