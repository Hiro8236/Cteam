<%-- 共通テンプレート --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="/Cteam1/css/styles.css">

<title>${param.title}</title>

<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
${param.scripts}
</head>
<body>
	<div id="wrapper" class="container">
		<header>
			<c:import url="/staffcommon/header.jsp" />
		</header>

		<div class="row justify-content-center">
			<c:choose>
				<%-- ログイン済みの場合 --%>
				<c:when test="${staff.isAuthenticated()}">

					<main class="col-9 border-start"> ${param.content} </main>
				</c:when>
				<%-- 未ログインの場合 --%>
				<c:otherwise>
					<main class="col-8"> ${param.content} </main>
				</c:otherwise>
			</c:choose>
		</div>

		<footer class="footer">
			<c:import url="/staffcommon/footer.jsp" />
		</footer>

	</div>
</body>
</html>
