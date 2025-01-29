package staff.normalstaff.institution;

import java.io.File;
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
            // パラメータのデバッグ出力
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                System.out.println("Parameter: " + paramName + " = " + request.getParameter(paramName));
            }

            // パーツのデバッグ出力
            Collection<Part> parts = request.getParts();
            parts.stream().forEach(part -> {
                System.out.println("name: " + part.getName());
            });

            // フォームの値を取得
            String name = request.getParameter("institution_name");
            String detail = request.getParameter("institution_detail");
            String video = request.getParameter("institution_video");
            Part pdfPart = request.getPart("institution_pdf");

            System.out.println("name: " + name);
            System.out.println("detail: " + detail);
            System.out.println("video: " + video);
            System.out.println("pdfPart: " + pdfPart);

            // 入力検証
            if (name == null || name.isEmpty()) {
                throw new Exception("支援名は必須です。");
            }

            // PDF保存
            String uploadPath = request.getServletContext().getRealPath("/WEB-INF/pdf/");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String pdfPath = null;
            if (pdfPart != null && pdfPart.getSize() > 0) {
                String fileName = pdfPart.getSubmittedFileName();
                pdfPath = uploadPath + File.separator + fileName;
                pdfPart.write(pdfPath);
            }

            // データベースに登録
            Institution institution = new Institution();
            institution.setName(name);
            institution.setDetail(detail);
            institution.setVideo(video);
            institution.setPdfPath(pdfPath);

            InstitutionDao dao = new InstitutionDao();
            dao.insert(institution);

            // 完了ページへリダイレクト
            response.sendRedirect("StaffInstitutionCreateComplete.action");
        } catch (Exception e) {
            // エラー時の処理
            e.printStackTrace();
            request.setAttribute("errors", e.getMessage());
            request.setAttribute("institution_name", request.getParameter("institution_name"));
            request.setAttribute("institution_detail", request.getParameter("institution_detail"));
            request.setAttribute("institution_video", request.getParameter("institution_video"));
            request.getRequestDispatcher("staff_institution_create.jsp").forward(request, response);
        }
    }
}
