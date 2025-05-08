package Server;

import java.util.UUID;

public class Player{
    private String user, userID;
    private int score = 0;
    private Position pos;
    private long lastMov = -1;

    public Player(){}
    
    public Player(String user, Position pos){
        setUser(user);
        setUserID(makeUserID());
        setPos(pos);
    }
    
    public String getUser(){
        return user;
    }
    
    public String getUserID(){
        return userID;
    }
    
    public int getScore(){
        return score;
    }
    
    public Position getPos(){
        return pos;
    }
    
    public long getLastMov(){
        return lastMov;
    }
    
    public void setUser(String user){
        this.user = user;
    }
    
    public void setUserID(String userID){
        this.userID = userID;
    }
    
    public void setScore(int score){
        this.score = score;
    }
    
    public void setPos(Position pos){
        this.pos = pos;
    }
    
    public void setLastMov(long lastMov){
        this.lastMov = lastMov;
    }
    
    private String makeUserID(){
        return UUID.randomUUID().toString();
    }
    
    @Override
    public String toString(){
        return user + ";" + userID + ";" + score + ";" + pos;
    }
}
