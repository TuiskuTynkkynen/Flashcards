package fi.jyu.ohj2.tynkottu.muistikortit.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Card {
    private final StringProperty title = new SimpleStringProperty("");
    private final StringProperty description = new SimpleStringProperty("");
    private final BooleanProperty isFlipped = new SimpleBooleanProperty(false);

    @SuppressWarnings("unused")
    public Card() { }

    public Card(String title, String description) {
        this.title.setValue(title);
        this.description.setValue(description);
    }

    public StringProperty getTitleProperty() {
        return title;
    }

    public String getTitle() {
        return title.getValue();
    }

    public void setTitle(String newTitle) {
        title.setValue(newTitle);
    }

    public StringProperty getDescriptionProperty() {
        return description;
    }

    public String getDescription() {
        return description.getValue();
    }

    public void setDescription(String newDescription) {
        description.setValue(newDescription);
    }

    public BooleanProperty getFlippedProperty() {
        return isFlipped;
    }

    public Boolean getFlipped() {
        return isFlipped.getValue();
    }

    public void setFlipped(Boolean newIsFlipped) {
        isFlipped.setValue(newIsFlipped);
    }
}
