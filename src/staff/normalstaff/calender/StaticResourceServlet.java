package staff.normalstaff.calender;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/static/*")
public class StaticResourceServlet extends HttpServlet {
    private String staticDir;

    @Override
    public void init() throws ServletException {
        staticDir = getServletContext().getRealPath("/static/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        File file = new File(staticDir, path);

        if (file.exists() && file.isFile()) {
            resp.setContentType(getServletContext().getMimeType(file.getName()));
            try (FileInputStream in = new FileInputStream(file); OutputStream out = resp.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
