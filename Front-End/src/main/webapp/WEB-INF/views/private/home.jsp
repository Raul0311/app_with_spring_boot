<%@ page session="true"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<meta name="description" content="Web site created using create-react-app" />
	<title>React App</title>
	<script defer="defer" src="${pageContext.request.contextPath}/static/js/main.e12a804b.js"></script>
	<link href="${pageContext.request.contextPath}/static/css/main.d8d7eb30.css" rel="stylesheet">
</head>
<body>
	<div id="root" data-csrf-token="${_csrf.token}" data-user-token="${userToken}" data-user-id="${userId}"></div>
</body>
</html>
