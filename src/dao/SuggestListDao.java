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
        List<Suggest> suggestlists = new ArrayList<>();
        String sql = "SELECT id, name, detail FROM Suggest";

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

    // 支援をデータベースに挿入するメソッド
    public void insert(Suggest suggest) throws Exception {
        // SQLのINSERT文
        String sql = "INSERT INTO Suggest (name, detail) VALUES (?, ?)";

        // データベース接続
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // パラメータの設定
            statement.setString(1, suggest.getName());
            statement.setString(2, suggest.getDetail());

            // 実行
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // エラーが発生した場合
            throw e;
        }
    }
}
