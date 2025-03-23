package hi.verkefni.vinnsla;

import javafx.beans.property.*;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Leikur klasi heldur utan um spilabordid, leikmenn og þroun leiksins.
 * Samanstendur af adferdum til að hefja nýjan leik, faera leikmenn og akvarda
 * hver vinnur leikinn.
 */
public class Leikur {
    private Items bord;
    private int[] leikmenn;
    private int nuverandiLeikmadur;
    private Teningur teningur;
    private SimpleBooleanProperty leikLokid;
    private SimpleStringProperty sigurvegari;
    private SimpleStringProperty naestiLeikmadur;
    private SimpleIntegerProperty kastaNidurstada;
    private SimpleStringProperty skilabod1 = new SimpleStringProperty("");
    private SimpleStringProperty skilabod2 = new SimpleStringProperty("");
    private SimpleIntegerProperty[] leikmennProperties;

    /**
     * Smidur fyrir Leikur sem frumstillir bordid, leikmann o.fl.
     * @param fjoldiLeikmanna fjoldi leikmanna (alltaf 2 hja okkur)
     */
    public Leikur(int fjoldiLeikmanna) {
        bord = new Items();
        leikmenn = new int[fjoldiLeikmanna];
        leikmennProperties = new SimpleIntegerProperty[fjoldiLeikmanna];
        for (int i = 0; i < fjoldiLeikmanna; i++) {
            leikmenn[i] = 1;
            leikmennProperties[i] = new SimpleIntegerProperty(1);
        }
        teningur = new Teningur();
        leikLokid = new SimpleBooleanProperty(false);
        sigurvegari = new SimpleStringProperty();
        naestiLeikmadur = new SimpleStringProperty();
        nuverandiLeikmadur = 0;
        nyrLeikur();
        kastaNidurstada = new SimpleIntegerProperty(1);
    }

    /**
     * Endurstillir allt og faerir leikmenn aftur a byrjunarreit
     */
    public void nyrLeikur() {
        for (int i = 0; i < leikmenn.length; i++) {
            leikmenn[i] = 1;
            leikmennProperties[i].set(1);
        }
        leikLokid.set(false);
        sigurvegari.set("");
        naestiLeikmadur.set("Leikmadur 1");
        nuverandiLeikmadur = 0;
        skilabod1.set("");
        skilabod2.set("");
    }

    /**
     * athugar hvort leiknum sé lokid, keyrir hann afram ef ekki
     * med teningakasti og uppfaerir stadsetningu leikmanns
     * @return true ef leikurinn er buinn, annars false
     */
    public boolean leikaLeik() {
        if (leikLokid.get()) { return true;}

        teningur.kasta();
        int kast = teningur.getTala().get();
        kastaNidurstada.set(kast);

        faeraLeikmann(kast);

        if (leikmenn[nuverandiLeikmadur] >= 24) {
            leikLokid.set(true);
            sigurvegari.set("Leikmadur " + (nuverandiLeikmadur + 1));
            //System.out.println("DEBUG: leikLokid is now: " + leikLokid.get());
            //System.out.println(sigurvegari.get() + " hefur unnid!");
            skilabod2.set(sigurvegari.get() + " hefur unnid!");
            return true;
        }

        setjaNaestaLeikmann();
        return false;
    }

    /**
     * Skilar BooleanProperty sem segir til um hvort leiknum se lokid
     * @return BooleanProperty leikLokid
     */
    public BooleanProperty getLeikLokid() {
        return leikLokid;
    }

    /**
     * Faerir leikmann eftir thvi sem hann faer ur teningakasti
     * og uppfaerir skilabodin fyrir labelin i vidmotinu
     * @param kast upphaed fra teninga kasti
     */
    private void faeraLeikmann(int kast) {
        int nyrReitur = leikmenn[nuverandiLeikmadur] + kast;
        int tempReitur = leikmenn[nuverandiLeikmadur];

        leikmenn[nuverandiLeikmadur] = bord.getLendingarReitur(nyrReitur);
        leikmennProperties[nuverandiLeikmadur].set(leikmenn[nuverandiLeikmadur]);

        if (tempReitur > leikmenn[nuverandiLeikmadur]) {
            skilabod1.set("Leikmadur " + (nuverandiLeikmadur + 1) + " for nidur slöngu og er nu a reit " + leikmenn[nuverandiLeikmadur]);
        }
        else if (leikmenn[nuverandiLeikmadur] - tempReitur > 6) {
            skilabod1.set("Leikmadur " + (nuverandiLeikmadur + 1) + " for upp stiga og er nu a reit " + leikmenn[nuverandiLeikmadur]);
        }
        else {
            skilabod1.set("");
        }

        /**
        System.out.println("-------------------");
        System.out.println("tempreitur: " + tempReitur);
        System.out.println("nyrReitur: " + leikmenn[nuverandiLeikmadur]);
        System.out.println(skilabod1.get());
        System.out.println("Leikmadur " + (nuverandiLeikmadur + 1) + " er nu a reit " + leikmenn[nuverandiLeikmadur]);
        System.out.println("-------------------");**/
    }

    /**
     * Uppfaerir hvor leikmadurinn á ad gera naest
     */
    private void setjaNaestaLeikmann() {
        nuverandiLeikmadur = (nuverandiLeikmadur + 1) % leikmenn.length;
        naestiLeikmadur.set("Leikmadur " + (nuverandiLeikmadur + 1));
        //System.out.println("Naesti leikmadur er " + naestiLeikmadur.get());
        skilabod2.set("Naesti leikmadur er " + naestiLeikmadur.get());
    }

    /**
     * skilar IntegerProperty af hvad leikmadur fekk ur sidasta kasti
     * @return IntegerProperty kastaNidurstada
     */
    public IntegerProperty kastaNidurstadaProperty() {
        return kastaNidurstada;
    }

    /**
     * skilar StringProperty fyrir skilabod 1
     * @return StringProperty af skilabod1
     */
    public StringProperty skilabod1Property() {
        return skilabod1;
    }

    /**
     * skilar StringProperty fyrir skilabod 2
     * @return StringProperty af skilabod2
     */
    public StringProperty skilabod2Property() {
        return skilabod2;
    }

    /**
     * skilar IntegerProperty af stadsetningu leikmanns
     * @return IntegerProperty sem geymir stadsetningu leikmanns
     */
    public IntegerProperty getLeikmadurReiturProperty(int index) {
        return leikmennProperties[index];
    }

    /**
     * prufunartilvik
     * @param args
     */
    public static void main(String[] args) {
        Leikur leikur = new Leikur(2);
        leikur.nyrLeikur();

        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        System.out.print("A naesti leikmadur ad gera? (j/n) ");
        String svar = scanner.next();

        while ("j".equalsIgnoreCase(svar)) {
            if (leikur.leikaLeik()) {
                System.out.println(leikur.sigurvegari.get() + " kominn i mark!");
                return;
            }
            System.out.print("A naesti leikmadur ad gera? (j/n) ");
            svar = scanner.next();
        }
    }
}
