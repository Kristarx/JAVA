import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.util.ArrayList;
import java.util.List;

public class BazaZapytania {

    List<Student> students;
    List<Przedmiot> przedmioty;
    List<Oceny> oceny;
    boolean wczytane=false;

    //########################### DLA PROWADZACEGO ###################################
    public void wczytajPrzedmioty(int id) {
        przedmioty=new ArrayList<Przedmiot>();
        Przedmiot przedmiot;
        Przedmiot znajdzPrzedmiot = new Przedmiot(0, null,0,0);
        ObjectContainer db = Db4o.openFile("Wirtualny_Dziekanat");
        try
        {
            ObjectSet result = db.queryByExample(znajdzPrzedmiot);
            while (result.hasNext())
            {
                przedmiot = (Przedmiot) result.next();
                for (int i = 0; i <przedmiot.prowadzacy.size() ; i++)
                {
                    if(przedmiot.prowadzacy.get(i)==id)
                        przedmioty.add(przedmiot);
                }
            }
        }
        finally
        {
            db.close();
        }
    }

    public void wczytajStudentowPoIdPrzedmiotu(int id) {
        students=new ArrayList<Student>();
        Student student;
        Student znajdzStudenta = new Student(null, null);
        ObjectContainer db = Db4o.openFile("Wirtualny_Dziekanat");
        try
        {
            ObjectSet result = db.queryByExample(znajdzStudenta);
            while (result.hasNext())
            {
                student=(Student) result.next();
                for (int i = 0; i <student.przedmioty.size() ; i++)
                {
                    if(student.przedmioty.get(i)==id)
                    {
                        students.add(student);
                        System.out.println(student.toString(3));
                    }
                }
            }
            //System.out.println("ilosc przedmiotow: "+students.size());
        }
        finally
        {
            db.close();
        }
    }

    public void wczytajOcenyStudentaZPrzedmiotu(int studentId, int przedmiotId) {
        oceny=new ArrayList<Oceny>();
        Oceny znajdzOceny = new Oceny(0, null, 0, 0,studentId,przedmiotId);
        ObjectContainer db = Db4o.openFile("Wirtualny_Dziekanat");
        try
        {
            ObjectSet result = db.queryByExample(znajdzOceny);
            while (result.hasNext())
            {
                oceny.add((Oceny) result.next());
                System.out.println(oceny.get(oceny.size()-1).toString());
            }
        }
        finally
        {
            db.close();
        }
    }

    public void podajSredniaStudenta(int studentId, int przedmiotId)
    {
        float stopnie=0;
        float wagi=0;
        float srednia=0;
        Oceny ocena;
        Oceny znajdzOceny = new Oceny(0, null, 0, 0,studentId,przedmiotId);
        ObjectContainer db = Db4o.openFile("Wirtualny_Dziekanat");
        try
        {
            ObjectSet result = db.queryByExample(znajdzOceny);
            while (result.hasNext())
            {
                ocena=(Oceny) result.next();
                stopnie+=(ocena.wartosc*ocena.waga);
                wagi+=ocena.waga;
            }
            srednia=stopnie/wagi;
            System.out.println("Srednia = "+srednia);
        }
        finally {
            db.close();
        }
    }

    public void dodajOcene(int studentId, int przedmiotId, double stopien, int waga, String nazwa)
    {
        int noweID = 0;
        ObjectContainer db = Db4o.openFile("Wirtualny_Dziekanat");
        try
        {
            Oceny oceny =  new Oceny(0, null, 0, 0,0,0);
            ObjectSet result=db.queryByExample(oceny);
            while(result.hasNext())
            {
                result.next();
                noweID++;
            }
            noweID++;
            oceny = new Oceny(noweID, nazwa, stopien, waga,studentId,przedmiotId);
            db.store(oceny);
            System.out.println("Dodano ocene!");
        }
        finally
        {
            db.close();
        }
    }
    //########################### DLA STUDENTA ###################################
    public void wczytajPrzedmiotyDlaStudenta(int id)
    {
        przedmioty = new ArrayList<Przedmiot>();
        Przedmiot przedmiot;
        Przedmiot znajdzPrzedmiot = new Przedmiot(0, null,0,0);
        ObjectContainer db = Db4o.openFile("Wirtualny_Dziekanat");
        try
        {
            ObjectSet result = db.queryByExample(znajdzPrzedmiot);
            while (result.hasNext())
            {
                przedmiot=(Przedmiot) result.next();
                for (int i = 0; i <przedmiot.students.size() ; i++)
                {
                    if(przedmiot.students.get(i)==id)
                        przedmioty.add(przedmiot);
                }
            }
        }
        finally
        {
            db.close();
        }
    }

    //########################### DLA PRACOWNIKA DZIEKANATU ###################################
    public static void wypiszWszystkichProwadzacych()
    {
        System.out.println("\nPROWADZACY\n");
        ObjectContainer db = Db4o.openFile("Wirtualny_Dziekanat");
        try
        {
            Prowadzacy prowadzacy = new Prowadzacy(null, null);
            ObjectSet result;
            result = db.queryByExample(prowadzacy);
            while (result.hasNext())
            {
                prowadzacy = (Prowadzacy) result.next();
                System.out.println(prowadzacy.toString(3));
            }
        }
        finally
        {
            db.close();
        }
    }
    public static void wypiszWszystkichStudentow()
    {
        System.out.println("\nSTUDENCI\n");
        ObjectContainer db = Db4o.openFile("Wirtualny_Dziekanat");
        try
        {
            Student student = new Student(null, null);
            ObjectSet result;
            result = db.queryByExample(student);
            while (result.hasNext())
            {
                student = (Student) result.next();
                System.out.println(student.toString(3));
            }
        }
        finally
        {
            db.close();
        }
    }

    public static void wypiszWszystkiePrzedmioty() {
        System.out.println("\nPRZEDMIOTY\n");
        ObjectContainer db = Db4o.openFile("Wirtualny_Dziekanat");
        try
        {
            Przedmiot przedmiot = new Przedmiot(0, null, 0, 0);
            ObjectSet result;
            result = db.queryByExample(przedmiot);
            while (result.hasNext())
            {
                System.out.println(result.next());
            }
        }
        finally
        {
            db.close();
        }
    }

    public static void wypiszWszystkieOceny()
    {
        System.out.println("\nOCENY\n");
        ObjectContainer db = Db4o.openFile("Wirtualny_Dziekanat");
        try
        {
            Oceny oceny = new Oceny(0, null, 0, 0, 0, 0);
            ObjectSet result;
            result = db.queryByExample(oceny);
            while (result.hasNext())
            {
                System.out.println(result.next());
            }
        }
        finally
        {
            db.close();
        }
    }

    public void dodajStudenta(Student student) {
        ObjectContainer db = Db4o.openFile("Wirtualny_Dziekanat");
        try
        {
            Student stud = new Student(student.login, null);
            ObjectSet result = db.queryByExample(stud);
            if (!result.hasNext())
            {
                db.store(student);
                System.out.println("Dodano Studenta!");
            }
            else
            {
                System.out.println("Login zajęty!");
            }
        }
        finally
        {
            db.close();
        }
    }
}
