<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/staffcommon/base.jsp">
    <c:param name="title">
        サポ助
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="logout-container">
            <h2 class="logout-title">ログアウト</h2>
            <div class="logout-message">
                <p>ログアウトしました</p>
            </div>
            <div class="back-to-login">
                <a href="/Cteam1/staff/StaffLogin.action">ログイン</a>
            </div>
        </section>
    </c:param>
</c:import>
