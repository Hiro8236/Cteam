package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Institusion;

public class InstitusionDao extends Dao{

	public List<Institusion> getAll() throws Exception {
	    List<Institusion> institusions = new ArrayList<>();
	    String sql = "SELECT id, name, detail FROM institusion";

	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql);
	         ResultSet resultSet = statement.executeQuery()) {

	        while (resultSet.next()) {
	            Institusion institusion = new Institusion();
	            institusion.setId(resultSet.getInt("id"));
	            institusion.setName(resultSet.getString("name"));
	            institusion.setDetail(resultSet.getString("detail"));
	            institusions.add(institusion);
	        }
	    }

	    return institusions;
	}

	}
