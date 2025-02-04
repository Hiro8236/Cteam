<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/staffcommon/base.jsp">
    <c:param name="title">お知らせ投稿</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">お知らせ投稿</h2>

            <p>ログイン中: <c:out value="${user.staffName}" /></p>

            <c:if test="${not empty errors}">
                <ul class="list-unstyled text-danger">
                    <c:forEach var="error" items="${errors}">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>

            <h3 class="mt-5">新しい支援を登録</h3>
            <form id="notification-form" action="StaffNotificationCreateExecute.action" method="post">
                <div class="mb-3">
                    <label for="notification_title" class="form-label">件名:</label>
                    <input type="text" class="form-control" id="notification_title" name="notification_title" value="${notification_title}" />
                </div>
                <div class="mb-3">
                    <label for="notification_detail" class="form-label">お知らせ詳細:</label>
                    <textarea class="form-control" id="notification_detail" name="notification_detail" rows="3">${notification_detail}</textarea>
                </div>
                <button type="submit" id="post-notification" class="btn btn-primary">投稿</button>
            </form>
        </section>

<script>
    document.getElementById('notification-form').addEventListener('submit', function (e) {
        e.preventDefault(); // フォームのデフォルト送信を防ぐ
        const form = document.getElementById('notification-form');
        const title = document.getElementById('notification_title').value.trim();
        const detail = document.getElementById('notification_detail').value.trim();
        const postButton = document.getElementById('post-notification');

        // 既存のエラーメッセージを削除
        const existingAlert = form.querySelector('.alert');
        if (existingAlert) {
            existingAlert.remove();
        }

        if (!title || !detail) {
            // 入力チェックエラー時にエラーメッセージを表示して送信を中止
            const errorMessage = document.createElement('div');
            errorMessage.classList.add('alert', 'alert-danger');
            errorMessage.textContent = "件名と詳細を入力してください。";
            form.prepend(errorMessage);
            return;
        }

        // ボタンを無効化して送信中の表示に変更
        postButton.disabled = true;
        postButton.textContent = "送信中...";

        // 直接フォームを送信
        form.submit();
    });
</script>
    </c:param>
</c:import>
