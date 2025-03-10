<%-- 科目削除完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		サポ助
	</c:param>

	<c:param name="scripts"></c:param>

	<%-- コンテンツセクション --%>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4" style="background-color:#f2f2f2">削除完了画面</h2>
			<div class="bg-success bg-opacity-50 text-center lh-lg">
				<p>削除が完了しました</p>
			</div>
			<div class="lh-lg row" style="margin-top: 8rem;">
				<div class="mx-3 col-2">
					<a href="BookmarkList.action">ブックマーク一覧</a>
				</div>
			</div>
		</section>
	</c:param>
</c:import>
