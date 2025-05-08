package Server;

public class Position{
    private int x;
    private int y;
    
    
    public Position(int x, int y){
        setX(x);
        setY(y);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }
    
    @Override
    public boolean equals(Object o){
        Position p = (Position)o;
        
        if(p.getX() == x && p.getY() == y) return true;
        return false;
    }
    
    @Override
    public String toString(){
        return "(" + x + "," + y + ")";
    }
}