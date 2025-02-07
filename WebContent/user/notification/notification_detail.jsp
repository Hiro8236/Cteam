
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String id = request.getParameter("id");
    String name = request.getParameter("title");
    String detail = request.getParameter("detail");
%>

<c:import url="/common/base.jsp">
    <c:param name="title" value="サポ助" />
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <h1>お知らせ詳細</h1>

        <section class="institution-details">
             <div class="notification-name">
                <h2>件名: <%= (name != null && !name.isEmpty()) ? name : "件名がありません" %></h2>
            </div>


            <div class="notification-detail">
                <h3>お知らせ内容</h3>
                <p><%= (detail != null && !detail.isEmpty()) ? detail : "お知らせ内容がありません" %></p>
            </div>

        </section>

        <a href="Notification.action">一覧へ戻る</a>

    </c:param>
</c:import>
