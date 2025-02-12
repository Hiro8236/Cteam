<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
    <c:param name="title" value="サポ助" />

    <c:param name="content">
        <!-- メッセージが存在する場合、表示 -->
        <c:if test="${not empty message}">
            <div class="alert alert-info">
                <c:out value="${message}"/>
            </div>
        </c:if>

        <!-- ユーザー情報セクション -->
        <div class="user-info-section">
            <h2 class="user-info-title">メールアドレス</h2>
            <div class="user-info-row">
                <p class="user-email">EmailAddress: ${user.getEmailAddress()}</p>
                <a class="btn-link" href="/Cteam1/user/user_update/UserUpdate.action?action=1&cd=${UserID}">変更</a>
            </div>
        </div>

        <div class="user-info-section">
            <h2 class="user-info-title">パスワード</h2>
            <div class="user-info-row">
                <a class="btn-link" href="/Cteam1/user/user_update/UserUpdate.action?action=2&cd=${UserID}">変更</a>
            </div>
        </div>

        <div class="user-info-section">
            <h2 class="user-info-title">詳細情報</h2>
            <div><a class="btn-link" href="/Cteam1/user/user_update/UserSuggestInfo.action">詳細情報登録</a></div>
        </div>

        <!-- アカウント削除 -->
        <div class="account-delete-section">
            <h3 class="delete-title">アカウント削除:</h3>
            <a class="btn-danger" href="/Cteam1/user/user_update/UserDelete.action?action=3&cd=${UserID}">削除</a>
        </div>
    </c:param>
</c:import>
