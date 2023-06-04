package com.jdc.pos.repo.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jdc.pos.entities.Category;
import com.jdc.pos.entities.Item;
import com.jdc.pos.repo.ItemRepo;
import com.jdc.pos.util.ConnectionManager;
import com.jdc.pos.util.MessageHandler;

public class ItemRepoImpl implements ItemRepo{
	
	private static ItemRepoImpl instance = new ItemRepoImpl();

	private static final String SELECT = "select id,name,price,category from item where 1 = 1";
	private static final String INSERT = "insert into item (id, name, category, price) values (?, ?, ?, ?)";
	
	
	ItemRepoImpl(){
		System.out.println("save data in database...");
	}
	
	public static ItemRepoImpl getInstance() {
		return instance;
	}
	
	@Override
	public List<Item> search(Category c, String idName) {
		
		// query
		StringBuilder query = new StringBuilder(SELECT);
		
		// parameter
		List<Object> params = new ArrayList<>();
		List<Item> results = new ArrayList<>();
		
		if(null != c) {
			query.append(" and category = ?");
			params.add(c.toString());
		}
		
		if(null != idName && !idName.isEmpty()) {
			
			if(check(idName)) {
				query.append(" and id = ?");
				params.add(idName);
			}else {
				query.append(" and name like ?");
				params.add(idName.concat("%"));
			}
		}
		
		//TODO: Show Query
/*		MessageHandler.show("Query: "+ query.toString() 
								+"\nSize: "+params.size());
		MessageHandler.toFront();
*/		
		try(Connection conn = ConnectionManager.getDBConnection();
				PreparedStatement prep = conn.prepareStatement(query.toString())) {
			
			// set parameters for generated query
			for(int i = 0; i<params.size(); i++) {
				prep.setObject(i + 1, params.get(i));
			}
			
			ResultSet rs = prep.executeQuery();
			
			while(rs.next()) {
				results.add(getItem(rs));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return results;
	}

	private Item getItem(ResultSet rs) throws SQLException {
		
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setCategory(Category.valueOf(rs.getString("category")));
		item.setName(rs.getString("name"));
		item.setPrice(rs.getInt("price"));
		return item;
	}

	private boolean check(String idName) {
		
		try {
			Integer.parseInt(idName);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public void add(Item item) {
		try(Connection conn = ConnectionManager.getDBConnection();
				PreparedStatement prep = conn.prepareStatement(INSERT)){
			
			prep.setInt(1, item.getId());
			prep.setString(2, item.getName());
			prep.setString(3, item.getCategory().toString());
			prep.setInt(4, item.getPrice());
			prep.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void add(String path) { // File path pay, 1 line chin si data htae pay;
		try {
			
			Files.lines(Paths.get(path))
			.skip(1)
			.map(line -> line.split("\t"))
			.map(arr -> new Item(arr))
			.forEach(item -> add(item));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
