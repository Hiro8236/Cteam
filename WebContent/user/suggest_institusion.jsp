<%-- ログインJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title" value="ぽシステム" />
    <c:param name="content">

         <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4" style="background-color:#f2f2f2">おすすめ支援一覧</h2>
			<c:choose>
			    <c:when test="${suggestinstitusions != null && suggestinstitusions.size() > 0}">
			        <table class="table table-hover">
			            <thead>
			                <tr>
			                    <th>支援名</th>
			                    <th>支援詳細</th>
			                </tr>
			            </thead>
			            <tbody>
			                <c:forEach var="suggestinstitusions" items="${suggestinstitusions}">
			                    <tr>
			                        <td>${suggestinstitusions.name}</td>
			                        <td>${suggestinstitusions.detail}</td>
			                        <td>
                                        <form action="bookmark" method="post">
                                            <input type="hidden" name="suggestinstitusionId" value="${suggestinstitusion.ID}" />
                                            <button type="submit" class="btn btn-primary">ブックマーク登録</button>
                                        </form>
                                    </td>
			                    </tr>
			                </c:forEach>
			            </tbody>
			        </table>
			    </c:when>

			    <c:otherwise>
			        <div>支援情報が存在しませんでした</div>
			    </c:otherwise>
			</c:choose>
        </section>
    </c:param>
</c:import>
