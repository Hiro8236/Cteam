package bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class Video implements Serializable {

    private int videoId;           // 動画ID
    private String title;          // 動画タイトル
    private String thumbnailUrl;   // サムネイルURL
    private String uploader;       // 投稿者
    private Timestamp uploadDate;  // 投稿日時

    // Getterメソッド
    public int getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getUploader() {
        return uploader;
    }

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    // Setterメソッド
    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }
}