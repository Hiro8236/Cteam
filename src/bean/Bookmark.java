package bean;

import java.io.Serializable;

public class Bookmark implements Serializable {

    private int BookmarkID;
    private int UserID;
    private int InstitutionID;
    private String name;
    private String detail;

    public int getBookmarkID() {
        return BookmarkID;
    }

    public void setBookmarkID(int BookmarkID) {
        this.BookmarkID = BookmarkID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public int getInstitutionID() {
        return InstitutionID;
    }

    public void setInstitutionID(int InstitutionID) {
        this.InstitutionID = InstitutionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
