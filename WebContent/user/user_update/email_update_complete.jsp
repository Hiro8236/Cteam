<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>メールアドレス更新完了</title>
    <link rel="stylesheet" href="/common/styles.css">
</head>
<body>
   <c:choose>
    <c:when test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:when>
    <c:when test="${isUpdated}">
        <div class="alert alert-success">メールアドレスが正常に更新されました。</div>
    </c:when>
    <c:otherwise>
        <div class="alert alert-warning">パスワードが一致しませんでした。メールアドレスの更新は行われませんでした。</div>
    </c:otherwise>
</c:choose>


        <!-- ホームへ戻るボタン -->
        <a href="/Cteam1/user/Home.action" class="btn btn-success fs-5">ホームへ戻る</a>
</body>

</html>
