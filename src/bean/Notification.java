package bean;

import java.io.Serializable;

public class Notification implements Serializable{

	/**
	 * NoticeID
	 */

	private int id;

	/**
	 * タイトル:String
	 */
	private String title;

	/**
	 * 内容:Strin
	 */
	private String sentence;

	/**
	 * ゲッター、セッター
	 */

	 public int getId() {
			return id;
		}

	 public void setId(int id){
		 this.id =id;
	 }

	 public String getTitle() {
			return title;
		}

	 public void setTitle(String title){
		 this.title=title;
	 }


	 public String getSentence (){
		 return sentence;
	 }

	 public void setSentence(String sentence){
		 this.sentence=sentence;
	 }


}
