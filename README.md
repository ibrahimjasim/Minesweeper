# 💣 Minesweeper – Java Console Edition  

> 🎮 *A fully-featured, color-enhanced console Minesweeper game built in Java by Ibrahim, Pinar, and Micke.*

---

## 🧠 Overview  

This version of **Minesweeper** is a complete, interactive console game featuring:  

✅ Player vs CPU or Single-Player mode  
✅ Colored player turns and last-move highlighting  
✅ Smart CPU move selection  
✅ Persistent highscores  
✅ Replay system (play multiple rounds without restarting)  
✅ Name memory (player and CPU names persist across rounds)  
✅ Clean, evenly spaced grid layout  
✅ Safe “Press ENTER to continue” interactions  

---

## 🧩 Features  

### 🎯 Game Modes  
- **Single Player** — Classic Minesweeper against the board.  
- **Player vs CPU** — Take turns with the CPU to reveal safe cells.  

### 🎨 Console Colors  
| Player | Color | Symbol |
|:-------:|:------:|:------:|
| 👤 Player | 🔵 Blue | Safe moves |
| 🤖 CPU | 🔴 Red | Safe moves |
| 💣 Bomb | White “B” | Revealed when hit |

---

## 💡 Gameplay Example  

```
====== Welcome to Minesweeper ======
Created by: Ibrahim, Pinar and Micke
Press ENTER to start...

Enter your name: Ibrahim
Select difficulty:
1. Easy (8x8, 8 mines)
2. Medium (10x10, 15 mines)
3. Hard (12x12, 25 mines)
Choose difficulty: 2

Select Game Mode:
1. Single Player
2. Player vs CPU
Choose mode: 2

Hello Ibrahim! Let's play Minesweeper!
```

Then you’ll see a clean, aligned board:

```
     0  1  2  3  4
    ---------------
 0 |  .  .  1  1  .
 1 |  1  2  B  2  1
 2 |  .  1  1  .  .
 3 |  .  .  .  .  .
```

---

## 🧱 Core Classes  

| Class | Description |
|:------|:-------------|
| **`Main`** | Entry point of the game — displays intro and starts the `GameController`. |
| **`GameController`** | Handles gameplay flow, turns, CPU logic, and round restarts. |
| **`Board`** | Manages the Minesweeper grid, cell logic, win detection, and printing. |
| **`Cell`** | Represents a single cell (mine, flagged, or safe). |
| **`CpuUser`** | AI logic — randomly picks unrevealed cells each turn. |
| **`Player`** | Represents the player and their name. |
| **`HighScore`** | Saves and loads scores from `highscores.txt`. |
| **`IO`** | Handles user input/output and spacing utilities. |

---

## ⚙️ Controls  

| Action | Command |
|:--------|:--------|
| Reveal cell | Enter row and column numbers |
| Continue game | Press **ENTER** when prompted |
| Exit or Skip | Enter **N** when asked “Play another round?” |

---

## 🪄 Spacing & Layout  

The board uses clean console spacing for a professional look:

```java
System.out.printf("%3d |", r);  // row label
System.out.printf("%3s", cellContent); // evenly spaced cells
```

Vertical spacing between game messages and the board is handled by:
```java
IO.printSpacing(2); // adds two blank lines
```

---

## 🧰 Utility: Wait for Enter  

A custom helper ensures that only **Enter key** proceeds:

```java
public static void waitForEnter(String message) {
    System.out.print(message);
    while (true) {
        String input = readString("");
        if (input.isEmpty()) break;
        else System.out.println("Just press ENTER to continue...");
    }
}
```

Used like this:

```java
IO.waitForEnter("Press ENTER to start...");
```

---

## 🏆 Highscore System  

All scores are saved in `highscores.txt` like this:
```
Ibrahim;42
CPU;37
Pinar;50
Micke;41
```

They’re automatically sorted by highest score when displayed:
```
****** HIGHSCORES ******
 1. Pinar           50
 2. Ibrahim         42
 3. CPU             37
 4. Micke           41
========================
```

---

## 🎮 Game Flow Summary  

1. **Welcome screen** → press Enter  
2. **Enter name**  
3. **Choose difficulty**  
4. **Select mode (Single or CPU)**  
5. **Game starts**  
6. After game over → choose to play again or exit  

---

## 💻 Example Run  

```
====== Welcome to Minesweeper ======
Created by: Ibrahim, Pinar and Micke
Press ENTER to start...

Enter your name: Ibrahim
Select difficulty:
1. Easy (8x8, 8 mines)
2. Medium (10x10, 15 mines)
3. Hard (12x12, 25 mines)
Choose difficulty: 1
Select Game Mode:
1. Single Player
2. Player vs CPU
Choose mode: 2

Starting Player vs CPU
Hello Ibrahim! Let's play Minesweeper!

===== Player’s Turn =====

     0  1  2  3  4
    ---------------
 0 |  .  .  1  1  .
 1 |  1  2  B  2  1

Choose row: 1
Choose column: 2
💥 Boom! You hit a mine!

====== GAME OVER ======
Ibrahim’s score: 14
CPU’s score: 17
CPU wins!
========================
Do you want to play another round? (Y/N):
```

---

## 🧑‍💻 Authors  

**Team Minesweeper**  
- Ibrahim  
- Pinar  
- Micke  

---

## 🌟 Bonus Ideas  

You can expand this game by adding:
- Flagging functionality (`toggleFlag`)
- A timer for speed scoring
- Smarter CPU with probability-based moves
- Graphical interface (Swing or JavaFX)

---

**💣 Have fun playing Minesweeper!**  
> “Luck meets logic — can you clear the board?” 🧩  
