
package goalplanner.domain;

import org.junit.Before;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;


public class GoalPlannerServiceGoalTest {
    
    FakeUserDao userDao;
    FakeGoalDao goalDao;
    GoalPlannerService service;
    
    @Before
    public void setUp() {
        userDao = new FakeUserDao();
        goalDao = new FakeGoalDao();
        User user1 = new User("Eka", "eka");
        User user2 = new User("Toka", "toka");
        userDao.create(user1);
        userDao.create(user2);
        goalDao.create(new Goal("80 op", new Date(2020, 05, 23), user1));
        service = new GoalPlannerService(userDao, goalDao);
        service.login("eka");
    }
    
    @Test
    public void canCreateGoal() {
        boolean result = service.createGoal("run a marathon", new Date(2020, 06,01));
        assertTrue(result);
    }
    
    @Test
    public void listContainsInitializedGoal() {
        List<Goal> goals = service.getUnachieved();
        assertEquals(1, goals.size());
        Goal goal = goals.get(0);
        assertEquals("80 op", goal.getName());
        assertEquals("eka", goal.getUser().getUsername());
    }
    
    @Test 
    public void emptyListIfUserDoesntHaveGoals() {
        service.logout();
        service.login("toka");
        List<Goal> goals = service.getUnachieved();
        assertEquals(0, goals.size());
    }
    
    @Test 
    public void achievedGoalNotListed() {
        service.setAchieved(1);
        List<Goal> goals = service.getUnachieved();
        assertEquals(0, goals.size());
    }
    
    @Test
    public void loggedUserCanAddGoals() {
        service.createGoal("test", new Date(2020, 01, 01));
        List<Goal> goals = service.getUnachieved();
        assertEquals(2, goals.size());
        Goal goal = goals.get(0);
        assertEquals("test", goal.getName());
    }
    
}
