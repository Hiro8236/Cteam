<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/common/base.jsp">
    <c:param name="title" value="サポ助" />
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <h1>お知らせ一覧</h1>

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
<c:set var="totalItems" value="${fn:length(institutions)}" />
<!-- 修正: 総ページ数の計算をシンプルな式に変更 -->
<c:set var="totalPages" value="${(totalItems + itemsPerPage - 1) div itemsPerPage}" />
<!-- 表示開始／終了インデックス -->
<c:set var="startIndex" value="${(page - 1) * itemsPerPage}" />
<c:set var="endIndex" value="${startIndex + itemsPerPage}" />


        <!-- お知らせ一覧セクション（スタッフ管理の見た目を統一） -->
        <section class="staff-management">
            <!-- ヘッダー部分：一覧タイトルと新規投稿ボタン -->
            <div class="staff-list-header">
                <h3 class="staff-list-title">お知らせ一覧</h3>
            </div>

            <c:choose>
                <c:when test="${notifications != null and notifications.size() > 0}">
                    <table class="staff-table">
                        <thead>
                            <tr>
                                <th>件名</th>
                                <th>お知らせ詳細</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="notification" items="${notifications}" varStatus="status">
                                <!-- 現在のページの範囲内の場合のみ表示 -->
                                <c:if test="${status.index >= startIndex and status.index < endIndex}">
                                    <tr style="cursor: pointer;" onclick="location.href='notification_detail.jsp?id=${notification.notificationID}'">
                                        <td>
                                            <c:choose>
                                                <c:when test="${fn:length(notification.title) > 10}">
                                                    ${fn:substring(notification.title, 0, 10)}&hellip;
                                                </c:when>
                                                <c:otherwise>
                                                    ${notification.title}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${fn:length(notification.detail) > 20}">
                                                    ${fn:substring(notification.detail, 0, 20)}&hellip;
                                                </c:when>
                                                <c:otherwise>
                                                    ${notification.detail}
                                                </c:otherwise>
                                            </c:choose>
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
