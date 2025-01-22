<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/staffcommon/base.jsp">
    <c:param name="title" value="支援詳細 - ぽシステム" />
    <c:param name="content">
        <h1>支援詳細</h1>

        <style>
            .institution-details { max-width: 800px; margin: 0 auto; padding: 20px; font-family: Arial, sans-serif; background-color: #f9f9f9; border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); }
            .institution-name { margin-bottom: 20px; }
            .institution-name h2 { font-size: 24px; color: #333; }
            .institution-detail { margin-bottom: 30px; }
            .institution-detail h3 { font-size: 20px; color: #555; }
            .institution-detail p { font-size: 16px; line-height: 1.6; color: #666; }
            .video-section { margin-top: 40px; padding-top: 20px; border-top: 2px solid #eee; }
            .video-section h3 { font-size: 20px; color: #333; }
            video { width: 100%; max-width: 1000px; height: auto; border: 1px solid #ddd; background-color: #000; }
        </style>

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
                    <iframe width="100%" height="315" src="https://www.youtube.com/embed/${institution.video}" frameborder="0" allowfullscreen></iframe>
                </c:if>
                <c:if test="${empty institution.video}">
                    <p>動画がありません</p>
                </c:if>
            </div>
        </section>
    </c:param>
</c:import>
