<%-- ログインJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/staffcommon/base.jsp">
	<c:param name="title">
		サポ助
	</c:param>

	<c:param name="content">
    <section class="login-container">
        <form action="StaffLoginExecute.action" method="post" class="login-form">
            <h2 class="login-title">ログイン</h2>

            <!-- エラーメッセージ -->
            <c:if test="${errors.size()>0}">
                <div class="error-messages">
                    <ul>
                        <c:forEach var="error" items="${errors}">
                            <li>${error}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <!-- スタッフID -->
            <div class="form-floating">
                <input class="form-control" id="id-input" maxlength="20" name="StaffId"
                    placeholder="スタッフIDを入力" type="text" value="${EmailAddress}" required />
                <label for="id-input">スタッフID</label>
            </div>

            <!-- パスワード -->
            <div class="form-floating">
                <input class="form-control" id="password-input" maxlength="20" name="password"
                    placeholder="パスワードを入力" type="password" required />
                <label for="password-input">パスワード</label>
            </div>

            <!-- パスワード表示チェックボックス -->
            <div class="password-toggle">
                <input type="checkbox" id="password-display" />
                <label for="password-display">パスワードを表示</label>
            </div>

            <!-- ログインボタン -->
            <div>
                <button type="submit" class="login-button">ログイン</button>
            </div>
        </form>
    </section>
</c:param>


	<c:param name="content">
		<section class="w-75 text-center m-auto border pb-3">
			<form action = "StaffLoginExecute.action" method="post">
				<div id="wrap_box">
					<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2">ログイン</h2>
					<c:if test="${errors.size()>0}">
						<div>
							<ul>
								<c:forEach var="error" items="${errors}">
									<li>${error}</li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
					<div>
						<!-- ＩＤ -->
						<div class="form-floating mx-5">
							<input class="form-control px-5 fs-5" autocomplete="off"
								id="id-input" maxlength="20" name="StaffId" placeholder="半角でご入力下さい"
								style="ime-mode: disabled" type="text" value="${EmailAddress}" required />
							<label>スタッフID</label>
						</div>
						<!-- パスワード -->
						<div class="form-floating mx-5 mt-3">
							<input class="form-control px-5 fs-5" autocomplete="off"
								id="password-input" maxlength="20" name="password"
								placeholder="20文字以内の半角英数字でご入力下さい" style="ime-mode: disabled"
								type="password" required />
							<label>パスワード</label>
						</div>
						<div class="form-check mt-3">
							<label class="form-check-label" for="password-display">
								<input class="form-check-input" id="password-display" name="chk_d_ps" type="checkbox" />
								パスワードを表示
							</label>
						</div>
					</div>

					<div class="mt-4">
						<input class="w-25 btn btn-lg btn-primary" type="submit" name="login" value="ログイン"/>
					</div>
				</div>
			</form>
		</section>
	</c:param>
</c:import>