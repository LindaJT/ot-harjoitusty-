
package goalplanner.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class GoalTest {
    
    @Test
    public void goalsInTimeOrderInSortedList() {
        User user = new User("user", "user");
        List<Goal> goals = new ArrayList();
        goals.add(new Goal("test1", new Date(2020, 01, 01), user));
        goals.add(new Goal("test2", new Date(2019, 04, 02), user));
        Collections.sort(goals);
        assertEquals("test2", goals.get(0).getName());
        
    }
    
}
