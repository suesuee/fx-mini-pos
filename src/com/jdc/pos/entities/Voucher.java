package com.jdc.pos.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Voucher {

	private int id;
	private LocalDate saleDate;
	private LocalTime saleTime;
	
	// not in db, tabaw tayar aya apyin mhr pyn pe voucher htl mhr pr dk all items ko list anay nk pya loh ya ag;  
	private List<OrderDetail> list;
	
	public Voucher() {
		list = new ArrayList<OrderDetail>();
		//NullPointerException m tat ag; 
		//null list htl win lr yin tat ng dk error ko preventing
	}

	public List<OrderDetail> getList() {
		return list;
	}

	public void setList(List<OrderDetail> list) {
		this.list = list;// apor ka arraylist hr tar m shi yin null win lr pe null pl return mhr
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(LocalDate saleDate) {
		this.saleDate = saleDate;
	}

	public LocalTime getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(LocalTime saleTime) {
		this.saleTime = saleTime;
	}
}
