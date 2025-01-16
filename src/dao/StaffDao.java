package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Staff;

public class StaffDao extends Dao {

    /**
     * 職員IDとパスワードで認証する
     *
     * @param staffId  職員ID
     * @param password パスワード
     * @return 認証成功: 職員インスタンス, 認証失敗: null
     * @throws Exception
     */
    public Staff login(Integer staffId, String password) throws Exception {
        Staff staff = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String sql = "SELECT * FROM Staff WHERE staffId = ? AND password = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, staffId);
            statement.setString(2, password);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                staff = new Staff();
                staff.setStaffID(resultSet.getInt("staffId"));
                staff.setStaffName(resultSet.getString("staffname"));
                staff.setStaffRole(resultSet.getString("staffrole"));
                staff.setPassword(resultSet.getString("password"));
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return staff;
    }

    /**
     * 職員IDで職員情報を検索する
     *
     * @param staffId 職員ID
     * @return 該当する職員インスタンス、見つからない場合はnull
     * @throws Exception
     */
    public Staff findById(int staffId) throws Exception {
        Staff staff = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String sql = "SELECT * FROM Staff WHERE staffId = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, staffId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                staff = new Staff();
                staff.setStaffID(resultSet.getInt("staffId"));
                staff.setStaffName(resultSet.getString("staffname"));
                staff.setStaffRole(resultSet.getString("staffrole"));
                staff.setPassword(resultSet.getString("password"));
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return staff;
    }

    /**
     * 職員情報を登録する
     *
     * @param staff 職員インスタンス
     * @throws Exception
     */
    public void save(Staff staff) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            String sql = "INSERT INTO Staff (staffname, staffrole, password) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, staff.getStaffName());
            statement.setString(2, staff.getStaffRole());
            statement.setString(3, staff.getPassword());
            statement.executeUpdate();

            // 自動生成された職員IDを取得
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                staff.setStaffID(resultSet.getInt(1));  // 自動生成されたIDを設定
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    /**
     * 指定された条件で職員情報をフィルタリングする
     *
     * @param staffId   職員ID (0の場合は条件から除外)
     * @param staffName 氏名 (nullまたは空文字の場合は条件から除外)
     * @param staffRole 役職 (nullまたは空文字の場合は条件から除外)
     * @return 条件に一致する職員のリスト
     * @throws Exception
     */
    public List<Staff> filterStaff(int staffId, String staffName, String staffRole) throws Exception {
        List<Staff> staffList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();

            // SQL文を構築
            StringBuilder sql = new StringBuilder("SELECT * FROM Staff WHERE 1=1");

            // 条件を追加
            if (staffId > 0) {
                sql.append(" AND staffId = ?");
            }
            if (staffName != null && !staffName.isEmpty()) {
                sql.append(" AND staffname LIKE ?");
            }
            if (staffRole != null && !staffRole.isEmpty()) {
                sql.append(" AND staffrole LIKE ?");
            }

            statement = connection.prepareStatement(sql.toString());

            // パラメータを設定
            int index = 1;
            if (staffId > 0) {
                statement.setInt(index++, staffId);
            }
            if (staffName != null && !staffName.isEmpty()) {
                statement.setString(index++, "%" + staffName + "%");
            }
            if (staffRole != null && !staffRole.isEmpty()) {
                statement.setString(index++, "%" + staffRole + "%");
            }

            resultSet = statement.executeQuery();

            // 結果をリストに格納
            while (resultSet.next()) {
                Staff staff = new Staff();
                staff.setStaffID(resultSet.getInt("staffId"));
                staff.setStaffName(resultSet.getString("staffname"));
                staff.setStaffRole(resultSet.getString("staffrole"));
                staff.setPassword(resultSet.getString("password"));
                staffList.add(staff);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return staffList;
    }
}
