<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>通知一覧</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }
        h1 {
            text-align: center;
            margin-top: 20px;
            color: #333;
        }
        table {
            border-collapse: collapse;
            width: 80%;
            margin: 20px auto;
            background: #fff;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        table, th, td {
            border: 1px solid #ccc;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f4f4f4;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .no-data {
            text-align: center;
            padding: 20px;
        }
    </style>
</head>
<body>

    <h1>通知一覧</h1>

    <c:if test="${empty notificationList}">
        <div class="no-data">
            <p>現在、お知らせはありません。</p>
        </div>
    </c:if>

    <c:if test="${not empty notificationList}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>タイトル</th>
                    <th>内容</th>
                </tr>
            </thead>
            <tbody>
                <!-- NotificationListをループで処理 -->
                <c:forEach var="notification" items="${notificationList}">
                    <tr>
                        <td>${notification.id}</td>
                        <td>${notification.title}</td>
                        <td>${notification.sentence}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

</body>
</html>
