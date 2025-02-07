<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title" value="サポ助" />
    <c:param name="scripts"></c:param>

	<%-- コンテンツセクション --%>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4" style="background-color:#f2f2f2">登録画面</h2>

            <!-- エラーメッセージがある場合に表示 -->
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-warning">
                    ${errorMessage}
                </div>
            </c:if>

            <!-- 登録が完了した場合のみ表示 -->
            <c:if test="${empty errorMessage}">
                <div class="bg-success bg-opacity-50 text-center lh-lg">
                    <p>登録が完了しました</p>
                </div>
            </c:if>

			<div class="lh-lg row" style="margin-top: 8rem;">
				<div class="mx-3 col-2">
					<a href="BookmarkList.action">登録済み支援一覧</a>
				</div>
			</div>
		</section>
	</c:param>
</c:import>
