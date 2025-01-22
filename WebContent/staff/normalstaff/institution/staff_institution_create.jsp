<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/staffcommon/base.jsp">
    <c:param name="title">支援登録</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">支援登録</h2>

            <!-- ログイン中の職員情報を表示 -->
            <p>ログイン中: <c:out value="${user.staffName}" /></p>

            <!-- エラーメッセージの表示 -->
            <c:if test="${not empty errors}">
                <ul class="list-unstyled text-danger">
                    <c:forEach var="error" items="${errors}">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>

            <!-- 新しい支援を登録するフォーム -->
            <h3 class="mt-5">新しい支援を登録</h3>
            <form action="StaffInstitutionCreateExecute.action" method="post">
                <div class="mb-3">
                    <label for="institution_name" class="form-label">支援名:</label>
                    <input type="text" class="form-control" id="institution_name" name="institution_name" value="${institution_name}" />
                </div>
                <div class="mb-3">
                    <label for="institution_detail" class="form-label">支援詳細:</label>
                    <textarea class="form-control" id="institution_detail" name="institution_detail" rows="3">${institution_detail}</textarea>
                </div>
                <!-- 動画URL入力フィールドを追加 -->
                <div class="mb-3">
                    <label for="institution_video" class="form-label">動画URL:</label>
                    <input type="text" class="form-control" id="institution_video" name="institution_video" value="${institution_video}" />
                </div>
                <button type="submit" class="btn btn-primary">登録</button>
            </form>
        </section>
    </c:param>
</c:import>
