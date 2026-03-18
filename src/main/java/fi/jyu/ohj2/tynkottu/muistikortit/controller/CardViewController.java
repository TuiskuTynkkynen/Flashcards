package fi.jyu.ohj2.tynkottu.muistikortit.controller;

import fi.jyu.ohj2.tynkottu.muistikortit.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CardViewController {
    @FXML
    private Button cardCenterButton;
    @FXML
    private Text cardCenterText;
    @FXML
    private Button cardLeftButton;
    @FXML
    private Label cardNumberLabel;
    @FXML
    private Button cardRightButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button cardTurnButton;
    @FXML
    private Label deckTitleLabel;

    @FXML
    void handleCardLeft(ActionEvent event) {
        IO.println("Card Left");
    }

    @FXML
    void handleCardRight(ActionEvent event) {
        IO.println("Card Right");
    }

    @FXML
    void handleCardTurn(ActionEvent event) {
        IO.println("Turn Card");
    }

    @FXML
    void handleExit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("deck_view.fxml"));
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.setTitle("Flashcards");
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
