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

        String sql = "SELECT * FROM Staff WHERE staffId = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, staffId);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    staff = new Staff();
                    staff.setStaffID(resultSet.getInt("staffId"));
                    staff.setStaffName(resultSet.getString("staffname"));
                    staff.setStaffRole(resultSet.getString("staffrole"));
                    staff.setPassword(resultSet.getString("password"));
                } else {
                    System.out.println("ログイン失敗: 職員IDまたはパスワードが間違っています");
                }
            }
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

        String sql = "SELECT * FROM Staff WHERE staffId = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, staffId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    staff = new Staff();
                    staff.setStaffID(resultSet.getInt("staffId"));
                    staff.setStaffName(resultSet.getString("staffname"));
                    staff.setStaffRole(resultSet.getString("staffrole"));
                    staff.setPassword(resultSet.getString("password"));
                }
            }
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
        String sql = "INSERT INTO Staff (staffname, staffrole, password) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, staff.getStaffName());
            statement.setString(2, staff.getStaffRole());
            statement.setString(3, staff.getPassword());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    staff.setStaffID(resultSet.getInt(1));  // 自動生成されたIDを設定
                }
            }
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

        StringBuilder sql = new StringBuilder("SELECT * FROM Staff WHERE 1=1");
        if (staffId > 0) sql.append(" AND staffId = ?");
        if (staffName != null && !staffName.isEmpty()) sql.append(" AND staffname LIKE ?");
        if (staffRole != null && !staffRole.isEmpty()) sql.append(" AND staffrole LIKE ?");

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            int index = 1;
            if (staffId > 0) statement.setInt(index++, staffId);
            if (staffName != null && !staffName.isEmpty()) statement.setString(index++, "%" + staffName + "%");
            if (staffRole != null && !staffRole.isEmpty()) statement.setString(index++, "%" + staffRole + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Staff staff = new Staff();
                    staff.setStaffID(resultSet.getInt("staffId"));
                    staff.setStaffName(resultSet.getString("staffname"));
                    staff.setStaffRole(resultSet.getString("staffrole"));
                    staff.setPassword(resultSet.getString("password"));
                    staffList.add(staff);
                }
            }
        }
        return staffList;
    }

    /**
     * 単一の職員を削除する
     *
     * @param staffId 削除する職員ID
     * @return 成功時true、失敗時false
     * @throws Exception
     */
    public boolean deleteStaffById(int staffId) throws Exception {
        String sql = "DELETE FROM Staff WHERE staffId = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, staffId);
            return pstmt.executeUpdate() > 0; // 削除された行がある場合はtrueを返す
        }
    }

    /**
     * 複数の職員を削除する
     *
     * @param staffIds 削除する職員IDのリスト
     * @return 削除された行数
     * @throws Exception
     */
    public int deleteStaffByIds(List<Integer> staffIds) throws Exception {
        if (staffIds == null || staffIds.isEmpty()) return 0;

        String placeholders = String.join(",", staffIds.stream().map(id -> "?").toArray(String[]::new));
        String sql = "DELETE FROM Staff WHERE staffId IN (" + placeholders + ")";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < staffIds.size(); i++) {
                pstmt.setInt(i + 1, staffIds.get(i));
            }
            return pstmt.executeUpdate();
        }
    }
}
