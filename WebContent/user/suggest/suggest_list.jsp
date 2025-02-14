<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/common/base.jsp">
    <c:param name="title" value="サポ助" />
    <c:param name="content">
        <section class="staff-management">

            <!-- エラーメッセージがある場合はアラートで表示 -->
            <c:if test="${not empty errorMessage}">
                <script type="text/javascript">
                    alert("${errorMessage}");
                </script>
            </c:if>

            <!-- ログイン情報を取得（もし登録ボタンの表示を制御するなら使う） -->
            <c:set var="userID" value="${sessionScope.userID}" />

            <!-- ログインされていない場合のメッセージ表示 -->
            <c:if test="${empty userID}">
                 <div> <a href="../login/Login.action">ログイン</a>してください</div>
            </c:if>

            <!-- ページネーション設定 -->
            <c:set var="itemsPerPage" value="5" />

            <c:choose>
                <c:when test="${not empty param.page}">
                    <!-- 数値として扱うために (* 1) -->
                    <c:set var="page" value="${param.page * 1}" />
                </c:when>
                <c:otherwise>
                    <c:set var="page" value="1" />
                </c:otherwise>
            </c:choose>

            <!-- 総件数（suggestlistsの要素数） -->
            <c:set var="totalItems" value="${fn:length(suggestlists)}" />

            <!-- 総ページ数の計算： (totalItems + itemsPerPage - 1) div itemsPerPage -->
            <c:set var="totalPages" value="${(totalItems + itemsPerPage - 1) div itemsPerPage}" />
            <!-- 小数点以下を切り捨てた整数値 -->
            <c:set var="totalPagesInt" value="${totalPages - (totalPages mod 1)}" />

            <!-- 表示開始／終了インデックス -->
            <c:set var="startIndex" value="${(page - 1) * itemsPerPage}" />
            <c:set var="endIndex" value="${startIndex + itemsPerPage}" />

            <!-- おすすめ一覧のテーブル -->
            <div class="staff-list-header">
                <h3 class="staff-list-title">おすすめ支援一覧</h3>
            </div>

            <c:choose>
                <c:when test="${suggestlists != null and suggestlists.size() > 0}">
                    <table class="staff-table">
                        <thead>
                            <tr>
                                <th>支援名</th>
                                <th>制度詳細</th>
                                <th>登録</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- ページ範囲内の要素のみ表示 -->
                            <c:forEach var="suggest" items="${suggestlists}" varStatus="status">
                                <c:if test="${status.index >= startIndex and status.index < endIndex}">
                                    <tr style="cursor: pointer;"
                                        onclick="location.href='/Cteam1/user/institution/InstitutionsDetail.action?id=${suggest.institutionID}'">
                                        <td>
                                            <!-- 支援名を10文字で切り詰め -->
                                            <c:choose>
                                                <c:when test="${fn:length(suggest.name) > 10}">
                                                    ${fn:substring(suggest.name, 0, 10)}&hellip;
                                                </c:when>
                                                <c:otherwise>
                                                    ${suggest.name}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <!-- 支援詳細を20文字で切り詰め -->
                                            <c:choose>
                                                <c:when test="${fn:length(suggest.detail) > 20}">
                                                    ${fn:substring(suggest.detail, 0, 20)}&hellip;
                                                </c:when>
                                                <c:otherwise>
                                                    ${suggest.detail}
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <!-- 登録ボタンのクリックで行クリックを止める -->
                                            <a href="/Cteam1/user/bookmark/BookmarkCreate.action?institutionID=${suggest.institutionID}"
                                               class="btn btn-success"
                                               onclick="event.stopPropagation();">登録</a>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <p>該当する支援制度がありません。</p>
                </c:otherwise>
            </c:choose>

            <!-- ページネーション -->
            <div class="pagination" style="text-align: center; margin: 20px 0;">
                <c:if test="${page gt 1}">
                    <a href="?page=${page - 1}" style="margin-right: 10px;">&laquo; 前へ</a>
                </c:if>
                ページ ${page} / <fmt:formatNumber value="${totalPages}" type="number" maxFractionDigits="0" />
                <c:if test="${page lt totalPagesInt}">
                    <a href="?page=${page + 1}" style="margin-left: 10px;">次へ &raquo;</a>
                </c:if>
            </div>

            <!-- トップページへ戻るリンク -->
            <div class="suggest-link">
                <a href="/Cteam1/user/Home.action">トップページへ戻る</a>
            </div>
        </section>
    </c:param>
</c:import>
