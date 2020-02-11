import java.util.ArrayList;
import java.util.List;

public class Przedmiot
{

    public int id;
    public String nazwa;
    public int pkrECTS;
    public int semestr;
    public List<Integer> prowadzacy= new ArrayList<Integer>();
    public List<Integer> students= new ArrayList<Integer>();
    public List<Integer> oceny= new ArrayList<Integer>();

    public Przedmiot(int id, String nazwa, int pktECTS, int semestr)
    {
        this.id=id;
        this.nazwa=nazwa;
        this.pkrECTS=pktECTS;
        this.semestr=semestr;
    }

    public String toString()
    {
        return "id: " + id + ", nazwa: " + nazwa + ", pkt ECTS: " + pkrECTS + ", nr semestr: " + semestr +", ilosc prowadzacych: " + prowadzacy.size();
    }

    public void dodajProwadzacego(Integer id){
        prowadzacy.add(id);
    }

    public void dodajStudenta(Integer id){
        students.add(id);
    }

    public void dodajOcene(Integer id){
        oceny.add(id);
    }
}
