
package goalplanner.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class GoalPlannerServiceUserTest {
    private FakeUserDao userDao;
    private GoalPlannerService service;
    private FakeGoalDao goalDao;
    
    public GoalPlannerServiceUserTest() {
    }

    
    @Before
    public void setUp() {
        userDao = new FakeUserDao();
        goalDao = new FakeGoalDao();
        service = new GoalPlannerService(userDao, goalDao);
    }
    
    @Test
    public void canCreateNewUser() {
        boolean result = service.createUser("Testi2", "testi2");
        assertTrue(result);
    }
    
    @Test
    public void canNotCreateUserWithSameUsername() {
        service.createUser("Pekka", "pekka");
        boolean result = service.createUser("Peksi", "pekka");
        assertFalse(result);
    }
    
    @Test
    public void userCanLogIn() {
        service.createUser("Pekka", "pekka");
        boolean result = service.login("pekka");
        assertTrue(result);
    }
    
    @Test
    public void userCanNotLogInWithNonExcistingUsername() {
        boolean result = service.login("matti");
        assertFalse(result);
    }
}
