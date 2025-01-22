package staff.normalstaff.institution;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Institution;
import bean.Staff;
import dao.InstitutionDao;
import tool.Action;

public class StaffInstitutionCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        System.out.println("支援登録開始");

        // ローカル変数の宣言
        HttpSession session = req.getSession();
        InstitutionDao institutionDao = new InstitutionDao();
        Map<String, String> errors = new HashMap<>();

        // ログインユーザーの情報を取得
        Staff staff = (Staff) session.getAttribute("user");
        if (staff == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // リクエストパラメータの取得
        String institutionName = req.getParameter("institution_name");
        String institutionDetail = req.getParameter("institution_detail");
        String institutionVideo = req.getParameter("institution_video");

        // 入力値の検証
        if (institutionName == null || institutionName.trim().isEmpty()) {
            errors.put("institution_name", "支援名を入力してください");
        }
        if (institutionDetail == null || institutionDetail.trim().isEmpty()) {
            errors.put("institution_detail", "支援詳細を入力してください");
        }
        if (institutionVideo == null || institutionVideo.trim().isEmpty()) {
            errors.put("institution_video", "動画URLを入力してください");
        } else {
            // YouTube動画IDを抽出
            String videoId = extractYouTubeVideoId(institutionVideo);
            if (videoId == null) {
                errors.put("institution_video", "有効なYouTubeのURLを入力してください");
            } else {
                institutionVideo = videoId; // 動画IDに置き換える
            }
        }

        // エラーがある場合はJSPへフォワード
        if (!errors.isEmpty()) {
            System.out.println("入力にエラーがあります");
            req.setAttribute("errors", errors);
            req.setAttribute("institution_name", institutionName);
            req.setAttribute("institution_detail", institutionDetail);
            req.setAttribute("institution_video", institutionVideo);
            req.getRequestDispatcher("staff_institution_create.jsp").forward(req, res);
            return;
        }

        // 支援インスタンスを作成して保存
        Institution institution = new Institution();
        institution.setName(institutionName);
        institution.setDetail(institutionDetail);
        institution.setVideo(institutionVideo);

        // 支援を挿入して生成されたIDを取得
        int institutionId = institutionDao.insert(institution);

        // 職員と支援を関連付ける処理
        institutionDao.insert(staff.getStaffID(), institutionId);

        // 完了ページへフォワード
        req.getRequestDispatcher("staff_institution_create_done.jsp").forward(req, res);
    }

    /**
     * YouTubeの動画URLから動画IDを抽出するメソッド
     */
    private String extractYouTubeVideoId(String url) {
        // 正規表現パターン
        String regex = "(?:https?://)?(?:www\\.)?(?:youtube\\.com/watch\\?v=|youtu\\.be/)([a-zA-Z0-9_-]{11})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1); // 動画IDを返す
        }
        return null; // マッチしない場合はnull
    }
}
