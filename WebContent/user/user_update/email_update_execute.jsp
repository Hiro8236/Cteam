<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>メールアドレス更新</title>
    <link rel="stylesheet" href="/common/styles.css">
</head>
<body>
    <section class="w-50 text-center m-auto border pb-3">
        <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2">メールアドレス更新</h2>

        <!-- 現在のメールアドレス表示 -->
        <div class="form-floating mx-5 mb-3">
            <input class="form-control px-5 fs-5" type="email" id="current-email" name="currentEmail"
                   value="${param.currentEmail}" readonly>
            <label for="current-email">現在のメールアドレス</label>
        </div>

        <!-- 新しいメールアドレス表示 -->
        <div class="form-floating mx-5 mb-3">
            <input class="form-control px-5 fs-5" type="email" id="new-email" name="newEmail"
                   value="${param.newEmail}" readonly>
            <label for="new-email">新しいメールアドレス</label>
        </div>

        <!-- パスワード入力フォーム -->
        <form action="/Cteam1/user/UpdateEmailProcess.action" method="post">
            <input type="hidden" name="currentEmail" value="${param.currentEmail}">
            <input type="hidden" name="newEmail" value="${param.newEmail}">
            
            <div class="form-floating mx-5 mb-3">
                <input class="form-control px-5 fs-5" type="password" id="password" name="password"
                       placeholder="ログインパスワードを入力してください" required>
                <label for="password">ログインパスワード</label>
            </div>

            <!-- 更新ボタン -->
            <button class="btn btn-primary px-5 fs-5" type="submit">メールアドレスを更新</button>
        </form>
    </section>
</body>
</html>
