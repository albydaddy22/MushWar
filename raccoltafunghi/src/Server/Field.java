package Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Field{
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Mushroom> mushrooms = new ArrayList<>();
    
    public Field(){
        new Thread(new CreateMushrooms(this)).start();
        new Thread(new DestroyMushrooms(this)).start();
    }
    
    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void setPlayers(ArrayList<Player> players){
        this.players = players;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public ArrayList<Mushroom> getMushrooms(){
        return mushrooms;
    }

    public void setMushrooms(ArrayList<Mushroom> mushrooms){
        this.mushrooms = mushrooms;
    }

    public void addMushroom(Mushroom mushroom){
        mushrooms.add(mushroom);
    }
    
    public boolean userIsPresent(String str){
        for(Player player: players){
            if(player.getUser().equals(str)) return true;
        }
        
        return false;
    }
    
    public Player findPlayer(String userID){
        for(Player p: players){
            if(userID.equals(p.getUserID())) return p;
        }        
        return null;
    }
    
    public int posIsOccupied(Position pos){
        for(Player p: players){
            if(p.getPos().equals(pos)) return -1;
        }
            
        for(Mushroom m: mushrooms){
            if(m.getPos().equals(pos)){
                mushrooms.remove(m);
                return m.getValue();
            }
        }
        
        return -2;
    }
}