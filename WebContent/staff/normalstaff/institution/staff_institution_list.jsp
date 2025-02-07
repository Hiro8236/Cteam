<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/staffcommon/base.jsp">
    <c:param name="title">支援一覧</c:param>
    <c:param name="content">
        <h1>StaffHOME</h1>

        <!-- メッセージが存在する場合、表示 -->
        <c:if test="${not empty message}">
            <div class="alert alert-info">
                <c:out value="${message}"/>
            </div>
        </c:if>

        <!-- ページネーションの設定 -->
        <c:set var="itemsPerPage" value="5" />
        <!-- ページ番号をリクエストパラメータから取得。なければ1 -->
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
        <!-- 総ページ数（割り切れるかどうかで切り上げ） -->
        <c:set var="totalPages" value="${(totalItems mod itemsPerPage == 0) ? (totalItems div itemsPerPage) : ((totalItems div itemsPerPage) + 1)}" />
        <!-- 表示開始／終了インデックス -->
        <c:set var="startIndex" value="${(page - 1) * itemsPerPage}" />
        <c:set var="endIndex" value="${startIndex + itemsPerPage}" />

        <!-- 以下、スタッフ一覧の見た目を適用 -->
        <section class="staff-management">
            <!-- ページタイトル -->
            <h2 class="page-title">支援管理</h2>

            <!-- 支援一覧ヘッダー（タイトル + 新規登録ボタン） -->
            <div class="staff-list-header">
                <h3 class="staff-list-title">支援一覧</h3>
                <a href="StaffInstitutionCreate.action" class="btn btn-success">+ 新規登録</a>
            </div>

            <c:choose>
                <c:when test="${institutions != null and institutions.size() > 0}">
                    <table class="staff-table">
                        <thead>
                            <tr>
                                <th>支援ID</th>
                                <th>支援名</th>
                                <th>支援詳細</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="institution" items="${institutions}" varStatus="status">
                                <c:if test="${status.index >= startIndex and status.index < endIndex}">
                                    <!-- 行全体に詳細画面へのリンクを設定 -->
                                    <tr style="cursor: pointer;" onclick="location.href='StaffInstitutionsDetail.action?id=${institution.ID}'">
                                        <td>${institution.ID}</td>
                                        <td>${institution.name}</td>
                                        <td>${institution.detail}</td>
                                        <td class="staff-actions">
                                            <!-- 変更ボタン -->
                                            <form action="StaffInstitutionEdit.action" method="post" style="display:inline;">
                                                <input type="hidden" name="id" value="${institution.ID}" />
                                                <button type="submit" class="btn btn-edit" onclick="event.stopPropagation();">変更</button>
                                            </form>
                                            <!-- 削除ボタン -->
                                            <form action="StaffInstitutionDelete.action" method="post" style="display:inline;">
                                                <input type="hidden" name="id" value="${institution.ID}" />
                                                <button type="submit" class="btn btn-delete" onclick="return confirm('本当に削除しますか？');">削除</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div class="empty-container">
                        <div>支援情報が存在しませんでした</div>
                        <a href="StaffInstitutionCreate.action" class="btn btn-success">+ 新規登録</a>
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
