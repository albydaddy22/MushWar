package Server;

import java.util.Random;

public class CreateMushrooms implements Runnable{
    public static final int MaxM = 700;
    public static final int MinM = 350;
    
    private Field field;
    
    public CreateMushrooms(Field field){
        this.field = field;
    }
    
    @Override
    public void run(){
        Random random = new Random();
        Position pos;
        int x, y, value, TTL = 0;
        
        do{
            try{
                Thread.sleep(19);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
            
            synchronized (field) {
                if(field.getMushrooms().size() < MaxM){
                    do{
                        x = random.nextInt(100);
                        y = random.nextInt(70);
                    }while(field.posIsOccupied(new Position(x, y)) != -2);
                    
                    value = random.nextInt(4);
                    
                    switch(value){
                        case 0 -> TTL = 20;
                        case 1 -> TTL = 10;
                        case 2 -> TTL = 5;
                        case 3 -> TTL = 2;
                    }
                    
                    field.addMushroom(new Mushroom(value, new Position(x, y), TTL));
                }
            }
        }while(true);
    }
}
