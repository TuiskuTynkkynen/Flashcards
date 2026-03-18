package fi.jyu.ohj2.tynkottu.muistikortit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class DeckViewController implements Initializable {
    @FXML
    private Button deckLeft;
    @FXML
    private Button deckRight;
    @FXML
    private Label deckCenterLabel;
    @FXML
    private Text deckCenterText;
    @FXML
    private Button deckCenterButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deckLeft.setText("Left Deck Title");
        deckRight.setText("Right Deck Title");

        deckCenterLabel.setText("Deck Title");
        deckCenterText.setText("Deck Description");

        deckCenterButton.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER){
                switchToCardView();
            }
        });
        deckCenterButton.setOnMouseClicked(mouseEvent ->  {
            if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2){
                switchToCardView();
            }
        });
    }

    private void switchToCardView() {
        IO.println("switch to card view");
    }

    @FXML
    public void handleDeckCreate(ActionEvent event) {
        IO.println("Create Deck");
    }

    @FXML
    public void handleDeckDelete(ActionEvent event) {
        IO.println("Delete Deck");
    }

    @FXML
    public void handleDeckEdit(ActionEvent event) {
        IO.println("Edit Deck");
    }

    @FXML
    public void handleDeckLeft(ActionEvent event) {
        IO.println("Deck Left");
    }

    @FXML
    public void handleDeckRight(ActionEvent event) {
        IO.println("Deck Right");
    }
}
