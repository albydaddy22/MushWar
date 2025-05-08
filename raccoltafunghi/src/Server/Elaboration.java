package Server;

import java.net.Socket;
import java.util.Random;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class Elaboration implements Runnable {
    private Socket client;
    private Field field;
    
    public Elaboration(Socket client, Field field){
        this.client = client;
        this.field = field;
    }

    @Override
    public void run(){
        BufferedReader in;
        BufferedWriter out;
        
        String req = null;
        String res = null;
        
        try{
            // Inizializzazione 
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            
            // Lettura
            req = in.readLine();
            System.out.println("Richiesta Client: " + (req == null ? "Errore" : req));
            
            // Elaborazione
            res = elaborazione(req);
            
            // Scrittura
            out.write(res);
            out.newLine();
            out.flush();
                        
            // Chiusura connessione
            in.close();
            out.close();
            client.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally{
            // Debug
            System.out.println("Elaborazione partita: " + Thread.currentThread().getName());
            System.out.println("Risposta Server: " + (res == null ? "Errore" : res));
        }
    }
    
    private String elaborazione(String req){
        String res = "";
        
        if(req.length() < 2){
            return "KO";
        }
        
        switch(req.substring(0, 2)){
            case "LG":
                res = openSession(req.substring(2));
                break;
                
            case "MO":
                res = movePlayer(req.substring(2));
                break;
                
            case "RS":
                res = printScoreBoard(req.substring(2));
                break;
                
            case "RT":
                res = takeState(req.substring(2));
                break;
                
            case "KO":
                res = closeSession(req.substring(2));
                break;
            default:
                res = "KO";
        }
        
        return res;
    }
    
    public String openSession(String req){
        if(req.isEmpty()) return "KO";
        
        synchronized(field){
            if(field.userIsPresent(req)) return "KO";
            
            Player newP;
            Position pos;
            int x, y;
            
            do{
                Random random = new Random();
                x = random.nextInt(100);
                y = random.nextInt(70);
                pos = new Position(x, y);
            }while(field.posIsOccupied(pos) != -2);
            
            newP = new Player(req, pos);
            field.addPlayer(newP);
            
            return "OK" + newP.getUserID(); 
        }
    }
    
    public String movePlayer(String req){ 
        String[] info = req.split("\\|");
        if(info.length < 2) return "KO";
        
        synchronized(field){
            Player player = field.findPlayer(info[1]);
            
            if(player == null ||System.currentTimeMillis() - player.getLastMov() < 200) return "KO";
            
            int Mx = 99, My = 69;
            int x = player.getPos().getX();
            int y = player.getPos().getY();
            
            Position newPos = null;
            String direction = info[0];
            
            switch(direction){
                case "NN":
                    if(y > 0) y = y - 1;
                    else y = My;
                    
                    newPos = new Position(x, y);
                    break;
                case "NE":
                    if(y > 0) y = y - 1;
                    else y = My;
                
                    if(x < Mx) x = x + 1;
                    else x = 0;
                    
                    newPos = new Position(x, y);
                    break;
                case "NW":
                    if(y > 0) y = y - 1;
                    else y = My;

                    if(x > 0) x = x - 1;
                    else x = Mx;
                    
                    newPos = new Position(x, y);
                    break;
                case "SS":
                    if(y < My) y = y + 1;
                    else y = 0;
                    
                    newPos = new Position(x, y);
                    break;
                case "SE":
                    if(y < My) y = y + 1;
                    else y = 0;
                    
                    if(x < Mx) x = x + 1;
                    else x = 0;
                    
                    newPos = new Position(x, y);
                    break;
                case "SW":
                    if(y < My) y = y + 1;
                    else y = 0;
                    
                    if(x > 0) x = x - 1;
                    else x = Mx;
                    
                    newPos = new Position(x, y);
                    break;
                case "EE":
                    if(x < Mx) x = x + 1;
                    else x = 0;
                    
                    newPos = new Position(x, y);
                    break;
                case "WW":
                    if(x > 0) x = x - 1;
                    else x = Mx;
                    
                    newPos = new Position(x, y);
                    break;
                default:
                    return "KO";
            }
            
            if(newPos == null) return "KO";
            
            int value = field.posIsOccupied(newPos);
            
            if(value == -1){
                player.setLastMov(System.currentTimeMillis());
                return "KO"; 
            }else if(value != -2){
                player.setScore(player.getScore() + (value == 0 ? -10 : value));
                player.setPos(newPos);
                player.setLastMov(System.currentTimeMillis());
                return String.valueOf(value);
            }else{
                player.setPos(newPos);
                return "OK";
            }
        }
    }
    
    public String printScoreBoard(String req){   
        synchronized(field){
            if(field.findPlayer(req) == null) return "KO";
            
            StringBuilder score = new StringBuilder();
            
            for(Player p: field.getPlayers()){
                score.append(p.getUser()).append("|").append(p.getScore()).append("|");
            }
            
            if(score.length() > 0){
                score.setLength(score.length() - 1);
            }
            
            return score.toString();
        }
    }
    
    public String takeState(String req){    
        synchronized(field){
            if(field.findPlayer(req) == null) return "KO";
            
            String state = "";
            
            for(Player p: field.getPlayers()){
                state+=p.getUser()+"|"+p.getPos().getX()+"|"+p.getPos().getY()+"|";
            }
            
            state+="|";
                
            for(Mushroom m: field.getMushrooms()){
                state+=m.getValue()+"|"+m.getPos().getX()+"|"+m.getPos().getY()+"|";
            }
                
            return state.substring(0, state.length()-1);
        }
    }
    
    public String closeSession(String req){
        synchronized(field){
            Player p = field.findPlayer(req);
            if(p == null) return "KO";

            field.getPlayers().remove(p);
                
            return "OK";
        }
    }
}