package hi.verkefni.vinnsla;

public class Item {
    public enum ItemType { WEAPON, ARMOUR }
    private String name;
    private ItemType type;
    private int bonus;

    public Item(String name, ItemType type, int bonus) {
        this.name = name;
        this.type = type;
        this.bonus = bonus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return name + " (" + (type == ItemType.WEAPON ? "Vopn" : "Brynja") + ") með bónus " + bonus;
    }
}