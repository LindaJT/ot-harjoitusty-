
package goalplanner.dao;

import goalplanner.domain.Goal;
import java.util.List;


public interface GoalDao {
    
    Goal create(Goal goal) throws Exception;
    List<Goal> getAll();
    void setAchieved(int Id) throws Exception;
    
    
}
