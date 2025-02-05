package staff.normalstaff.institution;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Institution;
import dao.InstitutionDao;
import tool.Action;

public class StaffInstitutionDownloadAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // (1) リクエストパラメータからIDを取得
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            // IDが無いならエラー扱い
            request.setAttribute("message", "PDFをダウンロードできません。IDが指定されていません。");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // (2) InstitutionDaoを使って該当IDのレコードを取得
        InstitutionDao dao = new InstitutionDao();
        Institution institution = dao.findById(Integer.parseInt(idParam));

        if (institution == null) {
            // 該当データがなければエラー
            request.setAttribute("message", "該当する支援情報が見つかりません。");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // (3) pdfPathがnullや空の場合もダウンロード不可
        String pdfPath = institution.getPdfPath();
        if (pdfPath == null || pdfPath.isEmpty()) {
            request.setAttribute("message", "PDFファイルが登録されていません。");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // (4) ファイルを取得
        File pdfFile = new File(pdfPath);
        if (!pdfFile.exists() || !pdfFile.isFile()) {
            request.setAttribute("message", "PDFファイルがサーバに存在しません。");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // (5) レスポンスヘッダを設定 → PDFをダウンロードさせる or 表示させる
        response.setContentType("application/pdf");
        // Content-Disposition を "attachment" にすれば強制ダウンロード
        // 例) filename は適宜ファイル名を分解して取得
        String downloadName = pdfFile.getName();  // 例: 物理ファイル名そのまま
        response.setHeader("Content-Disposition",
                           "attachment; filename=\"" + downloadName + "\"");

        // (6) PDFファイルをレスポンス出力ストリームに書き込む
        try (FileInputStream fis = new FileInputStream(pdfFile);
             OutputStream os = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("message", "PDFダウンロード中にエラーが発生しました: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
