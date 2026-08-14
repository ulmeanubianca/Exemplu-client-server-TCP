package server;

import model.Aventura;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable{
    private final Socket clientSocket;
    private final List<Aventura> aventuri;

    public ClientHandler(Socket clientSocket, List<Aventura> aventuri) {
        this.clientSocket = clientSocket;
        this.aventuri = aventuri;
    }
    @Override
    public void run() {
        try(BufferedReader in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out=new PrintWriter(clientSocket.getOutputStream(),true)){
            String denumire;
            long threadId = Thread.currentThread().getId();
            while ((denumire=in.readLine())!=null){
                if("STOP".equalsIgnoreCase(denumire)){
                    System.out.println("[Thread "+threadId+"] Clientul a cerut STOP. Se inchide conexiunea.");
                    out.println("byee");
                    break;
                }
                System.out.println("[Thread " + threadId + "] Serverul a primit cerere pentru: " + denumire);
                Thread.sleep(2000);
                boolean gasit=false;
                for(Aventura aventura: aventuri){
                    if(denumire.equals(aventura.getDenumire())) {
                        out.println(aventura.getLocuriDisponibile());
                        System.out.println("[Thread " + threadId + "] Serverul raspunde: " + aventura.getLocuriDisponibile());
                        gasit=true;
                        break;
                    }
                }
                if(!gasit){
                    out.println("nu exista");
                }
            }
        } catch (Exception e) {
            System.err.println("eroare procesare cerere"+e.getMessage());
        }finally {
            try{
                clientSocket.close();
            }catch (Exception ex){}
        }
    }
}
