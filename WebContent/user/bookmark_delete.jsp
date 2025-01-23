<%-- 科目削除JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		ぽシステム
	</c:param>

	<c:param name="scripts"></c:param>

	<%-- コンテンツセクション --%>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4" style="background-color:#f2f2f2">ブックマーク削除</h2>
			<form action="BookmarkDeleteExecute.action" method="post">
			    <div class="mx-3 py-2">
			        <div class="mt-3">
			            削除してもよろしいですか?<br>
			            <input type="hidden" name="id" value="${bookmark.BookmarkID}">
			            ${bookmark.BookmarkID}
			            <input class="btn btn-secondary" type="submit" value="削除">
			        </div>
			    </div>
			</form>

			<div class="lh-lg row">
				<div class="mx-3 col-1">
					<a href="BookmarkList.action">戻る</a>
				</div>
			</div>
		</section>
	</c:param>
</c:import>