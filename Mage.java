package p1;

public class Mage extends GameCharacter {
    private int mana = 100;
    private static final int MAX_MANA = 100, ATTACK_COST = 10, SPECIAL_COST = 40;

    public Mage(String name) { super(name); }

    @Override public int attack() { mana -= ATTACK_COST; return roll(10, 30); }
    @Override public int specialMove() { mana -= SPECIAL_COST; return roll(20, 35); }
    @Override public String moveName(boolean special) { return special ? "Lightning Storm" : "Fireball"; }
    @Override public String icon() { return "✦"; }

    @Override public boolean canUse(boolean special) { return mana >= (special ? SPECIAL_COST : ATTACK_COST); }
    @Override public boolean hitsAll(boolean special) { return special; }   // hits all enemies
    @Override public void endTurn() { mana = Math.min(MAX_MANA, mana + 15); }
    @Override public int getMana() { return mana; }
    @Override public int getMaxMana() { return MAX_MANA; }
}

class Healer extends GameCharacter {
    private int mana = 100;
    private static final int MAX_MANA = 100, HEAL_COST = 30;

    public Healer(String name) { super(name); }

    @Override public int attack() { return roll(8, 15); }
    @Override public int specialMove() { mana -= HEAL_COST; return roll(25, 40); }
    @Override public String moveName(boolean special) { return special ? "Holy Light" : "Smite"; }
    @Override public String icon() { return "✚"; }

    @Override public boolean canUse(boolean special) { return !special || mana >= HEAL_COST; }
    @Override public boolean heals(boolean special) { return special; }     // heals the weakest ally
    @Override public void endTurn() { mana = Math.min(MAX_MANA, mana + 15); }
    @Override public int getMana() { return mana; }
    @Override public int getMaxMana() { return MAX_MANA; }
}