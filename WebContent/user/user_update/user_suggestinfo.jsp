<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:import url="/common/base.jsp">
    <c:param name="title">推奨情報登録</c:param>
    <c:param name="content">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">

<div class="suggest-container">
    <h2 class="suggest-title">推奨情報を登録</h2>


    <form class="suggest-form" action="UserSuggestInfoExecute.action" method="post">
        <label class="suggest-label" for="annualIncome">年間収入</label>
        <input class="suggest-input" id="annualIncome" name="annualIncome" value="${user.incomeRequirement}" type="number"/>

        <label class="suggest-label" for="childrenCount">子供の人数</label>
        <input class="suggest-input" id="childrenCount" name="childrenCount" value="${user.eligibleChildrenCount}" type="number"/>

<label class="suggest-label" for="employmentStatus">雇用状況</label>
<select class="suggest-select" id="employmentStatus" name="employmentStatus">
    <option value="" <c:if test="${empty user.requiredEmploymentStatus}">selected</c:if>>選択してください</option>
    <option value="就業中" <c:if test="${user.requiredEmploymentStatus eq '就業中'}">selected</c:if>>就業中</option>
    <option value="休職中" <c:if test="${user.requiredEmploymentStatus eq '休職中'}">selected</c:if>>休職中</option>
    <option value="求職中" <c:if test="${user.requiredEmploymentStatus eq '求職中'}">selected</c:if>>求職中</option>
</select>

<label class="suggest-label" for="singleParentReason">ひとり親の理由</label>
<select class="suggest-input" id="singleParentReason" name="singleParentReason">
    <option value="" <c:if test="${empty user.eligibilityReason}">selected</c:if>>選択してください</option>
    <option value="離婚" <c:if test="${user.eligibilityReason eq '離婚'}">selected</c:if>>離婚</option>
    <option value="死別" <c:if test="${user.eligibilityReason eq '死別'}">selected</c:if>>死別</option>
    <option value="その他" <c:if test="${user.eligibilityReason eq 'その他'}">selected</c:if>>その他</option>
</select>

<label class="suggest-label" for="childSchoolStatus">子供の学習状況</label>
<select class="suggest-input" id="childSchoolStatus" name="childSchoolStatus">
    <option value="" <c:if test="${empty user.requiredSchoolStatus}">selected</c:if>>選択してください</option>
    <option value="未就学" <c:if test="${user.requiredSchoolStatus eq '未就学'}">selected</c:if>>未就学</option>
    <option value="幼稚園" <c:if test="${user.requiredSchoolStatus eq '幼稚園'}">selected</c:if>>幼稚園</option>
    <option value="保育園" <c:if test="${user.requiredSchoolStatus eq '保育園'}">selected</c:if>>保育園</option>
    <option value="小学校" <c:if test="${user.requiredSchoolStatus eq '小学校'}">selected</c:if>>小学校</option>
    <option value="中学校" <c:if test="${user.requiredSchoolStatus eq '中学校'}">selected</c:if>>中学校</option>
    <option value="高校" <c:if test="${user.requiredSchoolStatus eq '高校'}">selected</c:if>>高校</option>
    <option value="その他" <c:if test="${user.requiredSchoolStatus eq 'その他'}">selected</c:if>>その他</option>
</select>

        <input type="submit" value="保存" class="suggest-btn" />
    </form>

</div>
</c:param>
</c:import>
