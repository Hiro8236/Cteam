<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="header-container">
    <ul class="navigation-list">
        <li><a href="/Cteam1/user/Home.action">ホーム</a></li>
        <li class="dropdown">
		    <a href="#" class="dropbtn" onclick="return false;">制度</a>
		    <ul class="dropdown-content">
		        <li><a href="/Cteam1/user/institution/InstitutionList.action">制度一覧</a></li>
		        <li><a href="/Cteam1/user/institution/SuggestList.action">おすすめ制度一覧</a></li>
		        <li><a href="/Cteam1/user/bookmark/BookmarkList.action">ブックマーク一覧</a></li>
		    </ul>
		</li>
        <li><a href="/Cteam1/user/calendar/UserCalendar.action">カレンダー</a></li>
        <li><a href="/Cteam1/user/notification/Notification.action">お知らせ</a></li>
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

