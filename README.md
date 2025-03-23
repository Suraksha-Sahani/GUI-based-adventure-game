# **GUI-Based Adventure Game in Java (JavaFX)**

## **Overview**
This is a simple GUI-based adventure game implemented in **Java** using **JavaFX**. The player navigates through a **10x10 grid** using the keyboard keys **(W, A, S, D)**. The game includes various elements such as:

- **Walls** that block the player’s movement.
- **Power-ups** (represented by a **`?` block**) that enable the player to pass through walls a limited number of times.
- **Pipe** (destination) that triggers a winning event when reached.

When the player reaches the pipe, a winning image is displayed, and they are given the option to either **play again** or **quit**.

---

## **Game Features**

### **Player Controls**
- **GridPane**: The game board is represented by a **10x10 grid**.
- **Movement Controls**:
  - **W (up)**: Moves the player one step **up** (decreases row position).
  - **A (left)**: Moves the player one cell **left**.
  - **S (down)**: Moves the player one cell **down**.
  - **D (right)**: Moves the player one cell **right**.

---

### **Game Elements**

- **Walls**:
  - Represented by **ImageView** elements that block the player's movement.
  - The player can only pass through walls if a **power-up** is active.

- **Power-up (‘?’ Block)**:
  - When the player collects the **‘?’ block**, the power-up becomes active.
  - This allows the player to pass through walls **twice** before the power-up expires.

- **Pipe**:
  - The player must reach the **pipe** to win the game.
  - A **winning image** is shown and an alert appears with options to **restart** or **quit** the game.

---

### **Handling User Inputs**

- The player’s movement is controlled by pressing the **W, A, S, D** keys.
- Any other key inputs are **invalid** and won’t affect the game.
- If the player collides with a wall, movement is restricted unless a power-up is active.
- When the player collects the **‘?’ block**, the power-up is activated, allowing the player to pass through walls up to **two times**.

---

## **Running the Game**

### **Clone the Repository and Navigate to the Project Directory and run JavaFX project using maven**
Clone the repository to your local machine and navigate into the project directory by running the following commands in your terminal:

```bash
git clone https://github.com/Suraksha-Sahani/GUI-based-adventure-game.git
cd GUI-based-adventure-game
mvn javafx:run
