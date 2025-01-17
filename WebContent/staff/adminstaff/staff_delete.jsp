<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/staffcommon/base.jsp">
    <c:param name="title" value="職員削除" />
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">職員削除</h2>

            <c:if test="${not empty errors}">
                <div class="alert alert-danger">
                    <ul>
                        <c:forEach var="error" items="${errors}">
                            <li>${error.value}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <c:if test="${not empty staff}">
                <h3>職員削除確認</h3>
                <p>職員ID: ${staff.staffID}</p>
                <p>氏名: ${staff.staffName}</p>
                <p>
                    役職:
                    <c:choose>
                        <c:when test="${staff.staffRole == '1'}">職員</c:when>
                        <c:when test="${staff.staffRole == '2'}">管理者</c:when>
                    </c:choose>
                </p>

                <form action="StaffDeleteExecute.action" method="post">
                    <input type="hidden" name="staffId" value="${staff.staffID}" />
                    <button type="submit" class="btn btn-danger">削除</button>
                    <a href="StaffList.action" class="btn btn-secondary">キャンセル</a>
                </form>
            </c:if>
        </section>
    </c:param>
</c:import>
