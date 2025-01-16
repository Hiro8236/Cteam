<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>お知らせ一覧</title>
    <style>
        table {
            border-collapse: collapse;
            width: 80%;
            margin: 20px auto;
        }
        table, th, td {
            border: 1px solid #333;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f4f4f4;
        }
    </style>
</head>
<body>
    <h1 style="text-align: center;">お知らせ一覧</h1>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>タイトル</th>
                <th>内容</th>
            </tr>
        </thead>
        <tbody>
            <!-- NotificationListを繰り返し処理 -->
            <c:forEach var="notification" items="${NotificationList}">
                <tr>
                    <td>${notification.id}</td>
                    <td>${notification.title}</td>
                    <td>${notification.sentence}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>