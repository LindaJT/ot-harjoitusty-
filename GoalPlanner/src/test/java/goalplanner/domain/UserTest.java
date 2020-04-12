
package goalplanner.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author linjokin
 */
public class UserTest {
    
    @Test
    public void getNameTest() {
        User user = new User("Pekka", "pekka");
        String name = user.getName();
        assertEquals("Pekka", name);
    }
    
    
}
