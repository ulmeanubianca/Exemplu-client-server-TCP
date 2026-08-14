package data;

import model.Aventura;
import model.Rezervare;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public List<Aventura> citireAventuri(String cale){
        List<Aventura> aventuri = new ArrayList<>();
        try(FileReader fisier=new FileReader(cale)){
            JSONArray jsonArray=new JSONArray(new JSONTokener(fisier));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                aventuri.add(new Aventura(
                        jsonObject.getInt("cod_aventura"),
                        jsonObject.getString("denumire"),
                        jsonObject.getDouble("tarif"),
                        jsonObject.getInt("locuri_disponibile")
                ));
            }
        } catch (Exception e) {
            System.err.println("eroare citire json "+e.getMessage());
        }
        return aventuri;
    }
    public List<Rezervare> citireRezervari(String cale){
        List<Rezervare> rezervari=new ArrayList<>();
        try(BufferedReader bf=new BufferedReader(new FileReader(cale))){
            String linie;
            linie=bf.readLine();//pentru antet
            while((linie=bf.readLine())!=null){
                String[] tokens =linie.split(",");
                rezervari.add(new Rezervare(
                        tokens[0],
                        Integer.parseInt(tokens[1].trim()),
                        Integer.parseInt(tokens[2].trim())
                ));
            }
        }
        catch (Exception e) {
        System.err.println("eroare citire txt "+e.getMessage());
    }
        return rezervari;
    }
}
