<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
<c:import url="/staffcommon/base.jsp">
    <c:param name="title" value="支援詳細 - ぽシステム" />
    <c:param name="content">
        <h1>支援詳細</h1>
		<a href=StaffInstitution.action>戻る</a>
        <section class="institution-details">

            <div class="institution-name">
                <h2>支援名: ${institution.name != null ? institution.name : '支援名がありません'}</h2>
            </div>

            <div class="institution-detail">
                <h3>支援内容</h3>
                <p>${institution.detail != null ? institution.detail : '支援内容がありません'}</p>
            </div>
<div class="video-section">
    <h3>動画</h3>
    <c:if test="${not empty institution.video}">
        <c:set var="videoUrl" value="${institution.video}" />
        <c:set var="videoId" value="${fn:replace(videoUrl, 'https://www.youtube.com/watch?v=', '')}" />

        <iframe width="560" height="315" src="https://www.youtube.com/embed/${videoId}" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
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
			        <a href="StaffInstitutionDownload.action?id=${institution.ID}" target="_blank">
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
