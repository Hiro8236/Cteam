<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title" value="ぽシステム" />

	<c:param name="scripts"></c:param>

	<%-- コンテンツセクション --%>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4" style="background-color:#f2f2f2">登録画面</h2>
			<div class="bg-success bg-opacity-50 text-center lh-lg">
				 <input type="hidden" name="id" value="${id}" />
    			 <input type="hidden" name="name" value="${name}" />
				<p>登録が完了しました</p>
			</div>
			<div class="lh-lg row" style="margin-top: 8rem;">
				<div class="mx-3 col-2">
					<a href="BookmarkList.action">登録済み支援一覧</a>
				</div>
			</div>
		</section>
	</c:param>
</c:import>
