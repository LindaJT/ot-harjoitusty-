
package goalplanner.domain;

import java.util.Date;

public class Goal {
    
    private int id;
    private String name;
    private Date goalDate;
    private Boolean achieved;
    private User user;
    
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

    public void setName(String name) {
        this.name = name;
    }

    public Date getGoalDate() {
        return goalDate;
    }

    public void setGoalDate(Date goalDate) {
        this.goalDate = goalDate;
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

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
    
}
