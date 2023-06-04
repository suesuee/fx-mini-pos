package com.jdc.pos.repo;

import java.time.LocalDate;
import java.util.List;

import com.jdc.pos.entities.Category;
import com.jdc.pos.entities.Item;
import com.jdc.pos.entities.OrderDetail;
import com.jdc.pos.entities.Voucher;
import com.jdc.pos.repo.database.SaleRepoImpl;

public interface SaleRepo {
	
	public static SaleRepo getInstance() {
		return SaleRepoImpl.getInstance();
	}
	
	void paid(Voucher voucher);//Order List ya
	
	List<OrderDetail> search(Category c, Item item,
			LocalDate from, LocalDate to);
	
	
	
}
