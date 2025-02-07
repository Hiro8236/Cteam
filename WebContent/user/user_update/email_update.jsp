<%-- 新規登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">
        サポ助
    </c:param>

    <c:param name="content">
        <section class="w-75 text-center m-auto border pb-3">
            <form action="/Cteam1/user/user_update/UserEmailUpdateProcess.action" method="post">
                <div id="wrap_box">
                    <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2">メールアドレス変更</h2>

                    <c:if test="${errors != null}">
                        <div>
                            <ul>
                                <c:forEach var="error" items="${errors}">
                                    <li>${error}</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>

                    <div>
                        <!-- メールアドレス -->
                        <div class="form-floating mx-5">
                            <input class="form-control px-5 fs-5" autocomplete="off"
                                   id="email-input" maxlength="255" name="NewEmailAddress"
                                   placeholder="メールアドレスを入力してください" type="email" required />
                            <label for="email-input">メールアドレス</label>
                        </div>

                        <!-- メール送信ボタン -->
                        <div class="mt-3 mx-5">
                            <button class="btn btn-secondary px-5 fs-5" type="button" id="send-auth-mail">
                                認証メールを送信
                            </button>
                            <!-- カウントダウン表示エリア -->
                            <div id="countdown-timer" style="margin-top: 10px; color: red; font-weight: bold; text-align: center;"></div>
                        </div>

                        <script>
                            document.getElementById('send-auth-mail').addEventListener('click', function (e) {
                                const button = e.target;
                                const email = document.getElementById('email-input').value;
                                const countdownTimer = document.getElementById('countdown-timer');
                                const cooldownTime = 10; // 再送信可能までの時間（秒）

                                if (!email) {
                                    alert('メールアドレスを入力してください。');
                                    return;
                                }

                                button.disabled = true;
                                let remainingTime = cooldownTime;
                                countdownTimer.textContent = "再送信可能まで: " + remainingTime + "秒";

                                const countdownInterval = setInterval(function () {
                                    remainingTime -= 1;
                                    if (remainingTime > 0) {
                                        countdownTimer.textContent = "再送信可能まで: " + remainingTime + "秒";
                                    } else {
                                        clearInterval(countdownInterval);
                                        countdownTimer.textContent = "";
                                        button.disabled = false;
                                    }
                                }, 1000);

                                fetch('/Cteam1/user/mail/UpdateEmail.action', {
                                    method: 'POST',
                                    headers: {
                                        'Content-Type': 'application/x-www-form-urlencoded',
                                    },
                                    body: new URLSearchParams({ EmailAddress: email })
                                })
                                .then(function (response) {
                                    return response.json();
                                })
                                .then(function (data) {
                                    if (data.success) {
                                        alert(data.message);
                                    } else {
                                        alert(data.message);
                                    }
                                })
                                .catch(function (error) {
                                    console.error('Error:', error);
                                    alert('エラーが発生しました。時間を置いてもう一度試してください。');
                                });
                            });
                        </script>

                        <!-- 認証コード入力 -->
                        <div class="form-floating mx-5 mt-3">
                            <input class="form-control px-5 fs-5" autocomplete="off"
                                   id="AuthenticationCode-input" maxlength="255" name="AuthenticationCode"
                                   placeholder="認証コードを入力してください" type="text" required />
                            <label for="AuthenticationCode-input">認証コード</label>
                        </div>

                        <!-- 認証コード確認および送信ボタン -->
                        <div class="mt-3 mx-5">
                            <button class="btn btn-primary px-5 fs-5" type="button" id="verify-and-submit">
                                                認証
                            </button>
                            <div id="verify-message" style="margin-top: 10px; color: red; font-weight: bold; text-align: center;"></div>
                        </div>

                        <script>
                            document.addEventListener('DOMContentLoaded', function () {
                                document.getElementById('verify-and-submit').addEventListener('click', async function () {
                                    const email = document.getElementById('email-input').value;
                                    const authCode = document.getElementById('AuthenticationCode-input').value;
                                    const verifyMessage = document.getElementById('verify-message');

                                    if (!email || !authCode) {
                                        alert('メールアドレスと認証コードを入力してください。');
                                        return;
                                    }

                                    try {
                                        const response = await fetch('/Cteam1/user/VerifyCodeEmail.action', {
                                            method: 'POST',
                                            headers: {
                                                'Content-Type': 'application/x-www-form-urlencoded',
                                            },
                                            body: new URLSearchParams({
                                                EmailAddress: email,
                                                AuthenticationCode: authCode
                                            })
                                        });

                                        const data = await response.json();
                                        if (data.success) {
                                            verifyMessage.style.color = 'green';
                                            verifyMessage.textContent = '認証に成功しました。登録を進めます。';
                                            document.querySelector('form').submit();
                                        } else {
                                            verifyMessage.style.color = 'red';
                                            verifyMessage.textContent = '認証に失敗しました。認証コードを確認してください。';
                                        }
                                    } catch (error) {
                                        console.error('Error:', error);
                                        verifyMessage.style.color = 'red';
                                        verifyMessage.textContent = 'エラーが発生しました。時間を置いてもう一度試してください。';
                                    }
                                });
                            });
                        </script>
                    </div>
                </div>
            </form>
        </section>
    </c:param>
</c:import>
