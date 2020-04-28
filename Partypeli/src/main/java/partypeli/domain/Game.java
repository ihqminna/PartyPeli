package partypeli.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
//import partypeli.dao.TaskDao;
import partypeli.dao.TaskFileDao;

/**
 * Luokka on yksi pelikerta, joka sisältää pelaajat ja muut pelin tiedot 
 * 
 */

public class Game {
    public ArrayList<Player> players;
    public ArrayList<Task> tasks;
    public int difficulty;
    public int drinkingAmount;
    public int turn;
    public TaskFileDao taskdao;
    public Random rnd;
        
    public Game() {
        this.players = new ArrayList();
        this.tasks = new ArrayList();
        this.turn = 0;
        this.rnd = new Random();
        this.taskdao = new TaskFileDao();
    }
    
    /**
     * Metodi lisää pelaajan peliin.
     * 
     * @param Pelaaja
     */
    
    public void addPlayer(Player player) {
        this.players.add(player);
    }
    
    public void setDifficulty(int dif) {
        this.difficulty = dif;
    }
    
    public void setDrinkingAmount(int drink) {
        this.drinkingAmount = drink;
    }
    
    /**
     * Metodi hakee seuraavan pelaajan nimen.
     * 
     * @return seuraavan pelaajan nimi merkkijonona
     */
    
    public String getNextPlayerName() {
        String player = players.get(this.turn).getName();
        
        if (this.turn < (players.size() - 1)) {
            this.turn += 1;
        } else {
            this.turn = 0;
        }
        return player;
    }
    
    /**
     * Metodi hakee kaikkien pelaajien nimet yhdeksi merkkijonoksi.
     * 
     * @return kaikkien pelaajien nimet 
     */
    
    public String getNames() {
        String names = "";
        for (int i = 0; i < players.size(); i++) {
            names = names + players.get(i).getName();
            if (i < (players.size() - 1)) {
                names = names + ", ";
            }
        }
        return names;
    }
    
    public int numberOfPlayers() {
        return players.size();
    }
    
    public int numberOfTasks() {
        return this.tasks.size();
    }
    
    /**
     * Metodi poistaa kaikki pelaajat.
     */
                
    public void deletePlayers() {
        players.clear();
    }
    
    public int getDifficulty() {
        return this.difficulty;
    }
    
    public int getDrinkingAmount() {
        return this.drinkingAmount;
    }
    
    /**
     * Metodi valmistelee peliin tehtävä/kysymyslistan.
     */
    
    public void makeTaskList() {
        this.tasks.addAll(taskdao.getQuestions());
        
        for (int i = 0; i < this.tasks.size(); i++) {
            Task task = this.tasks.get(i);
            if (task.getDifficulty() > this.difficulty) {
                this.tasks.remove(i);
                i--;
            }
        }
        
        //add with drinking tasks separately

    }
    
    /*public void makeTaskListWODrinking() {
        this.tasks.addAll(taskdao.getQuestions());
    }
    
    public void makeTaskListWDrinking() {
        ArrayList<Task> questions = new ArrayList();
        ArrayList<Task> drinkingTasks = new ArrayList();
        //questions = taskdao.getQuestions();
        drinkingTasks = taskdao.getDrinkingTasks();
        Collections.shuffle(questions);
        Collections.shuffle(drinkingTasks);
        
        if (this.drinkingAmount == 1) {
            makeTaskListWLittleDrinking(questions, drinkingTasks);
        } else if (this.drinkingAmount == 2) {
            makeTaskListWLotDrinking(questions, drinkingTasks);
        }
    }
    
    public void makeTaskListWLittleDrinking(ArrayList<Task> questions, ArrayList<Task> drinkingTasks) {
        int thirdOfQuestions = questions.size();
        if (thirdOfQuestions < drinkingTasks.size()){
            drinkingTasks = new ArrayList(drinkingTasks.subList(0, thirdOfQuestions));
        } else if (thirdOfQuestions > drinkingTasks.size()) {
            
        }
        this.tasks.addAll(questions);
        this.tasks.addAll(drinkingTasks);
    }
    
    public void makeTaskListWLotDrinking(ArrayList<Task> questions, ArrayList<Task> drinkingtasks) {
        
    }*/
    
    /**
     * Metodi hakee satunnaisen tehtävän/kysymyksen.
     * 
     * @return satunnainen tehtävä tai kysymys
     */
    
    public String getRandomTask() {
        String task = tasks.get(rnd.nextInt(tasks.size())).getTask();
        return task;
    }
}