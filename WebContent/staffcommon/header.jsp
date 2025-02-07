<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- セッション情報の取得 -->
<%@ page session="true" %>

<!-- ヘッダー部分の共通処理 -->
<c:choose>
    <c:when test="${user != null && user.staffRole == 1}">
        <c:set var="headerClass" value="normal-header" />
    </c:when>
    <c:when test="${user != null && user.staffRole == 2}">
        <c:set var="headerClass" value="admin-header" />
    </c:when>
    <c:otherwise>
        <c:set var="headerClass" value="" />
    </c:otherwise>
</c:choose>

<header class="header-container ${headerClass}">
    <!-- 左側メニュー -->
    <ul class="navigation-list">
        <c:if test="${user != null && user.staffRole == 1}">
            <li><a href="/Cteam1/staff/normalstaff/StaffHome.action">ホーム</a></li>
            <li><a href="/Cteam1/staff/normalstaff/institution/StaffInstitution.action">制度</a></li>
            <li><a href="/Cteam1/staff/normalstaff/calendar/StaffCalendar.action">カレンダー</a></li>
            <li><a href="/Cteam1/staff/normalstaff/notification/StaffNotification.action">お知らせ</a></li>
        </c:if>
        <c:if test="${user != null && user.staffRole == 2}">
            <li><a href="/Cteam1/staff/adminstaff/AdminStaffHome.action">ホーム</a></li>
            <li><a href="/Cteam1/staff/adminstaff/StaffList.action">職員一覧</a></li>
        </c:if>
    </ul>

    <!-- 右側のログアウトとスタッフ情報 -->
    <div class="auth-links">
        <c:if test="${user != null && user.isAuthenticated()}">
            <!-- 認証済みの場合 -->
            <span class="user-id">${user.staffName}</span>
            <a class="auth-link" href="/Cteam1/staff/StaffLogout.action">ログアウト</a>
        </c:if>
        <c:if test="${user == null || !user.isAuthenticated()}">
            <!-- 未認証の場合 -->
            <h1>職員用ホームページ</h1>
        </c:if>
    </div>
</header>
