package staff.normalstaff.calendar;

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

public class StaffCalendarAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        try {
        	// DAOを使用してスタッフ用および公開イベントデータを取得
        	EventDao eventDao = new EventDao();

        	// スタッフ用イベントを取得
        	List<Event> staffEvents = eventDao.getStaffEvents();

        	// 公開イベントを取得
        	List<Event> publicEvents = eventDao.getPublicEvents();

        	// イベントを結合
        	List<Event> events = new ArrayList<>();
        	events.addAll(staffEvents);
        	events.addAll(publicEvents);

        	// 重複を削除する（必要な場合、IDで一意にする例）
        	Set<Integer> uniqueEventIds = new HashSet<>();
        	List<Event> uniqueEvents = new ArrayList<>();
        	for (Event event : events) {
        	    if (uniqueEventIds.add(event.getEventID())) {
        	        uniqueEvents.add(event);
        	    }
        	}


            // イベントデータをリクエストスコープに設定
            req.setAttribute("events", events);

            // staff_calendar.jsp にフォワード
            req.getRequestDispatcher("staff_calendar.jsp").forward(req, res);

        } catch (SQLException e) {
            // 例外が発生した場合、エラー情報をリクエストスコープに設定しエラーページへ
            e.printStackTrace();
            req.setAttribute("error", "イベントデータの取得中にエラーが発生しました。");
            req.getRequestDispatcher("error.jsp").forward(req, res);
        }
    }
}
