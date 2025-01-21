<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/staffcommon/base.jsp">
	<c:param name="title">
		ぽシステム
	</c:param>

	<c:param name="content">
	    <h1>StaffHOME</h1>


         <a href="　　　　　　　　　　　　　　　.action">投稿</a>

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
			                <c:forEach var="institutions" items="${institutions}">
			                    <tr onclick="location.href='StaffInstitutionsDetail.action?id=${institutions.ID}'"style="cursor: pointer;">
			                        <td>${institutions.name}</td>
			                        <td>${institutions.detail}</td>
							        <td><a href="　　　　　　　　　　.action?id=${institutions.ID}">${institutions.ID}:編集</a></td>
							        <td><a href="　　　　　　　　　　.action?id=${institutions.ID}">${institutions.ID}:削除</a></td>

			                    </tr>
			                </c:forEach>
			            </tbody>
			        </table>
			    </c:when>
			    <c:otherwise>
			        <div>支援情報が存在しませんでした</div>
			    </c:otherwise>
			</c:choose>


	</section >

	</c:param>
</c:import>