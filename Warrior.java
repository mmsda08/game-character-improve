package p1;

public class Warrior extends GameCharacter {
    public Warrior(String name) { super(name); }

    @Override public int attack() { return roll(15, 25); }
    @Override public int specialMove() { return roll(25, 40); }
    @Override public String moveName(boolean special) { return special ? "Shield Bash" : "Mighty Sword"; }
    @Override public String icon() { return "⚔"; }
}

class Assassin extends GameCharacter {
    public Assassin(String name) { super(name); }

    @Override public int attack() { return roll(18, 28); }
    @Override public int specialMove() { return roll(30, 45); }
    @Override public String moveName(boolean special) { return special ? "Backstab" : "Dagger Slash"; }
    @Override public String icon() { return "🗡"; }
    @Override public boolean targetsWeakest(boolean special) { return special; } // Backstab hunts the weakest enemy
}