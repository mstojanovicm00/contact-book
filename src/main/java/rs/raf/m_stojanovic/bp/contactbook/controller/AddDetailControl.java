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
import rs.raf.m_stojanovic.bp.contactbook.model.DetailDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddDetailControl implements EventHandler<ActionEvent> {

    private static final Alert NULL_CONTACT_ALERT = new Alert(Alert.AlertType.ERROR,
            "You must select a contact to add a contact detail",
            ButtonType.OK);

    private static final Alert NULL_VALUE_ALERT = new Alert(Alert.AlertType.ERROR,
            "Value for a contact detail must not be empty",
            ButtonType.OK);

    private final String category;
    private final TextField valueTextField;
    private final TableView<ContactDto> contactDtoTableView;
    private final TableView<DetailDto> detailDtoTableView;

    public AddDetailControl(String category, TextField valueTextField, TableView<ContactDto> contactDtoTableView, TableView<DetailDto> detailDtoTableView) {
        this.category = category;
        this.valueTextField = valueTextField;
        this.contactDtoTableView = contactDtoTableView;
        this.detailDtoTableView = detailDtoTableView;
    }

    @Override
    public void handle(ActionEvent event) {
        String value = this.valueTextField.getText();
        if (value == null || value.isBlank()) {
            NULL_VALUE_ALERT.showAndWait();
            return;
        }
        ContactDto contactDto = this.contactDtoTableView.getSelectionModel().getSelectedItem();
        if (contactDto == null) {
            NULL_CONTACT_ALERT.showAndWait();
            return;
        }
        this.runQuery(Config.getRelationalDatabaseConnection(), contactDto.getId(), value, this.category);
        this.detailDtoTableView.setItems(FXCollections.observableArrayList(
                DetailDto.readForContact(Config.getRelationalDatabaseConnection(), contactDto.getId())));
        this.valueTextField.clear();
    }

    private void runQuery(Connection connection, int contact, String value, String category) {
        String query = "CALL proc_add_detail(?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, contact);
            preparedStatement.setString(2, value);
            preparedStatement.setString(3, category);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
