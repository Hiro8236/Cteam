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
        <input class="suggest-input" id="annualIncome" name="annualIncome" type="number" required />

        <label class="suggest-label" for="childrenCount">子供の人数</label>
        <input class="suggest-input" id="childrenCount" name="childrenCount" type="number" required />

        <label class="suggest-label" for="employmentStatus">雇用状況</label>
        <select class="suggest-select" id="employmentStatus" name="employmentStatus" required>
            <option value="" selected>選択してください</option>
            <option value="就業中">就業中</option>
            <option value="休職中">休職中</option>
            <option value="求職中">求職中</option>
        </select>

        <label class="suggest-label" for="singleParentReason">ひとり親の理由</label>
        <input class="suggest-input" id="singleParentReason" name="singleParentReason" type="text" required />

        <label class="suggest-label" for="childSchoolStatus">子供の学習状況</label>
        <input class="suggest-input" id="childSchoolStatus" name="childSchoolStatus" type="text" required />

        <input type="submit" value="保存" class="suggest-btn" />
    </form>
</div>
</c:param>
</c:import>


