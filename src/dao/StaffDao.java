package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
