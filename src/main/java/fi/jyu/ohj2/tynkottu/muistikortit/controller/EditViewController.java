package fi.jyu.ohj2.tynkottu.muistikortit.controller;

import fi.jyu.ohj2.tynkottu.muistikortit.App;
import fi.jyu.ohj2.tynkottu.muistikortit.model.Card;
import fi.jyu.ohj2.tynkottu.muistikortit.model.Deck;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditViewController implements Initializable {
    @FXML
    private Label cardCountLabel;
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
        title.setCellValueFactory(cd -> cd.getValue().getTitleProperty());
        cardTable.getColumns().add(title);

        TableColumn<Card, String> description = new TableColumn<>("Description");
        description.setCellValueFactory(cd -> cd.getValue().getDescriptionProperty());
        cardTable.getColumns().add(description);

        cardTable.getSelectionModel().selectedItemProperty().addListener((_, oldCard, newCard) -> {
            saveCardData(oldCard);
            updateCardDisplay(newCard);
        });
    }

    public void setDeck(Deck deck) {
        this.deck = deck;

        cardTable.setItems(deck.getCards());

        deckTitleField.setText(deck.getTitle());
        deckDescriptionArea.setText(deck.getDescription());
        cardCountLabel.setText("Cards: " + deck.size());
        deck.getCards().addListener((ListChangeListener<Card>) change ->
                cardCountLabel.setText("Cards: " + change.getList().size()));
        updateCardDisplay(null);
    }

    public void updateCardDisplay(Card card) {
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

        cardTitleField.setText(card.getTitle());
        cardDescriptionArea.setText(card.getDescription());
    }

    public void saveCardData(Card card) {
        if (card == null) {
            return;
        }

        card.setTitle(cardTitleField.getText());
        card.setDescription(cardDescriptionArea.getText());
    }

    public void saveDeckData() {
        deck.setTitle(deckTitleField.getText());
        deck.setDescription(deckDescriptionArea.getText());
    }

    @FXML
    void handleCardCreate() {
        deck.addCard("", "");
        cardTable.getSelectionModel().select(deck.getCards().getLast());
        cardTitleField.requestFocus();
    }

    @FXML
    void handleCardDelete() {
        Card card = cardTable.getSelectionModel().getSelectedItem();
        deck.removeCard(card);
    }

    @FXML
    void handleSave() {
        saveCardData(cardTable.getSelectionModel().getSelectedItem());
        saveDeckData();
    }

    @FXML
    void handleExit() {
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
