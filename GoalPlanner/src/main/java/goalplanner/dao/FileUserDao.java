
package goalplanner.dao;

import goalplanner.domain.User;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * Käyttäjien tiedostoon tallentamisesta ja tiedostosta hakemisesta vastaava luokka
 */
public class FileUserDao implements UserDao {
    
    private List<User> users;
    private String file;
    
    public FileUserDao(String file) {
        users = new ArrayList<>();
        this.file = file;
        load();
    }
    
    /**
     * Käyttäjien hakeminen tiedostosta
     */
    private void load() {
        try {
            Scanner reader = new Scanner(new File(file));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(";");
                User u = new User(parts[0], parts[1]);
                users.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Käyttäjien tallentaminen tiedostoon 
     */
    
    private void save() {
        try {
            FileWriter writer = new FileWriter(new File(file));
            for (User user : users) {
                writer.write(user.getName() + ";" + user.getUsername() + "\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Uuden käyttäjän luominen ja tallentaminen tiedostoon
     * 
     * @param user uusi käyttäjä
     * @return luotu käyttäjä
     */
    
    @Override
    public User create(User user) {
        users.add(user);
        save();
        return user;
    }
    
    /**
     * Metodi hakee käyttäjän käyttäjänimen perusteella
     * 
     * @param username käyttäjänimi jolla haetaan
     * 
     * @return löydetty käyttäjä tai null, jos käyttäjänimeä ei löytynyt 
     */
    @Override
    public User findByUsername(String username) {
        return users.stream().filter(u->u.getUsername().equals(username)).findFirst().orElse(null);
    }
    
    /**
     * Lista kaikista käyttäjistä
     * 
     * @return kaikki käyttäjät 
     */
    @Override
    public List<User> getAll() {
        return users;
    }

}
