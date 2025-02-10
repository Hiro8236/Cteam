<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/common/base.jsp">
    <c:param name="title" value="サポ助" />
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <h1>StaffHOME</h1>

        <!-- メッセージが存在する場合、表示 -->
        <c:if test="${not empty message}">
            <div class="alert alert-info">
                <c:out value="${message}" />
            </div>
        </c:if>

        <!-- ページネーションの設定 -->
        <c:set var="itemsPerPage" value="5" />
        <c:choose>
            <c:when test="${not empty param.page}">
                <c:set var="page" value="${param.page}" />
            </c:when>
            <c:otherwise>
                <c:set var="page" value="1" />
            </c:otherwise>
        </c:choose>
        <c:set var="totalItems" value="${fn:length(notifications)}" />
        <c:set var="totalPages" value="${(totalItems mod itemsPerPage == 0) ? (totalItems div itemsPerPage) : ((totalItems div itemsPerPage) + 1)}" />
        <c:set var="startIndex" value="${(page - 1) * itemsPerPage}" />
        <c:set var="endIndex" value="${startIndex + itemsPerPage}" />

        <!-- 通知一覧セクション（スタッフ管理ページの見た目と統一） -->
        <section class="staff-management">
            <!-- ページタイトル -->
            <h2 class="page-title">お知らせ管理</h2>

            <!-- ヘッダー部分：一覧タイトルと新規投稿ボタン -->
            <div class="staff-list-header">
                <h3 class="staff-list-title">お知らせ一覧</h3>
                <a href="NotificationCreate.action" class="btn btn-success">+ 投稿</a>
            </div>

            <c:choose>
                <c:when test="${notifications != null and notifications.size() > 0}">
                    <table class="staff-table">
                        <thead>
                            <tr>
                                <th>件名</th>
                                <th>お知らせ詳細</th>
                                <th style="text-align: right;">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="notification" items="${notifications}" varStatus="status">
                                <!-- 現在のページの範囲内の場合のみ表示 -->
                                <c:if test="${status.index >= startIndex and status.index < endIndex}">
                                    <tr style="cursor: pointer;" onclick="location.href='NotificationDetail.action?id=${notification.notificationID}'">
                                        <td>${notification.title}</td>
                                        <td>${notification.detail}</td>
                                        <td class="staff-actions" style="text-align: right;">
                                            <a href="NotificationEdit.action?id=${notification.notificationID}"
                                               class="btn btn-edit"
                                               onclick="event.stopPropagation();">編集</a>
                                            <a href="NotificationDelete.action?id=${notification.notificationID}"
                                               class="btn btn-delete"
                                               onclick="if(!confirm('本当に削除しますか？')) { event.stopPropagation(); return false; } else { event.stopPropagation(); }">削除</a>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="empty-container">
                        <div>お知らせが存在しませんでした</div>
                        <a href="NotificationCreate.action" class="btn btn-success">+ 投稿</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </section>

        <!-- ページネーションリンク -->
        <div class="pagination" style="text-align: center; margin: 20px 0;">
            <c:if test="${page > 1}">
                <a href="?page=${page - 1}" style="margin-right: 10px;">&laquo; 前へ</a>
            </c:if>
            ページ ${page} / <fmt:formatNumber value="${totalPages}" type="number" maxFractionDigits="0" />
            <c:if test="${page < totalPages}">
                <a href="?page=${page + 1}" style="margin-left: 10px;">次へ &raquo;</a>
            </c:if>
        </div>
    </c:param>
</c:import>
