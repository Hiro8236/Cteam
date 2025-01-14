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
//	            System.out.println(institusion.getName() + "Daoだお");
	            institusions.add(institusion);
	        }
	    }

	    return institusions;
	}

	}
//public List<Institusion> filter(Institusion institusion) throws Exception {
//    // リストを初期化
//    List<Institusion> list = new ArrayList<>();
//    // コネクションを確立
//    Connection connection = getConnection();
//    // プリペアードステートメント
//    PreparedStatement statement = null;
//    // リザルトセット
//    ResultSet rSet = null;
//    // SQL文の条件
//    try {
//        // プリペアードステートメントにSQL文をセット
//        statement = connection.prepareStatement("select * from institusion where id=? and name = ? and detail = ?");
//        // プリペアードステートメントに学校コードをバインド
////        statement.setString(1, institusion.getid());
//        // プリペアードステートメントを実行
//        rSet = statement.executeQuery();
//        // リストへの格納処理を実行
//        try {
//        	while (rSet.next()){
//        		Institusion institusion = new Institusion();
//                subject.setCd(rSet.getString("cd"));
//                subject.setName(rSet.getString("name"));
//                subject.setSchool(school);
//                list.add(subject);
//        	}
//        }catch(SQLException | NullPointerException e) {
//        	e.printStackTrace();
//        }
//    }catch (SQLException sqle){
//    	throw sqle;
//    }finally {
//    	if (statement != null) {
//    		try {
//    			statement.close();
//    		}catch (SQLException sqle){
//    			throw sqle;
//    		}
//    	}
//    	if (connection != null) {
//    		try{
//    			connection.close();
//    		}catch (SQLException sqle) {
//    			throw sqle;
//    		}
//    	}
//    }
//    return list;
//}
/*
public boolean save(Institusion institusion) throws Exception {
	Connection connection = getConnection();
	PreparedStatement statement = null;

	int count = 0;
	try {
		Institusion old = get(institusion.getID(), institusion.getName(), institusion.getDetail());
		if (old == null) {
			statement = connection.prepareStatement(
				"insert into subject(id, name, detail) values(?, ?, ?)");
			statement.setInt(1, institusion.getID());
			statement.setString(2, institusion.getName());
			statement.setString(3, institusion.getDetail());
			count = statement.executeUpdate();
		}else {
			statement = connection.prepareStatement("update institusion set id=?, name=?, detail=? where id=?");
			statement.setInt(1, institusion.getID());
			statement.setString(2, institusion.getName());
			statement.setString(3, institusion.getDetail());

			count = statement.executeUpdate();
		}
	}catch (Exception e) {
		throw e;
	} finally {
		if (statement != null) {
            try {
                statement.close();
            } catch (SQLException sqle) {
                throw sqle;
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException sqle) {
                throw sqle;
            }
        }
	}
	if (count > 0) {
		return true;
	} else {
		return false;
	}
}

public boolean delete(Institusion institusion) throws Exception {
    // コネクションを確立
    Connection connection = getConnection();
    // プリペアードステートメント
    PreparedStatement statement = null;
    // 実行件数
    int count = 0;


    try {
        // プリペアードステートメントにDELETE文をセット
        statement = connection.prepareStatement("delete from institusion where id=?");
        // プリペアードステートメントに学生番号をバインド
        statement.setInt(1, institusion.getID());
        // プリペアードステートメントを実行
        count = statement.executeUpdate();
    } catch (Exception e) {
        throw e;
    } finally {
        // プリペアードステートメントを閉じる
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException sqle) {
                throw sqle;
            }
        }
        // コネクションを閉じる
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException sqle) {
                throw sqle;
            }
        }
    }
    if (count > 0) {
        // 実行件数が1件以上ある場合
        return true;
    } else {
        // 実行件数が0件の場合
        return false;
    }
}
*/


