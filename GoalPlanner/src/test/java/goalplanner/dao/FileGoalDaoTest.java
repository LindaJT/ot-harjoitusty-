
package goalplanner.dao;

import java.io.File;
import goalplanner.dao.GoalDao;
import goalplanner.domain.FakeUserDao;
import goalplanner.domain.Goal;
import goalplanner.domain.User;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class FileGoalDaoTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    File userFile;
    GoalDao dao;
    

    
    @Before
    public void setUp() throws Exception {
        userFile = testFolder.newFile("test_users.txt");
        UserDao userDao = new FakeUserDao();
        userDao.create(new User("Pena", "testaaja"));
        
        try (FileWriter file = new FileWriter(userFile.getAbsolutePath())) {
            file.write("1;80 op;2020-09-01;false;testaaja;opiskelu\n");
        }
        
        dao = new FileGoalDao(userFile.getAbsolutePath(), userDao); 
    }
    
    @Test
    public void goalsAreReadCorrectlyFromFile() {
        List<Goal> goals = dao.getAll();
        assertEquals(1, goals.size());
        Goal goal = goals.get(0);
        assertEquals("80 op", goal.getName());
        assertFalse(goal.getAchieved());
        assertEquals(1, goal.getId());
        assertEquals("testaaja", goal.getUser().getUsername());
    }
    
    @Test
    public void excistingGoalIsFound() {
        Goal goal = dao.findById(1);
        assertEquals("80 op", goal.getName());
        assertEquals("testaaja", goal.getUser().getUsername());
    }
    
    @Test
    public void nonExcistingUserIsNotFound() {
        Goal goal = dao.findById(5);
        assertEquals(null, goal);
    }
  
    @Test
    public void createdGoalIsFound() throws Exception {
        User user = new User("Linda", "linda");
        Goal goal = new Goal("testi", LocalDate.of(2020, 5, 23), user, "terveys");
        Goal goal2 = new Goal("testi2", LocalDate.of(2020, 6, 23), user, "terveys");
        dao.create(goal);
        dao.create(goal2);
        
       Goal goalFound = dao.findById(3);
        assertEquals("testi2", goalFound.getName());
    }
    
    @After
    public void tearDown() {
        userFile.delete();
    }

}
