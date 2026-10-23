[Uploading README (3).md…]()
# java-game-character

# 🎮 Data Abstraction in Java (DSA Lesson)

## 📌 What is Data Abstraction?

* **Abstraction** is the process of showing only the essential details and hiding the implementation.
* In **OOP with Java**, abstraction is usually implemented using **abstract classes** and **interfaces**.
* It lets us focus on _what_ an object does, not _how_ it does it.
* The code that uses an object only needs to know the **methods** it offers, not the **details** inside them.

---

## 🐾 Example: Game Characters with Abstraction

We define an **abstract class** `GameCharacter` that all characters must extend.
It forces every character to implement `attack()`, `specialMove()`, `moveName()` and `icon()`, but allows unique behaviors too (`hitsAll()`, `canUse()`, `endTurn()`).

```java
public abstract class GameCharacter {
    // Must be implemented by every subclass
    public abstract int attack();
    public abstract int specialMove();
    public abstract String moveName(boolean special);
    public abstract String icon();

    // Defaults that a subclass can override
    public boolean canUse(boolean special) { return true; }
    public boolean hitsAll(boolean special) { return false; }
    public void endTurn() { }
}
```

The two concrete characters fill in the details:

| | ⚔ Warrior | ✦ Mage |
|---|---|---|
| **Attack** | *Mighty Sword* (15-25 damage) | *Fireball* (10-30 damage, costs 10 mana) |
| **Special** | *Shield Bash* (25-40 damage) | *Lightning Storm* (20-35 damage, costs 40 mana, hits **all** enemies) |
| **Mana** | None | Starts at 100, regains 15 at the end of each turn |
| **HP** | 100 | 100 |

---

## 🎯 How the Game Works

1. Type a name, pick **Warrior** or **Mage**, and click **Add Character**. Add at least 2 characters.
2. Characters wait in a **queue**. The one at the front (marked with ▶) takes the turn.
3. Click **Attack** or **Special Move**. The front character hits the next one in the queue.
4. After its turn, the character goes to the **back** of the queue.
5. A character at 0 HP is defeated and removed. The **last one standing wins!**

The Mage cannot attack or use a special move without enough mana. The game shows a warning if you try.

---

## 🕹️ Sample Output

```
Added Myk to the arena.
Added Lawrence to the arena.
Myk uses Mighty Sword on Lawrence for 21 damage!
Lawrence uses Fireball on Myk for 18 damage!
Myk uses Shield Bash on Lawrence for 32 damage!
Lawrence uses Lightning Storm on everyone for 27 damage!
Myk uses Shield Bash on Lawrence for 38 damage!
Lawrence uses Fireball on Myk for 25 damage!
Myk uses Mighty Sword on Lawrence for 19 damage!
Lawrence was defeated!
Winner: Myk!
```

---

## 🧩 Concepts Applied

| Concept | Where it is used |
|---------|------------------|
| **Abstraction** | `GameCharacter` abstract class. `Main.act()` only calls its methods and never checks which character type it has (polymorphism) |
| **Inheritance** | `Warrior` and `Mage` both `extend GameCharacter` |
| **ADT** | `Queue<GameCharacter>` (`LinkedList`) for turn order: `peek`, `poll`, `offer`, `remove` |
| **GUI** | Java Swing: `JFrame`, layouts, `JProgressBar`, `JTextArea`, `JMenuBar`, custom-painted `Arena` panel |
| **User input** | Name field, class dropdown, buttons, and `JOptionPane` dialogs for errors and help |

---

## ✅ Input Validation

* Names must be **1-12 letters, numbers, or spaces**.
* Names must be **unique** (not case-sensitive).
* You need **at least 2 characters** before you can battle.
* A Mage must have **enough mana** for the move it picks.
* **Remove Front** shows a message if the queue is empty.

---

## 🧾 Menus

| Menu | Item | What it does |
|------|------|--------------|
| **Game** | New Game | Clears the queue and the log (`Ctrl+N`) |
| **Game** | Clear Log | Clears only the battle log |
| **Game** | Exit | Asks for confirmation, then closes the game |
| **Help** | How to Play | Shows the game rules |
| **Help** | About | Shows what the project demonstrates |

---

## ▶️ How to Run

Requires **JDK 11+**. Keep the files in a folder named `p1` (they use `package p1;`).

```bash
mkdir out
javac -encoding UTF-8 -d out *.java
java -cp out p1.Main
```

Or open the project in IntelliJ / Eclipse / VS Code and run `Main`.

---

## 📁 Files

| File | Purpose |
|------|---------|
| `Main.java` | GUI, queue, and turn logic |
| `GameCharacter.java` | Abstract base class |
| `Warrior.java` | `Warrior` class |
| `Mage.java` | `Mage` class |

---

## 📝 Summary

* **Abstraction** = “Define what, hide how.”
* Abstract classes/interfaces define the **rules**.
* Subclasses provide the **details**.
* Our game example makes it clear:
  * `GameCharacter` = abstract base
  * `Warrior`, `Mage` = concrete implementations
* Characters **Myk** and **Lawrence** show how the same `attack()` and `specialMove()` calls behave differently for each class.
