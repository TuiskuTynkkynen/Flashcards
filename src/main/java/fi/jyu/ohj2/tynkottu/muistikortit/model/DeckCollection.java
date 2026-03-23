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

    public DeckCollection() {
    }

    public ObservableList<Deck> getDecks() {
        return decks;
    }

    public int size() {
        return decks.size();
    }

    public void addDeck(String title, String description) {
        decks.add(new Deck(title, description));
    }

    public void removeDeck(Deck deck) {
        if (deck != null) {
            decks.remove(deck);
        }
    }

    public void SelectNext() {
        int index = selectedDeckIndex.get();
        if (index >= decks.size() - 1) {
            selectedDeckIndex.setValue(decks.isEmpty() ? 0 : decks.size() - 1);
        }

        selectedDeckIndex.setValue(index + 1);
    }

    public void SelectPrevious() {
        int index = selectedDeckIndex.get();
        if (index <= 0) {
            selectedDeckIndex.setValue(0);
        }

        selectedDeckIndex.setValue(index - 1);
    }
}
