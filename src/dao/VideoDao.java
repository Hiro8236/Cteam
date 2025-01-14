// 動画一覧表示機能のDAOクラス

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Video;

public class VideoDao extends Dao {

    /**
     * データベースからすべての動画を取得するメソッド。
     * @return 動画のリスト。
     * @throws Exception データ取得中にエラーが発生した場合。
     */
    public List<Video> getAllVideos() throws Exception {
        List<Video> videos = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // SQLクエリの準備
            statement = connection.prepareStatement("SELECT * FROM videos ORDER BY upload_date DESC");
            ResultSet resultSet = statement.executeQuery();

            // 結果セットを動画オブジェクトに変換してリストに追加
            while (resultSet.next()) {
                Video video = new Video();
                video.setVideoId(resultSet.getInt("video_id"));
                video.setTitle(resultSet.getString("title"));
                video.setThumbnailUrl(resultSet.getString("thumbnail_url"));
                video.setUploader(resultSet.getString("uploader"));
                video.setUploadDate(resultSet.getTimestamp("upload_date"));
                videos.add(video);
            }

        } catch (SQLException e) {
            throw new Exception("動画データの取得中にエラーが発生しました。", e);
        } finally {
            // リソースのクリーンアップ
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

        return videos;
    }

    /**
     * 指定した動画IDに基づいて動画情報を取得するメソッド。
     * @param videoId 動画ID。
     * @return 動画オブジェクト、またはnull（見つからない場合）。
     * @throws Exception データ取得中にエラーが発生した場合。
     */
    public Video getVideoById(int videoId) throws Exception {
        Video video = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // SQLクエリの準備
            statement = connection.prepareStatement("SELECT * FROM videos WHERE video_id = ?");
            statement.setInt(1, videoId);
            ResultSet resultSet = statement.executeQuery();

            // 結果セットから動画オブジェクトを作成
            if (resultSet.next()) {
                video = new Video();
                video.setVideoId(resultSet.getInt("video_id"));
                video.setTitle(resultSet.getString("title"));
                video.setThumbnailUrl(resultSet.getString("thumbnail_url"));
                video.setUploader(resultSet.getString("uploader"));
                video.setUploadDate(resultSet.getTimestamp("upload_date"));
            }

        } catch (SQLException e) {
            throw new Exception("動画データの取得中にエラーが発生しました。", e);
        } finally {
            // リソースのクリーンアップ
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

        return video;
    }

    /**
     * 新しい動画をデータベースに保存するメソッド。
     * @param title 動画のタイトル。
     * @param thumbnailUrl サムネイル画像のURL。
     * @param uploader 投稿者。
     * @return 保存成功ならtrue、それ以外はfalse。
     * @throws Exception データ保存中にエラーが発生した場合。
     */
    public boolean saveVideo(String title, String thumbnailUrl, String uploader) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // SQLクエリの準備
            statement = connection.prepareStatement(
                "INSERT INTO videos (title, thumbnail_url, uploader, upload_date) VALUES (?, ?, ?, NOW())"
            );
            statement.setString(1, title);
            statement.setString(2, thumbnailUrl);
            statement.setString(3, uploader);

            // クエリの実行
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new Exception("動画データの保存中にエラーが発生しました。", e);
        } finally {
            // リソースのクリーンアップ
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
    }

    /**
     * 指定した動画IDの動画をデータベースから削除するメソッド。
     * @param videoId 動画ID。
     * @return 削除成功ならtrue、それ以外はfalse。
     * @throws Exception データ削除中にエラーが発生した場合。
     */
    public boolean deleteVideo(int videoId) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // SQLクエリの準備
            statement = connection.prepareStatement("DELETE FROM videos WHERE video_id = ?");
            statement.setInt(1, videoId);

            // クエリの実行
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new Exception("動画データの削除中にエラーが発生しました。", e);
        } finally {
            // リソースのクリーンアップ
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
    }
}
