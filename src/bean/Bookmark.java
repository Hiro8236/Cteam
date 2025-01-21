package bean;

import java.io.Serializable;

public class Bookmark implements Serializable {

	/**
	 * ユーザID:Int
	 */
	private int BookmarkID;

	/**
	 * ユーザID:Int
	 */
	private int UserID;

	/**
	 * 制度ID:Int
	 */
	private int InstitutionID;

	private String name; // Institutionのname
    private String detail; // Institutionのdetail

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