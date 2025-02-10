<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/common/base.jsp">
    <c:param name="title" value="サポ助" />
    <c:param name="content">
        <section class="me-4">
            <h1>HOME</h1>

            <!-- エラーメッセージがある場合、表示 -->
            <c:if test="${not empty errorMessage}">
                <script type="text/javascript">
                    alert("${errorMessage}");
                </script>
            </c:if>

            <!-- ログインしていない場合は「登録」ボタンと「操作」列を表示しない -->
            <c:set var="userID" value="${sessionScope.userID}" />

            <!-- ページネーションの設定 -->
            <c:set var="itemsPerPage" value="5" />
            <c:choose>
                <c:when test="${not empty param.page}">
                    <!-- 数値として扱うために * 1 を掛ける -->
                    <c:set var="page" value="${param.page * 1}" />
                </c:when>
                <c:otherwise>
                    <c:set var="page" value="1" />
                </c:otherwise>
            </c:choose>
            <!-- 総件数 -->
            <c:set var="totalItems" value="${fn:length(institutions)}" />
            <!-- 総ページ数の計算： (totalItems + itemsPerPage - 1) div itemsPerPage -->
            <c:set var="totalPages" value="${(totalItems + itemsPerPage - 1) div itemsPerPage}" />
            <!-- 小数点以下を切り捨てた整数値 -->
            <c:set var="totalPagesInt" value="${totalPages - (totalPages mod 1)}" />
            <!-- 表示開始／終了インデックス -->
            <c:set var="startIndex" value="${(page - 1) * itemsPerPage}" />
            <c:set var="endIndex" value="${startIndex + itemsPerPage}" />

            <!-- 支援一覧セクション -->
            <section class="staff-management">
                <!-- ヘッダー部分：一覧タイトルと新規登録ボタン -->
                <div class="staff-list-header">
                    <h3 class="staff-list-title">支援一覧</h3>
                </div>

                <c:choose>
                    <c:when test="${institutions != null and institutions.size() > 0}">
                        <table class="staff-table">
                            <thead>
                                <tr>
                                    <th>支援名</th>
                                    <th>支援詳細</th>
                                    <c:if test="${not empty userID}">
                                        <th>操作</th>
                                    </c:if>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="institution" items="${institutions}" varStatus="status">
                                    <c:if test="${status.index >= startIndex and status.index < endIndex}">
                                        <tr onclick="location.href='../user/institution/InstitutionsDetail.action?id=${institution.ID}'" style="cursor: pointer;">
                                            <td>
                                                <c:choose>
                                                    <c:when test="${fn:length(institution.name) > 10}">
                                                        ${fn:substring(institution.name, 0, 10)}&hellip;
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${institution.name}
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${fn:length(institution.detail) > 15}">
                                                        ${fn:substring(institution.detail, 0, 15)}&hellip;
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${institution.detail}
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <c:if test="${not empty userID}">
                                                <td class="staff-actions">
                                                    <a href="bookmark/BookmarkCreate.action?institutionID=${institution.ID}">登録</a>
                                                </td>
                                            </c:if>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <div>支援情報が存在しませんでした</div>
                    </c:otherwise>
                </c:choose>
            </section>

            <!-- ページネーションリンク -->
            <div class="pagination" style="text-align: center; margin: 20px 0;">
                <c:if test="${page > 1}">
                    <a href="?page=${page - 1}" style="margin-right: 10px;">&laquo; 前へ</a>
                </c:if>
                ページ ${page} / <fmt:formatNumber value="${totalPagesInt}" type="number" maxFractionDigits="0" />
                <c:if test="${page < totalPagesInt}">
                    <a href="?page=${page + 1}" style="margin-left: 10px;">次へ &raquo;</a>
                </c:if>
            </div>
        </section>
    </c:param>
</c:import>
