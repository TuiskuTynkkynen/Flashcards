package fi.jyu.ohj2.tynkottu.muistikortit.controller;

import fi.jyu.ohj2.tynkottu.muistikortit.App;
import fi.jyu.ohj2.tynkottu.muistikortit.model.Card;
import fi.jyu.ohj2.tynkottu.muistikortit.model.Deck;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditViewController implements Initializable {
    @FXML
    private Label cardCountLabel;
    @FXML
    private Label cardTitleError;
    @FXML
    private Label cardDescriptionError;
    @FXML
    private Label deckTitleError;
    @FXML
    private Button exitButton;
    @FXML
    private Button cardDeleteButton;
    @FXML
    private TextArea cardDescriptionArea;
    @FXML
    private TableView<Card> cardTable;
    @FXML
    private TextField cardTitleField;
    @FXML
    private TextArea deckDescriptionArea;
    @FXML
    private TextField deckTitleField;
    private Deck deck;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<Card, String> title = new TableColumn<>("Title");
        title.setCellValueFactory(cd -> {
            if (cd.getValue().getTitle().isEmpty()) {
                cd.getValue().setTitle("Untitled");
            }
            return cd.getValue().getTitleProperty();
        });
        cardTable.getColumns().add(title);

        TableColumn<Card, String> description = new TableColumn<>("Description");
        description.setCellValueFactory(cd -> cd.getValue().getDescriptionProperty());
        cardTable.getColumns().add(description);

        cardTable.getSelectionModel().selectedItemProperty().addListener((_, oldCard, newCard) -> {
            if (oldCard != newCard && validateCard(newCard) && !validateCard(oldCard)) {
                Platform.runLater(() -> cardTable.getSelectionModel().select(oldCard));
                return;
            }

            updateCardDisplay(newCard);
        });

        cardTitleField.textProperty().addListener(_ -> saveCardData(cardTable.getSelectionModel().getSelectedItem()));
        cardDescriptionArea.textProperty().addListener(_ -> saveCardData(cardTable.getSelectionModel().getSelectedItem()));
    }

    public void setDeck(Deck deck) {
        this.deck = deck;

        cardTable.setItems(deck.getCards());

        deckTitleField.setText(deck.getTitle());
        deckDescriptionArea.setText(deck.getDescription());
        deckTitleField.textProperty().addListener(_ -> {
            saveDeckData();
            validateDeck();
        });
        deckDescriptionArea.textProperty().addListener(_ -> {
            saveDeckData();
            validateDeck();
        });

        cardCountLabel.setText("Cards: " + deck.size());
        deck.getCards().addListener((ListChangeListener<Card>) change ->
                cardCountLabel.setText("Cards: " + change.getList().size()));
        updateCardDisplay(null);
    }

    private void updateCardDisplay(Card card) {
        if (card == null) {
            cardDeleteButton.setDisable(true);
            cardTitleField.setDisable(true);
            cardDescriptionArea.setDisable(true);

            cardTitleField.setText("");
            cardDescriptionArea.setText("");
            return;
        }

        cardDeleteButton.setDisable(false);
        cardTitleField.setDisable(false);
        cardDescriptionArea.setDisable(false);

        String title = card.getTitle(), description = card.getDescription();
        cardTitleField.setText(title);
        cardDescriptionArea.setText(description);
    }

    private void saveCardData(Card card) {
        if (card == null) {
            return;
        }

        card.setTitle(cardTitleField.getText());
        card.setDescription(cardDescriptionArea.getText());
    }

    private void saveDeckData() {
        deck.setTitle(deckTitleField.getText());
        deck.setDescription(deckDescriptionArea.getText());
    }

    private void setErrorEnabled(Label errorLabel, boolean enabled) {
        errorLabel.setVisible(enabled);
        errorLabel.setPadding(new Insets(enabled ? 0 : -100));
    }

    private boolean validateCard(Card card) {
        if (card == null) {
            return true;
        }

        boolean invalidDescription = card.getDescription().isBlank();
        setErrorEnabled(cardDescriptionError, invalidDescription);
        if (invalidDescription) cardDescriptionArea.requestFocus();

        boolean invalidTitle = card.getTitle().isBlank();
        setErrorEnabled(cardTitleError, invalidTitle);
        if (invalidTitle) cardTitleField.requestFocus();

        return !invalidTitle && !invalidDescription;
    }

    private boolean validateCards() {
        for (Card card : deck.getCards()) {
            if (!validateCard(card)) {
                cardTable.getSelectionModel().select(card);
                return false;
            }
        }
        return true;
    }

    private boolean validateDeck() {
        boolean invalidTitle = deck.getTitle().isBlank();
        setErrorEnabled(deckTitleError, invalidTitle);
        if (invalidTitle) deckTitleField.requestFocus();

        return !invalidTitle;
    }

    @FXML
    private void handleCardCreate() {
        if (!validateCard(cardTable.getSelectionModel().getSelectedItem())) return;
        deck.addCard("", "");
        cardTable.getSelectionModel().select(deck.getCards().getLast());
        cardTitleField.requestFocus();
    }

    @FXML
    private void handleCardDelete() {
        Card card = cardTable.getSelectionModel().getSelectedItem();
        card.setTitle("deleted");
        card.setDescription("deleted");
        deck.removeCard(card);
    }

    @FXML
    private void handleExit() {
        if (!validateDeck() || !validateCards()) return;

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
