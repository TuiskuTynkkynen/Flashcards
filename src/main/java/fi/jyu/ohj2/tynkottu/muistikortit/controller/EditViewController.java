package fi.jyu.ohj2.tynkottu.muistikortit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

}
