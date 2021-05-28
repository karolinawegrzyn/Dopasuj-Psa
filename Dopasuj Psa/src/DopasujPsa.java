//Karolina Węgrzyn, Łukasz Szczepaniak
//aby kompilacja sie udala nalezy dodac JUnit4 to classpath (inaczej testy się nie skompilują)
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.JFrame;

//zgodnie z zasadą S tworzymy 3 klasy - każda odpowiada za jedną rzecz
//stworzy klasę abstrakcyjną Zwierze, która będzie otwarta na rozszerzenia i zamknięta na modyfikacje
//mozna np stworzyc kota

abstract class Zwierze {
    public String nazwa;
    public String opis;
    public String[] klucz_odpowiedzi;

    public String getNazwa(){
        return nazwa;
    }
    public String getOpis(){
        return opis;
    }
    public String getKlucz_odpowiedzi(int i){
        return klucz_odpowiedzi[i];
    }
    public void setOpis(String nowyOpis){
        opis = nowyOpis;
    }
}

//zgodnosc metod z klasa bazową (zasada L)
class Pies extends Zwierze{
    Pies(String n, String o, String[] klucz_odp) {          //konstruktor
        if (n.isEmpty() || o.isEmpty())
            throw new IllegalArgumentException();
        nazwa = n;
        opis = o;
        klucz_odpowiedzi = klucz_odp;
    }
}

//korzystamy z Singletona, gdyż tworzymy tylko jeden obiekt tego typu (mamy jedną baze)
//argumenty funkcji to klasy abstrakcyjne - nie konkretny typ (zasada D)
class Baza {
    private static final Baza instance = new Baza();
    public static Baza getInstance() {
        return instance;
    }
    Map<String, Zwierze> baza = new HashMap<>();

    private Baza() { } //konstruktor

    boolean CzyZwierzeWBazie(Zwierze p) {
        return baza.containsKey(p.getNazwa());
    }

    void DodajZwierze(Zwierze p) {
        if (p.getNazwa().isEmpty() || p.getOpis().isEmpty())
            throw new IllegalArgumentException();

        if (!CzyZwierzeWBazie(p))
            baza.put(p.getNazwa(), p);
    }

    void UsunZwierze(Zwierze p) {
        if (CzyZwierzeWBazie(p))
            baza.remove(p.getNazwa());
    }

    void EdytujZwierze(Zwierze p, String nowyOpis) {
        if (nowyOpis.equals(""))
            throw new IllegalArgumentException();

        if (CzyZwierzeWBazie(p))
            p.setOpis(nowyOpis);
        else
            throw new IllegalArgumentException();
    }
}

//korzystamy z Iteratora w celu przeglądania mapy
//korzystamy z wbudowanego interfejsu, który ma jedną metodę (zasada I)
class Quiz implements ActionListener{
    private final int liczba_pytan = 12;
    private final String[] pytania = {"Czy lubisz duże psy?",
            "Gdzie mieszkasz?",
            "Jak aktywny powinien być Twój Pies?",
            "Jak długo Twój pies będzie zostawał sam w domu?",
            "Czy Twój pies będzie miał kontakt z dziećmi?",
            "Czy posiadasz kota lub psa?",
            "Czy miałeś już kiedyś psa?",
            "Czy linienie psa to dla Ciebie duży problem?",
            "Czy będziesz dbał o dietę swojego psa?",
            "Czy przeszkadza Ci gdy pies dużo szczeka?",
            "Jak długą sierść preferujesz u psów?",
            "Które z tych słów najlepiej opisuje twojego wymarzonego psa?"};
    private final String[][] odpowiedzi = {{"Nie, wolę małe", "Wolę średnie", "Tak, lubię duże"},
            {"W mieszkaniu", "W domu w mieście", "W domu na wsi"},
            {"Leniuch", "Aktywny, ale nie za dużo", "Pełen energii"},
            {"Bedę częściej w domu niż mój pies", "około 5 godzin", "prawie cały dzień"},
            {"Nie", "Sporadycznie", "Tak, regularnie"},
            {"Nie", "Mam kota", "Mam psa"},
            {"Miałem wiele psów w przeszłości", "Miałem jednego psa", "Nie miałem"},
            {"Tak, jest to duży problem", "Jeśli nie za dużo to nie problem", "Niech sobie linieje ile chce"},
            {"Będę przykładał do tego dużą uwagę", "Będę mu dawał zwykłe jedzenia dla psów", "Pies będzie jadł to co ja"},
            {"Tak, przeszkadza", "Wolę, by nie szczekal zbyt duzo", "Nie przeszkadza"},
            {"Długą", "Krótka", "Srednia"},
            {"Pieszczoch", "Pies Stróżujący", "Inteligent"}};

