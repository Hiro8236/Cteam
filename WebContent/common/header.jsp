<%-- ヘッダー --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
<ul>
    <li><a href="#ホーム">ホーム</a></li>
    <li><a href="#制度">制度</a></li>
    <li><a href="#カレンダー">カレンダー</a></li>
    <li><a href="#お知らせ">お知らせ</a></li>
    <li><a href="/Cteam1/user/MailSender.action">メール送信</a></li>

</ul>


  <div class="ms-auto">
        <c:if test="${not empty userID}">
            <!-- 認証済みの場合 -->
            <a class="nav-item px-2" href="/Cteam1/user/UserInfo.action">情報変更</a>
            <span class="nav-item px-2">UserID: ${userID}</span>
            <a class="nav-item px-2" href="Logout.action">ログアウト</a>
        </c:if>
        <c:if test="${empty userID}">
            <!-- 未認証の場合 -->
            <a class="nav-item px-2" href="/Cteam1/user/Login.action">ログイン</a>
        </c:if>
    </div>




</div>
