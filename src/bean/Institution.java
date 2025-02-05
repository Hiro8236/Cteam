package bean;

import java.io.Serializable;

public class Institution implements Serializable {
    private int id;
    private String name;
    private String detail;
    private String video;
    private String pdfPath; // PDFのパスを保存

    public int getID() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getVideo() {
        return video;
    }
    public void setVideo(String video) {
        this.video = video;
    }
    public String getPdfPath() {
        return pdfPath;
    }
    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }
}
