<%-- 新規登録完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/staffcommon/base.jsp">
	<c:param name="title">
		サポ助
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">職員削除</h2>
			<div class="bg-success bg-opacity-50 text-center lh-lg">
				<p>削除しました</p>
			</div>
			<div class="lh-lg row" style="margin-top: 4rem;">
				<div class="mx-3 col-2">
					<a href="StaffList.action">職員一覧へ</a>
				</div>
			</div>
		</section>
	</c:param>
</c:import>