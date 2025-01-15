package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Staff;

public class StaffDao extends Dao {

    /**
     * loginメソッド スタッフIDとパスワードで認証する
     * @param staffId スタッフID
     * @param password パスワード
     * @return 認証成功: スタッフクラスのインスタンス, 認証失敗: null
     * @throws Exception
     */
    public Staff login(Integer staffId, String password) throws Exception {
        // スタッフインスタンスを初期化
        Staff staff = null;

        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // スタッフIDとパスワードで検索
            String sql = "SELECT * FROM Staff WHERE StaffId = ? AND password = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, staffId);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // 認証成功時、スタッフインスタンスを作成
                staff = new Staff();
                staff.setStaffID(resultSet.getInt("staffId"));
                staff.setStaffName(resultSet.getString("staffname"));
                staff.setPassword(resultSet.getString("password"));
                staff.setStaffRole(resultSet.getString("staffrole"));  // staffroleを取得
            }
        } catch (Exception e) {
            throw e;
        } finally {
            // リソースを解放
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw e;
                }
            }
        }

        return staff;
}

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
                staffList.add(staff);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            // リソースを解放
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return staffList;
    }

}
