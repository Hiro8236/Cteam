<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/staffcommon/base.jsp">
    <c:param name="title">職員登録</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">職員登録</h2>

            <!-- ログイン中の職員情報を表示 -->
            <p>ログイン中: <c:out value="${user.staffName}" /> </p>

            <!-- エラーメッセージの表示 -->
            <c:if test="${not empty errors}">
                <ul class="list-unstyled text-danger">
                    <c:forEach var="error" items="${errors}">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>

            <!-- 既存の職員一覧を表示 -->
            <h3 class="mt-5">既存の職員一覧</h3>
            <table class="table table-bordered table-striped">
                <thead>
                    <tr>
                        <th>職員ID</th>
                        <th>氏名</th>
                        <th>役職</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- 職員のみ表示（staffRole == 1） -->
                    <c:forEach var="staff" items="${staff_list}">
                        <c:if test="${staff.staffRole == 1}">
                            <tr>
                                <td><c:out value="${staff.staffID}" /></td>
                                <td><c:out value="${staff.staffName}" /></td>
                                <td>職員</td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>

            <!-- 新しい職員を登録するフォーム -->
            <h3 class="mt-5">新しい職員を登録</h3>
            <form action="StaffCreateExecute.action" method="post">
                <!-- 職員ID（ユーザーが手動で入力）-->
                <div class="mb-3">
                    <label for="staff_id" class="form-label">職員ID:</label>
                    <input type="text" class="form-control" id="staff_id" name="staff_id" value="${staff_id}" required />
                </div>

                <div class="mb-3">
                    <label for="staff_name" class="form-label">氏名:</label>
                    <input type="text" class="form-control" id="staff_name" name="staff_name" value="${staff_name}" required />
                </div>

                <!-- 役職は職員のみ -->
                <div class="mb-3">
                    <label for="staff_role" class="form-label">役職:</label>
                    <select class="form-control" id="staff_role" name="staff_role">
                        <option value="1" selected>職員</option> <!-- 職員のみ選択肢 -->
                    </select>
                </div>

                <!-- パスワードフィールドを追加 -->
                <div class="mb-3">
                    <label for="password" class="form-label">パスワード:</label>
                    <input type="password" class="form-control" id="password" name="password" required />
                </div>

                <button type="submit" class="btn btn-primary">登録</button>
            </form>

        </section>
    </c:param>
</c:import>
