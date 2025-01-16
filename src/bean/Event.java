package bean;

import java.util.Date;

public class Event {
    private int eventId;           // イベントID
    private String title;          // イベントタイトル
    private String description;    // イベントの詳細
    private Date startTime;        // 開始時間
    private Date endTime;          // 終了時間
    private int createdBy;         // 作成者（スタッフID）

    // コンストラクタ
    public Event() {}

    public Event(String title, String description, Date startTime, Date endTime, int createdBy) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdBy = createdBy;
    }

    // GetterとSetter
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
}
