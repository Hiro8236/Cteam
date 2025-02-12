
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">サポ助</c:param>
    <c:param name="content">
        <section class="w-75 text-center m-auto border pb-3">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2">パスワード更新</h2>
            <c:if test="${errors.size() > 0}">
                <div>
                    <ul>
                        <c:forEach var="error" items="${errors}">
                            <li>${error}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

    <section class="w-50 text-center m-auto border pb-3">
        <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2">メールアドレス更新</h2>

        <!-- 現在のメールアドレス表示 -->
        <div class="form-floating mx-5 mb-3">
            <input class="form-control px-5 fs-5" type="email" id="current-email" name="currentEmail"
                   value="${user.getEmailAddress()}" readonly>
            <label for="current-email">現在のメールアドレス</label>
        </div>

        <!-- 新しいメールアドレス表示 -->
        <div class="form-floating mx-5 mb-3">
            <input class="form-control px-5 fs-5" type="email" id="new-email" name="newEmailAddress"
                   value="${NewEmailAddress}" readonly>
            <label for="new-email">新しいメールアドレス</label>
        </div>

        <!-- パスワード入力フォーム -->
        <form action="/Cteam1/user/user_update/UserEmailUpdateExecute.action" method="post">
            <!-- 現在のメールアドレス -->
            <input type="hidden" name="EmailAddress" value="${user.getEmailAddress()}">
            <!-- 新しいメールアドレス -->
            <input type="hidden" name="NewEmailAddress" value="${NewEmailAddress}">

            <div class="form-floating mx-5 mb-3">
                <input class="form-control px-5 fs-5" type="Password" id="Password" name="Password"
                       placeholder="ログインパスワードを入力してください" required>
                <label for="Password">ログインパスワード</label>
            </div>
                <!-- エラーメッセージの表示 -->
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
                <c:out value="${errorMessage}" />
            </div>
        </c:if>

            <!-- 更新ボタン -->
            <button class="btn btn-primary px-5 fs-5" type="submit">メールアドレスを更新</button>
        </form>
    </section>
    </c:param>
</c:import>
