<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/staffcommon/base.jsp">

    <c:param name="title">職員管理システム</c:param>

    <c:param name="content">
        <section class="staff-management">
            <!-- ページタイトル -->
            <h2 class="page-title">職員管理</h2>

            <!-- 職員一覧ヘッダー（タイトル + 新規登録ボタン） -->
            <div class="staff-list-header">
                <h3 class="staff-list-title">職員一覧</h3>
                <a href="StaffCreate.action" class="btn btn-success">+ 新規登録</a>
            </div>

            <!-- 職員一覧テーブル -->
            <table class="staff-table">
                <thead>
                    <tr>
                        <th>職員ID</th>
                        <th>氏名</th>
                        <th>役職</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="staff" items="${staffs}">
                        <tr>
                            <td>${staff.staffID}</td>
                            <td>${staff.staffName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${staff.staffRole == 1}">職員</c:when>
                                    <c:when test="${staff.staffRole == 2}">管理者</c:when>
                                    <c:otherwise>不明</c:otherwise>
                                </c:choose>
                            </td>
                            <td class="staff-actions">
                                <!-- 変更ボタン -->
                                <form action="StaffUpdate.action" method="post">
                                    <input type="hidden" name="staffId" value="${staff.staffID}" />
                                    <button type="submit" class="btn btn-edit">変更</button>
                                </form>

                                <!-- 削除ボタン -->
                                <form action="StaffDelete.action" method="post">
                                    <input type="hidden" name="staffId" value="${staff.staffID}" />
                                    <button type="submit" class="btn btn-delete">削除</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </section>
    </c:param>
</c:import>