    private String odpowiedzi_uzytkownika = "";
    private int indeks;
    private final JFrame frame;
    private final JTextArea textarea;
    private final JButton buttonA;
    private final JButton buttonB;
    private final JButton buttonC;
    private final JButton buttonRozwiazPonownie;
    private final JLabel odpA;
    private final JLabel odpB;
    private final JLabel odpC;
    private final JTextArea nazwa;
    private final JTextArea opis;
    Baza bazaPsow;

    public Quiz(Baza bp){
        frame = new JFrame("Quiz o pieskach");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.getContentPane().setBackground(new Color(22, 172, 161));
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        textarea = new JTextArea();
        textarea.setBounds(5, 5, 970, 130);
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);
        textarea.setBackground(new Color(22, 172, 161));
        textarea.setForeground(new Color(1, 2, 16));
        textarea.setFont(new Font("serif", Font.PLAIN, 50));
        textarea.setEditable(false);

        buttonA = new JButton();
        buttonA.setBounds(5, 150, 100, 100);
        buttonA.setFont(new Font("serif", Font.BOLD, 35));
        buttonA.setFocusable(false);
        buttonA.addActionListener(this);
        buttonA.setText("A");

        buttonB = new JButton();
        buttonB.setBounds(5, 300, 100, 100);
        buttonB.setFont(new Font("serif", Font.BOLD, 35));
        buttonB.setFocusable(false);
        buttonB.addActionListener(this);
        buttonB.setText("B");

        buttonC = new JButton();
        buttonC.setBounds(5, 450, 100, 100);
        buttonC.setFont(new Font("serif", Font.BOLD, 35));
        buttonC.setFocusable(false);
        buttonC.addActionListener(this);
        buttonC.setText("C");

        buttonRozwiazPonownie = new JButton();
        buttonRozwiazPonownie.setFont(new Font("serif", Font.BOLD, 20));
        buttonRozwiazPonownie.setFocusable(false);
        buttonRozwiazPonownie.addActionListener(this);
        buttonRozwiazPonownie.setText("Roziwąż ponownie");

        odpA = new JLabel();
        odpA.setBounds(130, 150, 1000, 100);
        odpA.setBackground(new Color(50, 50, 50));
        odpA.setForeground(new Color(1, 2, 16));
        odpA.setFont(new Font("serif", Font.PLAIN, 35));

        odpB = new JLabel();
        odpB.setBounds(130, 300, 1000, 100);
        odpB.setBackground(new Color(50, 50, 50));
        odpB.setForeground(new Color(1, 2, 16));
        odpB.setFont(new Font("serif", Font.PLAIN, 35));

        odpC = new JLabel();
        odpC.setBounds(130, 450, 1000, 100);
        odpC.setBackground(new Color(50, 50, 50));
        odpC.setForeground(new Color(1, 2, 16));
        odpC.setFont(new Font("serif", Font.PLAIN, 35));

        nazwa = new JTextArea();
        nazwa.setBounds(5, 150, 970, 130);
        nazwa.setLineWrap(true);
        nazwa.setWrapStyleWord(true);
        nazwa.setBackground(new Color(22, 172, 161));
        nazwa.setForeground(new Color(1, 2, 16));
        nazwa.setFont(new Font("serif", Font.PLAIN, 40));
        nazwa.setEditable(false);

