package rs.raf.m_stojanovic.bp.contactbook.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import rs.raf.m_stojanovic.bp.contactbook.Config;
import rs.raf.m_stojanovic.bp.contactbook.model.ContactDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddContactControl implements EventHandler<ActionEvent> {

    private static final Alert NULL_NAMES_ALTERT = new Alert(Alert.AlertType.ERROR,
            "First name and last name of a contact must not be empty",
            ButtonType.OK);

    private final TextField textFieldFirstName;
    private final TextField textFieldLastName;
    private final TableView<ContactDto> contactDtoTableView;

    public AddContactControl(TextField textFieldFirstName, TextField textFieldLastName, TableView<ContactDto> contactDtoTableView) {
        this.textFieldFirstName = textFieldFirstName;
        this.textFieldLastName = textFieldLastName;
        this.contactDtoTableView = contactDtoTableView;
    }

    @Override
    public void handle(ActionEvent event) {
        String firstName = this.textFieldFirstName.getText();
        String lastName = this.textFieldLastName.getText();
        if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank()) {
            NULL_NAMES_ALTERT.showAndWait();
            return;
        }
        this.runQuery(Config.getRelationalDatabaseConnection(), firstName.trim(), lastName.trim());
        this.contactDtoTableView.setItems(FXCollections.observableArrayList(
                ContactDto.readAll(Config.getRelationalDatabaseConnection())));
        this.textFieldFirstName.clear();
        this.textFieldLastName.clear();
    }

    private void runQuery(Connection connection, String firstName, String lastName) {
        String query = "INSERT INTO contact(first_name, last_name) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
