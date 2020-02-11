import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main
{

    public static void main(String[] args)
    {
        BazaZapytania bazazapytania;
        Scanner in = new Scanner(System.in);
        Student student;
        int idUzyt = 0, idPrzedmiotu, opcja;
        boolean powrot = false;

        while(true)
        {
            powrot = false;
            System.out.println("\n");
            System.out.println("****************************************");
            System.out.println("          WIRTUALNY DZIEKANAT           ");
            System.out.println("****************************************");
            System.out.println("\nLogowanie");
            System.out.println("1. Dla prowadzacyh");
            System.out.println("2. Dla studentow");
            System.out.println("3. Dla pracowników dziekanatu");
            System.out.println("4. Wyjście");
            int zmienna = Integer.parseInt(in.nextLine());
            if(zmienna==1)
            {
                String login = "";
                String haslo = "";
                while (login.equals(""))
                {
                    System.out.println("Podaj login");
                    login = in.nextLine();
                }
                while (haslo.equals(""))
                {
                    System.out.println("Podaj haslo");
                    haslo = in.nextLine();
                }
                Prowadzacy prowadzacy = new Prowadzacy(login, haslo);
                idUzyt = prowadzacy.Zaloguj();

                while ((idUzyt == 0) && (powrot == false))
                {
                    System.out.println("Naciśnij:\n 1. Jeśli chcesz spróbować ponownie,\n 2. Jeśli chcesz zmienić hasło,\n 3. Wyjście!");
                    zmienna = Integer.parseInt(in.nextLine());
                    if (zmienna == 1) {
                        login = "";
                        haslo = "";
                        while (login.equals("")) {
                            System.out.println("Podaj login");
                            login = in.nextLine();
                        }

                        while (haslo.equals("")) {
                            System.out.println("Podaj haslo");
                            haslo = in.nextLine();
                        }
                        prowadzacy = new Prowadzacy(login, haslo);
                    }
                    else if (zmienna == 3)
                        powrot = true;
                    else if (zmienna == 2) {
                        prowadzacy.ZmienHaslo();
                    }
                    else
                        System.out.println("Brak opcji");
                    idUzyt = prowadzacy.Zaloguj();
                }
                bazazapytania = new BazaZapytania();
                bazazapytania.wczytajPrzedmioty(idUzyt);
                while (powrot == false) {
                    System.out.println();
                    System.out.println("********************************************");
                    System.out.println("Prowadzący id=" + idUzyt + "\n");
                    System.out.println("1. Zobacz swoje dane \n2. Zobacz liste przedmiotów  \n3. Wyloguj");
                    System.out.print("Podaj opcje:  ");
                    opcja = in.nextInt();
                    switch (opcja) {
                        case 1:
                            System.out.println(prowadzacy.toString(2));
                            break;
                        case 2:
                            while (!powrot)
                            {
                                System.out.println();
                                System.out.println("********************************************");
                                for (Przedmiot przedmiot: bazazapytania.przedmioty) {
                                    System.out.println(przedmiot.toString());
                                }
                                bazazapytania.przedmioty.toString();
                                System.out.println("1. Sprawdź Liste studentów z przedmiotu");
                                System.out.print("2. Powrot \nPodaj opcje:");
                                opcja = in.nextInt();
                                switch (opcja) {
                                    case 1:
                                        System.out.print("Podaj id przedmiotu: ");
                                        idPrzedmiotu = in.nextInt();
                                        bazazapytania.wczytajStudentowPoIdPrzedmiotu(idPrzedmiotu);
                                        while (!powrot)
                                        {
                                            System.out.println();
                                            System.out.println("********************************************");
                                            System.out.println("1. Sprawdź oceny studenta z przedmiotu");
                                            System.out.println("2. Sprawdź średnia studenta z przedmiotu");
                                            System.out.println("3. Wstaw nową ocene");
                                            System.out.print("4. Powrot \nPodaj opcje:");
                                            opcja = in.nextInt();
                                            switch (opcja) {
                                                case 1:
                                                    System.out.print("Podaj id studenta: ");
                                                    opcja = in.nextInt();
                                                    bazazapytania.wczytajOcenyStudentaZPrzedmiotu(opcja, idPrzedmiotu);
                                                    break;
                                                case 2:
                                                    System.out.print("Podaj id studenta: ");
                                                    opcja = in.nextInt();
                                                    bazazapytania.podajSredniaStudenta(opcja, idPrzedmiotu);
                                                    break;
                                                case 3:
                                                    double ocena;
                                                    int waga;
                                                    int id;
                                                    String name;
                                                    System.out.print("Podaj id studenta: ");
                                                    id = in.nextInt();
                                                    System.out.print("Podaj ocene: ");
                                                    ocena = in.nextDouble();
                                                    System.out.print("Podaj wage oceny: ");
                                                    waga = in.nextInt();
                                                    in.nextLine();
                                                    System.out.print("Podaj nazwę: ");
                                                    name = in.nextLine();
                                                    bazazapytania.dodajOcene(id,idPrzedmiotu,ocena,waga,name);
                                                    break;
                                                case 4:
                                                    powrot = true;
                                                    break;
                                                default:
                                                    System.out.println("Błędna opcja");
                                            }
                                        }
                                        powrot = false;
                                        break;
                                    case 2:
                                        powrot = true;
                                        break;
                                    default:
                                        System.out.println("Błędna opcja");
                                }
                            }
                            powrot = false;
                            break;
                        case 3:
                            in.nextLine();
                            powrot = true;
                            break;
                        default:
                            System.out.println("Błędna opcja");
                    }
                }
            }
//###############################################################################################################333
            else if(zmienna==2)
            {
                powrot = false;
                String login="";
                String haslo="";
                while (login.equals(""))
                {
                    System.out.println("Podaj login");
                    login = in.nextLine();
                }
                while (haslo.equals(""))
                {
                    System.out.println("Podaj haslo");
                    haslo = in.nextLine();
                }
                student=new Student(login,haslo);
                idUzyt = student.Zaloguj();
                while((idUzyt == 0) && (powrot == false))
                {
                    System.out.println("Naciśnij:\n 1 Jeśli chcesz spróbować ponownie,\n 2. Dla zmiany hasła,\n 3. Wyjście!");
                    zmienna = Integer.parseInt(in.nextLine());
                    if (zmienna == 1)
                    {
                        login = "";
                        haslo = "";
                        while (login.equals(""))
                        {
                            System.out.println("Podaj login");
                            login = in.nextLine();
                        }
                        while (haslo.equals(""))
                        {
                            System.out.println("Podaj haslo");
                            haslo = in.nextLine();
                        }
                        student = new Student(login, haslo);
                    }
                    else if (zmienna == 3)
                    {
                        powrot = true;
                    }
                    else if (zmienna == 2)
                    {
                        student.ZmienHaslo();
                    }
                    else
                        System.out.println("Brak opcji");
                    idUzyt = student.Zaloguj();
                }
                bazazapytania = new BazaZapytania();
                System.out.println(idUzyt);
                bazazapytania.wczytajPrzedmiotyDlaStudenta(1);
                while(powrot == false)
                {
                    System.out.println();
                    System.out.println("********************************************");
                    System.out.println("STUDENT id=" + idUzyt + "\n");
                    System.out.println("1. Zobacz swoje dane \n2. Zobacz liste przedmiotów \n3. Wyloguj");
                    System.out.print("Podaj opcje: ");
                    opcja = in.nextInt();
                    switch (opcja)
                    {
                        case 1:
                            System.out.println(student.toString(2));
                            break;
                        case 2:
                            while(!powrot)
                            {
                                System.out.println();
                                System.out.println("********************************************");
                                for (Przedmiot przedmiot: bazazapytania.przedmioty)
                                {
                                    System.out.println(przedmiot.toString());
                                }
                                System.out.println("1. Sprawdź oceny z przedmiotu");
                                System.out.println("2. Sprawdź srednią z przedmiotu");
                                System.out.print("3. Powrot \nPodaj opcje:");
                                opcja = in.nextInt();
                                switch (opcja)
                                {
                                    case 1:
                                        System.out.print("Podaj id przedmiotu: ");
                                        idPrzedmiotu = in.nextInt();
                                        bazazapytania.wczytajOcenyStudentaZPrzedmiotu(idUzyt, idPrzedmiotu);
                                        break;
                                    case 2:
                                        System.out.print("Podaj id przedmiotu: ");
                                        idPrzedmiotu = in.nextInt();
                                        bazazapytania.podajSredniaStudenta(idUzyt, idPrzedmiotu);
                                        break;
                                    case 3:
                                        powrot = true;
                                        break;
                                    default:
                                        System.out.println("Błędna opcja");
                                }
                            }
                            powrot = false;
                            break;
                        case 3:
                            in.nextLine();
                            powrot = true;
                            break;
                        default:
                            System.out.println("Błędna opcja");
                    }
                }
            }
            else if (zmienna == 3)
            {
                bazazapytania= new BazaZapytania();
                while (powrot == false) {
                    System.out.println();
                    System.out.println("********************************************");
                    System.out.println("Pracownik dziekantu\n");
                    System.out.println("1. Pokaż liste użytkowników");
                    System.out.println("2. Pokaż liste prowadzących");
                    System.out.println("3. Pokaż liste studentów");
                    System.out.println("4. Pokaż liste przedmitów");
                    System.out.println("5. Pokaż liste ocen");
                    System.out.println("6. Rejestracja nowego studenta");
                    System.out.println("7. Wyloguj");
                    System.out.print("Podaj opcje: ");
                    opcja = in.nextInt();
                    switch (opcja)
                    {
                        case 1:
                            bazazapytania.wypiszWszystkichProwadzacych();
                            bazazapytania.wypiszWszystkichStudentow();
                            break;
                        case 2:
                            bazazapytania.wypiszWszystkichProwadzacych();
                            break;
                        case 3:
                            bazazapytania.wypiszWszystkichStudentow();
                            break;
                        case 4:
                            bazazapytania.wypiszWszystkiePrzedmioty();
                            break;
                        case 5:
                            bazazapytania.wypiszWszystkieOceny();
                            break;
                        case 6:
                            in.nextLine();
                            List<String> dane = new ArrayList<String>();
                            System.out.println("Podaj login");
                            dane.add(in.nextLine());
                            System.out.println("Podaj haslo");
                            dane.add(in.nextLine());
                            System.out.println("Podaj pesel");
                            dane.add(in.nextLine());
                            System.out.println("Podaj imie");
                            dane.add(in.nextLine());
                            System.out.println("Podaj nazwisko");
                            dane.add(in.nextLine());
                            System.out.println("Podaj wydzial");
                            dane.add(in.nextLine());
                            System.out.println("Podaj kierunek");
                            dane.add(in.nextLine());
                            System.out.println("Podaj stopien studiow");
                            dane.add(in.nextLine());
                            System.out.println("Podaj nr semestru");
                            dane.add(in.nextLine());
                            student= new Student(dane.get(0), dane.get(1));
                            student.ustawDane(Integer.parseInt(dane.get(2)), dane.get(3), dane.get(4), dane.get(5), dane.get(6), Integer.parseInt(dane.get(7)), Integer.parseInt(dane.get(8)));
                            student.dodajPrzedmiot(1);student.dodajPrzedmiot(2);
                            student.dodajOcene(1);student.dodajOcene(2);student.dodajOcene(3);student.dodajOcene(4);

                            bazazapytania = new BazaZapytania();
                            bazazapytania.dodajStudenta(student);

                            break;
                        case 7:
                            powrot = true;
                            in.nextLine();
                            break;
                        default:
                            System.out.println("z opcja");
                    }
                }
            }
            else
                System.exit(0);
        }
    }
}
