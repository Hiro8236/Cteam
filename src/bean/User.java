package bean;

import java.io.Serializable;

public class User implements Serializable {

    private int userID;
    private String emailAddress;
    private String password;

    private
    boolean isAuthenticated;

    // 新たに追加したフィールド
    private int incomeRequirement;
    private int eligibleChildrenCount;
    private String requiredEmploymentStatus;
    private String eligibilityReason;
    private String requiredSchoolStatus;

    // Getter/Setter（追加したフィールドも含めて）
    public int getUserID() {
        return userID;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    // 新たに追加したフィールドのgetter/setter
    public int getIncomeRequirement() {
        return incomeRequirement;
    }

    public void setIncomeRequirement(int incomeRequirement) {
        this.incomeRequirement = incomeRequirement;
    }

    public int getEligibleChildrenCount() {
        return eligibleChildrenCount;
    }

    public void setEligibleChildrenCount(int eligibleChildrenCount) {
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

    // 既存のgetter/setter
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

}
