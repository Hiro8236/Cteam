<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/staffcommon/base.jsp" />
<html>
<head>
    <title>職員登録</title>
</head>
<body>
    <h1>職員登録</h1>

    <!-- ログイン中の職員情報を表示 -->
    <p>ログイン中: <c:out value="${user.staffName}" /> (<c:out value="${user.staffRole}" />)</p>

    <!-- 職員一覧を表示 -->
    <h2>既存の職員一覧</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>氏名</th>
            <th>役職</th>
        </tr>
        <c:forEach var="staff" items="${staff_list}">
            <tr>
                <td><c:out value="${staff.staffID}" /></td>
                <td><c:out value="${staff.staffName}" /></td>
                <td><c:out value="${staff.staffRole}" /></td>
            </tr>
        </c:forEach>
    </table>

	<c:if test="${not empty errors}">
        <ul style="color: red;">
            <c:forEach var="error" items="${errors}">
                <li>${error.value}</li>
            </c:forEach>
        </ul>
    </c:if>
    <!-- 新しい職員を登録するフォーム -->
    <h2>新しい職員を登録</h2>
<form action="StaffCreateExecute.action" method="post">
        <!-- 職員名 -->
        <label for="staff_name">氏名:</label>
        <input type="text" id="staff_name" name="staff_name" value="${staff_name}" />
        <br />

        <!-- 役職 -->
        <label for="staff_role">役職:</label>
        <input type="text" id="staff_role" name="staff_role" value="${staff_role}" />
        <br />

        <!-- パスワード -->
        <label for="password">パスワード:</label>
        <input type="password" id="password" name="password" />
        <br />

        <input type="submit" value="登録" />
    </form>

</body>
</html>
