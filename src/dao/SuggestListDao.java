package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Suggest;

public class SuggestListDao extends Dao {

    // 全ての支援を取得するメソッド
    public List<Suggest> getAll() throws Exception {
        List<Suggest> suggestLists = new ArrayList<>();
        String sql = "SELECT id, name, detail FROM suggest";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Suggest suggest = new Suggest();
                suggest.setId(resultSet.getInt("id"));
                suggest.setName(resultSet.getString("name"));
                suggest.setDetail(resultSet.getString("detail"));
                suggestLists.add(suggest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("支援リストの取得中にエラーが発生しました: " + e.getMessage());
        }

        return suggestLists;
    }

    // 支援をデータベースに挿入するメソッド
    public void insert(Integer userId, Integer institutionId) throws Exception {
        // SQLのINSERT文
        String sql = "INSERT INTO bookmark (user_id, institution_id) VALUES (?, ?)";

        // データベース接続
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // パラメータの設定
            statement.setInt(1, userId);
            statement.setInt(2, institutionId);

            // 実行
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // エラーが発生した場合
            throw new Exception("支援の挿入中にエラーが発生しました: " + e.getMessage());
        }
    }
}
