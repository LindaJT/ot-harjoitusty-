
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
    
    public GoalPlannerServiceUserTest() {
    }

    
    @Before
    public void setUp() {
        userDao = new FakeUserDao();
        service = new GoalPlannerService(userDao);
    }
    
    @Test
    public void canCreateNewUser() {
        boolean result = service.createUser("Testi2", "testi2");
        assertTrue(result);
    }
}
