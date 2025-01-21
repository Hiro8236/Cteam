<%-- ログインJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title" value="ぽシステム" />
    <c:param name="content">
        <h1>HOME</h1>

         <section class="me-4">

		  	<c:choose>
			    <c:when test="${institutions != null && institutions.size() > 0}">
			        <table class="table table-hover">
			            <thead>
			                <tr>
			                    <th>支援名</th>
			                    <th>支援詳細</th>
			                </tr>
			            </thead>
			            <tbody>
			                <c:forEach var="institution" items="${institutions}">
			                    <tr onclick="location.href='InstitutionsDetail.action?id=${institution.ID}'"style="cursor: pointer;">
			                        <td>${institution.name}</td>
			                        <td>${institution.detail}</td>
			                        <td><a href="BookmarkCreate.action?id=${institution.ID}">${institution.ID}:登録</a></td>

			                    </tr>
			                </c:forEach>
			            </tbody>
			        </table>
			    </c:when>
			    	<c:otherwise>
			        	<div>支援情報が存在しませんでした</div>
			    	</c:otherwise>
			</c:choose>




        <div class="">
			<a href="SuggestList.action">おすすめ一覧</a>
		</div>
	</section >
    </c:param>
</c:import>
