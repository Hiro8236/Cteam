<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/staffcommon/base.jsp">

    <c:param name="title">職員管理システム</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">職員管理</h2>
            <div class="my-2 text-end px-4">
                <a href="StaffCreate.action">新規登録</a>
            </div>

            <!-- 職員一覧 -->
            <h3>職員一覧</h3>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>職員ID</th>
                        <th>氏名</th>
                        <th>役職</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- 職員のリスト（staffRole == 1） -->
                    <c:forEach var="staff" items="${staffs}">
                        <c:if test="${staff.staffRole == 1}">
                            <tr>
                                <td>${staff.staffID}</td>
                                <td>${staff.staffName}</td>
                                <td>職員</td>
                                <td>
                                    <a href="StaffUpdate.action?staffId=${staff.staffID}">変更</a>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
        </section>
    </c:param>
</c:import>
