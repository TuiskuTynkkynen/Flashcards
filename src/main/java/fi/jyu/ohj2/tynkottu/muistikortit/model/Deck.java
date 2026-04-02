package fi.jyu.ohj2.tynkottu.muistikortit.model;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;
import java.util.List;

public class Deck {
    private final StringProperty title = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final IntegerProperty selectedCardIndex = new SimpleIntegerProperty(0);
    final private ObservableList<Card> cards = FXCollections.observableArrayList(card -> new Observable[]{
            card.getTitleProperty(),
            card.getDescriptionProperty(),
            card.getFlippedProperty(),
    });

    @SuppressWarnings("unused")
    public Deck() { }

    public Deck(String title, String description) {
        setTitle(title);
        setDescription(description);
    }

    public ObservableList<Card> getCards() {
        return cards;
    }

    @SuppressWarnings("unused")
    public void setCards(List<Card> cards) {
        this.cards.clear();
        this.cards.addAll(cards);
    }

    public int size() {
        return cards.size();
    }

    public void addCard(String title, String description) {
        cards.add(new Card(title, description));
    }

    public void removeCard(Card card) {
        if (card == null) {
            return;
        }

        cards.remove(card);
        setSelectedCardIndex(selectedCardIndex.get());
    }

    public void shuffle() {
        selectedCardIndex.setValue(0);
        Collections.shuffle(cards);
        cards.forEach(card -> card.setFlipped(false));
    }

    public void selectNext() {
        setSelectedCardIndex(selectedCardIndex.get() + 1);
    }

    public void selectPrevious() {
        setSelectedCardIndex(selectedCardIndex.get() - 1);
    }

    public StringProperty getTitleProperty() {
        return title;
    }

    public String getTitle() {
        return title.getValue();
    }

    public void setTitle(String newTitle) {
        title.setValue(newTitle.trim());
    }

    public StringProperty getDescriptionProperty() {
        return description;
    }

    public String getDescription() {
        return description.getValue();
    }

    public void setDescription(String newDescription) {
        description.setValue(newDescription.trim());
    }

    public Card getSelectedCard() {
        int index = selectedCardIndex.get();
        return (index >= 0 && index < cards.size()) ? cards.get(index) : null;
    }

    public IntegerProperty getSelectedCardIndexProperty() {
        return selectedCardIndex;
    }

    public int getSelectedCardIndex() {
        return selectedCardIndex.get();
    }

    public void setSelectedCardIndex(int newSelectedIndex) {
        selectedCardIndex.setValue(Math.clamp(newSelectedIndex, 0, cards.isEmpty() ? 0 : cards.size() - 1));
    }
}
