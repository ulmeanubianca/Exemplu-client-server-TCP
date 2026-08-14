package server;

import data.DataLoader;
import model.Aventura;
import model.Rezervare;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) {
        DataLoader loader=new DataLoader();
        List<Aventura> aventuri=loader.citireAventuri("data/aventuri.json");

        final int PORT_NUMBER=2222;
        final int THREAD_COUNT = 10;

        var threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
        try(ServerSocket serverSocket=new ServerSocket(PORT_NUMBER)){
            while(true){
                Socket clientSocket=serverSocket.accept();
                threadPool.submit(new ClientHandler(clientSocket,aventuri));
            }
        } catch (Exception e) {
            System.err.println("eroare la server socket "+e.getMessage());
        }finally {
            threadPool.shutdown();
        }
    }
}
