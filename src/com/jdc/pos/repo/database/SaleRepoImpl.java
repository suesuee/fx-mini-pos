package com.jdc.pos.repo.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.jdc.pos.entities.Category;
import com.jdc.pos.entities.Item;
import com.jdc.pos.entities.OrderDetail;
import com.jdc.pos.entities.Voucher;
import com.jdc.pos.repo.SaleRepo;
import com.jdc.pos.util.ConnectionManager;

public class SaleRepoImpl implements SaleRepo {

	private static final SaleRepo instance = new SaleRepoImpl();
	
	private static final String INSERT_VOUCHER = "insert into voucher (sale_date, sale_time) values (?, ?)";
	private static final String INSERT_ORDER = "insert into order_detail (item_id, voucher_id, count) values (?, ?, ?)";
	
	SaleRepoImpl(){
		
	}
	
	public static SaleRepo getInstance() {
		return instance;
	}
	
	@Override
	public void paid(Voucher voucher) {// voucher ka nay generate lok twr dk key ti poh lo
		try(Connection conn = ConnectionManager.getDBConnection();
				PreparedStatement prepVoucher = conn.prepareStatement(INSERT_VOUCHER, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement prepOrder = conn.prepareStatement(INSERT_ORDER)) {
			
			// create voucher 
			prepVoucher.setDate(1, Date.valueOf(LocalDate.now()));// java date to mysql date
			prepVoucher.setTime(2, Time.valueOf(LocalTime.now()));
			prepVoucher.executeUpdate();
			
			ResultSet rs = prepVoucher.getGeneratedKeys();// htae sone khan mhr youk
			
			// create order_detail with voucher no
			while(rs.next()) {
				
			  int voucherId = rs.getInt(1);
			  
			  for(OrderDetail order : voucher.getList()) {
				  
				  prepOrder.setInt(1, order.getItem().getId());
				  prepOrder.setInt(2, voucherId);
				  prepOrder.setInt(3, order.getCount());
				  prepOrder.executeUpdate();
			  }
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<OrderDetail> search(Category c, Item item, LocalDate from, LocalDate to) {
		return null;
	}

}
