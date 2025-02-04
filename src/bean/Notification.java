package bean;

import java.io.Serializable;

public class Notification implements Serializable {

	/**
	 * お知らせID:Int
	 */
	private int NotificationID;

	/**
	 * お知らせ名:String
	 */
	private String title;

	/**
	 *内容
		*/
	private String Detail;

	/**
	 * ゲッター、セッター
	 */

	public int getNotificationID(){
		return NotificationID;
	}

	public void setNotificationID(int NotificationID) {
		this.NotificationID = NotificationID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title){
		this.title= title;
	}

	public String getDetail(){
		return Detail;
	}

	public void setDetail(String Detail) {
		this.Detail= Detail;
	}
}