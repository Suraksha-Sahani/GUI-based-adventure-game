package com.adventure.textbasedgui;

/**
 *
 * @author surakshasahani
 */
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class GameLogic {

    private final GridPane grid;
    private final ImageView player, winner, pipe, questionBlock;

    private int playerX = 1, playerY = 1;
    private boolean hasPowerUp = false;
    private int powerUpUses = 0;
    private final PrimaryController controller;
    private boolean runningState = true;

    public GameLogic(GridPane grid, ImageView player, ImageView winner, ImageView pipe, ImageView questionBlock, PrimaryController controller) {
        this.grid = grid;
        this.player = player;
        this.winner = winner;
        this.pipe = pipe;
        this.questionBlock = questionBlock;
        this.controller = controller;

    }

    public void movePlayer(KeyCode keyCode) {

        if (!runningState) {
            return; // Don't allow movement if the game is won or over
        }
        int newColumn = playerX; // X-axis (left to right)
        int newRow = playerY;    // Y-axis (top to bottom)

        switch (keyCode) {
            case W:
                newRow--; // Move UP
                break;
            case A:
                newColumn--; // Move LEFT
                break;
            case S:
                newRow++; // Move DOWN
                break;
            case D:
                newColumn++; // Move RIGHT
                break;
            default:
                System.out.println("Invalid command! \n"
                        + "Use (W A S D) for player move.");
        }

        System.out.println("Attempting to Move To: " + newColumn + ", " + newRow);

        // Check if player collects the '?' block
        if (newColumn == GridPane.getColumnIndex(questionBlock) && newRow == GridPane.getRowIndex(questionBlock)) {
            collectPowerUp();
        }

        // Check if player reaches the pipe
        if (newColumn == GridPane.getColumnIndex(pipe)
                && newRow == GridPane.getRowIndex(pipe)) {
            handleWinningState();
            return;
        }

        // Check player is within boundary and not colliding with walls
        if (newColumn >= 0 && newColumn < 10 && newRow >= 0 && newRow < 10) {
            if (controller.isValidMove(newColumn, newRow)) {
                playerX = newColumn;
                playerY = newRow;
                updatePlayerPosition();
            } else if (hasPowerUp && powerUpUses > 0) {
                powerUpUses--;
                playerX = newColumn;
                playerY = newRow;
                updatePlayerPosition();
                System.out.println("Remaining uses: " + powerUpUses);
                if (powerUpUses == 0) {
                    hasPowerUp = false;
                }
            } else if (isStuck()) {
                handleGameOver();
            } else {
                showCollisionMessage();

            }
        }
    }

    private void updatePlayerPosition() {
        GridPane.setColumnIndex(player, playerX);
        GridPane.setRowIndex(player, playerY);
    }

    private void collectPowerUp() {
        hasPowerUp = true;
        powerUpUses = 2;
        questionBlock.setVisible(false);
    }

    private void handleWinningState() {
        playerX = GridPane.getColumnIndex(pipe);
        playerY = GridPane.getRowIndex(pipe);
        updatePlayerPosition();
        winner.setVisible(true);
        System.out.println("Cogratulations! you won!");
        runningState = false;

        controller.showWinDialog();
    }

    public void resetGame() {
        playerX = 1;
        playerY = 1;
        hasPowerUp = false;
        powerUpUses = 0;
        winner.setVisible(false);
        questionBlock.setVisible(true);
        updatePlayerPosition();

        runningState = true;

    }

    private void showCollisionMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Collision Detected!");
        alert.setHeaderText(null);
        alert.setContentText("You hit a wall! Try another direction.");
        alert.showAndWait();
    }

    private boolean isStuck() {
        int maxColumns = grid.getColumnConstraints().size();
        int maxRows = grid.getRowConstraints().size();

        boolean upBlocked = (playerY - 1 < 0) || !controller.isValidMove(playerX, playerY - 1);
        boolean downBlocked = (playerY + 1 >= maxRows) || !controller.isValidMove(playerX, playerY + 1);
        boolean leftBlocked = (playerX - 1 < 0) || !controller.isValidMove(playerX - 1, playerY);
        boolean rightBlocked = (playerX + 1 >= maxColumns) || !controller.isValidMove(playerX + 1, playerY);

        // Handling corners separately
        if (playerX == maxColumns - 1 && playerY == 0) { // Top-right corner
            return leftBlocked && downBlocked;
        }
        if (playerX == 0 && playerY == 0) { // Top-left corner
            return rightBlocked && downBlocked;
        }
        if (playerX == 0 && playerY == maxRows - 1) { // Bottom-left corner
            return upBlocked && rightBlocked;
        }
        if (playerX == maxColumns - 1 && playerY == maxRows - 1) { // Bottom-right corner
            return upBlocked && leftBlocked;
        }

        return upBlocked && downBlocked && leftBlocked && rightBlocked;
    }

    private void handleGameOver() {
        // Display a Game Over message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("You are stuck and cannot move anymore. The game is over!");
        ButtonType replay = new ButtonType("Play Again");
        ButtonType exit = new ButtonType("Exit");

        alert.getButtonTypes().setAll(replay, exit);

        alert.showAndWait().ifPresent(response -> {
            if (response == replay) {
                resetGame();
            } else {
                Platform.exit();

            }
        });
    }

}
