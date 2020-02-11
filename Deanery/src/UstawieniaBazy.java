import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.util.Scanner;

public class UstawieniaBazy {

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        while (true)
        {
            System.out.println("\n*******************************************************\n" + "Wybierz dzialanie\n1.Dodaj do bazy danych\n2.Wypisz wszystkie dostepne dane\n3.Usuń wszystkie dostepne dane\n4.Zamknij program\nCo wybierasz? ");
            int option = Integer.parseInt(in.nextLine());
            switch (option) {
                case 1: UstawieniaBazy.dodaj(); break;
                case 2: UstawieniaBazy.wypiszWszystko(); break;
                case 3: UstawieniaBazy.usunWszystko(); break;
                case 4: System.exit(0);
                default: System.out.println("Cos poszlo nie tak, sprobuj jeszcze raz"); break;
            }
        }
    }

    public static void dodaj(){
        System.out.println("\nDodawanie...\n");
        ObjectContainer db= Db4o.openFile("Wirtualny_Dziekanat");
        try {
            Prowadzacy prowadzacy1 = new Prowadzacy("anna", "anna1");
            prowadzacy1.ustawDane(11,"Anna", "Suchenia");
            db.store(prowadzacy1);
            Prowadzacy prowadzacy2 = new Prowadzacy("mieczyslaw", "mieczyslaw1");
            prowadzacy2.ustawDane(12,"Mieczyslaw", "Drabowski");
            db.store(prowadzacy2);
            //##################
            Student student1 = new Student("Kris", "Kris1");
            student1.ustawDane(1, "Krzysztof", "Maciejowski", "WIEiK", "informatyka", 1, 5);
            student1.dodajPrzedmiot(1);student1.dodajPrzedmiot(2);
            student1.dodajOcene(1);student1.dodajOcene(2);student1.dodajOcene(3);student1.dodajOcene(4);
            db.store(student1);
            //##################
            Student student2 = new Student("Jan", "Jan1");
            student2.ustawDane(2, "Jan", "Nowak", "WIEiK","informatyka", 1, 5);
            student2.dodajPrzedmiot(1);student2.dodajPrzedmiot(2);
            student2.dodajOcene(5);student2.dodajOcene(6);student2.dodajOcene(7);student2.dodajOcene(8);
            db.store(student2);
            //##################
            Przedmiot to = new Przedmiot(1, "TO", 6 , 5);
            to.dodajProwadzacego(11);
            to.dodajStudenta(1); to.dodajStudenta(2);
            to.dodajOcene(1);to.dodajOcene(2);to.dodajOcene(5);to.dodajOcene(6);
            db.store(to);
            //##################
            Przedmiot ip = new Przedmiot(2, "IP", 6 , 5);
            ip.dodajProwadzacego(11); ip.dodajProwadzacego(12);
            ip.dodajStudenta(1); ip.dodajStudenta(2);
            ip.dodajOcene(3); ip.dodajOcene(4); ip.dodajOcene(7); ip.dodajOcene(8);
            db.store(ip);
            //##################
            Oceny kol11 = new Oceny(1, "kolokwium 1", 5 , 3, 1, 1);
            db.store(kol11);
            Oceny kol21 = new Oceny(3, "kolokwium 1", 4.5 , 3, 1, 2);
            db.store(kol21);
            Oceny kol12 = new Oceny(5, "kolokwium 1", 5 , 3, 2, 1);
            db.store(kol12);
            Oceny kol22 = new Oceny(7, "kolokwium 1", 4.0 , 3, 2, 2);
            db.store(kol22);
        }
        finally
        {
            db.close();
        }
    }

    public static void usunWszystko()
    {
        System.out.println("\nUsuwanie...\n");
        ObjectContainer db= Db4o.openFile("Wirtualny_Dziekanat");
        try
        {
            Prowadzacy prowadzacy = new Prowadzacy(null, null);
            Student student = new Student(null, null);
            Przedmiot przedmiot = new Przedmiot(0, null, 0, 0);
            Oceny oceny = new Oceny(0, null, 0, 0, 0, 0);
            ObjectSet result;
            result= db.queryByExample(prowadzacy);
            while (result.hasNext())
            {
                Prowadzacy found = (Prowadzacy) result.next();
                db.delete(found);
            }
            result = db.queryByExample(student);
            while (result.hasNext())
            {
                Student found = (Student) result.next();
                db.delete(found);
            }
            result = db.queryByExample(przedmiot);
            while (result.hasNext())
            {
                Przedmiot found = (Przedmiot) result.next();
                db.delete(found);
            }
            result=db.queryByExample(oceny);
            while(result.hasNext())
            {
                Oceny found = (Oceny) result.next();
                db.delete(found);
            }
        }
        finally
        {
            db.close();
        }
    }

    public static void wypiszWszystko()
    {
        System.out.println("\nDane bazy 'Wirtualny_Dziekanat'\n");
        ObjectContainer db= Db4o.openFile("Wirtualny_Dziekanat");
        try
        {
            Prowadzacy prowadzacy = new Prowadzacy(null, null);
            Student student = new Student(null, null);
            Przedmiot przedmiot = new Przedmiot(0, null, 0, 0);
            Oceny oceny = new Oceny(0, null, 0, 0, 0, 0);
            ObjectSet result;
            result= db.queryByExample(student);
            System.out.println("******Studenci******");

            while (result.hasNext())
            {
                student=(Student) result.next();
                System.out.println(student.toString(1));
            }
            System.out.println();

            result = db.queryByExample(prowadzacy);
            System.out.println("******Prowadzacy******");

            while (result.hasNext())
            {
                prowadzacy=(Prowadzacy) result.next();
                System.out.println(prowadzacy.toString(1));
            }
            System.out.println();

            result = db.queryByExample(przedmiot);
            System.out.println("******Dostepne przedmioty******");

            while (result.hasNext())
            {
                System.out.println(result.next());
            }
            System.out.println();

            result = db.queryByExample(oceny);
            System.out.println("******Wszystkie wpisane oceny******");

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
}
