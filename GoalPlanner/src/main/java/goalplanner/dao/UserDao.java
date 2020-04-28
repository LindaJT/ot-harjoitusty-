
package goalplanner.dao;

import goalplanner.domain.User;
import java.util.List;

/**
 * Rajapinta, jonka toteuttava luokka vastaa käyttäjien luomisesta ja hakemisesta
 * 
 */

public interface UserDao {
    
    /**
     * Metodi vastaa uuden käyttäjän luomisesta
     * 
     * @param user luotava käyttäjä
     * 
     * @return luotu käyttäjä
     */
    
    User create(User user);
    
    /**
     * Metodi hakee käyttäjän käyttäjänimen perusteella
     * 
     * @param username haettavan käyttäjän käyttäjänimi
     * 
     * @return löytynyt käyttäjä tai null
     */
    
    User findByUsername(String username);
    
    /**
     * Metodi hakee kaikki käyttäjät
     * 
     * @return Lista kaikista käyttäjistä 
     */
    
    List<User> getAll();
    
}
