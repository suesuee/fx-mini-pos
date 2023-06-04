package com.jdc.pos.repo.memory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.jdc.pos.entities.Category;
import com.jdc.pos.entities.Item;
import com.jdc.pos.repo.ItemRepo;

public class ItemRepoImpl implements ItemRepo {

	private static ItemRepoImpl instance = new ItemRepoImpl();
	
	private List<Item> items;
	
	ItemRepoImpl() {
		System.out.println("save data in memory ...");
		if(null==items)
			items=new ArrayList<Item>();
	}
	
	public static ItemRepoImpl getInstance() { //static twae ka 1 time pl intitalize lok loh;
		return instance;//obj souk pay dk method;
	}
	
	@Override
	public List<Item> search(Category c, String idName) {
		
		Predicate<Item> cond = a -> true;//Find All anay ahtr asin pyay ag
		
		// search by category
		if(null != c) {
			cond = cond.and(item -> item.getCategory() == c);
		}
		
		// search by idName
		if(null != idName && !idName.isEmpty()) {
			
			//tuu lr shar dr, tuu yin true return predicate ka;
			Predicate<Item> id = a -> String.valueOf(a.getId()).equals(idName);
			
			Predicate<Item> name = a -> a.getName().toLowerCase()	
										.startsWith(idName.toLowerCase());
			
			cond = cond.and(id.or(name));//2 khu ko pyn chate pay;
		}
		
		
		return items.stream().filter(cond).collect(Collectors.toList());
	}

	@Override
	public void add(Item item) {
		// TODO;
	}

	@Override
	public void add(String path) {
		
		try {
			items = Files.lines(Paths.get(path)).skip(1)
			.map(line->line.split("\t"))
			.map(arr -> new Item(arr))//Convert from array into item obj
			.collect(Collectors.toList());
		} catch (IOException e) { //file can't be found shi ng;
			e.printStackTrace();
		}
		
	/*	To try with method reference;
	 * try {
			Files.lines(Paths.get(path)).skip(1)
			.map(line->line.split("\t"))
			.map(arr -> new Item(arr))
			.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	*/
	}

}
