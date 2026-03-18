package fi.jyu.ohj2.tynkottu.muistikortit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

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
}
