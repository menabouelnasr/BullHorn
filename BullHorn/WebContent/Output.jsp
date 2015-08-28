<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <style> 
  body {text: white; background-color: white;  text-align: center; }
   p {background-color: white;}
   h1{text:white; text-aligh-left;}
   </style>
<title>Bullhorn</title>
</head>
<body>
<nav class="navbar navbar-inverse">
<div class="jumbotron"> 
  <h1>BullHorn Blog</h1>
</div>
</nav>
<% if ((Boolean)session.getAttribute("LoggedIn")) {%>
<form action = "Index.jsp">
<input type=submit name=submit value="New Post"> </input>
</form>

<div class= "container"></div>

<form action= userProfile method="Get">

<input type= submit name=submit value="See Profile"></input>
</form>
<% }; %>
<br>
${message}
<br>

<p></p>
</form>  
</div>  

</body>
</html>