<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search</title>
<script src="../js/jquery-1.7.2.js"></script>
<script src="../js/form_validation.js"></script>

<link href="../css/css/layout.css" rel="stylesheet" type="text/css" />
<link href="../css/css/forms.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">

$(document).ready(function(){
	$('#btnSearch').click(function(){
	
	   var selection = $('#txtSearchBy').val();
	   var keyword = $('#txtSearchText').val();       
	          
	          
	   var msj="";
	   var validaciones = false;
	            
	  if (digit_regex.test(keyword)){
	      validaciones=true;
	  }else{msj="Title is mandatory";}
	  
        if (validaciones == true){
        
	     $.ajax({
		    url: "../search",
		    type: 'GET',
			dataType : 'json',
			data: {selection: selection,keyword:keyword},//Parameters send to the servlet
			  error : function(){alert('error')},
			  success : function(data){ 		
              
                    if(data.length == 0){ $('#tableResults tr').remove(); }
                    
                  for (i = 0; i < data.length; i++) { 
				      val = data[i];
				  $('#tableResults').append('<TR><TD>'+val.title+'</TD><TD><a href="ViewMinute.jsp?id='+val.id+'" ">View Minute</a></TD></TR>');
			      }  
			      
                    
			     }
           });
           }else{alert(msj);}
	
	});
});	
</script>

</head>
<body>
<div id="wrap">
<div id="content">
<div id="home_main"><div id="search"> 

<div class="tab">
<h2>Search Minutes</h2>
</div>

<div class="container">
<form  name="frmSearch">
<table id="tableSearch"  class="search_form">
<tr>
<td class="label" style="width: 66px; ">
Search By </td> <td> <select id="txtSearchBy" name="txtSearchBy"  class="select_field">
          <option value="title">Title</option>
          <option value="name">Name</option>
          <option value="content">Content</option>
          <option value="subject">Subject</option>
          </select></td>
<td>
<input type="text" id="txtSearchText" name="txtSearchText" class="textfields">
</td>
<td>
<input type="button" id="btnSearch" name="btnSearch" value="Search" >
</td>
</tr>
</table>
<DIV>
  <TABLE ID="tableResults">
  </TABLE>
</DIV>
</form>
</div></div></div></div></div> 
</body>
</html>