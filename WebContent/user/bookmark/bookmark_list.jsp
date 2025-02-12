<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
    <c:param name="title" value="サポ助" />
    <c:param name="content">
        <section class="me-4">
            <div class="staff-list-header">
                <h3 class="staff-list-title">ブックマーク一覧</h3>
            </div>

            <!-- セッションからユーザーIDを取得 -->
            <c:set var="userID" value="${sessionScope.userID}" />

            <!-- ログインしていない場合、メッセージを表示し、ログインリンクを追加 -->
            <c:if test="${empty userID}">
                <div> <a href="../login/Login.action">ログイン</a>してください</div>
            </c:if>

            <!-- ログインしている場合、ブックマーク一覧を表示 -->
            <c:if test="${not empty userID}">
                <c:choose>
                    <c:when test="${bookmarklists != null && not empty bookmarklists}">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>支援名</th>
                                    <th>支援詳細</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="bookmark" items="${bookmarklists}">
                                    <tr>
                                        <td>
                                            <c:choose>
                                                <c:when test="${fn:length(bookmark.name) > 15}">
                                                    ${fn:substring(bookmark.name, 0, 15)}&hellip;
                                                </c:when>
                                                <c:otherwise>
                                                    ${bookmark.name}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${fn:length(bookmark.detail) > 30}">
                                                    ${fn:substring(bookmark.detail, 0, 30)}&hellip;
                                                </c:when>
                                                <c:otherwise>
                                                    ${bookmark.detail}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <form action="BookmarkDelete.action" method="post">
                                                <input type="hidden" name="BookmarkID" value="${bookmark.bookmarkID}" />
                                                <input class="btn btn-delete" type="submit" value="削除" />
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <div>あなたのブックマークはありません</div>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </section>
    </c:param>
</c:import>
