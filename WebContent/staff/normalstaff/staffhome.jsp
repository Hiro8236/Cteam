<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/staffcommon/base.jsp">
    <c:param name="title">
        ぽシステム
    </c:param>

    <c:param name="content">
        <div class="home-container">
            <!-- タイトル -->
            <h1 class="home-title">StaffHOME</h1>

            <!-- ナビゲーションメニュー -->
            <nav class="home-nav">
                <ul class="home-nav-list">
                    <li class="home-nav-item">
                        <a class="home-nav-btn" href="/Cteam1/staff/normalstaff/notification/StaffNotification.action">お知らせ</a>
                    </li>
                    <li class="home-nav-item">
                        <a class="home-nav-btn" href="/Cteam1/staff/normalstaff/institution/StaffInstitution.action">制度</a>
                    </li>
                    <li class="home-nav-item">
                        <a class="home-nav-btn" href="/Cteam1/staff/normalstaff/calendar/StaffCalendar.action">カレンダー</a>
                    </li>
                </ul>
            </nav>
        </div>
    </c:param>
</c:import>