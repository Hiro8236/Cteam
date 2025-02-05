<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:import url="/common/base.jsp">
    <c:param name="title" value="支援詳細 - ぽシステム" />
    <c:param name="content">
        <h1>支援詳細</h1>

        <div style="text-align: right;">
            <a href="Home.action">戻る</a>
        </div>

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

            <!-- 動画セクション -->
            <div class="video-section">
                <h3>支援関連動画</h3>
                <c:if test="${not empty institution.video}">
                    <iframe width="100%" height="315" src="https://www.youtube.com/embed/${institution.video}" frameborder="0" allowfullscreen></iframe>
                </c:if>
                <c:if test="${empty institution.video}">
                    <p>動画がありません</p>
                </c:if>
            </div>

            <div class="pdf-section">
			    <h3>PDF ファイル</h3>

			    <!-- pdfPath が空ではない場合 (ファイルパスが存在する場合) -->
			    <c:if test="${not empty institution.pdfPath}">
			        <!-- ダウンロード用のアクションにIDをパラメータとして付与 -->
			        <a href="InstitutionDownload.action?id=${institution.ID}" target="_blank">
			            PDFをダウンロード
			        </a>
			    </c:if>

			    <!-- pdfPath が空の場合 (PDFファイルが登録されていない場合) -->
			    <c:if test="${empty institution.pdfPath}">
			        <p>PDFファイルはありません</p>
			    </c:if>
			</div>


        </section>
    </c:param>
</c:import>
