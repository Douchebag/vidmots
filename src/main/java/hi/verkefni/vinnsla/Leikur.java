package hi.verkefni.vinnsla;

import hi.verkefni.vidmot.View;
import hi.verkefni.vidmot.ViewSwitcher;
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
    private Leikmadur[] leikmenn;
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
        leikmenn = new Leikmadur[fjoldiLeikmanna];
        for (int i = 0; i < fjoldiLeikmanna; i++) {
            leikmenn[i] = new Leikmadur("Leikmadur " + (i + 1));
        }

        leikmennProperties = new SimpleIntegerProperty[fjoldiLeikmanna];
        for (int i = 0; i < fjoldiLeikmanna; i++) {
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

        if (leikmennProperties[nuverandiLeikmadur].get() < 24) {
            faeraLeikmann(kast);
        } else {
            skilabod1.set(leikmenn[nuverandiLeikmadur].getLeikmadur() + " hefur klárað borðið.");
        }

        boolean allFinished = true;
        for (SimpleIntegerProperty pos : leikmennProperties) {
            if (pos.get() < 24) {
                allFinished = false;
                break;
            }
        }

        if (allFinished) {
            leikLokid.set(true);
            int winnerIndex = 0;
            for (int i = 1; i < leikmennProperties.length; i++) {
                if (leikmennProperties[i].get() > leikmennProperties[winnerIndex].get()) {
                    winnerIndex = i;
                }
            }
            sigurvegari.set(leikmenn[winnerIndex].getLeikmadur());
            //skilabod2.set(sigurvegari.get() + " hefur unnid!");
            ViewSwitcher.switchTo(View.FIGHT);
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
     * kallar á pickupItem til að sækja item (ef einhver)
     * og uppfaerir skilabodin fyrir labelin i vidmotinu
     * @param kast upphaed fra teninga kasti
     */
    private void faeraLeikmann(int kast) {
        int nuverandiReitur = leikmennProperties[nuverandiLeikmadur].get();
        int nyrReitur = nuverandiReitur + kast;

        Item pickedItem = bord.getItemAtTile(nyrReitur);
        if (pickedItem != null) {
            leikmenn[nuverandiLeikmadur].pickupItem(pickedItem);
            skilabod1.set(leikmenn[nuverandiLeikmadur].getLeikmadur()
                    + " fékk " + pickedItem.toString()
                    + " á reit " + nyrReitur);
        } else {
            skilabod1.set("");
        }
        leikmennProperties[nuverandiLeikmadur].set(nyrReitur);
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

    public Items getBord() {
        return bord;
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
