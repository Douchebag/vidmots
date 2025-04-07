package hi.verkefni.vinnsla;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Leikmadur klasinn sér um leikmann og stadsetningu leikmanns
 */
public class Leikmadur {
    private SimpleStringProperty leikmadur = new SimpleStringProperty("");
    private static final int MAX = 24;
    private static int stadsetningarReitur;
    private SimpleIntegerProperty reitur;
    private Item bestaVopn;
    private Item bestaBrynja;
    private List<Item> collectedItems = new ArrayList<>();

    /**
     * Smidur fyrir Leikmann
     * @param leikmadur nafn leikmanns, annadhvort 1 eda 2 i okkur tilfelli
     */
    public Leikmadur(String leikmadur) {
        this.leikmadur.set(leikmadur);
        this.reitur = new SimpleIntegerProperty(1);
    }

    /**
     * Færir leikmann á reit reitur en markið er í reit max
     * @param kasta nidurstada ur kasti
     * @param max mark-reitur á borðinu
     */
    public void faera (int kasta, int max) {
        int nyReitur = reitur.get() + kasta;
        if (nyReitur < max) {
            reitur.set(nyReitur);
        } else {
            reitur.set(max);
        }
    }

    public void pickupItem(Item item) {
        collectedItems.add(item);
        if (item.getType() == Item.ItemType.WEAPON) {
            if (bestaVopn == null || item.getBonus() > bestaVopn.getBonus()) {
                bestaVopn = item;
                System.out.println(getLeikmadur() + " uppfærði besta vopn í: " + bestaVopn);
            }
        } else if (item.getType() == Item.ItemType.ARMOUR) {
            if (bestaBrynja == null || item.getBonus() > bestaBrynja.getBonus()) {
                bestaBrynja = item;
                System.out.println(getLeikmadur() + " uppfærði besta brynju í: " + bestaBrynja);
            }
        }
    }

    /**
     * Skilar nafni leikmanns
     * @return nafn leikmanns
     */
    public String getLeikmadur() {
        return leikmadur.get();
    }

    /**
     * Stillir nafn leikmans
     * @param leikmadur nafn leikmanns
     */
    public void setLeikmadur(String leikmadur) {
        this.leikmadur.set(leikmadur);
    }

    /**
     * skilar nuverandi stadsetningu leikmanns
     * @return stadsetningar reitur leikmannsins
     */
    public int getStadsetningarReitur() {
        return reitur.get();
    }

    /**
     * Skilar besta vopninu sem leikmadurinn hefur
     * @return besta vopninu
     */
    public Item getBestaVopn() {
        return bestaVopn;
    }

    /**
     * Skilar bestu brynjunni sem leikmadurinn hefur
     * @return bestu brynjunni
     */
    public Item getBestaBrynja() {
        return bestaBrynja;
    }

    public void setLeikmadur(SimpleStringProperty leikmadur) {
        this.leikmadur = leikmadur;
    }

    /**
     * prufunartilvik
     * @param args
     */
    public static void main(String[] args){
        Leikmadur gamer = new Leikmadur("gamer");
        System.out.println(gamer.getLeikmadur());
        System.out.println(gamer.getStadsetningarReitur());
        gamer.faera(5, 24);
        gamer.setLeikmadur("gamer2");
        System.out.println(gamer.getLeikmadur());
        System.out.println(gamer.getStadsetningarReitur());
        gamer.faera(19, 24);
        System.out.println(gamer.getLeikmadur());
        System.out.println(gamer.getStadsetningarReitur());
        gamer.faera(1, 24);
        System.out.println(gamer.getStadsetningarReitur());
    }
}
