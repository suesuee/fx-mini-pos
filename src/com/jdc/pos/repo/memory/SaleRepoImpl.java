package com.jdc.pos.repo.memory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.jdc.pos.entities.Category;
import com.jdc.pos.entities.Item;
import com.jdc.pos.entities.OrderDetail;
import com.jdc.pos.entities.Voucher;
import com.jdc.pos.repo.SaleRepo;
import com.jdc.pos.util.MessageHandler;

public class SaleRepoImpl implements SaleRepo{
	
	private static SaleRepo INSTANCE = new SaleRepoImpl();
	
	private List<Voucher> vouchers;
	
	SaleRepoImpl(){
		if(vouchers == null)
			vouchers = new ArrayList<Voucher>();
	}

	public static SaleRepo getInstance() {
		return INSTANCE;
	}
	
	@Override
	public void paid(Voucher voucher) {
		
		voucher.setSaleDate(LocalDate.now());
		voucher.setSaleTime(LocalTime.now());
 		voucher.setId((int)(System.currentTimeMillis()/1000));
		
		voucher.getList().forEach(order -> order.setVoucher(voucher));
		vouchers.add(voucher);//voucher = id (in db)
		
		MessageHandler.show("Order Details: " + voucher.getList().size());
		MessageHandler.toFront();
	}

	@Override
	public List<OrderDetail> search(Category c, Item item, LocalDate from, LocalDate to) {
		
		Predicate<OrderDetail> cond = a->true;
		
		// by category
		if(null != c) { /* order = orderdetail */
			cond = cond.and(order -> order.getItem().getCategory() == c);
		}
		
		// by item
		if(null != item) {
			cond = cond.and(order -> order.getItem().equals(item));
		}
		
		// by dateFrom
		if(null != from) {
			cond = cond.and(order -> order.getSaleDate().compareTo(from) >= 0);
		}
		
		// by dateTo
		if(null != to) {
			cond = cond.and(order -> order.getSaleDate().compareTo(to) <= 0);
		}
		
		return vouchers.stream()
				.flatMap(voucher->voucher.getList().stream())
				.filter(cond)
				.collect(Collectors.toList());
	}
	

}
