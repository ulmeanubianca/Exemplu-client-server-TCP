package model;

public class Rezervare {
    private final String idRezervare;
    private int codAventura;
    private int nrLocuriRezervate;

    public Rezervare(String idRezervare, int codAventura, int nrLocuriRezervate) {
        this.idRezervare = idRezervare;
        this.codAventura = codAventura;
        this.nrLocuriRezervate = nrLocuriRezervate;
    }

    public String getIdRezervare() {
        return idRezervare;
    }

    public int getCodAventura() {
        return codAventura;
    }

    public void setCodAventura(int codAventura) {
        this.codAventura = codAventura;
    }

    public int getNrLocuriRezervate() {
        return nrLocuriRezervate;
    }

    public void setNrLocuriRezervate(int nrLocuriRezervate) {
        this.nrLocuriRezervate = nrLocuriRezervate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("model.Rezervare{");
        sb.append("idRezervare=").append(idRezervare);
        sb.append(", codAventura=").append(codAventura);
        sb.append(", nrLocuriRezervate=").append(nrLocuriRezervate);
        sb.append('}');
        return sb.toString();
    }
}
