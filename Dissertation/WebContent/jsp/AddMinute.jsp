<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<Script src="../js/jquery-1.7.2.js"></script>

<link href="../css/css/layout.css" rel="stylesheet" type="text/css" />
<link href="../css/css/forms.css" rel="stylesheet" type="text/css" />

<Script type="text/javascript">
$(document).ready(function(){
	$.ajax({
	    url: "../agenda",
		type: 'GET',
		dataType : 'json',
		data: {action:'GetCleanAgendas'},//Parameters send to the servlet
		error : function(){alert('error');},
		success : function(data){ 

			
		var val;
		var i = 0;
			  for (i = 0; i < data.length; i++) { 
				  val = data[i];
				  $('#tableAddMinute').append('<TR><TD>'+val.title+'</TD><TD><a href="CreateMinute.jsp?id='+val.id+'" ">Create Minute</a></TD></TR>');
			      }  
		 }
      });
	
	
	
	
});
</Script>
</head>
<body>
<div id="wrap">
<div id="content">
<div id="home_main"><div id="search"> 

<div class="tab">
<h2>Add New Minute </h2>
</div>

<div class="container">

<form id="frmAddMinute">
<table id="tableAddMinute" class="search_form">
<TR>
<TD>Minute Title </TD>  <TD></TD>
</TR>
</table>
</form>
</div></div>
</div></div></div>
</body>
</html>