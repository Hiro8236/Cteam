<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- セッション情報の取得 -->
<%@ page session="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- ヘッダー部分の共通処理 --%>
<div class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
    <ul>
        <!-- 職員ログインの場合 -->
        <c:if test="${user != null && user.staffRole == 1}">
            <li><a href="/Cteam1/staff/normalstaff/StaffHome.action">ホーム</a></li>
            <li><a href="#制度">制度</a></li>
            <li><a href="/Cteam1/staff/normalstaff/StaffCalendar.action">カレンダー</a></li>
            <li><a href="#お知らせ">これは職員です</a></li>
        </c:if>

        <!-- 管理者ログインの場合 -->
        <c:if test="${user != null && user.staffRole == 2}">
            <li><a href="/Cteam1/staff/adminstaff/AdminStaffHome.action">ホーム</a></li>
            <li><a href="#制度">制度</a></li>
            <li><a href="#カレンダー">カレンダー</a></li>
            <li><a href="#お知らせ">これは管理者です</a></li>
        </c:if>
    </ul>

    <div class="ms-auto">
        <c:if test="${user != null && user.isAuthenticated()}">
            <!-- 認証済みの場合 -->
            <a class="nav-item px-2" href="/Cteam1/staff/UserInfo.action">情報変更</a>
            <span class="nav-item px-2">staffID: ${user.staffID}</span>
            <a class="nav-item px-2" href="StaffLogout.action">ログアウト</a>
        </c:if>
        <c:if test="${user == null || !user.isAuthenticated()}">
            <!-- 未認証の場合 -->
            <h1>職員用ホームページ</h1>
        </c:if>
    </div>
</div>
