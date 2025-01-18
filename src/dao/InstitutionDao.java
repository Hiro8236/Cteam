package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Institution;

public class InstitutionDao extends Dao{

	public List<Institution> getAll() throws Exception {
	    List<Institution> institutions = new ArrayList<>();
	    String sql = "SELECT id, name, detail FROM institution";

	    try (Connection connection = getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql);
	         ResultSet resultSet = statement.executeQuery()) {

	        while (resultSet.next()) {
	            Institution institution = new Institution();
	            institution.setId(resultSet.getInt("id"));
	            institution.setName(resultSet.getString("name"));
	            institution.setDetail(resultSet.getString("detail"));
	            institutions.add(institution);
	        }
	    }

	    return institutions;
	}

	}
