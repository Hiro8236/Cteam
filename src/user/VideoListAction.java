package user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Video;
import dao.VideoDao;

public class VideoListAction {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            // DAOを使用して動画一覧を取得
            VideoDao videoDao = new VideoDao();
            List<Video> videoList = videoDao.getAllVideos();

            // リクエストに動画一覧をセット
            request.setAttribute("videoList", videoList);

            // 表示するJSPのパスを返す
            return "/user/videos.jsp";

        } catch (Exception e) {
            e.printStackTrace();
            // エラーページにリダイレクト
            return "/error.jsp";
        }
    }
}
