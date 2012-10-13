<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create New Person</title>
<script src="../js/jquery-1.7.2.js"></script>
<script src="../js/form_validation.js"></script>

<link href="../css/css/layout.css" rel="stylesheet" type="text/css" />
<link href="../css/css/forms.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">

$(document).ready(function(){
	$('#btnCreate').click(function(){
	
	var first_name= $('#txtFIRST_NAME').val();
	var last_name= $('#txtLAST_NAME').val();
	var initials= $('#txtINITIALS').val();
	var company= $('#txtCOMPANY').val();
	var position= $('#txtPOSITION').val();
	var email= $('#txtEMAIL').val();
	var mobile= $('#txtMOBILE').val();
	var telephone= $('#txtTELEPHONE').val();
	
	var msj="error";
	var validaciones = false;
	if (digit_regex.test(first_name)){
	   if (digit_regex.test(last_name)){
	      if (digit_regex.test(initials)){
	         if (company != ''){
	             if (position != ''){
	             if (num_regex.test(telephone)){ 
	                if(mobile != ''){	             	             
	                   if(num_regex.test(mobile)){
	                     if(email != ''){
	                       if (email_regex.test(email)){validaciones = true;}else{msj="Not a valid Email";}	                     
	                       }
	                     }else{msj="mobile phone Only Numbers";}
	                  }else{
	                  if(email != ''){
	                       if (email_regex.test(email)){validaciones = true;}else{msj="Not a valid Email";}	                     
	                       }else{validaciones = true;}
	                   }  	             
	                   }else{msj="telephone is mandatory";}
	                }else{msj="position is mandatory";}
	             }else{msj="company is mandatory";}  
	         }else{msj="Initials is mandatory";}	    
	     }else{msj="Last Name is mandatory";}	
	  }else{msj="First Name is mandatory";}
	
	
	if (validaciones == true){
		$.ajax({
		    url: "../people",
			type: 'GET',
			dataType : 'json',
			data: {action:'Create',FIRST_NAME:first_name,LAST_NAME:last_name,INITIALS:initials,COMPANY:company,POSITION:position,TELEPHONE:telephone,EMAIL:email,MOBILE:mobile},//Parameters send to the servlet
			error : function(){alert('error')},
			success : function(data){ 
			
			      alert("New Person Succesfully Created");	
					$('#txtFIRST_NAME').val('');
	                $('#txtLAST_NAME').val('');
	                $('#txtINITIALS').val('');
	                $('#txtCOMPANY').val('');
	                $('#txtPOSITION').val('');
	                $('#txtEMAIL').val('');
	                $('#txtMOBILE').val('');
	                $('#txtTELEPHONE').val('');
 
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
<h2>Create New Person</h2>
</div>

<div class="container">
<form name="frmCreatePerson">
<TABLE class="search_form">
<tr><td class="label">FIRST NAME   :  </td><td><INPUT TYPE="text" NAME="txtFIRST_NAME" ID="txtFIRST_NAME" class="textfields"></td></tr>  
<tr><td class="label">LAST NAME    :  </td><td><INPUT TYPE="text" NAME="txtLAST_NAME" ID="txtLAST_NAME" class="textfields"></td></tr>  
<tr><td class="label">INITIALS     :  </td><td><INPUT TYPE="text" NAME="txtINITIALS" ID="txtINITIALS" class="textfields"></td></tr>  
<tr><td class="label">COMPANY      :  </td><td><INPUT TYPE="text" NAME="txtCOMPANY" ID="txtCOMPANY" class="textfields"></td></tr>  
<tr><td class="label">POSITION     :  </td><td><INPUT TYPE="text" NAME="txtPOSITION" ID="txtPOSITION" class="textfields"></td></tr>  
<tr><td class="label">TELEPHONE     :  </td><td><INPUT TYPE="text" NAME="txtTELEPHONE" ID="txtTELEPHONE" class="textfields"></td></tr>  
<tr><td class="label">EMAIL        :  </td><td><INPUT TYPE="text" NAME="txtEMAIL" ID="txtEMAIL" class="textfields"></td></tr>  
<tr><td class="label">MOBILE       :  </td><td><INPUT TYPE="text" NAME="txtMOBILE" ID="txtMOBILE" class="textfields"></td></tr>         	              
<tr><td><INPUT TYPE="button" NAME="btnCreate" ID="btnCreate" value="CREATE PERSON" src="../images/searchbtn.gif"></td></tr> 
</TABLE>
<INPUT type="button"  id="btnClose" value="Close Window" onclick="window.close()">
</form>
</div></div></div></div></div>
</body>
</html>