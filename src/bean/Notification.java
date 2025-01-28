package bean;

import java.io.Serializable;

public class Notification implements Serializable {

	/**
	 * お知らせID:Int
	 */
	private int Noticeid;

	/**
	 * お知らせ名:String
	 */
	private String title;

	/**
	 *内容
		*/
	private String sentence;

	/**
	 * ゲッター、セッター
	 */

	public int getNoticeid(){
		return Noticeid;
	}

	public void setNoticeid(int Noticeid) {
		this.Noticeid = Noticeid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title){
		this.title= title;
	}

	public String getSentence(){
		return sentence;
	}

	public void setSenrence(String sentence) {
		this.sentence= sentence;
	}
}