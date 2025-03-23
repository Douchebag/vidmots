package hi.verkefni.vinnsla;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * SlonguStigar klasinn heldur utan um upplysinga slanga og stiga a bordinu
 */
public class Items {
    private HashMap<Integer, Item> items;
    private SimpleStringProperty skrefUppl;

    /**
     * Smidur fyrir slongustiga
     * Smidurinn skilgreinir stadsetningu slanga og stiga
     */
    public Items() {
        this.items = new HashMap<>();
        skrefUppl = new SimpleStringProperty("");
        populateItems();
    }

    private void populateItems() {
        List<Item> allItems = new ArrayList<>();

        // vopn
        allItems.add(new Item("Bronze Scimitar", Item.ItemType.WEAPON, 6));
        allItems.add(new Item("Steel Longsword", Item.ItemType.WEAPON, 16));
        allItems.add(new Item("Mithril Scimitar", Item.ItemType.WEAPON, 20));
        allItems.add(new Item("Adamant Longsword", Item.ItemType.WEAPON, 31));
        allItems.add(new Item("Rune Scimitar", Item.ItemType.WEAPON, 44));
        allItems.add(new Item("Granite Maul", Item.ItemType.WEAPON, 79));
        allItems.add(new Item("Dragon Dagger", Item.ItemType.WEAPON, 40));
        allItems.add(new Item("Dragon Scimitar", Item.ItemType.WEAPON, 66));
        allItems.add(new Item("Dragon Warhammer", Item.ItemType.WEAPON, 85));
        allItems.add(new Item("Abyssal Whip", Item.ItemType.WEAPON, 82));
        allItems.add(new Item("Dharok's Greataxe", Item.ItemType.WEAPON, 105));
        allItems.add(new Item("Armadyl Godsword", Item.ItemType.WEAPON, 132));

        // armour
        allItems.add(new Item("Bronze Platebody", Item.ItemType.ARMOUR, 15));
        allItems.add(new Item("Steel Platebody", Item.ItemType.ARMOUR, 32));
        allItems.add(new Item("Mithril Platebody", Item.ItemType.ARMOUR, 46));
        allItems.add(new Item("Adamant Platebody", Item.ItemType.ARMOUR, 65));
        allItems.add(new Item("Rune Platebody", Item.ItemType.ARMOUR, 82));
        allItems.add(new Item("Dragon Platebody", Item.ItemType.ARMOUR, 109));
        allItems.add(new Item("Helm of Neitiznot", Item.ItemType.ARMOUR, 31));
        allItems.add(new Item("Fighter Torso", Item.ItemType.ARMOUR, 62));
        allItems.add(new Item("Dragon Defender", Item.ItemType.ARMOUR, 25));
        allItems.add(new Item("Bandos Chestplate", Item.ItemType.ARMOUR, 98));
        allItems.add(new Item("Torag’s Platebody", Item.ItemType.ARMOUR, 122));
        allItems.add(new Item("Torva Platebody", Item.ItemType.ARMOUR, 117));

        Collections.shuffle(allItems);

        for (int tile = 1; tile <= 24; tile++) {
            items.put(tile, allItems.get(tile - 1));
        }
    }

    public Item getItemAtTile(int tile) {
        if (items.containsKey(tile)) {
            Item pickedItem = items.get(tile);
            skrefUppl.set("Þú fékkst: " + pickedItem.toString());
            items.remove(tile);
            return pickedItem;
        } else {
            skrefUppl.set("Enginn hlutur á reit " + tile);
            return null;
        }
    }

    /**
     * skilar iteminu á tile-i, notað fyrir að setja items á borðið
     * @param tile
     * @return
     */
    public Item getItemAtTileForDisplay(int tile) {
        if (items.containsKey(tile)) {
            Item pickedItem = items.get(tile);
            skrefUppl.set("Þú fékkst: " + pickedItem.toString());
            return pickedItem;
        } else {
            skrefUppl.set("Enginn hlutur á reit " + tile);
            return null;
        }
    }

    /**
     * Skilar reitnum sem leikmaður lendir á. Ef reiturinn hefur einhver item, birtir hann skilaboð um hvaða item var tekið upp
     * og fjarlægir iteminn úr borðinu svo hann verði ekki tekinn upp tvisvar.
     * @param reitur reiturinn sem leikmaður lenti á
     * @return reiturinn sem leikmaður er færður á
     */
    public int getLendingarReitur(int reitur) {
        if (items.containsKey(reitur)) {
            Item pickedItem = items.get(reitur);
            skrefUppl.set("Þú fékkst: " + pickedItem.toString());

            items.remove(reitur);
        } else {
            skrefUppl.set("Enginn hlutur á reit " + reitur);
        }
        return reitur;
    }

    /**
     * Skilar upplysingum um hvaða item var tekið upp
     * @return
     */
    public String getSkrefUppl() {
        return skrefUppl.get();
    }

    /**
     * prufunartilvik
     * @param args
     */
    public static void main(String[] args) {
        Items leikbord = new Items();

        int[] tilraunir = {1, 5, 11, 17, 19, 21};
        for (int reitur : tilraunir) {
            int nyrReitur = leikbord.getLendingarReitur(reitur);
            System.out.println(leikbord.getSkrefUppl());
        }
    }

}
