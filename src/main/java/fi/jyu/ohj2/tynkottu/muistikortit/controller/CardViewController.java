package fi.jyu.ohj2.tynkottu.muistikortit.controller;

import fi.jyu.ohj2.tynkottu.muistikortit.App;
import fi.jyu.ohj2.tynkottu.muistikortit.model.Card;
import fi.jyu.ohj2.tynkottu.muistikortit.model.Deck;
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
    private Deck deck;

    public void setDeck(Deck deck) {
        this.deck = deck;
        deck.shuffle();

        deckTitleLabel.setText(deck.getTitle());
        deck.getSelectedCardIndexProperty().addListener(_ -> updateDisplays());
        updateDisplays();
    }

    public void updateDisplays() {
        updateCardDisplay(deck.getSelectedCard());

        int index = deck.getSelectedCardIndex(), size = deck.size();
        cardNumberLabel.setText((index + 1) + " / " + size);

        // Hide buttons, if unable to scroll further
        cardRightButton.setVisible(index < size - 1);
        cardLeftButton.setVisible(index > 0);
    }

    public void updateCardDisplay(Card card) {
        if (card == null) {
            cardCenterButton.setDisable(true);
            cardTurnButton.setVisible(false);

            cardCenterText.setText("This deck has no cards");
            return;
        }

        cardCenterButton.setDisable(false);
        cardTurnButton.setVisible(true);

        String text = card.getFlipped() ? card.getDescription() : card.getTitle();
        cardCenterText.setText(text);
    }

    @FXML
    private void handleCardLeft() {
        deck.selectPrevious();
    }

    @FXML
    private void handleCardRight() {
        deck.selectNext();
    }

    @FXML
    private void handleCardTurn() {
        Card card = deck.getSelectedCard();
        if (card != null) {
            card.setFlipped(!card.getFlipped());
            updateCardDisplay(card);
        }
    }

    @FXML
    private void handleExit() {
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
