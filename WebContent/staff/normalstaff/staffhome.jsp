<%-- ログインJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>ぽシステム</title>
</head>
<body>

<c:import url="/staffcommon/base.jsp">
    <c:param name="title">
        ぽシステム
    </c:param>

    <c:param name="content">
        <h1>StaffHOME</h1>
        <nav class="vertical-nav">
            <ul class="nav flex-column">
                <li class="nav-item">
                    <a class="nav-link btn btn-outline-primary btn-sm" href="/Cteam1/staff/normalstaff/StaffHome.action">ホーム</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link btn btn-outline-primary btn-sm" href="/Cteam1/staff/normalstaff/institution/StaffInstitution.action">制度</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link btn btn-outline-primary btn-sm" href="/Cteam1/staff/normalstaff/calendar/StaffCalendar.action">カレンダー</a>
                </li>
            </ul>
        </nav>
    </c:param>
</c:import>

</body>
</html>
