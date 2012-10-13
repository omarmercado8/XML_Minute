<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link href="../css/default.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.7.2.js"></script>
<script src="../js/glDatePicker.js"></script>
<script src="../js/form_validation.js"></script>

<link href="../css/css/layout.css" rel="stylesheet" type="text/css" />
<link href="../css/css/forms.css" rel="stylesheet" type="text/css" />

<title>Create New Committee</title>
<script type="text/javascript">

$(document).ready(function(){

	
	// Basic date picker with default settings 
    $("#date1").glDatePicker(); 




	$.ajax({
		    url: "../people",
			type: 'GET',
			dataType : 'json',
			data: {action:'GetAllPeople'},//Parameters send to the servlet
			error : function(){alert('error');},
			success : function(data){ 
	
				
			var val;
			var i = 0;
				  for (i = 0; i < data.length; i++) { 
					  val = data[i];
					  $('#selMEMBERS').append('<option value="'+val.id+'">'+val.first_name +' -  '+val.last_name +'</option>');
				      }  
			 }
          });
	
	$('#btnMember').click(function(){
         var dat = $('#selMEMBERS option:selected').text();
             
         if($.trim(dat) != ''){
		    $('#listSelected').append('<li>'+$('#selMEMBERS option:selected').text()+'<INPUT id="R1" type="radio" value='+$('#selMEMBERS').val()+' name="RadioGroup"> </li><a href="#" class="remove_person">remove</a>' );	
	       }
	});
	
	    $('a.remove_person').live('click',function(){
	          $(this).prev().remove();
              $(this).remove();
           });
	
	$('#btnCreate').click(function(){
		
		var arr = new Array();

		$('#listSelected input:radio').each(function() { 
		      var dat = this.value;
              if ($.trim(dat) != '') { arr.push(dat);}		
		});


        var c = $('input[name=RadioGroup]:checked').val();

		var n = $('#txtNAME').val();
		var f = $('#txtFREQUENCY').val();
		var NextM = $("#date1").val();

              	var msj="";
	            var validaciones = false;
	            
	if (digit_regex.test(n)){
	   if (NextM != ''){
	     if(arr.length > 0){
	       if ($('input[name=RadioGroup]:checked').length) {   
	          validaciones=true;
	         }else{msj="Select Chair";} 
	       }else{msj="Add memebers";}
	    }else{msj="First meeting is mandatory";}	
	 }else{msj="Committee Name is mandatory";}
	  

        if (validaciones == true){

		$.ajax({
		    url: "../committee",
			type: 'GET',
			dataType : 'json',
			data: {action:'Create',name:n,frequency:f,chair:c,members:arr.toString(),nextM:NextM},//Parameters send to the servlet
			error : function(){alert('error');},
			success : function(data){ 
			  	alert('Committee Succesfully Created');
			  	window.close();
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
<h2>Create New Committee</h2>
</div>

<div class="container">
<form name="frmCreateCommittee">
<TABLE class="search_form"><TR><TD class="label">COMMITTE NAME :  </TD><TD><INPUT TYPE="text" NAME="txtNAME" ID="txtNAME"></TD></TR>
  <TR><TD class="label">Select Memebers <SELECT ID="selMEMBERS" class="select_field" onchange=""></SELECT></TD><TD><INPUT TYPE="button" NAME="btnMember" ID="btnMember" value="Add"></TD></TR>	
 <TR><TD> <DIV ID="divSelected"><ul id="listSelected"><li>Name / Chair </li></ul></DIV> </TD></TR>
<TR><TD class="label">FREQUENCY : </TD><TD>  <SELECT ID="txtFREQUENCY" class="select_field">
<OPTION VALUE="ONE_TIME">ONE TIME</OPTION>   
<OPTION VALUE="WEEKLY">WEEKLY</OPTION>   
<OPTION VALUE="MONTHLY">MONTHLY</OPTION>
<OPTION VALUE="YEARLY">YEARLY</OPTION>   
</SELECT></TD></TR>

<TR><TD class="label">First Meeting : <INPUT id=date1 type=text readonly="readonly"></TD></TR>
<TR><TD><INPUT TYPE="button" NAME="btnCreate" ID="btnCreate" value="CREATE COMMITTEE" ></TD></TR>
<TR><TD><INPUT TYPE="button" NAME="btnClose" ID="btnClose" value="Close Window" onclick="window.close()"></TD></TR>
</TABLE>
</form>

</div></div></div></div></div>
</body>
</html>