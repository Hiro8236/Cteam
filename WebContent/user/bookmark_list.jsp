<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title" value="ぽシステム" />
    <c:param name="content">

        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4" style="background-color:#f2f2f2">
                ブックマーク一覧
            </h2>

            <!-- セッションからユーザーIDを取得 -->
            <c:set var="userID" value="${sessionScope.userID}" />

            <!-- ログインしていない場合、メッセージを表示し、ログインリンクを追加 -->
            <c:if test="${empty userID}">
                <div> <a href="login/Login.action">ログイン</a>してください</div>
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
                                        <td>${bookmark.name}</td>
                                        <td>${bookmark.detail}</td>
                                        <td>
                                            <form action="BookmarkDelete.action" method="post">
                                                <input type="hidden" name="BookmarkID" value="${bookmark.bookmarkID}" />
                                                <input class="btn btn-secondary" type="submit" value="削除" />
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
