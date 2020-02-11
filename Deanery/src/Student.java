import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student extends uzytkownik{

    int pesel;
    List<Integer> przedmioty= new ArrayList<Integer>();
    List<Integer> oceny=new ArrayList<Integer>();;

    public Student(String login, String haslo){
        super(login,haslo);
    }

    public int Zaloguj() {
        przedmioty=new ArrayList<>();
        ObjectContainer db = Db4o.openFile("Wirtualny_Dziekanat");
        try {
            ObjectSet result = db.queryByExample(this);
            if(result.hasNext()) {
                Student student=(Student) result.next();
                this.ustawDane(student.pesel, student.imie, student.nazwisko, student.wydzial,
                        student.kierunek, student.stopienStudiow, student.nrSemestru);
                zalogowany=student.pesel;
            }
        } finally {
            db.close();
        }
        if(zalogowany!=0)
            System.out.println("Zalogowano " + login);
        else
            System.out.println("Nieprawidłowa nazwa użytkownika i/lub haslo ");
        return zalogowany;
    }

    @Override
    public void ZmienHaslo() {
        System.out.println("Podaj Pesel");
        Scanner in = new Scanner(System.in);
        int id_zmiana_hasla = Integer.parseInt(in.nextLine());
        System.out.println("Podaj nowe hasło");
        String nowe_haslo = in.nextLine();
        ObjectContainer db= Db4o.openFile("Wirtualny_Dziekanat");
        try {
            Student zmiana =new Student( login, null);
            zmiana.ustawDane(id_zmiana_hasla, null, null,null, null, 0, 0 );
            ObjectSet result=db.queryByExample(zmiana);
            if(result.hasNext()) {
                Student found = (Student) result.next();
                found.haslo = nowe_haslo;
                db.store(found);
                System.out.println("Hasło zmienione dla "+ login );
            }
            else
                System.out.println("Nieprawidłowy login lub ID");
        }
        finally {
            db.close();
        }
    }

    public void ustawDane(int pesel, String imie, String nazwisko, String wydzial, String kierunek, int stopienStudiow, int nrSemestru)
    {
        super.ustawDaneStudenta(imie, nazwisko, wydzial, kierunek, stopienStudiow, nrSemestru);
        this.pesel=pesel;
    }

    public String toString(int opcja)
    {
        if(opcja==1)
        {
            return "student, login: " + login + " haslo: " + haslo;
        }
        else if(opcja==2)
        {
            return "student::\nid: " + pesel + ",\nhaslo: " + haslo + ", \nimie: " + imie +",\nnazwisko: "+ nazwisko + ",\nwydzial: "
                    +  wydzial  + ",\nkierunek: "+ kierunek + ",\nstopien studiow: "+ stopienStudiow + ",\nSemestr: "+ nrSemestru;
        }
        else
        {
            return "student::  id: " + pesel + ", imie: " + imie +",nazwisko: "+ nazwisko;
        }

    }

    public void dodajPrzedmiot(Integer id){
        przedmioty.add(id);
    }

    public void dodajOcene(Integer id){
        oceny.add(id);
    }

}
