package Server;

public class Mushroom{
    private int value;
    private int TTL;
    private Position pos;

    public Mushroom(){}

    public Mushroom(int value, Position pos, int TTL){
        setValue(value);
        setPos(pos);
        setTTL(TTL);
    }
    
    public int getValue(){
        return value;
    }
    
    public Position getPos(){
        return pos;
    }

    public int getTTL(){
        return TTL;
    }
    
    public void setValue(int value){
        this.value = value;
    }
    
    public void setPos(Position pos){
        this.pos = pos;
    }
    
    public void setTTL(int TTL){
        this.TTL = TTL;
    }
    
    public boolean equals(Object o){
        Mushroom m = (Mushroom)o;
        
        if(m.getPos().equals(pos)) return true;
        return false;
    }

}
