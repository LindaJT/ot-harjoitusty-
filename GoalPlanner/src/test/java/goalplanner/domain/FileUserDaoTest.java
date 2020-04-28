
package goalplanner.domain;

import goalplanner.dao.FileUserDao;
import goalplanner.dao.UserDao;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class FileUserDaoTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    File userFile;
    UserDao dao;
    
    @Before
    public void setUp() throws Exception{
        userFile = testFolder.newFile("test_users.txt");  
        
        try (FileWriter file = new FileWriter(userFile.getAbsolutePath())) {
            file.write("Pena;testaaja\n");
        }
        
        dao = new FileUserDao(userFile.getAbsolutePath());
    }

    @Test
    public void usersAreReadCorrectlyFromFile() {
        List<User> users = dao.getAll();
        assertEquals(1, users.size());
        User user = users.get(0);
        assertEquals("Pena", user.getName());
        assertEquals("testaaja", user.getUsername());
    }
    
    @Test
    public void excistingUserIsFound() {
        User user = dao.findByUsername("testaaja");
        assertEquals("Pena", user.getName());
        assertEquals("testaaja", user.getUsername());
    }
    
    @Test
    public void nonExcistingUserIsNotFound() {
        User user = dao.findByUsername("linda");
        assertEquals(null, user);
    }
  
    @Test
    public void createdUserIsFound() throws Exception {
        User user = new User("Linda", "lindajt");
        dao.create(user);
        
        User user2 = dao.findByUsername("lindajt");
        assertEquals("Linda", user2.getName());
        assertEquals("lindajt", user2.getUsername());
    }
    
    @After
    public void tearDown() {
        userFile.delete();
    }
}
    
