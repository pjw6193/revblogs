<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload Picture</title>
<script src="${pageContext.servletContext.contextPath}/resources/js/jquery-3.1.0.min.js"></script>
<style type="text/css">
#loading{
visibility: hidden;
}
</style>
</head>
<body>
<div class="container page-content">
	<form method="POST" action="upload-picture" enctype="multipart/form-data">
		<input type="file" name="file" id="fileChooser" /><br />
		<input type="submit" id= "upload" name="upload" value="Upload" />
	</form>
</div>
<img id="loading" src="http://blogs.pjw6193.tech/content/resources/img/rev.gif" />

</body>
<script src="${pageContext.servletContext.contextPath}/resources/js/spinner.js"></script>

<script src="${pageContext.servletContext.contextPath}/resources/js/angular.min.js"></script>
</html>
