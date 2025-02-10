<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title" value="サポ助" />
    <c:param name="content">

        <section class="me-4">
            <h1>適用可能な支援制度</h1>

            <!-- エラーメッセージがある場合はアラートで表示 -->
            <c:if test="${not empty errorMessage}">
                <script type="text/javascript">
                    alert("${errorMessage}");
                </script>
            </c:if>

            <c:set var="userID" value="${sessionScope.userID}" />

            <c:choose>
                <c:when test="${suggestlists != null && suggestlists.size() > 0}">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>支援名</th>
                                <th>制度詳細</th>
                                <th>登録</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="suggest" items="${suggestlists}">
                                <tr onclick="location.href='/Cteam1/user/institution/InstitutionsDetail.action?id=${suggest.institutionID}'" style="cursor: pointer;">
                                    <td>${suggest.name}</td>
                                    <td>${suggest.detail}</td>
                                    <td>
                                        <a href="/Cteam1/user/bookmark/BookmarkCreate.action?institutionID=${suggest.institutionID}"
                                           onclick="event.stopPropagation();">登録</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <p>該当する支援制度がありません。</p>
                </c:otherwise>
            </c:choose>

            <div class="suggest-link">
                <a href="/Cteam1/user/Home.action">トップページへ戻る</a>
            </div>
        </section>

    </c:param>
</c:import>
