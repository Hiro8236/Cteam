<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>アカウント削除</title>
    <link rel="stylesheet" href="/common/styles.css">
</head>
<body>
     <c:choose>
        <c:when test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:when>
        <c:when test="${isDeleted}">
            <div class="alert alert-success">アカウントが正常に削除されました。</div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-warning">エラーが発生しました。アカウントの削除は行われませんでした。</div>
        </c:otherwise>
    </c:choose>


   <a href="/Cteam1/user/Home.action" class="btn btn-success fs-5">ホームへ戻る</a>
</body>
</html>
