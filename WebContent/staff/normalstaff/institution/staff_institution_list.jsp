<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/staffcommon/base.jsp">
    <c:param name="title">
        サポ助
    </c:param>
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

        <section class="me-4">
            <c:choose>
                <c:when test="${institutions != null and institutions.size() > 0}">
                    <!-- 投稿ボタン -->
                    <a href="StaffInstitutionCreate.action" class="btn-post">投稿</a>
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>支援名</th>
                                <th>支援詳細</th>
                                <th style="text-align: right;">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- ループ変数名を "institution" とし、varStatusでインデックスを取得 -->
                            <c:forEach var="institution" items="${institutions}" varStatus="status">
                                <!-- インデックスがstartIndex～endIndex未満の場合のみ表示 -->
                                <c:if test="${status.index >= startIndex and status.index < endIndex}">
                                    <tr style="cursor: pointer;" onclick="location.href='StaffInstitutionsDetail.action?id=${institution.ID}'">
                                        <td>${institution.name}</td>
                                        <td>${institution.detail}</td>
                                        <td style="text-align: right;">
                                            <a href="StaffInstitutionEdit.action?id=${institution.ID}"
                                               class="btn-edit"
                                               onclick="event.stopPropagation();">編集</a>
                                            <a href="StaffInstitutionDelete.action?id=${institution.ID}"
                                               class="btn-delete"
                                               onclick="if(!confirm('本当に削除しますか？')) { event.stopPropagation(); return false; } else { event.stopPropagation(); }">削除</a>
                                        </td>
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
		    <!-- 前ページリンク（1ページより大きい場合のみ表示） -->
		    <c:if test="${page > 1}">
		        <a href="?page=${page - 1}" style="margin-right: 10px;">&laquo; 前へ</a>
		    </c:if>
		    ページ ${page} / <fmt:formatNumber value="${totalPages}" type="number" maxFractionDigits="0" />
		    <!-- 次ページリンク（現在のページが総ページ数未満の場合のみ表示） -->
		    <c:if test="${page < totalPages}">
		        <a href="?page=${page + 1}" style="margin-left: 10px;">次へ &raquo;</a>
		    </c:if>
		</div>

    </c:param>
</c:import>
