package bean;

import java.io.Serializable;

public class Suggest implements Serializable {
    private int institutionID;
    private String name;
    private String detail;
    private String video;
    private String pdfPath;
    private int incomeRequirement;
    private int eligibleChildrenCount;
    private String requiredEmploymentStatus;
    private String eligibilityReason;
    private String requiredSchoolStatus;

    // Getter & Setter
    public int getInstitutionID() { return institutionID; }
    public void setInstitutionID(int institutionID) { this.institutionID = institutionID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }

    public String getVideo() { return video; }
    public void setVideo(String video) { this.video = video; }

    public String getPdfPath() { return pdfPath; }
    public void setPdfPath(String pdfPath) { this.pdfPath = pdfPath; }

    public int getIncomeRequirement() { return incomeRequirement; }
    public void setIncomeRequirement(int incomeRequirement) { this.incomeRequirement = incomeRequirement; }

    public int getEligibleChildrenCount() { return eligibleChildrenCount; }
    public void setEligibleChildrenCount(int eligibleChildrenCount) { this.eligibleChildrenCount = eligibleChildrenCount; }

    public String getRequiredEmploymentStatus() { return requiredEmploymentStatus; }
    public void setRequiredEmploymentStatus(String requiredEmploymentStatus) { this.requiredEmploymentStatus = requiredEmploymentStatus; }

    public String getEligibilityReason() { return eligibilityReason; }
    public void setEligibilityReason(String eligibilityReason) { this.eligibilityReason = eligibilityReason; }

    public String getRequiredSchoolStatus() { return requiredSchoolStatus; }
    public void setRequiredSchoolStatus(String requiredSchoolStatus) { this.requiredSchoolStatus = requiredSchoolStatus; }
}
