package fi.jyu.ohj2.tynkottu.muistikortit.model;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DeckCollection {
    final private ObservableList<Deck> decks = FXCollections.observableArrayList(deck -> new Observable[]{
            deck.getTitleProperty(),
            deck.getDescriptionProperty(),
            deck.getSelectedCardIndexProperty(),
            deck.getCards(),
    });

    private final IntegerProperty selectedDeckIndex = new SimpleIntegerProperty(0);

    public DeckCollection() { }

    public int size() {
        return decks.size();
    }

    public void addDeck(String title, String description) {
        decks.add(new Deck(title, description));
    }

    public void removeDeck(Deck deck) {
        if (deck == null) {
            return;
        }

        decks.remove(deck);
        setSelectedDeckIndex(selectedDeckIndex.get());
    }

    public void selectNext() {
        setSelectedDeckIndex(selectedDeckIndex.get() + 1);
    }

    public void selectPrevious() {
        setSelectedDeckIndex(selectedDeckIndex.get() - 1);
    }

    public Deck getSelectedDeck() {
        int index = selectedDeckIndex.get();
        return (index >= 0 && index < decks.size()) ? decks.get(index) : null;
    }

    public Deck getOffsetDeck(int offset) {
        int index = selectedDeckIndex.get() + offset;
        return (index >= 0 && index < decks.size()) ? decks.get(index) : null;
    }

    public IntegerProperty getSelectedDeckIndexProperty() {
        return selectedDeckIndex;
    }

    public int getSelectedDeckIndex() {
        return selectedDeckIndex.get();
    }

    public void setSelectedDeckIndex(int newSelectedIndex) {
        selectedDeckIndex.setValue(Math.clamp(newSelectedIndex, 0, decks.isEmpty() ? 0 :  decks.size() - 1));
    }
}
