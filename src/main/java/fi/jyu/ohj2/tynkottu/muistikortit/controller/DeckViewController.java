package fi.jyu.ohj2.tynkottu.muistikortit.controller;

import fi.jyu.ohj2.tynkottu.muistikortit.App;
import fi.jyu.ohj2.tynkottu.muistikortit.model.Deck;
import fi.jyu.ohj2.tynkottu.muistikortit.model.DeckCollection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DeckViewController implements Initializable {
    @FXML
    private Button deckLeftButton;
    @FXML
    private Button deckRightButton;
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
    @FXML
    private Button deckEditButton;
    final private DeckCollection deckCollection = new DeckCollection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deckCenterButton.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                switchToCardView();
            }
        });
        deckCenterButton.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2) {
                switchToCardView();
            }
        });

        deckCollection.getSelectedDeckIndexProperty().addListener(_ -> updateDisplays());
        updateDisplays();
    }

    private void switchToCardView() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("card_view.fxml"));
            Stage stage = (Stage) deckCenterButton.getScene().getWindow();
            stage.setTitle("Flashcards Play");
            stage.setScene(new Scene(loader.load()));

            CardViewController controller = loader.getController();
            controller.setDeck(deckCollection.getSelectedDeck());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateDisplays() {
        setMainDeckDisplay(deckCollection.getSelectedDeck());
        setSecondaryDeckDisplay(deckLeft, deckCollection.getOffsetDeck(-1));
        setSecondaryDeckDisplay(deckRight, deckCollection.getOffsetDeck(1));

        // Hide buttons, if unable to scroll further
        deckRightButton.setVisible(deckCollection.getSelectedDeckIndex() < deckCollection.size() - 1);
        deckLeftButton.setVisible(deckCollection.getSelectedDeckIndex() > 0);
    }

    private void setMainDeckDisplay(Deck deck) {
        if (deck == null) {
            deckCenterButton.setDisable(true);
            deckCenterText.setText("Create a deck");

            deckCenterLabel.setVisible(false);
            deckEditButton.setVisible(false);
            return;
        }

        deckCenterButton.setDisable(false);
        deckCenterLabel.setText(deck.getTitle());
        deckCenterText.setText(deck.getDescription());

        deckCenterLabel.setVisible(true);
        deckEditButton.setVisible(true);
    }

    private void setSecondaryDeckDisplay(Button display, Deck deck) {
        if (deck == null) {
            display.setVisible(false);
            return;
        }

        display.setVisible(true);
        display.setText(deck.getTitle());
    }

    @FXML
    public void handleDeckCreate() {
        deckCollection.addDeck("Title", "Description");
        deckCollection.setSelectedDeckIndex(deckCollection.size() - 1);

        updateDisplays();
    }

    @FXML
    public void handleDeckDelete() {
        deckCollection.removeDeck(deckCollection.getSelectedDeck());
        updateDisplays();
    }

    @FXML
    public void handleDeckEdit() {
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
    public void handleDeckLeft() {
        deckCollection.selectPrevious();
    }

    @FXML
    public void handleDeckRight() {
        deckCollection.selectNext();
    }
}
