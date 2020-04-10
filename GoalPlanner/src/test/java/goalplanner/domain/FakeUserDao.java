
package goalplanner.domain;

import goalplanner.dao.UserDao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author linjokin
 */
public class FakeUserDao implements UserDao {
    List<User> users = new ArrayList<>();

    public FakeUserDao() {
        users.add(new User("Testi", "testi"));
    }
    
    @Override
    public User findByUsername(String username) {
        return users.stream().filter(u->u.getUsername().equals(username)).findFirst().orElse(null);
    }
    
    @Override
    public User create(User user) {
        users.add(user);
        return user;
    } 
    
    @Override
    public List<User> getAll() {
        return users;
    }
    
}
