
package goalplanner.domain;

import goalplanner.dao.GoalDao;
import java.util.ArrayList;
import java.util.List;

public class FakeGoalDao implements GoalDao {
    List<Goal> goals;
    
    public FakeGoalDao() {
        goals = new ArrayList<>();
    }
    
    public List<Goal> getAll() {
        return goals;
    }
    
    public Goal create(Goal goal) {
        goal.setId(goals.size()+1);
        goals.add(goal);
        return goal;
    }   
    
    public void setAchieved(int id) {
        for (Goal goal : goals) {
            if ( goal.getId()==id) {
                goal.setAchieved();
            }
        }
    }   
    
}
