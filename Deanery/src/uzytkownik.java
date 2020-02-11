public abstract  class uzytkownik {
    String login;
    String haslo;
    String imie;
    String nazwisko;
    String wydzial;
    String kierunek;
    int stopienStudiow;
    int nrSemestru;
    int zalogowany=0;

    public uzytkownik(String login, String haslo){
        this.login=login;
        this.haslo=haslo;
    };

    public void ustawDaneStudenta(String imie, String nazwisko, String wydzial,
                                  String kierunek, int stopienStudiow, int nrSemestru){
        this.imie=imie;
        this.nazwisko=nazwisko;
        this.wydzial=wydzial;
        this.kierunek=kierunek;
        this.stopienStudiow=stopienStudiow;
        this.nrSemestru=nrSemestru;
    };

    public void ustawDaneProwadzacego(String imie, String nazwisko){
        this.imie=imie;
        this.nazwisko=nazwisko;
    };

    public void Wyloguj(){
        zalogowany=0;
    };

    public abstract int Zaloguj();

    public abstract void ZmienHaslo();
}
