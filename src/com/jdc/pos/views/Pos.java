package com.jdc.pos.views;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.ToIntFunction;

import com.jdc.pos.entities.Category;
import com.jdc.pos.entities.Item;
import com.jdc.pos.entities.OrderDetail;
import com.jdc.pos.entities.Voucher;
import com.jdc.pos.repo.ItemRepo;
import com.jdc.pos.repo.SaleRepo;
import com.jdc.pos.util.MessageHandler;
import com.jdc.pos.util.MiniPosException;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

public class Pos implements Initializable {

    @FXML
    private ComboBox<Category> category;
    @FXML
    private TextField idName;
    @FXML
    private Label headTotal;
    @FXML
    private TableView<Item> itemTable;
    @FXML
    private TableView<OrderDetail> cartTable;// OrderDetail obj twae shi twr
    @FXML
    private TableColumn<OrderDetail, Integer> count;
//Double click nate yin item's price edit loh ya ag separate @fxml id pay htr ya ;
//Generic type ka primitive ko m check py ng; therefore use wrapper;
//OrderDetail = thu shi nay mk parent prj; Integer = ko sar pyu mk tgl value;
    @FXML
    private Label subTotal;
    @FXML
    private Label tax;
    @FXML
    private Label total;
    private ItemRepo itemRepo;
    private SaleRepo saleRepo;
    
    @FXML
    void clearCart() {
    	cartTable.getItems().clear();
    	calculatePrice();
    }
    
    private Integer convertSum(ToIntFunction<OrderDetail> order) {
    	return cartTable.getItems().stream().mapToInt(order).sum();
    }

    private void calculatePrice() {
		subTotal.setText(convertSum(order -> order.getSubTotal()).toString());
		tax.setText(convertSum(order -> order.getTax()).toString());
		total.setText(convertSum(order -> order.getTotal()).toString());
	}

	@FXML
    void clearSearch() {
    	category.setValue(null);
    	idName.clear();
    }

    @FXML
    void delete() {
    	
    	try {
			OrderDetail order = cartTable.getSelectionModel().getSelectedItem();
			
			if(null != order) {
				
				cartTable.getItems().remove(order);
				calculatePrice();// remove lyk loh zay ll update;
				
			}else {
				throw new MiniPosException("Please select item in cart table to delete!!!");
			}
		} catch (Exception e) {
//			System.err.println(e.getMessage());
			MessageHandler.show(e);
		}
    }

    @FXML
    void paid() {
    	
    	try {
    		// check cart is not empty
        	if(cartTable.getItems().isEmpty()) {
        		throw new MiniPosException("Please add items in cart table !!!");
        	}
    		
        	// create voucher
        	Voucher voucher = new Voucher();
        	
        	// add order detail in voucher
        	voucher.getList().addAll(cartTable.getItems()); //cartTable = order detail
        	
        	// save in memory
        	saleRepo.paid(voucher);
        	
        	// clear cart
        	clearCart();
        	
		} catch (Exception e) {
			MessageHandler.show(e);
		}
    	

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
/*		MessageHandler.show("Welcome from Application");
		MessageHandler.toFront();
*/		
		itemRepo = ItemRepo.getInstance();
		saleRepo = SaleRepo.getInstance();
		
		category.getItems().addAll(Category.values());
		
		category.valueProperty().addListener((a,b,c)->search());
		idName.textProperty().addListener((a,b,c)->search());
		
		search();
		
		headTotal.textProperty().bind(total.textProperty());//Binding with Total
		
		itemTable.setOnMouseClicked(event -> {
			
			if(event.getClickCount() == 2) {
				
				// item objs amyr gyi htl ma bl item obj ko selection lok twr ll table htl ka pyn htote u mk hr, item obj ko pyn return ya mhr;
				// obj twae shi pe thar pay mk code ka lan tone loh ya ag var 1 lone htl ko memory address twae pwar htae chin;
				Item item = itemTable.getSelectionModel().getSelectedItem();
				
				// check cartTable for existing item (cartTable=collection)
				OrderDetail order = cartTable.getItems().stream()
									.filter(od -> od.getItem().getId() == item.getId())
									.findAny().orElse(null);
				
				if(order == null) {
					order = new OrderDetail();
					order.setItem(item);// set new item for the first time;
					cartTable.getItems().add(order);
				}
				
				order.setCount(order.getCount() + 1);
				order.calculate();
				cartTable.refresh();
//				System.out.println("Count: "+order.getCount());
				
				calculatePrice();
//				System.out.println(order.getTotal());
			}
		});
		
		cartTable.setEditable(true);
		
		// TODO: content menu
		
		// converter for count column
		count.setCellFactory(TextFieldTableCell.forTableColumn(
				new StringConverter<Integer>() {

					@Override
					public String toString(Integer object) {
						if(null != object) {
							return object.toString();
						}
						return null;
					}
					
					@Override
					public Integer fromString(String string) {
						try {
							if(null != string && !string.isEmpty()) {
								return Integer.parseInt(string);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						return null;
					}

				}));
		
		// edit count column with double-click
		count.setOnEditCommit(event -> {
			
			OrderDetail order = event.getRowValue();
			/* orderdetail anay nk 1 row lone mhr shi dk hr U twr;
			 UI bk ka memory mhr shi dk obj ko ref lan htouk */
			order.setCount(event.getNewValue());
			order.calculate();
			
/*			System.out.println("Count: " + order.getCount());
			System.out.println("Total: "+order.getTotal());
*/			
			cartTable.refresh();
			
			// update price on UI
			calculatePrice();
		});
		
	}

	private void search() {
		
		List<Item> items = itemRepo.search(category.getValue(), idName.getText());
		itemTable.getItems().clear();
		itemTable.getItems().addAll(items);
		
		MainFrame.clearOutput();
		MainFrame.showOutput("Total Items: "+items.size());
	}

}
