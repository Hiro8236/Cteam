<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:import url="/staffcommon/base.jsp">
    <c:param name="title" value="支援詳細 - ぽシステム" />
    <c:param name="content">
        <h1>支援詳細</h1>

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

            .institution-name h2,
            .institution-detail h3 {
                font-size: 24px;
                color: #333;
            }

            .form-section {
                margin-top: 40px;
                padding-top: 20px;
                border-top: 2px solid #eee;
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

            .video-section iframe {
                width: 100%;
                height: 315px;
                border: none;
                margin-bottom: 20px;
            }
        </style>

        <section class="institution-details">

            <!-- メッセージ表示 -->
            <c:if test="${not empty message}">
                <div class="alert alert-info">
                    <c:out value="${message}" />
                </div>
            </c:if>

            <!-- 支援名 -->
            <div class="institution-name">
                <h2>支援名: ${institution.name != null ? institution.name : '支援名がありません'}</h2>
            </div>

            <!-- 支援詳細 -->
            <div class="institution-detail">
                <h3>支援詳細</h3>
                <p>${institution.detail != null ? institution.detail : '詳細がありません'}</p>
            </div>

            <!-- PDFファイル -->
            <div class="pdf-section">
                <h3>PDF ファイル</h3>
                <c:if test="${not empty institution.pdfPath}">
                    <a href="StaffInstitutionDownload.action?id=${institution.institutionId}" target="_blank">
                        PDFをダウンロード
                    </a>
                </c:if>
                <c:if test="${empty institution.pdfPath}">
                    <p>PDFファイルはありません</p>
                </c:if>
            </div>

            <!-- 動画URL -->
            <div class="video-section">
                <h3>動画</h3>
                <c:choose>
                    <c:when test="${not empty institution.video}">
                        <iframe src="${institution.video.replace('watch?v=', 'embed/')}" allowfullscreen></iframe>
                    </c:when>
                    <c:otherwise>
                        <p>動画URLが設定されていません。</p>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- 編集フォーム -->
            <div class="form-section">
                <h3>支援情報を編集</h3>
                <form action="StaffInstitutionEditExecute.action" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="institutionId" value="${institution.ID}" />

                    <label for="name">支援名</label>
                    <input type="text" id="name" name="name" value="${institution.name}" required />

                    <label for="detail">支援詳細</label>
                    <textarea id="detail" name="detail" rows="4" required>${institution.detail}</textarea>

                    <label for="institution_pdf">PDFファイル</label>
                    <input type="file" id="institution_pdf" name="institution_pdf" accept=".pdf" />

                    <label for="video">動画URL</label>
                    <input type="url" id="video" name="video" value="${institution.video}" placeholder="https://example.com/video" />

                    <!-- 追加情報 -->
                    <label for="incomeRequirement">年間収入</label>
                    <input type="number" id="incomeRequirement" name="incomeRequirement" value="${institution.incomeRequirement}" />

                    <label for="eligibleChildrenCount">子供の人数</label>
                    <input type="number" id="eligibleChildrenCount" name="eligibleChildrenCount" value="${institution.eligibleChildrenCount}" />

                    <label for="requiredEmploymentStatus">雇用状況</label>
                    <input type="text" id="requiredEmploymentStatus" name="requiredEmploymentStatus" value="${institution.requiredEmploymentStatus}" />

                    <label for="eligibilityReason">ひとり親の理由</label>
                    <input type="text" id="eligibilityReason" name="eligibilityReason" value="${institution.eligibilityReason}" />

                    <label for="requiredSchoolStatus">子供の学歴状況</label>
                    <input type="text" id="requiredSchoolStatus" name="requiredSchoolStatus" value="${institution.requiredSchoolStatus}" />

                    <button type="submit">保存</button>


                </form>
            </div>
        </section>
    </c:param>
</c:import>


