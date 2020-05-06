
package goalplanner.dao;

import goalplanner.domain.Goal;
import java.util.List;

/**
 * Rajapinta, jonka toteuttava luokka vastaa käyttäjän tavoitteiden luomisesta, muokkaamisesta ja hakemisesta 
 * 
 */


public interface GoalDao {
    
    /**
     * Metodi luo käyttäjälle uuden tavoitteen
     * 
     * @param goal uusi tavoite
     * @return luoto tavoite
     * @throws Exception virhe
     */
    
    Goal create(Goal goal) throws Exception;
    
    /**
     * Metodi hakee kaikki tavoitteet
     * 
     * @return Lista kaikista tavoitteista 
     */
    
    List<Goal> getAll();
    
    /**
     * Metodi asettaa haetun tavoitteen saavutetuksi
     * 
     * @param id asetettavan tavoitteen id
     * @throws Exception virhe
     */
    
    void setAchieved(int id) throws Exception;
    
    Goal findById(int id);
    
    
}
