<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/staffcommon/base.jsp">
	<c:param name="title">
		サポ助
	</c:param>

	<c:param name="content">
	    <h1>StaffHOME</h1>


         <a href="StaffNotificationCreate.action">投稿</a>

        <!-- メッセージが存在する場合、表示 -->
        <c:if test="${not empty message}">
            <div class="alert alert-info">
                <c:out value="${message}"/>
            </div>
        </c:if>



         <section class="me-4">
		  	<c:choose>
			    <c:when test="${notifications != null && notifications.size() > 0}">
			        <table class="table table-hover">
			            <thead>
			                <tr>
			                    <th>件名</th>
			                    <th>お知らせ詳細</th>
			                </tr>
			            </thead>
			            <tbody>
			                <c:forEach var="notifications" items="${notifications}">
                                <tr onclick="location.href='staff_notification_detail.jsp?id=${notifications.notificationID}&title=${notifications.title}&detail=${notifications.detail}'" style="cursor: pointer;">
			                        <td>${notifications.title}</td>
			                        <td>${notifications.detail}</td>
			                    </tr>
			                    <tr>
							        <td><a href="StaffNotificationDelete.action?id=${notifications.notificationID}"onclick="return confirm('本当に削除しますか？');event.stopPropagation();">${notifications.notificationID}:削除</a></td>

			                    </tr>
			                </c:forEach>
			            </tbody>
			        </table>
			    </c:when>
			    <c:otherwise>
			        <div>お知らせが存在しませんでした</div>
			    </c:otherwise>
			</c:choose>


	</section >

	</c:param>
</c:import>