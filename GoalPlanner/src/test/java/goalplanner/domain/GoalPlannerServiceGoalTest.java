
package goalplanner.domain;

import java.time.LocalDate;
import java.time.Month;
import org.junit.Before;
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
        goalDao.create(new Goal("80 op", LocalDate.of(2020, 1, 1), user1, "opiskelu"));
        service = new GoalPlannerService(userDao, goalDao);
        service.login("eka");
    }
    
    @Test
    public void canCreateGoal() {
        Goal result = service.createGoal("run a marathon", LocalDate.of(2020, 06,01), "liikunta");
        assertEquals(result.getName(), "run a marathon");
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
    public void emptyListIfUserLogOut() {
        service.logout();
        List<Goal> goals = service.getUnachieved();
        assertEquals(0, goals.size());
    }
    
    @Test
    public void loggedUserCanAddGoals() {
        service.createGoal("test", LocalDate.of(2019, 01, 01), "terveys");
        List<Goal> goals = service.getUnachieved();
        assertEquals(2, goals.size());
        Goal goal = goals.get(0);
        assertEquals("test", goal.getName());
    }
    
    @Test
    public void repeatDailyRepeatsTwoTimes() {
        service.repeatDaily(1, 2);
        List<Goal> goals = service.getUnachieved();
        Goal secondGoal = goals.get(1);
        Goal thirdGoal = goals.get(2);
        assertEquals(LocalDate.of(2020, 1, 2), secondGoal.getGoalDate());
        assertEquals(LocalDate.of(2020, 1, 3), thirdGoal.getGoalDate());
        assertEquals(goals.size(), 3);
    }
    
    @Test
    public void repeatDailyWorksWithMultipleGoals() {
        service.createGoal("testi", LocalDate.of(2020, 6, 20), "terveys");
        service.repeatDaily(2, 2);
        List<Goal> goals = service.getUnachieved();
        assertEquals(goals.size(), 4);
        assertEquals("testi", goals.get(3).getName());
        assertEquals(LocalDate.of(2020, 6, 22), goals.get(3).getGoalDate());
    }
    
    @Test
    public void repeatWeeklyRepeatsTwoTimes() {
        service.repeatWeekly(1, 2);
        List<Goal> goals = service.getUnachieved();
        Goal secondGoal = goals.get(1);
        Goal thirdGoal = goals.get(2);
        assertEquals(LocalDate.of(2020, 1, 8), secondGoal.getGoalDate());
        assertEquals(LocalDate.of(2020, 1, 15), thirdGoal.getGoalDate());
        assertEquals(goals.size(), 3);
    }
    
    @Test
    public void repeatMonthlyRepeatsTwoTimes() {
        service.repeatMonthly(1, 2);
        List<Goal> goals = service.getUnachieved();
        Goal secondGoal = goals.get(1);
        Goal thirdGoal = goals.get(2);
        assertEquals(LocalDate.of(2020, 2, 1), secondGoal.getGoalDate());
        assertEquals(LocalDate.of(2020, 3, 1), thirdGoal.getGoalDate());
        assertEquals(goals.size(), 3);
    }
    
    @Test
    public void repeatYearlyRepeatsTwoTimes() {
        service.repeatYearly(1, 2);
        List<Goal> goals = service.getUnachieved();
        Goal secondGoal = goals.get(1);
        Goal thirdGoal = goals.get(2);
        assertEquals(LocalDate.of(2021, 1, 1), secondGoal.getGoalDate());
        assertEquals(LocalDate.of(2022, 1, 1), thirdGoal.getGoalDate());
        assertEquals(goals.size(), 3);
    }
    
    @Test
    public void getTodayGoalsReturnsTodaysGoals() {
        service.createGoal("testi", LocalDate.now(), "terveys");
        service.repeatDaily(2, 2);
        List<Goal> todaysGoals = service.getTodaysGoals();
        assertEquals(1, todaysGoals.size());
        assertEquals("testi", todaysGoals.get(0).getName());
    }
    
    @Test
    public void getWeekGoalsReturnsThisWeeksGoals() {
        service.createGoal("testi", LocalDate.now(), "terveys");
        service.repeatDaily(2, 2);
        List<Goal> weekGoals = service.getWeeklyGoals();
        assertEquals(3, weekGoals.size());
        assertEquals("testi", weekGoals.get(0).getName());
        service.createGoal("testi2", LocalDate.now().plusDays(2), "terveys");
        weekGoals = service.getWeeklyGoals();
        assertEquals(4, weekGoals.size());
    }
    
    @Test
    public void getMontGoalsReturnsThisMonthsGoals() {
        int month = LocalDate.now().getMonthValue();
        service.createGoal("testi", LocalDate.of(2020, month, 1), "terveys");
        service.repeatDaily(2, 2);
        List<Goal> monthGoals = service.getMonthGoals();
        assertEquals(3, monthGoals.size());
        assertEquals("testi", monthGoals.get(0).getName());
        service.createGoal("testi2", LocalDate.of(2020, month, 1), "terveys");
        monthGoals = service.getMonthGoals();
        assertEquals(4, monthGoals.size());
    }
    
     @Test
    public void getYearGoalsReturnsThisYearsGoals() {
        int year = LocalDate.now().getYear();
        service.createGoal("testi", LocalDate.of(year, 5, 5), "terveys");
        service.repeatDaily(2, 2);
        List<Goal> yearGoals = service.getYearGoals();
        assertEquals(4, yearGoals.size());
        assertEquals("testi", yearGoals.get(3).getName());
        service.createGoal("testi2", LocalDate.of(year, 6, 1), "terveys");
        yearGoals = service.getYearGoals();
        assertEquals(5, yearGoals.size());
    }
    
    
}
