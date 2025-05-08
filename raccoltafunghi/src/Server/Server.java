package Server;

import java.net.*;
import java.io.*;
import java.util.Random;

public class Server{    
    private static Field field;
    
    public static void main(String args[]){
        ServerSocket socket;
        field = new Field();
        
        // Apertura socket
        try{
            socket = new ServerSocket(1775);
        }catch(Exception e){
            System.out.println("APERTURA ServerSocket FALLITA!\nErrore: " + e.getMessage());
            return;
        }
        
        // Attivazione servizio
        while(true){
            // Ricezione richiesta
            try{
                Socket client = socket.accept();
                String client_name = client.getRemoteSocketAddress().toString().substring(1);
                System.out.println("---------------------------------------------------------------------------");
                System.out.println("Accettata connessione da: " + client_name);
                
                new Thread(new Elaboration(client, field), "Worker-" + client_name).start();
            }catch(Exception e){
                System.out.println("COMUNICAZIONE FALLITA!\nErrore: " + e.getMessage());
            }
        }
    }
}