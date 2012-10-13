<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<link href="../css/css/layout.css" rel="stylesheet" type="text/css" />
<link href="../css/css/forms.css" rel="stylesheet" type="text/css" />

<Script src="../js/jquery-1.7.2.js"></script>
<Script type="text/javascript">
$(document).ready(function(){
	$.ajax({
	    url: "../committee",
		type: 'GET',
		dataType : 'json',
		data: {action:'GetAllCommittees'},//Parameters send to the servlet
		error : function(){alert('error');},
		success : function(data){ 

		var val;
		var i = 0;
			  for (i = 0; i < data.length; i++) { 
				  val = data[i];
				  $('#tableDisCommittee').append('<TR><TD>'+val.name+'</TD><TD><a href="#"  id="'+val.id+'" class="remove_committee">Disable</a></TD></TR>');
			      }  
		 }
      });
	
	  $('a.remove_committee').live('click',function(){

		  var committee_id = $(this).attr("id");
          $(this).remove();
                  
          $.ajax({
      	    url: "../committee",
      		type: 'GET',
      		dataType : 'json',
      		data: {action:'disableCommittee',ci:committee_id},//Parameters send to the servlet
      		error : function(){alert('error');},
      		success : function(data){ 

              alert('Committee Disabled');
              
      		 }
            });
       });
	
});
</Script>
</head>
<body>

<div id="wrap">
<div id="content">
<div id="home_main"><div id="search"> 

<div class="tab">
<h2>Disable Committee</h2>
</div>

<div class="container">

<form id="frmDisCommittee" class="search_form">
<table id="tableDisCommittee">
<TR>
  <TH colspan="2">
    List of Committees
  </TH>
</TR>
</table>
</form>
</div></div></div></div></div>
</body>
</html>