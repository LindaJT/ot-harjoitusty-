
package goalplanner.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class GoalTest {
    
    @Test
    public void goalsInTimeOrderInSortedList() {
        User user = new User("user", "user");
        List<Goal> goals = new ArrayList();
        goals.add(new Goal("test1", LocalDate.of(2020, 01, 01), user));
        goals.add(new Goal("test2", LocalDate.of(2019, 04, 02), user));
        Collections.sort(goals);
        assertEquals("test2", goals.get(0).getName());
        
    }
    
    @Test
    public void goalsInTimeOrderSameDate() {
        User user = new User("user", "user");
        List<Goal> goals = new ArrayList();
        goals.add(new Goal("test1", LocalDate.of(2020, 01, 01), user));
        goals.add(new Goal("test2", LocalDate.of(2020, 01, 01), user));
        Collections.sort(goals);
        assertEquals("test1", goals.get(0).getName());
    }
    
    @Test
    public void goalsInTimeOrder() {
        User user = new User("user", "user");
        List<Goal> goals = new ArrayList();
        goals.add(new Goal("test1", LocalDate.of(2020, 01, 01), user));
        goals.add(new Goal("test2", LocalDate.of(2020, 07, 01), user));
        Collections.sort(goals);
        assertEquals("test1", goals.get(0).getName());
    }
    
     @Test
    public void equalWhenSameId() {
        Goal goal1 = new Goal(null, null, null);
        goal1.setId(1);
        Goal goal2 = new Goal(null, null, null);
        goal2.setId(1);
        assertTrue(goal1.equals(goal2));
    }
  
    @Test
    public void notEqualWhenDifferentId() {
        Goal goal1 = new Goal(null, null, null);
        goal1.setId(1);
        Goal goal2 = new Goal(null, null, null);
        goal2.setId(2);
        assertFalse(goal1.equals(goal2));
    }   
    
    @Test
    public void nonEqualWhenDifferentType() {
        Goal goal = new Goal(null, null, null);
        Object o = new Object();
        assertFalse(goal.equals(o));
    }      
    
}
