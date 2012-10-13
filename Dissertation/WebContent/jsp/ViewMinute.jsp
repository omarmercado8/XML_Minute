<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Minute</title>
<script src="../js/jquery-1.7.2.js"></script>

<link href="../css/css/layout.css" rel="stylesheet" type="text/css" />
<link href="../css/css/forms.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
<%
String Id = request.getParameter("id");
%>
$(document).ready(function(){

	   var id = <%=Id%>     
	          
	     $.ajax({
		    url: "../minute",
		    type: 'GET',
			dataType : 'json',
			data: {action:"View",id:id},//Parameters send to the servlet
			  error : function(){alert('error');},
			  success : function(data){
                                                             
                          $('#txtTitle').val(data[0].title);
                          $('#txtCommittee').val(data[0].committee);
                          $('#txtChair').val(data[0].chair);
                          $('#txtTitle').val(data[0].title);
                          $('#txtDuration').val(data[0].duration);
                          $('#txtLocation').val(data[0].location);
                          var val;

                      for (var i = 0; i < data[1].present.length; i++) { 
				           val = data[1].present[i];
				           $('#listPresent').append('<li>'+val.first_name+'     '+val.last_name+'     '+val.initials+'</li>');
			               }  
			               
			            for (var i = 0; i < data[2].absent.length; i++) { 
				           val = data[2].absent[i];
				           $('#listAbsent').append('<li>'+val.first_name+'     '+val.last_name+'     '+val.initials+'</li>');
			               }      
			      
                           for (var i = 0; i < data[3].content.length; i++) { 
				           val = data[3].content[i];    
                           $('#tableContent').append('<tr><td>'+
                                '<input type="text" id="txtContentTitle" value="'+val.title+'" readonly="readonly" style="width: 398px; "></td></tr>'+
                                '<tr><td><textarea id="textareaContent" readonly="readonly" rows="10" cols="60">'+val.body+'</textarea>'+
                                '</td></tr>');
                              } 
          $('#toPDF').append('<a href="http://localhost:8080/Dissertation/Print/minute.pdf">TO PDF</a>');
			     }
           });

});	
</script>

</head>
<body style="background:#E3F6FC">
<div id="wrap">
<div id="content">
<div id="home_main"><div id="search"> 

<div class="tab">
<h2>View Minute</h2>
</div>
<div id="toPDF">
</div>

<div style="background:#E3F6FC">
<form  name="frmViewMinute">
<table id = "tamble Main" style="width: 718px; ">
<tr>
<td>
<table id="tableViewMinute" class="search_form">
  <tr><td class="label"  style="width: 107px;">Title : </td><td><input type="text" id="txtTitle" name="txtTitle" class="textfields" readonly="readonly" style="width: 398px; "></td></tr>
  <tr><td class="label"  style="width: 107px;">Committee : <td><input type="text" id="txtCommittee" name="txtCommittee" class="textfields" readonly="readonly" style="width: 398px; "></tr>
  <tr><td class="label" style="width: 107px;">Duration : </td><td><input type="text" id="txtDuration" name="txtDuration" class="textfields" readonly="readonly" style="width: 398px; "></td></tr>
  <tr><td class="label"  style="width: 107px;">Location : </td><td><input type="text" id="txtLocation" name="txtLocation" class="textfields" readonly="readonly" style="width: 398px; "></td></tr>
  <tr><td class="label"  style="width: 107px;">Chair : </td><td><input type="text" id="txtChair" name="txtChair" class="textfields" readonly="readonly" style="width: 398px; "></td></tr>
  </table>
</td>
<td>
<table id="tablePerson">
  <tr><td><div>Present<UL id="listPresent"></UL></div></td></tr>
  <tr><td><div>Absent<UL id="listAbsent"></UL></div></td></tr>
</table>
</td>
</tr>
</table>



<table id="tableContent" class="search_form" style="width:100%; border:none;">
<h4>Minute Content</h4>
</table>
</form>
<div id="divPrint">     </div>
</div></div></div></div></div> 
</body>
</html>