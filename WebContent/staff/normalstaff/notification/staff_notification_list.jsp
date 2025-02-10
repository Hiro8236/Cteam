<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/staffcommon/base.jsp">
    <c:param name="title">お知らせ一覧</c:param>
    <c:param name="content">
        <h1>サポ助</h1>

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
        <!-- 総件数 -->
        <c:set var="totalItems" value="${fn:length(notifications)}" />
        <!-- 総ページ数（割り切れるかどうかで切り上げ） -->
        <c:set var="totalPages" value="${(totalItems mod itemsPerPage == 0) ? (totalItems div itemsPerPage) : ((totalItems div itemsPerPage) + 1)}" />
        <!-- 総ページ数が1未満の場合は1に固定 -->
        <c:if test="${totalPages < 1}">
            <c:set var="totalPages" value="1" />
        </c:if>
        <!-- リクエストされたページが総ページ数より大きい場合は1に戻す -->
        <c:if test="${page > totalPages}">
            <c:set var="page" value="1" />
        </c:if>
        <!-- 表示開始／終了インデックス -->
        <c:set var="startIndex" value="${(page - 1) * itemsPerPage}" />
        <c:set var="endIndex" value="${startIndex + itemsPerPage}" />

        <!-- お知らせ一覧セクション（スタッフ管理の見た目を統一） -->
        <section class="staff-management">
            <!-- ページタイトル -->
            <h2 class="page-title">お知らせ管理</h2>

            <!-- ヘッダー部分：一覧タイトルと新規投稿ボタン -->
            <div class="staff-list-header">
                <h3 class="staff-list-title">お知らせ一覧</h3>
                <a href="StaffNotificationCreate.action" class="btn btn-success">+ 投稿</a>
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
                                <c:if test="${status.index >= startIndex and status.index < endIndex}">
                                    <tr style="cursor: pointer;" onclick="location.href='StaffNotificationDetail.action?id=${notification.notificationID}'">
                                        <td>${notification.title}</td>
                                        <td>${notification.detail}</td>
                                        <td class="staff-actions" style="text-align: right;">
                                            <a href="StaffNotificationDelete.action?id=${notification.notificationID}"
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
                        <a href="StaffNotificationCreate.action" class="btn btn-success">+ 投稿</a>
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
