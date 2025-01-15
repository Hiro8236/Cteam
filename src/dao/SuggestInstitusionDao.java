package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.SuggestInstitusion;

public class SuggestInstitusionDao extends Dao{

	public List<SuggestInstitusion> getAll() throws Exception {
	    List<SuggestInstitusion> suggestinstitusions = new ArrayList<>();
	    String sql = "SELECT id, name, detail FROM suggest";

	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql);
	         ResultSet resultSet = statement.executeQuery()) {

	        while (resultSet.next()) {
	            SuggestInstitusion suggestinstitusion = new SuggestInstitusion();
	            suggestinstitusion.setId(resultSet.getInt("id"));
	            suggestinstitusion.setName(resultSet.getString("name"));
	            suggestinstitusion.setDetail(resultSet.getString("detail"));
	            suggestinstitusions.add(suggestinstitusion);
	        }
	    }

	    return suggestinstitusions;
	}

	}
