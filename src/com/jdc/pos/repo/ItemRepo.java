package com.jdc.pos.repo;

import java.util.List;

import com.jdc.pos.entities.Category;
import com.jdc.pos.entities.Item;
import com.jdc.pos.repo.database.ItemRepoImpl;

public interface ItemRepo {
	
	public static ItemRepo getInstance() {
		return ItemRepoImpl.getInstance();//Memory ka ItemREpoImpl yk getInstance;
	}
	
	//shi pe thar items pyn search poh;
	List<Item> search(Category c,String idName);
	void add(Item item);//item obj 1 lone pay data htae loh ya;
	void add(String path);
//itetm obj pr dk file path pay data htae loh ya,
//ae data ko memory mhr list obj,collection obj anay nk su pay htr;
	
}
