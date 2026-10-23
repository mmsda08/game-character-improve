[README (2).md](https://github.com/user-attachments/files/33877192/README.2.md)
# java-game-character

# 🎮 Data Abstraction in Java (DSA Lesson)

## 📌 What is Data Abstraction?

* **Abstraction** is the process of showing only the essential details and hiding the implementation.
* In **OOP with Java**, abstraction is usually implemented using **abstract classes** and **interfaces**.
* It lets us focus on _what_ an object does, not _how_ it does it.

## 🐾 Example: Game Characters with Abstraction

We define an **abstract class** `GameCharacter` that all characters must extend.
It forces every character to implement `attack()`, `specialMove()`, `moveName()` and `icon()`, but allows unique behaviors too (`hitsAll()`, `canUse()`, `endTurn()`).

* `Warrior` → *Mighty Sword* (attack) and *Shield Bash* (special)
* `Mage` → *Fireball* (attack) and *Lightning Storm* (special, hits all enemies)

## 🕹️ Sample Output

```
Added Myk to the arena.
Added Lawrence to the arena.
Myk uses Mighty Sword on Lawrence for 21 damage!
Lawrence uses Fireball on Myk for 18 damage!
Myk uses Shield Bash on Lawrence for 32 damage!
Lawrence uses Lightning Storm on everyone for 27 damage!
```

## 📝 Summary

* **Abstraction** = “Define what, hide how.”
* Abstract classes/interfaces define the **rules**.
* Subclasses provide the **details**.
* Our game example makes it clear:
  * `GameCharacter` = abstract base
  * `Warrior`, `Mage` = concrete implementations
