import data.DataLoader;
import model.Aventura;
import model.Rezervare;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static List<Aventura>aventuri=new ArrayList<>();
    public static List<Rezervare>rezervari=new ArrayList<>();

    static void salveazaTXT(){
        try(PrintWriter pw=new PrintWriter("venituri.txt")){
            var rezervariDupaCodAventura=rezervari.stream().
                    collect(Collectors.groupingBy(Rezervare::getCodAventura));
            aventuri.sort((a1,a2)->{
                        String d1=a1.getDenumire();
                        String d2=a2.getDenumire();
                        return d1.compareTo(d2);
                    });
            Map<Integer, Integer> totalLocuriPeAventura = rezervari.stream()
                    .collect(Collectors.groupingBy(Rezervare::getCodAventura,
                            Collectors.summingInt(Rezervare::getNrLocuriRezervate)));
            for (Aventura a : aventuri) {
                int totalLocuri = totalLocuriPeAventura.getOrDefault(a.getCodAventura(), 0);
                double venit = totalLocuri * a.getTarif();
                pw.println(a.getDenumire() + "," + totalLocuri + "," + venit);
            }
        } catch (Exception e) {
            System.err.println("eroare salvare txt "+e.getMessage());
        }
    }
    public static void main(String[] args) {
        DataLoader loader=new DataLoader();
        aventuri=loader.citireAventuri("data/aventuri.json");
        rezervari=loader.citireRezervari("data/rezervari.txt");

        System.out.println("\nCerinta 1 :");
        aventuri.stream().filter(a->a.getLocuriDisponibile()>=20).forEach(System.out::println);

        salveazaTXT();
        System.out.println("\nFisierul txt a fost salvat!");

        Map<Integer, Integer> totalRezervatPeAventura = rezervari.stream()
                .collect(Collectors.groupingBy(model.Rezervare::getCodAventura, // CORECTAT: cod aventura, nu id rezervare
                        Collectors.summingInt(model.Rezervare::getNrLocuriRezervate)));

        System.out.println("\nCerinta 2 :");
        for (model.Aventura a : aventuri) {
            int rezervate = totalRezervatPeAventura.getOrDefault(a.getCodAventura(), 0);
            int ramase = a.getLocuriDisponibile() - rezervate;
            if (ramase >= 5) {
                System.out.println(a.getCodAventura() + ", " + a.getDenumire() + ", " + ramase);
            }
        }
    }
}
