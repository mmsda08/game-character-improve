package p1;

public abstract class GameCharacter {
    protected String name;
    protected int hp = 100;
    protected int team = 1;
    public static final int MAX_HP = 100;

    public GameCharacter(String name) { this.name = name; }

    // Abstract methods (must be implemented by subclasses)
    public abstract int attack();
    public abstract int specialMove();
    public abstract String moveName(boolean special);
    public abstract String icon();

    // Defaults (subclasses override what they need)
    public boolean canUse(boolean special) { return true; }
    public boolean hitsAll(boolean special) { return false; }
    public boolean heals(boolean special) { return false; }
    public boolean targetsWeakest(boolean special) { return false; }
    public void endTurn() { }
    public int getMana() { return 0; }
    public int getMaxMana() { return 0; }

    // Concrete methods
    public String showName() { return icon() + " " + name + " (" + getClass().getSimpleName() + ")"; }
    protected int roll(int min, int max) { return min + (int) (Math.random() * (max - min + 1)); }
    public void takeDamage(int d) { hp = Math.max(0, hp - d); }
    public void heal(int h) { hp = Math.min(MAX_HP, hp + h); }
    public boolean isAlive() { return hp > 0; }
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getTeam() { return team; }
    public void setTeam(int team) { this.team = team; }
}