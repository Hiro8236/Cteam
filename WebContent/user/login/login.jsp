<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
    <c:param name="title">Cシステム</c:param>

    <c:param name="scripts">
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const passwordInput = document.getElementById('password-input');
                const passwordToggle = document.getElementById('password-display');

                passwordToggle.addEventListener('change', function () {
                    passwordInput.type = this.checked ? 'text' : 'password';
                });
            });
        </script>
    </c:param>

    <c:param name="content">
        <section class="login-container">
            <form action="LoginExecute.action" method="post" class="login-form">
                <h2 class="login-title">ログイン</h2>

                <c:if test="${errors.size() > 0}">
                    <div class="error-messages">
                        <ul>
                            <c:forEach var="error" items="${errors}">
                                <li>${error}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>

                <div class="form-group">
                    <label for="id-input">メールアドレス</label>
                    <input type="text" id="id-input" name="EmailAddress"
                           class="form-control" maxlength="50" placeholder="半角でご入力下さい"
                           autocomplete="off" required value="${EmailAddress}" />
                </div>

                <div class="form-group">
                    <label for="password-input">パスワード</label>
                    <input type="password" id="password-input" name="password"
                           class="form-control" maxlength="20" placeholder="20文字以内の半角英数字でご入力下さい"
                           autocomplete="off" required />
                </div>

                <div class="password-toggle">
                    <input type="checkbox" id="password-display" name="chk_d_ps" />
                    <label for="password-display">パスワードを表示</label>
                </div>

                <div class="button-container">
                    <button type="submit" class="btn btn-primary">ログイン</button>
                    <a href="/Cteam1/user/login/UserRegist.action" class="btn btn-secondary">新規登録</a>
                </div>
            </form>
        </section>
    </c:param>
</c:import>
