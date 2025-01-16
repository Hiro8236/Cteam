<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/common/base.jsp">
    <jsp:param name="title" value="ぽシステム"/>
</jsp:include>

<div id="wrap_box">
    <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2">ログイン</h2>
    <c:if test="${errors.size() > 0}">
        <div>
            <ul>
                <c:forEach var="error" items="${errors}">
                    <li>${error}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <form action="UserUpdateExecute.action" method="post">
        <!-- 現在のパスワード -->
        <div class="form-floating mx-5">
            <input class="form-control px-5 fs-5" autocomplete="off" id="id-input" maxlength="20" name="Password"
                   placeholder="半角でご入力下さい" style="ime-mode: disabled" type="text" value="${Password}" required/>
            <label for="id-input">現在のパスワード</label>
        </div>

        <!-- パスワード -->
        <div class="form-floating mx-5 mt-3">
            <input class="form-control px-5 fs-5" autocomplete="off" id="password-input" maxlength="20" name="Newpassword"
                   placeholder="20文字以内の半角英数字でご入力下さい" style="ime-mode: disabled" type="password" required/>
            <label for="password-input">新しいパスワード</label>
        </div>

        <div class="form-check mt-3">
            <input class="form-check-input" id="password-display" name="chk_d_ps" type="checkbox"/>
            <label class="form-check-label" for="password-display">パスワードを表示</label>
        </div>

        <div class="mt-4">
            <input class="w-25 btn btn-lg btn-primary" type="submit" name="update" value="更新"/>
        </div>
    </form>
</div>