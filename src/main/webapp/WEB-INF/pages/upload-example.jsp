<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- HEADERS NEEDED TO PREVENT BACK BUTTON ON LOGOUT. DO NOT REMOVE ME! -->
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
<title>File Upload Example</title>
</head>
<body>

<h2>Resource Upload Example</h2>
<form method="POST" action="upload-resource" enctype="multipart/form-data">
	<%-- <input type="hidden" name="identifier" value="${identifier}"> --%>
	<input type="file" name="file" id="fileChooser" /><br />
	<input type="submit" name="upload" value="Upload" />
</form>

<h2>Page Upload Example</h2>
<form method="POST" action="upload-page" enctype="multipart/form-data">
	<%-- <input type="hidden" name="identifier" value="${identifier}"> --%>
	<input type="file" name="file" id="fileChooser" /><br />
	<input type="submit" name="upload" value="Upload" />
</form>

</body>
</html>