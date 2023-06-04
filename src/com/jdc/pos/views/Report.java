package com.jdc.pos.views;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jdc.pos.entities.Category;
import com.jdc.pos.entities.Item;
import com.jdc.pos.entities.OrderDetail;
import com.jdc.pos.repo.ItemRepo;
import com.jdc.pos.repo.SaleRepo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;

public class Report implements Initializable {

    @FXML
    private ComboBox<Category> category;

    @FXML
    private ComboBox<Item> item;

    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private TableView<OrderDetail> table;
    
    private SaleRepo saleRepo;
    private ItemRepo itemRepo;

    @FXML
    void clear() {
    	category.setValue(null);
    	item.setValue(null);
    	dateFrom.setValue(null);
    	dateTo.setValue(null);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		saleRepo = SaleRepo.getInstance();
		itemRepo = ItemRepo.getInstance();
		
		category.getItems().addAll(Category.values());
		
		category.valueProperty().addListener((a,b,c) -> {
			item.getItems().clear();
			Category cat = category.getValue();
			
			if(null != cat) {
				List<Item> items = itemRepo.search(cat, null);
				item.getItems().addAll(items);
			}
			
			search();
		});
		
		// if changes happen, call search()
		item.valueProperty().addListener((a,b,c) -> search());
		dateFrom.valueProperty().addListener((a,b,c) -> search());
		dateTo.valueProperty().addListener((a,b,c) -> search());
		
		search();
	}

	private void search() {
		
		table.getItems().clear();
		List<OrderDetail> list = saleRepo.search(category.getValue(), item.getValue(), 
				dateFrom.getValue(), dateTo.getValue());
		table.getItems().addAll(list);
	}

}
