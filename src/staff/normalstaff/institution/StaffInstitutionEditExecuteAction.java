package staff.normalstaff.institution;

import java.io.File;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.InstitutionDao;
import tool.Action;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class StaffInstitutionEditExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ▼ 1) 文字項目（id, name, detail, pdfPath）は request.getParameter(...) で取得
        //    ※ ただし multipart/form-data だと Tomcatのバージョンや設定によっては
        //       getParameter() が空になる場合もある点に注意してください。
        //       動作しない場合は getPart(...) でテキスト項目を取得する方法に切り替えが必要です。
        String idParam = req.getParameter("id");
        String name    = req.getParameter("name");
        String detail  = req.getParameter("detail");
        String pdfPath = req.getParameter("pdfPath");

        // ▼ 2) 追加: ファイルパートを取得 (今まで無かった処理)
        Part pdfPart   = req.getPart("institution_pdf");  // <input type="file" name="institution_pdf" />

        // ▼ 3) バリデーション (ID, name, detail が空かどうか)
        if (idParam == null || idParam.isEmpty() ||
            name == null    || name.isEmpty()    ||
            detail == null  || detail.isEmpty()) {

            req.setAttribute("message", "入力された情報が不完全です");
            req.getRequestDispatcher("staff_institution_edit.jsp").forward(req, res);
            return;
        }

        try {
            // IDを数値に変換
            int id = Integer.parseInt(idParam);

            // ▼ 4) PDFの再アップロードがあった場合は、サーバにファイルを保存し、pdfPathを上書きする
            if (pdfPart != null && pdfPart.getSize() > 0) {
                // アップロード先フォルダを用意 (例: /WEB-INF/pdf)
                String uploadPath = req.getServletContext().getRealPath("/WEB-INF/pdf");
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // ファイル名を取り出し
                String fileName = extractFileName(pdfPart);

                // 実際にサーバに保存
                String newPdfFullPath = uploadPath + File.separator + fileName;
                pdfPart.write(newPdfFullPath);

                // ※ もし古いPDFを削除したいならここで削除しても良い
                //  ...

                // pdfPath を上書き
                pdfPath = newPdfFullPath;
            }

            // ▼ 5) InstitutionDaoを使用して更新処理
            InstitutionDao institutionDao = new InstitutionDao();
            // DB側updateで name, detail, pdfPath を更新する想定
            boolean isUpdated = institutionDao.updateInstitution(id, name, detail, pdfPath);

            if (isUpdated) {
                // 更新成功
                req.setAttribute("message", "支援情報が正常に更新されました");
                // 一覧や詳細へフォワード/リダイレクトなど
                req.getRequestDispatcher("StaffInstitution.action").forward(req, res);
            } else {
                // 更新失敗
                req.setAttribute("message", "支援情報の更新に失敗しました");
                req.getRequestDispatcher("staff_institution_edit.jsp").forward(req, res);
            }

        } catch (NumberFormatException e) {
            // IDが無効な場合
            req.setAttribute("message", "無効な支援IDです");
            req.getRequestDispatcher("staff_institution_edit.jsp").forward(req, res);
        }
    }

    /**
     * Tomcat8向け: Partのヘッダからファイル名を取り出す
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        if (contentDisp == null) {
            return "unknown_file.pdf";
        }
        for (String cd : contentDisp.split(";")) {
            cd = cd.trim();
            if (cd.startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).replace("\"", "");
            }
        }
        return "unknown_file.pdf";
    }
}
