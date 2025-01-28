package staff.normalstaff.institution;

import java.io.File;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import bean.Institution;
import dao.InstitutionDao;
import tool.Action;

@MultipartConfig(
	    location = "/tmp", // 一時的なファイル保存場所（ディレクトリ）
	    fileSizeThreshold = 1024 * 1024, // ファイルのしきい値、1MBを設定
	    maxFileSize = 1024 * 1024 * 10,  // 最大ファイルサイズ、10MB
	    maxRequestSize = 1024 * 1024 * 50 // 最大リクエストサイズ、50MB
	)

public class StaffInstitutionCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
/*	    // フォームからの入力値を取得
	    String name = request.getParameter("institution_name");
	    String detail = request.getParameter("institution_detail");
	    String video = request.getParameter("institution_video");

*/

	    Part pdfPart = request.getPart("institution_pdf"); // アップロードされたPDFファイル


		Part namePart = request.getPart("institution_name");
		Part detailPart = request.getPart("institution_detail");
		Part videoPart = request.getPart("institution_video");

		// Partから文字列データを取得
		String name = namePart != null ? namePart.getSubmittedFileName() : "";
		String detail = detailPart != null ? detailPart.getSubmittedFileName() : "";
		String video = videoPart != null ? videoPart.getSubmittedFileName() : "";


		System.out.println("。。。"+name);

	    // PDFファイルの保存先ディレクトリを定義
	 // 現在のクラスの位置から相対的に保存ディレクトリを指定
	    String uploadPath = getClass().getResource("").getPath() + "pdf/";
	    File uploadDir = new File(uploadPath);
	    if (!uploadDir.exists()) {
	        uploadDir.mkdirs(); // ディレクトリが存在しない場合は作成
	    }

	    // PDFファイルのパスを設定
	    String pdfPath = null;
	    if (pdfPart != null && pdfPart.getSize() > 0) {
	        String fileName = pdfPart.getSubmittedFileName();
	        pdfPath = uploadPath + fileName;
	        pdfPart.write(pdfPath); // PDFファイルを指定したディレクトリに保存
	    }

	    // Institutionオブジェクトを作成し、値を設定
	    Institution institution = new Institution();
	    institution.setName(name);
	    institution.setDetail(detail);
	    institution.setVideo(video);
	    institution.setPdfPath(pdfPath); // PdfPathを設定


	    System.out.println("Name: " + institution.getName());
	    System.out.println("Detail: " + institution.getDetail());
	    System.out.println("Video: " + institution.getVideo());
	    System.out.println("PdfPath: " + institution.getPdfPath());


	    // DAOを利用してデータベースに挿入
	    InstitutionDao dao = new InstitutionDao();
	    dao.insert(institution);

	    // 完了ページへのリダイレクト処理
	    response.sendRedirect("StaffInstitutionCreateComplete.action");
	}
}