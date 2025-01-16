package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Suggest;

public class SuggestListDao extends Dao{

	public List<Suggest> getAll() throws Exception {
	    List<Suggest> suggestlists = new ArrayList<>();
	    String sql = "SELECT id, name, detail FROM suggest";

	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql);
	         ResultSet resultSet = statement.executeQuery()) {

	        while (resultSet.next()) {
	            Suggest suggestlist = new Suggest();
	            suggestlist.setId(resultSet.getInt("id"));
	            suggestlist.setName(resultSet.getString("name"));
	            suggestlist.setDetail(resultSet.getString("detail"));
	            suggestlists.add(suggestlist);
	        }
	    }

	    return suggestlists;
	}

	}
