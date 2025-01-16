<%-- ログインJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		ぽシステム
	</c:param>


<c:param name="content">


    <h2>メールアドレス</h2>
    EmailAddress: ${user.getEmailAddress()}
    <a class="nav-item px-2" href="/Cteam1/user/UserUpdate.action?action=1&cd=${UserID}">変更</a>
    <h2>パスワード</h2>
    password: ${user.getPassword()}
    <a class="nav-item px-2" href="/Cteam1/user/UserUpdate.action?action=2&cd=${UserID}">変更</a>

    <h2>詳細情報</h2>


	</c:param>
</c:import>