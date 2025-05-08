package Server;

import java.util.Iterator;

public class DestroyMushrooms implements Runnable{
    private static final int MaxM = 700;
    private static final int MinM = 350;
    
    private Field field;
    
    public DestroyMushrooms(Field field){
        this.field = field;
    }
    
    @Override
    public void run(){
        do{
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }  
                
            synchronized(field){
                if(field.getMushrooms().size() > MinM){
                    Iterator<Mushroom> it = field.getMushrooms().iterator();
                    
                    while(it.hasNext()){
                        Mushroom m = it.next();
                        
                        if (m.getTTL() == 0) it.remove();
                        else  m.setTTL(m.getTTL() - 1);
                    } 
                }
            }
        }while(true);
    }
}