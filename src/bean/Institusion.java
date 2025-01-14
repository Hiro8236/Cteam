package bean;

import java.io.Serializable;

public class Institusion implements Serializable {

	/**
	 * 支援ID:Int
	 */
	private int id;

	/**
	 * 支援名:String
	 */
	private String name;

	/**
	 * 支援詳細:String
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