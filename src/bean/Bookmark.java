package bean;

import java.io.Serializable;

public class Bookmark implements Serializable {

	/**
	 * ブックマーク登録支援ID:Int
	 */
	private int id;

	/**
	 * ブックマーク登録支援名:String
	 */
	private String name;

	/**
	 * ブックマーク登録支援詳細:String
	 */
	private String detail;

	/**
	 * ゲッター、セッター
	 */
	public int getID() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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