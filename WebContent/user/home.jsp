<%-- ログインJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title" value="ぽシステム" />
    <c:param name="content">
        <h1>HOME</h1>

         <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4" style="background-color:#f2f2f2">支援一覧</h2>
			<c:choose>
			    <c:when test="${institusions != null && institusions.size() > 0}">
			        <table class="table table-hover">
			            <thead>
			                <tr>
			                    <th>支援名</th>
			                    <th>支援詳細</th>
			                </tr>
			            </thead>
			            <tbody>
			                <c:forEach var="institusions" items="${institusions}">
			                    <tr>
			                        <td>${institusions.name}</td>
			                        <td>${institusions.detail}</td>
			                    </tr>
			                </c:forEach>
			            </tbody>
			        </table>
			    </c:when>
			    <c:otherwise>
			        <div>支援情報が存在しませんでした</div>
			    </c:otherwise>
			</c:choose>
        </section>

        <div class="">
			<a href="SuggestList.action">おすすめ一覧</a>
		</div>

    </c:param>
</c:import>
