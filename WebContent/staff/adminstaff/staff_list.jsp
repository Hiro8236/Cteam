<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/staffcommon/base.jsp">

    <c:param name="title">職員管理システム</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">職員管理</h2>
            <div class="my-2 text-end px-4">
                <a href="StaffCreate.action" class="btn btn-primary">新規登録</a>
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
                    <!-- 職員のリスト -->
                    <c:forEach var="staff" items="${staffs}">
                        <tr>
                            <td>${staff.staffID}</td>
                            <td>${staff.staffName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${staff.staffRole == 1}">職員</c:when>
                                    <c:when test="${staff.staffRole == 2}">管理者</c:when>
                                    <c:otherwise>不明</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <!-- 変更ボタン -->
                                <a href="StaffUpdate.action?staffId=${staff.staffID}" class="btn btn-primary btn-sm">変更</a>

                                <!-- 削除ボタン -->
                                <form action="StaffDelete.action" method="post" style="display:inline;">
                                    <input type="hidden" name="staffId" value="${staff.staffID}" />
                                    <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('本当に削除しますか？');">
                                        削除
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </section>
    </c:param>
</c:import>

