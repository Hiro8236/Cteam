package bean;

import java.util.Date;

public class Event {
    private int eventID;
    private String title;
    private String description;
    private Date startTime;
    private Date endTime;
    private int createdBy;
    private boolean isPublic;     // 公開設定
    private boolean isStaffOnly;  // スタッフ限定公開設定
    private boolean notify; // 通知設定

    // Getters and Setters
    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
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

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isStaffOnly() {
        return isStaffOnly;
    }

    public void setStaffOnly(boolean isStaffOnly) {
        this.isStaffOnly = isStaffOnly;
    }

    public boolean isNotify() { // EL で `event.notify` を使うための getter
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    private String email; // ユーザーのメールアドレス

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




}
