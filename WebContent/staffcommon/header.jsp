<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- セッション情報の取得 -->
<%@ page session="true" %>

<!-- ヘッダー部分の共通処理 -->
<header class="d-flex justify-content-between align-items-center mb-3 mb-md-0 text-dark text-decoration-none">
    <!-- 左側メニュー -->
    <ul class="list-unstyled mb-0">
        <c:if test="${user != null && user.staffRole == 1}">
            <li><a href="/Cteam1/staff/normalstaff/StaffHome.action">ホーム</a></li>
            <li><a href="#制度">制度</a></li>
            <li><a href="/Cteam1/staff/normalstaff/calendar/staff_calendar.jsp">カレンダー</a></li>
            <li><a href="#お知らせ">これは職員です</a></li>
        </c:if>
        <c:if test="${user != null && user.staffRole == 2}">
            <li><a href="/Cteam1/staff/adminstaff/AdminStaffHome.action">ホーム</a></li>
            <li><a href="#制度">制度</a></li>
            <li><a href="#カレンダー">カレンダー</a></li>
            <li><a href="#お知らせ">これは管理者です</a></li>
        </c:if>
    </ul>

    <!-- 右側のログアウトとスタッフ情報 -->
    <div class="d-flex align-items-center">
        <c:if test="${user != null && user.isAuthenticated()}">
            <!-- 認証済みの場合 -->
            <span class="px-2">${user.staffName}</span>
            <a class="px-2" href="StaffLogout.action">ログアウト</a>
        </c:if>
        <c:if test="${user == null || !user.isAuthenticated()}">
            <!-- 未認証の場合 -->
            <h1>職員用ホームページ</h1>
        </c:if>
    </div>
</header>
