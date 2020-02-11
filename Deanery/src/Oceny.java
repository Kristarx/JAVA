public class Oceny {
    public int id;
    public String nazwa;
    public double wartosc;
    public int waga;
    public int idStudenta;
    public int idPrzedmiotu;

    public Oceny(int id, String nazwa, double wartosc, int waga, int idStudenta, int idPrzedmiotu) {
        this.id = id;
        this.nazwa = nazwa;
        this.wartosc = wartosc;
        this.waga = waga;
        this.idStudenta = idStudenta;
        this.idPrzedmiotu = idPrzedmiotu;
    }

    public String toString() {
        return "id: " + id + ", nazwa: " + nazwa + ", wartosc oceny: " + wartosc + ", waga: " + waga +", id studenta: "
                + idStudenta + ", id przedmiotu: " + idPrzedmiotu;
    }
}
