package hi.verkefni.vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Random;

/**
 * Teningur klasinn velur slembitölu milli 1-6 til ad likjast tening
 */
public class Teningur {
    private static final int MAX = 6;
    private static final IntegerProperty talaProperty = new SimpleIntegerProperty(MAX);

    /**
     * Kastar tening þannig að fundinn sé tala af handahófi á bilinu 1 til MAX+1
     */
    public void kasta() {
        Random randomTala = new Random();
        talaProperty.set(randomTala.nextInt((MAX+1) - 1) + 1);
    }

    /**
     * Skilar tolu teningsins sem IntegerProperty
     * @return IntegerProperty af tölunni
     */
    public static IntegerProperty getTala() {
        return talaProperty;
    }

    /**
     * prufunartilvik
     * @param args
     */
    public static void main(String[] args) {
        Teningur teningur = new Teningur();
        teningur.kasta();
        System.out.println(teningur.getTala());
        teningur.kasta();
        System.out.println(teningur.getTala());
        teningur.kasta();
        System.out.println(teningur.getTala());
        teningur.kasta();
        System.out.println(teningur.getTala());
        teningur.kasta();
        System.out.println(teningur.getTala());
        teningur.kasta();
        System.out.println(teningur.getTala());
        teningur.kasta();
        System.out.println(teningur.getTala());
        teningur.kasta();
        System.out.println(teningur.getTala());
        teningur.kasta();
        System.out.println(teningur.getTala());
    }
}
