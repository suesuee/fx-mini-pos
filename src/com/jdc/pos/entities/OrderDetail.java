package com.jdc.pos.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class OrderDetail {

	private int id;
	private int count;

	//item obj,voucher obj twae pyn access loh ya ag;  
	private Item item; //GetID doh pyn khaw loh ya twr;
	private Voucher voucher;
	
	// not in db, apyin view mhr pya loh ya ag;
	private int subTotal;
	private int total;
	private int tax;
	
	public int getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(int subTotal) {
		this.subTotal = subTotal;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Item getItem() {
		return item; // item obj shi yar location
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}
	
	// Custom Method
	public LocalDate getSaleDate() {
		return voucher.getSaleDate();
	}
	
	public LocalTime getSaleTime() {
		return voucher.getSaleTime();
	}
	
	public int getUnitPrice() {
		return item.getPrice();
	}
	
	public Category getCategory() {
		return item.getCategory();
	}
	
	public void calculate() {
		subTotal = getUnitPrice()*count;
		Double tax = subTotal * 0.05;
		this.tax = tax.intValue();
		total = this.tax + subTotal;
	}
}
