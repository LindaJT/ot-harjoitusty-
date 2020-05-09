
package goalplanner.dao;

import goalplanner.domain.Goal;
import goalplanner.domain.User;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * Tavoitteiden tiedostoon tallentamisesta ja tiedostosta hakemisesta vastaava luokka
 */

public class FileGoalDao implements GoalDao {
    
    public List<Goal> goals;
    private String file;
    
    public FileGoalDao(String file, UserDao users) throws Exception {
        this.goals = new ArrayList<>();
        this.file = file;
        try {
            Scanner reader = new Scanner(new File(file));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(";");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String[] dateparts = parts[2].split("-");
                Boolean achieved = Boolean.parseBoolean(parts[3]);
                LocalDate goalDate = LocalDate.of(Integer.parseInt(dateparts[0]), Integer.parseInt(dateparts[1]), Integer.parseInt(dateparts[2]));
                User user = users.getAll().stream().filter(u->u.getUsername().equals(parts[4])).findFirst().orElse(null);
                Goal goal = new Goal(id, name, goalDate, achieved, user, parts[5]);
                goals.add(goal);
            }
        } catch (Exception e) {
            FileWriter writer = new FileWriter(new File(file));
            writer.close();
        }
    }
    
    /**
     * Tallentaa tavoitteet tiedostoon
     * @throws Exception virhe
     */
    private void save() throws Exception {
        try (FileWriter writer = new FileWriter(new File(file))) {
            for (Goal goal : goals) {
                writer.write(goal.getId() + ";" + goal.getName() + ";" + goal.getGoalDate() + ";" + goal.getAchieved() + ";" + goal.getUser().getUsername() + "\n");
            }
        }
    }    
    
    /**
     * Luo tavoitteelle id:n
     * 
     * @return tavoitteen id 
     */
    private int generateId() {
        return goals.size() + 1;
    }
    
    /**
     * Metodi palauttaa kaikki tiedostossa olevat tavoitteet
     * @return lista tavoitteista
     */
    public List<Goal> getAll() {
        return goals;
    }
    
    /**
     * Luo uuden tavoitteen ja tallentaa sen tiedostoon
     * 
     * @param goal luotava tavoite
     * 
     * @return luotu tavoite
     * 
     * @throws Exception virhe
     */
    
    public Goal create(Goal goal) throws Exception {
        goal.setId(generateId());
        goals.add(goal);
        save();
        return goal;
    }   
    
    /**
     * Vaihtaa tavoitteen tilan savutetuksi ja tallentaa tavoitteen tiedostoon
     * 
     * @param id tavoitteen id
     * 
     * @throws Exception virhe
     */
    
    public void setAchieved(int id) throws Exception {
        for (Goal goal : goals) {
            if (goal.getId() == id) {
                goal.setAchieved();
            }
        }
        save();
    }  
    
    public Goal findById(int id) {
        return goals.stream().filter(g->g.getId() == id).findFirst().orElse(null);
    }
}