        opis = new JTextArea();
        opis.setBounds(5, 285, 970, 130);
        opis.setLineWrap(true);
        opis.setWrapStyleWord(true);
        opis.setBackground(new Color(22, 172, 161));
        opis.setForeground(new Color(1, 2, 16));
        opis.setFont(new Font("serif", Font.PLAIN, 30));
        opis.setEditable(false);

        bazaPsow = bp;
    }

    public void RozwiazQuiz() {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.setLayout(null);
        frame.add(odpA);
        frame.add(odpB);
        frame.add(odpC);
        frame.add(buttonA);
        frame.add(buttonB);
        frame.add(buttonC);
        frame.add(textarea);
        frame.setVisible(true);

        nastepnePytanie();
    }

    public void nastepnePytanie() {
        if (indeks >= liczba_pytan) {
            Zwierze p = WyborRasyZKlucza();
            wyswietlWynik(p);
        } else {
            textarea.setText((indeks + 1) + ". " + pytania[indeks]);
            odpA.setText(odpowiedzi[indeks][0]);
            odpB.setText(odpowiedzi[indeks][1]);
            odpC.setText(odpowiedzi[indeks][2]);
        }
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == buttonA) {
            odpowiedzi_uzytkownika += 'A';
        }

        if (source == buttonB) {
            odpowiedzi_uzytkownika += 'B';
        }

        if (source == buttonC) {
            odpowiedzi_uzytkownika += 'C';
        }

        if (source == buttonRozwiazPonownie) {
            indeks = 0;
            odpowiedzi_uzytkownika = "";
            RozwiazQuiz();
        } else {
            indeks++;
            nastepnePytanie();
        }
    }

    public void wyswietlWynik(Zwierze p) {
        textarea.setText("Wynik: ");
        nazwa.setText("Nazwa: " + p.getNazwa());
        opis.setText("Opis: " + p.getOpis() + '\n');
        frame.setLayout(new FlowLayout());
        String i = "zdjecia/" + p.getNazwa() + ".jpg";
        ImageIcon obrazek = new ImageIcon(i);
        JLabel obrazekLabel = new JLabel(obrazek, JLabel.CENTER);
        obrazekLabel.setVisible(true);
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.add(nazwa);
        frame.add(opis);
        frame.add(obrazekLabel);
        frame.add(buttonRozwiazPonownie);
    }

    Zwierze WyborRasyZKlucza() {
        int iloscRas = bazaPsow.baza.size();
        int[] LiczbaPunktowRasy = new int[iloscRas];

        for (int i = 0; i < iloscRas; i++)
            LiczbaPunktowRasy[i] = 0;

        Iterator<Map.Entry<String, Zwierze>> itr = bazaPsow.baza.entrySet().iterator();
        int i = 0;

        //dla kazdej rasy zliczamy ile odpowiedzi zgadza się z tymi podanymi przez użytkownika
        while (itr.hasNext()) {
            Map.Entry<String, Zwierze> entry = itr.next();
            for (int j = 0; j < liczba_pytan; j++) {
                char odpowiedzUzytkownika = odpowiedzi_uzytkownika.charAt(j);
                String odpowiedzDlaRasy = entry.getValue().getKlucz_odpowiedzi(j);

                if (odpowiedzUzytkownika == odpowiedzDlaRasy.charAt(0) || odpowiedzUzytkownika == odpowiedzDlaRasy.charAt(1) || odpowiedzUzytkownika == odpowiedzDlaRasy.charAt(2)) {
                    LiczbaPunktowRasy[i]++;
                }
            }
            i++;
        }

        //szukamy maksymalnej ilosci punktow
        int max = LiczbaPunktowRasy[0];
        int indeksPsa;
        int[] RasyZMaxIlosciaPunktow = new int[iloscRas];
        int iloscRasZMaxIlosciaPunktow = 0;

        for (int j = 0; j < iloscRas; j++) {
            if (LiczbaPunktowRasy[j] > max) {
                max = LiczbaPunktowRasy[j];
            }
        }

        //zapisujemy wszystkie rasy, które mają maksymalna liczbę punktów
        for (int j = 0; j < iloscRas; j++) {
            if (LiczbaPunktowRasy[j] == max) {
                RasyZMaxIlosciaPunktow[iloscRasZMaxIlosciaPunktow] = j;
                iloscRasZMaxIlosciaPunktow++;
            }
        }

        //losujemy psa, z psów, które mają maksymalną liczbę zgadzających się odpowiedzi
        indeksPsa = new Random().nextInt(iloscRasZMaxIlosciaPunktow);
        indeksPsa = RasyZMaxIlosciaPunktow[indeksPsa];

        i = 0;
        Zwierze p = null;
        itr = bazaPsow.baza.entrySet().iterator();
        while (itr.hasNext()) {
            if (i == indeksPsa) {
                p = itr.next().getValue();
                break;
            }
            itr.next();
            i++;
        }

        return p;
    }
}

