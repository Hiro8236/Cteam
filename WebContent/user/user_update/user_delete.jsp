<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">Cシステム</c:param>
    <c:param name="content">
        <section class="w-75 text-center m-auto border pb-3">
            <div id="wrap_box">
                <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2">アカウント削除</h2>

                <c:if test="${errors.size() > 0}">
                    <div>
                        <ul>
                            <c:forEach var="error" items="${errors}">
                                <li>${error}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>

                <form id="delete-form" action="UserPasswordUpdateExecute.action" method="post">
                    <!-- パスワード入力 -->
                    <div class="form-floating mx-5">
                        <input class="form-control px-5 fs-5" autocomplete="off" id="password" maxlength="20" name="Password"
                               placeholder="半角でご入力下さい" style="ime-mode: disabled" type="password" required/>
                        <label for="password">パスワード</label>
                    </div>

                    <!-- パスワード確認用 -->
                    <div class="form-floating mx-5 mt-3">
                        <input class="form-control px-5 fs-5" autocomplete="off" id="confirm-password" maxlength="20"
                               placeholder="再入力してください" style="ime-mode: disabled" type="password" required/>
                        <label for="confirm-password">パスワード（確認用）</label>
                    </div>

                    <!-- エラーメッセージ -->
                    <p id="password-error" class="text-danger mx-5 mt-2" style="display: none;">パスワードが一致しません</p>

                    <div class="form-check mt-3">
                        <input class="form-check-input" id="password-display" type="checkbox"/>
                        <label class="form-check-label" for="password-display">パスワードを表示</label>
                    </div>

                    <div class="mt-4">
                        <input class="w-25 btn btn-lg btn-primary" type="submit" name="update" value="削除"/>
                    </div>
                </form>
            </div>
        </section>
    </c:param>
</c:import>

<script>
    document.getElementById("delete-form").addEventListener("submit", function(event) {
        let password = document.getElementById("password").value;
        let confirmPassword = document.getElementById("confirm-password").value;
        let errorMsg = document.getElementById("password-error");

        if (password !== confirmPassword) {
            event.preventDefault(); // 送信を阻止
            errorMsg.style.display = "block"; // エラーメッセージ表示
        } else {
            errorMsg.style.display = "none"; // エラーメッセージ非表示
        }
    });

    document.getElementById("password-display").addEventListener("change", function() {
        let password = document.getElementById("password");
        let confirmPassword = document.getElementById("confirm-password");
        let type = this.checked ? "text" : "password";
        password.type = type;
        confirmPassword.type = type;
    });
</script>
