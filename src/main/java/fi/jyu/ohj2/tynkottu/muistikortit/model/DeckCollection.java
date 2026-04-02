package fi.jyu.ohj2.tynkottu.muistikortit.model;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DeckCollection {
    final private ObservableList<Deck> decks = FXCollections.observableArrayList(deck -> new Observable[]{
            deck.getTitleProperty(),
            deck.getDescriptionProperty(),
            deck.getSelectedCardIndexProperty(),
            deck.getCards(),
    });
    private final IntegerProperty selectedDeckIndex = new SimpleIntegerProperty(0);
    private Path filePath = Path.of("decks.json");
    private final ObjectMapper mapper = new ObjectMapper();

    public DeckCollection() {
        decks.addListener((ListChangeListener<Deck>) _ -> serialize());
    }

    public DeckCollection(String path) {
        filePath = Path.of(path);
        decks.addListener((ListChangeListener<Deck>) _ -> serialize());
    }

    public int size() {
        return decks.size();
    }

    public void addDeck(String title, String description) {
        decks.add(new Deck(title, description));
        setSelectedDeckIndex(decks.size() - 1);
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
        selectedDeckIndex.setValue(Math.clamp(newSelectedIndex, 0, decks.isEmpty() ? 0 : decks.size() - 1));
    }

    private record SerialFormat(List<Deck> decks, int selectedIndex) {
    }

    public void serialize() {
        mapper.writeValue(filePath, new SerialFormat(decks, selectedDeckIndex.getValue()));
    }

    public void deserialize() {
        if (Files.notExists(filePath)) {
            return;
        }

        try {
            SerialFormat data = mapper.readValue(filePath.toFile(), new TypeReference<>() {
            });
            decks.addAll(data.decks);
            selectedDeckIndex.setValue(data.selectedIndex);
        } catch (JacksonException je) {
            IO.println("Deserialization failed, could not read JSON: " + je.getMessage());
        }
    }
}
