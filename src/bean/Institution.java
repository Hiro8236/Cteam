package bean;

import java.io.Serializable;

public class Institution implements Serializable {
    private int id;
    private String name;
    private String detail;
    private String video;
    private String pdfPath; // PDFのパスを保存
    private Integer incomeRequirement; // NULL許容
    private Integer eligibleChildrenCount; // NULL許容
    private String requiredEmploymentStatus;
    private String eligibilityReason;
    private String requiredSchoolStatus;

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



    public Integer getIncomeRequirement() {
        return incomeRequirement;
    }

    public void setIncomeRequirement(Integer incomeRequirement) {
        this.incomeRequirement = incomeRequirement;
    }

    public Integer getEligibleChildrenCount() {
        return eligibleChildrenCount;
    }

    public void setEligibleChildrenCount(Integer eligibleChildrenCount) {
        this.eligibleChildrenCount = eligibleChildrenCount;
    }

    public String getRequiredEmploymentStatus() {
        return requiredEmploymentStatus;
    }

    public void setRequiredEmploymentStatus(String requiredEmploymentStatus) {
        this.requiredEmploymentStatus = requiredEmploymentStatus;
    }

    public String getEligibilityReason() {
        return eligibilityReason;
    }

    public void setEligibilityReason(String eligibilityReason) {
        this.eligibilityReason = eligibilityReason;
    }

    public String getRequiredSchoolStatus() {
        return requiredSchoolStatus;
    }

    public void setRequiredSchoolStatus(String requiredSchoolStatus) {
        this.requiredSchoolStatus = requiredSchoolStatus;
    }


}
