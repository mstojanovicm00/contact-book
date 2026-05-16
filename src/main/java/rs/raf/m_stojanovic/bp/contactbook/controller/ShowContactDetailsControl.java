package rs.raf.m_stojanovic.bp.contactbook.controller;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import rs.raf.m_stojanovic.bp.contactbook.Config;
import rs.raf.m_stojanovic.bp.contactbook.model.ContactDto;
import rs.raf.m_stojanovic.bp.contactbook.model.DetailDto;

import java.util.List;

public class ShowContactDetailsControl implements EventHandler<MouseEvent> {

    private final TableView<ContactDto> contactTable;
    private final TableView<DetailDto> detailTable;

    public ShowContactDetailsControl(TableView<ContactDto> contactTable, TableView<DetailDto> detailTable) {
        this.contactTable = contactTable;
        this.detailTable = detailTable;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getClickCount() == 1) {
            ContactDto contactDto = this.contactTable.getSelectionModel().getSelectedItem();
            if (contactDto == null)
                return;
            int contactId = contactDto.getId();
            List<DetailDto> detailDtos = DetailDto.readForContact(Config.getRelationalDatabaseConnection(), contactId);
            this.detailTable.setItems(FXCollections.observableArrayList(detailDtos));
        }
    }
}
