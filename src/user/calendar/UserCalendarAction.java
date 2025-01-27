package user.calendar;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Event;
import dao.EventDao;
import tool.Action;

public class UserCalendarAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        try {
        	// DAOを使用してイベントデータを取得
        	EventDao eventDao = new EventDao();

        	// 公開イベントを取得
        	List<Event> publicEvents = eventDao.getPublicEvents();

        	// ログイン状態を確認
        	Integer userId = (Integer) req.getSession().getAttribute("userID");

        	List<Event> events;
        	if (userId != null) {
        	    // ユーザーがログインしている場合
        	    List<Event> userEvents = eventDao.getEventsByUserID(userId);

        	    // 公開イベントとユーザーの個人用イベントを結合
        	    events = new ArrayList<>();
        	    events.addAll(publicEvents);
        	    events.addAll(userEvents);

        	    // 重複を排除（IDで一意にする）
        	    Set<Integer> uniqueEventIds = new HashSet<>();
        	    List<Event> uniqueEvents = new ArrayList<>();
        	    for (Event event : events) {
        	        if (uniqueEventIds.add(event.getEventID())) {
        	            uniqueEvents.add(event);
        	        }
        	    }

        	    // ユニークなイベントをリストに設定
        	    events = uniqueEvents;
        	} else {
        	    // 未ログイン時は公開イベントのみ取得
        	    events = publicEvents;
        	}

        	// JSPに渡す
        	req.setAttribute("event", events);
        	req.getRequestDispatcher("user_calendar.jsp").forward(req, res);

        } catch (SQLException e) {
            // 例外が発生した場合、エラー情報をリクエストスコープに設定しエラーページへ
            e.printStackTrace();
            req.setAttribute("error", "イベントデータの取得中にエラーが発生しました。");
            req.getRequestDispatcher("error.jsp").forward(req, res);
        }
    }
}
