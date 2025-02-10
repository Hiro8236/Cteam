<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/staffcommon/base.jsp">
    <c:param name="title">職員情報更新</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">職員情報更新</h2>

            <!-- 現在操作している職員情報の表示 -->
            <div class="alert alert-info">
                <p><strong>現在操作中の職員:</strong></p>
                <ul>
                    <li><strong>職員ID:</strong> <c:out value="${staff.staffID}" /></li>
                    <li><strong>氏名:</strong> <c:out value="${staff.staffName}" /></li>
                    <li><strong>役職:</strong> <c:out value="${staff.staffRole}" /></li>
                </ul>
            </div>

            <!-- エラーメッセージの表示 -->
            <c:if test="${not empty errors}">
                <ul class="list-unstyled text-danger">
                    <c:forEach var="error" items="${errors}">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>

            <!-- スタッフ情報フォーム -->
            <form action="StaffUpdateExecute.action" method="post">
                <!-- 現在の職員IDを隠しフィールドで送信 -->
                <input type="hidden" name="staffId" value="${staff.staffID}" />

                <div class="mb-3">
                    <label for="staffId" class="form-label">職員ID:</label>
                    <input type="text" class="form-control" id="staffId" name="staffId" value="${staff.staffID}" readonly />
                </div>

                <div class="mb-3">
                    <label for="staffName" class="form-label">氏名:</label>
                    <input type="text" class="form-control" id="staffName" name="staffName" value="${staff.staffName}" required />
                </div>

                <div class="mb-3">
                    <label for="staffRole" class="form-label">役職:</label>
                    <input type="text" class="form-control" id="staffRole" name="staffRole" value="${staff.staffRole}" required />
                </div>

                <button type="submit" class="btn btn-primary">更新</button>
            </form>

            <br>
            <a href="StaffList.action" class="btn btn-secondary">戻る</a>
        </section>
    </c:param>
</c:import>