public class DopasujPsa {
    public static void main(String[] args){
        Baza bp = Baza.getInstance();
        //klucze odpowiedzi dla danych ras

        String[] koBernardyn =           {"00C", "00C", "0B0", "A00", "ABC", "ABC", "AB0", "0BC", "A00", "00C", "00C", "0BC"};
        String[] koOwczarekPodhalanski = {"00C", "00C", "0B0", "ABC", "ABC", "ABC", "AB0", "00C", "AB0", "0BC", "A00", "0BC"};
        String[] koNowofundland =        {"00C", "0BC", "0B0", "A00", "ABC", "ABC", "ABC", "00C", "AB0", "ABC", "A00", "ABC"};
        String[] koRottweiler =          {"00C", "0BC", "00C", "AB0", "ABC", "AB0", "A00", "00C", "AB0", "ABC", "0B0", "00C"};
        String[] koDoberman =            {"00C", "0BC", "00C", "A00", "ABC", "ABC", "AB0", "00C", "AB0", "0BC", "0B0", "ABC"};
        String[] koAkita =               {"00C", "ABC", "0B0", "AB0", "ABC", "AB0", "A00", "0BC", "ABC", "ABC", "0B0", "0B0"};
        String[] koLabrador =            {"0B0", "0BC", "00C", "A00", "ABC", "ABC", "ABC", "00C", "A00", "0BC", "0B0", "A0C"};
        String[] koSamoyed =             {"0B0", "00C", "00C", "A00", "ABC", "A0C", "A00", "00C", "AB0", "00C", "A00", "A0C"};
        String[] koBorderCollie =        {"0B0", "ABC", "00C", "A00", "ABC", "ABC", "AB0", "0BC", "AB0", "0BC", "00C", "A0C"};
        String[] koOwczarekNiemiecki =   {"0B0", "ABC", "00C", "AB0", "ABC", "ABC", "AB0", "00C", "AB0", "0BC", "00C", "ABC"};
        String[] koCorgi =               {"0B0", "ABC", "0B0", "A00", "ABC", "ABC", "ABC", "0BC", "A00", "0BC", "0B0", "A0C"};
        String[] koGoldenRetriever =     {"0B0", "0BC", "00C", "A00", "ABC", "ABC", "ABC", "00C", "A00", "0BC", "A00", "A0C"};
        String[] koChowChow =            {"0B0", "ABC", "A00", "ABC", "AB0", "A00", "A00", "00C", "AB0", "ABC", "00C", "0B0"};
        String[] koPudel =               {"0B0", "ABC", "00C", "A00", "ABC", "ABC", "ABC", "ABC", "A00", "00C", "A00", "A0C"};
        String[] koBeagle =              {"0B0", "0BC", "00C", "A00", "ABC", "ABC", "AB0", "0BC", "A00", "0BC", "0B0", "A00"};
        String[] koHusky =               {"0B0", "00C", "00C", "A00", "ABC", "A0C", "A00", "00C", "A00", "00C", "00C", "A00"};
        String[] koDalmatynczyk =        {"0B0", "0BC", "00C", "A00", "ABC", "ABC", "A00", "00C", "A00", "00C", "0B0", "ABC"};
        String[] koYork =                {"A00", "ABC", "0B0", "AB0", "ABC", "ABC", "ABC", "ABC", "AB0", "00C", "A00", "A00"};
        String[] koShiba =               {"A00", "ABC", "0B0", "AB0", "ABC", "ABC", "AB0", "0BC", "A00", "0BC", "0B0", "0B0"};
        String[] koChihuahua =           {"A00", "ABC", "0B0", "AB0", "AB0", "ABC", "AB0", "ABC", "A00", "00C", "00C", "A00"};
        String[] koMops =                {"A00", "ABC", "A00", "A00", "ABC", "ABC", "ABC", "0BC", "A00", "ABC", "0B0", "A00"};
        String[] koJamnik =              {"A00", "ABC", "0B0", "AB0", "AB0", "A0C", "AB0", "0BC", "A00", "00C", "0B0", "A00"};
        String[] koPomeranian =          {"A00", "ABC", "0B0", "AB0", "AB0", "ABC", "ABC", "0BC", "AB0", "00C", "A00", "A0C"};
        String[] koMaltanczyk =          {"A00", "ABC", "A00", "A00", "ABC", "ABC", "ABC", "ABC", "A00", "00C", "A00", "00C"};
        String[] koJackRusselTerrier =   {"A00", "00C", "00C", "AB0", "ABC", "A00", "A00", "0BC", "A00", "00C", "0B0", "A0C"};
        String[] koPekinczyk =           {"A00", "ABC", "A00", "ABC", "A00", "ABC", "ABC", "0BC", "A00", "00C", "A00", "000"}; //
        String[] koBuldogFrancuski =     {"A00", "ABC", "0B0", "A00", "ABC", "AB0", "ABC", "0BC", "A00", "0BC", "0B0", "A00"};

        bp.DodajZwierze(new Pies("bernardyn", "Olbrzym o wielkim sercu. Kocha ludzi i jest świetnym psem" +
                " stróżującym. Warto wiedzieć, że jego warczenie przypomina ryk lwa.", koBernardyn));
        bp.DodajZwierze(new Pies("owczarek podhalanski", "Polski miś polarny, który ochroni twoje dzieci, a " +
                "gdy będzie potrzeba to stado owiec. Król Gór.", koOwczarekPodhalanski));
        bp.DodajZwierze(new Pies("nowofundland", " Idealny towarzysz rodziny, a w wolnych chwilach " +
                "ratownik wodny. Jak kraść to miliony, jak głaskać to jego.", koNowofundland));
        bp.DodajZwierze(new Pies("rottweiler", " Jeżeli masz w domu rottweilera, to możesz być pewien, że nawet" +
                " listonosz nie dostarczy ci poczty. Jednak dobrze wychowany może być pupilem.", koRottweiler));
        bp.DodajZwierze(new Pies("doberman", "Lepiej nie wchodź na jego teren bez zaproszenia. Może być dobrym " +
                "kompanem ale potrzebuje dobrego wychowania. Pies sportowiec.", koDoberman));
        bp.DodajZwierze(new Pies("akita", "Raczej nie pochwalisz się posłuszeństwem akity przy znajomych, jednak " +
                "jest bardzo wierny. Znasz Hachiko? To właśnie on.", koAkita));
        bp.DodajZwierze(new Pies("labrador", "Żołądek na czterech łapach. Bardzo uniwersalny - pobawi się z " +
                "dziećmi, pójdzie z tobą na jogging, ale na pewno nie przypilnuje ci domu.",
                koLabrador));
        bp.DodajZwierze(new Pies("samoyed", "Wrażliwy pieszczoch, wygląda jak wiecznie uśmiechnięta chmurka. " +
                "Cały czas szczeka, więc nikt go nie bierze na poważnie.", koSamoyed));
        bp.DodajZwierze(new Pies("border collie", "Pochodzi z inteligencji. Jak chcesz żeby poczytał Ci książke albo " +
                "zdał za ciebie egzamin to jest to dobry wybor. Jak ci nie przeszkadza że ma więcej IQ niż ty to ok.", koBorderCollie));
        bp.DodajZwierze(new Pies("owczarek niemiecki", "Jeśli jesteś typem kanapowca to raczej się nie dogadacie. " +
                "Sportowiec inteligent, więc na pewno nie dres.", koOwczarekNiemiecki));
        bp.DodajZwierze(new Pies("corgi", "Jest to typ królewski. Dawniej pilnował stada, teraz woli pieszczoty. " +
                "Nie lubi nudy", koCorgi));
        bp.DodajZwierze(new Pies("golden retriever", "Psy tej rasy mają nie tylko złotą sierść, ale i charakter. " +
                "To doskonały towarzysz, zarówno w pracy jak i w domu.", koGoldenRetriever));
        bp.DodajZwierze(new Pies("chow chow", "Wiedzie spokojne życie. Ten pies z niebieskim jęzorem woli spanko niż" +
                " zabawę. Jak go dotkniesz to będziesz w niebie.", koChowChow));
        bp.DodajZwierze(new Pies("pudel", " Jego zaletą jest szlachetny wygląd oraz milion IQ. Nadaje się właściwie" +
                " dla każdego." ,koPudel));
        bp.DodajZwierze(new Pies("beagle", " Nie za duży, nie za mały. Jest gończym psem, ale w odróżnieniu od innych" +
                " gończych, beagle przywiązuje się do właściciela.", koBeagle));
        bp.DodajZwierze(new Pies("husky", " Cechują go niebieskie oczy. Pochodzi z Syberii(zimiara), ma ADHD, więc" +
                " wykorzystuję się go do zaprzęgów.", koHusky));
        bp.DodajZwierze(new Pies("dalmatynczyk", " Krówka wśród psów. Jest bardzo energiczny więc nie potrzebujesz " +
                "ich 101 - wystarczy jeden.", koDalmatynczyk));
        bp.DodajZwierze(new Pies("york", "Poręczny, można go schować do torebki. Odważny do tego stopnia, że nie " +
                "czuje respektu przed dużymi psami.", koYork));
        bp.DodajZwierze(new Pies("shiba", " Uparty, czasem lubi sobie warknąć. Jeśli chcesz zacząć karierę w " +
                "internecie, to pamiętaj, że nigdy mu nie dorównasz.", koShiba));
        bp.DodajZwierze(new Pies("chihuahua","Duży pies w niewielkim opakowaniu, jeśli nie chcesz by cały czas " +
                "szczekał, to poświęć mu trochę uwagi(lubi szczekać)", koChihuahua));
        bp.DodajZwierze(new Pies("mops", "Jak nie lubisz dużo chodzić to dobrze, bo mops też nie. Podczas twojej" +
                " nieobecności będzie głównie spać.", koMops));
        bp.DodajZwierze(new Pies("jamnik", "ylko 1/3 szczeniaków przeznaczana jest na psy domowe(reszta poluje). " +
                "Jak jesteś fanem parówek, to jest to pies dla ciebie.", koJamnik));
        bp.DodajZwierze(new Pies("pomeranian", "Znajdzie se faworyta, którego nie odstąpi na krok i będzie " +
                "domagał się należnych mu pieszczot. Mały, ale potrzebuje dużo miłości.", koPomeranian));
        bp.DodajZwierze(new Pies("maltanczyk", "Jest delikatny i biały, więc uważaj bo łatwo się brudzi. Mimo " +
                "małego rozmiaru, jego serce jest ogromne.", koMaltanczyk));
        bp.DodajZwierze(new Pies("jack russel terrier", "Mały i słodki piesek, ale niech was to nie zmyli, niezły " +
                "z niego rozrabiaka. Ma mięśnie jak skały, fale się rozbijały.", koJackRusselTerrier));
        bp.DodajZwierze(new Pies("pekinczyk", "Wygląda trochę jak mały lew. Bardzo mało mówi, ale bardzo ładnie " +
                "milczy.", koPekinczyk));
        bp.DodajZwierze(new Pies("buldog francuski", "Uwodziciel z uszami nietoperza. Lubi spanko podczas którego " +
                "zdarza mu się chrapać.(chrapie głośniej niż twój stary)", koBuldogFrancuski));

        Quiz quiz = new Quiz(bp);
        quiz.RozwiazQuiz();
    }
}
