package staff.normalstaff.institution;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bean.Institution;
import dao.InstitutionDao;
import tool.Action;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class StaffInstitutionCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            // デバッグ: パラメータの一覧を出力
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                System.out.println("Parameter: " + paramName + " = " + request.getParameter(paramName));
            }

            // デバッグ: 送信されたパーツ（ファイルやフォームデータ）を出力
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                System.out.println("Part Name: " + part.getName() + ", Size: " + part.getSize());
            }

            // フォームデータの取得
            String name = getStringFromPart(request.getPart("institution_name"));
            String detail = getStringFromPart(request.getPart("institution_detail"));
            String video = getStringFromPart(request.getPart("institution_video"));
            Part pdfPart = request.getPart("institution_pdf");

            System.out.println("name: " + name);
            System.out.println("detail: " + detail);
            System.out.println("video: " + video);
            System.out.println("pdfPart: " + pdfPart);

            // 入力検証
            if (name == null || name.isEmpty()) {
                throw new Exception("支援名は必須です。");
            }

            // PDFの保存処理
            String uploadPath = request.getServletContext().getRealPath("/WEB-INF/pdf/");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String pdfPath = null;
            if (pdfPart != null && pdfPart.getSize() > 0) {
                String fileName = getFileName(pdfPart); // 修正: `getSubmittedFileName()` ではなく `getFileName()` を使用
                pdfPath = uploadPath + File.separator + fileName;
                pdfPart.write(pdfPath);
                System.out.println("PDF saved to: " + pdfPath);
            }

            // データベースに登録
            Institution institution = new Institution();
            institution.setName(name);
            institution.setDetail(detail);
            institution.setVideo(video);
            institution.setPdfPath(pdfPath);

            InstitutionDao dao = new InstitutionDao();
            dao.insert(institution);

            // 完了ページへフォワード
            request.getRequestDispatcher("staff_institution_create_done.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errors", "アップロードに失敗しました: " + e.getMessage());
            request.getRequestDispatcher("staff_institution_create.jsp").forward(request, response);
        }
    }

    /**
     * Java 8 で `Part` からテキストデータを取得するメソッド
     */
    private String getStringFromPart(Part part) throws IOException {
        if (part == null) return null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }
    }

    /**
     * `getSubmittedFileName()` を使わずにファイル名を取得するメソッド（Tomcat 8 互換）
     */
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        if (contentDisp == null) return "unknown_file.pdf";

        for (String content : contentDisp.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 1).trim().replace("\"", "");
            }
        }
        return "unknown_file.pdf";
    }
}
