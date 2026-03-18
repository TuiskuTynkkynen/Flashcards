package fi.jyu.ohj2.tynkottu.muistikortit.controller;

import fi.jyu.ohj2.tynkottu.muistikortit.App;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("card_view.fxml"));
            Stage stage = (Stage) deckCenterButton.getScene().getWindow();
            stage.setTitle("Flashcards Play");
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("edit_view.fxml"));
            Stage stage = (Stage) deckCenterButton.getScene().getWindow();
            stage.setTitle("Flashcards Edit");
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
