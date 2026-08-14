package client;

import data.DataLoader;
import model.Aventura;
import model.Rezervare;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        final int PORT_NUMBER=2222;
        try(Socket socket=new Socket("localhost",PORT_NUMBER);
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out=new PrintWriter(socket.getOutputStream(),true)){

            String[] cereri = {"CATARARE", "DRUMETIE", "STOP"}; // exemplu

            for (String cerere : cereri) {
                out.println(cerere);
                System.out.println("[CLIENT] Am trimis: " + cerere);
                String raspuns = in.readLine();
                System.out.println("[CLIENT] Raspuns: " + raspuns);
                if ("byee".equals(raspuns)) break;
            }
        } catch (Exception e) {
            System.err.println("eroare la client socket "+e.getMessage());
        }

    }
}
