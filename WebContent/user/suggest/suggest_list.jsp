<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/common/base.jsp">
    <c:param name="title" value="サポ助" />
    <c:param name="content">
        <section class="staff-management">

            <c:if test="${not empty errorMessage}">
                <script type="text/javascript">
                    alert("${errorMessage}");
                </script>
            </c:if>

            <c:set var="user" value="${sessionScope.user}" />
            <c:set var="itemsPerPage" value="5" />
            <c:set var="page" value="${not empty param.page ? param.page * 1 : 1}" />
            <c:set var="totalItems" value="${fn:length(suggestlists)}" />
            <c:set var="totalPages" value="${(totalItems + itemsPerPage - 1) div itemsPerPage}" />
            <c:set var="startIndex" value="${(page - 1) * itemsPerPage}" />
            <c:set var="endIndex" value="${startIndex + itemsPerPage}" />

            <div class="staff-list-header">
                <h3 class="staff-list-title">おすすめ支援一覧</h3>
            </div>

            <c:choose>
                <c:when test="${not empty suggestlists}">
                    <table class="staff-table">
                        <thead>
                            <tr>
                                <th>支援名</th>
                                <th>制度詳細</th>
                                <th>登録</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="suggest" items="${suggestlists}" varStatus="status">
                                <c:if test="
                                    (${empty suggest.incomeRequirement or user.AnnualIncome >= suggest.incomeRequirement}) and
                                    (${empty suggest.eligibleChildrenCount or user.ChildrenCount >= suggest.eligibleChildrenCount}) and
                                    (${empty suggest.requiredEmploymentStatus or user.EmploymentStatus eq suggest.requiredEmploymentStatus}) and
                                    (${empty suggest.eligibilityReason or user.SingleParentReason eq suggest.eligibilityReason}) and
                                    (${empty suggest.requiredSchoolStatus or user.ChildSchoolStatus eq suggest.requiredSchoolStatus})">
                                    <tr style="cursor: pointer;"
                                        onclick="location.href='/Cteam1/user/institution/InstitutionsDetail.action?id=${suggest.institutionID}'">
                                        <td>${suggest.name}</td>
                                        <td>${suggest.detail}</td>
                                        <td>
                                            <a href="/Cteam1/user/bookmark/BookmarkCreate.action?institutionID=${suggest.institutionID}"
                                               class="btn btn-success" onclick="event.stopPropagation();">登録</a>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <p>該当する支援制度がありません。</p>
                </c:otherwise>
            </c:choose>

            <div class="pagination" style="text-align: center; margin: 20px 0;">
                <c:if test="${page > 1}">
                    <a href="?page=${page - 1}" style="margin-right: 10px;">&laquo; 前へ</a>
                </c:if>
                ページ ${page} / <fmt:formatNumber value="${totalPages}" type="number" maxFractionDigits="0" />
                <c:if test="${page < totalPages}">
                    <a href="?page=${page + 1}" style="margin-left: 10px;">次へ &raquo;</a>
                </c:if>
            </div>

            <div class="suggest-link">
                <a href="/Cteam1/user/Home.action">トップページへ戻る</a>
            </div>
        </section>
    </c:param>
</c:import>