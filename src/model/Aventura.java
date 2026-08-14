package model;

public class Aventura {
    private int codAventura;
    private String denumire;
    private double tarif;
    private int locuriDisponibile;

    public Aventura(int codAventura, String denumire, double tarif, int locuriDisponibile) {
        this.codAventura = codAventura;
        this.denumire = denumire;
        this.tarif = tarif;
        this.locuriDisponibile = locuriDisponibile;
    }

    public Aventura() {
    }

    public int getCodAventura() {
        return codAventura;
    }

    public void setCodAventura(int codAventura) {
        this.codAventura = codAventura;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    public int getLocuriDisponibile() {
        return locuriDisponibile;
    }

    public void setLocuriDisponibile(int locuriDisponibile) {
        this.locuriDisponibile = locuriDisponibile;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("model.Aventura{");
        sb.append("codAventura=").append(codAventura);
        sb.append(", denumire='").append(denumire).append('\'');
        sb.append(", tarif=").append(tarif);
        sb.append(", locuriDisponibile=").append(locuriDisponibile);
        sb.append('}');
        return sb.toString();
    }
}
