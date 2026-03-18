package fi.jyu.ohj2.tynkottu.muistikortit.controller;

import fi.jyu.ohj2.tynkottu.muistikortit.App;
import javafx.event.ActionEvent;
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
    private Button cardCreateButton;
    @FXML
    private Button cardDeleteButton;
    @FXML
    private Button exitButton;
    @FXML
    private TextArea cardDescriptionArea;
    @FXML
    private TableView<Void> cardTable;
    @FXML
    private TextField cardTitleField;
    @FXML
    private TextArea deckDescriptionArea;
    @FXML
    private TextField deckTitleField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<Void, String> title = new TableColumn<>("Title");
        cardTable.getColumns().add(title);

        TableColumn<Void, String> description = new TableColumn<>("Description");
        cardTable.getColumns().add(description);
    }

    @FXML
    void handleCardCreate(ActionEvent event) {
        IO.println("Create Card");
    }

    @FXML
    void handleCardDelete(ActionEvent event) {
        IO.println("Delete Card");
    }

    @FXML
    void handleSave(ActionEvent event) {
        IO.println("Saved");
    }

    @FXML
    void handleExit(ActionEvent event) {
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
