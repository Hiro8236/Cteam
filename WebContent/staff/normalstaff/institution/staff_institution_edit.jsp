<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title" value="支援詳細 - ぽシステム" />
    <c:param name="content">
        <h1>支援詳細</h1>

        <!-- 内部CSSスタイル -->
        <style>
            .institution-details {
                max-width: 800px;
                margin: 0 auto;
                padding: 20px;
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }

            .institution-name,
            .institution-detail {
                margin-bottom: 20px;
            }

            .institution-name h2,
            .institution-detail h3 {
                font-size: 24px;
                color: #333;
            }

            .institution-detail p {
                font-size: 16px;
                line-height: 1.6;
                color: #666;
            }

            .form-section {
                margin-top: 40px;
                padding-top: 20px;
                border-top: 2px solid #eee;
            }

            .form-section h3 {
                font-size: 20px;
                color: #333;
            }

            .form-section label {
                display: block;
                font-size: 16px;
                margin-bottom: 5px;
                color: #555;
            }

            .form-section input,
            .form-section textarea {
                width: 100%;
                padding: 8px;
                font-size: 16px;
                margin-bottom: 15px;
                border: 1px solid #ddd;
                border-radius: 4px;
            }

            .form-section button {
                padding: 10px 20px;
                font-size: 16px;
                color: #fff;
                background-color: #007bff;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            .form-section button:hover {
                background-color: #0056b3;
            }
        </style>

        <section class="institution-details">
            <!-- 支援名 -->
            <div class="institution-name">
                <h2>支援名: ${institution.name != null ? institution.name : '支援名がありません'}</h2>
            </div>

            <!-- 支援詳細 -->
            <div class="institution-detail">
                <h3>支援詳細</h3>
                <p>${institution.detail != null ? institution.detail : '詳細がありません'}</p>
            </div>

            <!-- 編集フォームセクション -->
            <div class="form-section">
                <h3>支援情報を編集</h3>
                <form action="StaffInstitutionEditExecute.action" method="post">
                    <input type="hidden" name="id" value="${institution.ID}" />

                    <label for="name">支援名</label>
                    <input type="text" id="name" name="name" value="${institution.name}" required />

                    <label for="detail">支援詳細</label>
                    <textarea id="detail" name="detail" rows="4" required>${institution.detail}</textarea>

                    <button type="submit">保存</button>
                </form>
            </div>
        </section>
    </c:param>
</c:import>