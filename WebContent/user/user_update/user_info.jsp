<%-- ログインJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		ぽシステム
	</c:param>


<c:param name="content">

    <!-- メッセージが存在する場合、表示 -->
    <c:if test="${not empty message}">
        <div class="alert alert-info">
            <c:out value="${message}"/>
        </div>
    </c:if>



    <h2>メールアドレス</h2>
    EmailAddress: ${user.getEmailAddress()}
    <a class="nav-item px-2" href="/Cteam1/user/user_update/UserUpdate.action?action=1&cd=${UserID}">変更</a>
    <h2>パスワード</h2>
    password: ${user.getPassword()}
    <a class="nav-item px-2" href="/Cteam1/user/user_update/UserUpdate.action?action=2&cd=${UserID}">変更</a>

    <h2>詳細情報</h2>
    <div><a href="UserSuggestInfo.action">詳細情報登録</a></div>

    <h3>アカウント削除:<a class="nav-item px-2" href="/Cteam1/user/user_update/UserUpdate.action?action=3&cd=${UserID}">削除</a></h3>


	</c:param>
</c:import>