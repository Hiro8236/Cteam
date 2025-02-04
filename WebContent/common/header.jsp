<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="header-container">
    <ul class="navigation-list">
        <li><a href="/Cteam1/user/Home.action">ホーム</a></li>
        <li><a href="/Cteam1/user/InstitutionList.action">制度</a></li>
        <li><a href="/Cteam1/user/calendar/UserCalendar.action">カレンダー</a></li>
        <li><a href="#お知らせ">お知らせ</a></li>
        <li><a href="/Cteam1/user/BookmarkList.action">ブックマーク一覧</a></li>
    </ul>

    <div class="auth-links">
        <c:if test="${not empty userID}">
            <!-- 認証済みの場合 -->
            <a class="auth-link" href="/Cteam1/user/user_update/UserInfo.action">情報変更</a>
            <span class="user-id">UserID: ${userID}</span>
            <a class="auth-link" href="/Cteam1/user/login/Logout.action">ログアウト</a>
        </c:if>
        <c:if test="${empty userID}">
            <!-- 未認証の場合 -->
            <a class="auth-link" href="/Cteam1/user/login/Login.action">ログイン</a>
        </c:if>
    </div>
</div>
