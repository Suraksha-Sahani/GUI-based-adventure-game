package com.adventure.textbasedgui;

import java.util.HashSet;
import java.util.Set;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 *
 * @author surakshasahani
 */
public class PrimaryController {

    @FXML
    private GridPane gridId;
    @FXML
    private ImageView iv00;
    @FXML
    private ImageView iv10;
    @FXML
    private ImageView iv20;
    @FXML
    private ImageView iv30;
    @FXML
    private ImageView iv40;
    @FXML
    private ImageView iv50;
    @FXML
    private ImageView iv60;
    @FXML
    private ImageView iv70;
    @FXML
    private ImageView iv80;
    @FXML
    private ImageView iv90;
    @FXML
    private ImageView iv01;
    @FXML
    private ImageView iv02;
    @FXML
    private ImageView iv03;
    @FXML
    private ImageView iv04;
    @FXML
    private ImageView iv05;
    @FXML
    private ImageView iv06;
    @FXML
    private ImageView iv07;
    @FXML
    private ImageView iv08;
    @FXML
    private ImageView iv09;
    @FXML
    private ImageView iv19;
    @FXML
    private ImageView iv29;
    @FXML
    private ImageView iv39;
    @FXML
    private ImageView iv49;
    @FXML
    private ImageView iv59;
    @FXML
    private ImageView iv69;
    @FXML
    private ImageView iv79;
    @FXML
    private ImageView iv89;
    @FXML
    private ImageView iv99;
    @FXML
    private ImageView iv91;
    @FXML
    private ImageView iv92;
    @FXML
    private ImageView iv93;
    @FXML
    private ImageView iv94;
    @FXML
    private ImageView iv95;
    @FXML
    private ImageView iv96;
    @FXML
    private ImageView iv97;
    @FXML
    private ImageView iv98;
    @FXML
    private ImageView iv31;
    @FXML
    private ImageView iv32;
    @FXML
    private ImageView iv33;
    @FXML
    private ImageView iv34;
    @FXML
    private ImageView iv35;
    @FXML
    private ImageView iv58;
    @FXML
    private ImageView iv57;
    @FXML
    private ImageView iv56;
    @FXML
    private ImageView iv55;
    @FXML
    private ImageView iv54;
    @FXML
    private ImageView iv53;
    @FXML
    private ImageView iv63;
    @FXML
    private ImageView iv73;
    @FXML
    private ImageView iv86;
    @FXML
    private ImageView iv76;

    @FXML
    private ImageView ivPlayer, ivWinner, ivPipe, ivQues;

    private GameBoard gameLogic;

    private final Set<ImageView> wallBricks = new HashSet<>();

    public void initialize() {
        gridId.setFocusTraversable(true);
        gameLogic = new GameBoard(gridId, ivPlayer, ivWinner, ivPipe, ivQues, this);
        gridId.setOnKeyPressed(this::handleKeyPress);
        initializeObstacles();
    }

    private void initializeObstacles() {
        ImageView[] walls = {iv00, iv10, iv20, iv30, iv40, iv50, iv60, iv70, iv80, iv90,
            iv01, iv02, iv03, iv04, iv05, iv06, iv07, iv08, iv09,
            iv19, iv29, iv39, iv49, iv59, iv69, iv79, iv89, iv99,
            iv91, iv92, iv93, iv94, iv95, iv96, iv97, iv98,
            iv31, iv32, iv33, iv34, iv35, iv58, iv57, iv56, iv55, iv54, iv53,
            iv63, iv73, iv86, iv76};

        int addedCount = 0;
        for (ImageView wall : walls) {
            if (wall != null) {
                Integer colIndex = GridPane.getColumnIndex(wall);
                Integer rowIndex = GridPane.getRowIndex(wall);

                if (colIndex == null || rowIndex == null) {
                    if (wall.getId() != null && wall.getId().matches("iv\\d{2}")) {
                        int col = Character.getNumericValue(wall.getId().charAt(2));
                        int row = Character.getNumericValue(wall.getId().charAt(3));
                        GridPane.setColumnIndex(wall, col);
                        GridPane.setRowIndex(wall, row);
                    }
                }

                wallBricks.add(wall);
                addedCount++;
            }
        }
        System.out.println("Total bricks added to wallBricks: " + addedCount);

    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        gameLogic.movePlayer(event.getCode());
    }

    public boolean isValidMove(int x, int y) {

        for (ImageView wall : wallBricks) {
            Integer colIndex = GridPane.getColumnIndex(wall);
            Integer rowIndex = GridPane.getRowIndex(wall);

            if ((colIndex != null && colIndex == x) && (rowIndex != null && rowIndex == y)) {
                System.out.println("Collision detected at: " + x + ", " + y);
                return false; // Collision detected
            }
        }
        return true;
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showWinDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("You won! Do you want to play again?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                gameLogic.resetGame();
            } else {
                Platform.exit();
            }
        });
    }
}
