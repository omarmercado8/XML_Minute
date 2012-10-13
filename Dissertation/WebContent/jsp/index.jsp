<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="../css/css/layout.css" rel="stylesheet" type="text/css" />
<link href="../css/css/forms.css" rel="stylesheet" type="text/css" />
      
<script src="../js/jquery-1.7.2.js"></script>


<script type="text/javascript">

$(document).ready(function(){
	
	$("#btnRem").click(function(){
				
		 $.ajax({
		        url: "../committee",
			    type: 'GET',
			    dataType : 'json',
			    data: {action:'Alert'},//Parameters send to the servlet
			    error : function(){alert('error')},
			    success : function(data){  alert("Emails have been Sent");
			            }
             }); 
	});
	
 });


</script>

<title>Insert title here</title>

</head>
<body>

<div id="wrap">
<div id="content">
<div id="home_main"><div id="search"> 

<div class="tab">
<h2>XML Minute Database</h2>
</div>

<div class="container">
<table class="search_form">
  <tr> 
    <td class="label">Create New Person </td><td><a href="http://localhost:8080/Dissertation/jsp/CreatePerson.jsp" target="_blank">Create Person</a></td>
  </tr>
  <tr> 
    <td class="label">Create New Committee </td><td><a href="http://localhost:8080/Dissertation/jsp/CreateCommittee.jsp" target="_blank">Create Committee</a></td>
  </tr>
  <tr> 
    <td class="label">Create New Agenda </td><td><a href="http://localhost:8080/Dissertation/jsp/CreateAgenda.jsp" target="_blank">Create Agenda</a></td>
  </tr>
    <tr> 
    <td class="label">Add Minute to New Agendas </td><td><a href="http://localhost:8080/Dissertation/jsp/AddMinute.jsp" target="_blank">Add Minute</a></td>
  </tr>
  <tr> 
    <td class="label">Search for Minutes </td><td><a href="http://localhost:8080/Dissertation/jsp/Search.jsp" target="_blank">Search</a></td>
  </tr>
   <tr> 
    <td class="label">Disable Committees </td><td><a href="http://localhost:8080/Dissertation/jsp/DisableCommittee.jsp" target="_blank">Disable Committee</a></td>
  </tr>
  <tr> 
    <td class="label">Send Email Reminder to Future Committees </td><td><input type="button" id="btnRem" name="btnRem" value="Send Remeinder"></td>
  </tr>
</table>

</div></div></div></div></div>
</body>
</html>