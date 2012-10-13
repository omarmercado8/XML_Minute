<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Agenda</title>
<script src="../js/jquery-1.7.2.js"></script>
<script src="../js/form_validation.js"></script>

<link href="../css/css/layout.css" rel="stylesheet" type="text/css" />
<link href="../css/css/forms.css" rel="stylesheet" type="text/css" />


<script type="text/javascript">
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
				  $('#selectGetCommittees').append('<option value="'+val.id+'">'+val.name+'</option>');
			      }  
		 }
      });
	
    $('#btnSelectCommittee').click(function(){
        $.ajax({
	      url: "../committee",
		  type: 'GET',
		  dataType : 'json',
		  data: {id:$('#selectGetCommittees').val(),action:'GetCommittee'},//Parameters send to the servlet
		  error : function(){alert('error');},
		  success : function(data){
		  
		  $('#listPresent li').remove();
		   	
		    var val;
		    var i = 0;
			for (i = 4; i < data.length; i++) { 
				  val = data[i];
			
				
				if(data[1].chair == val.id ){ 
				$('#listPresent').append('<li> CHAIR :  '+val.first_name+'  '+val.last_name+'  -  '+val.initials+'</li>');
				   }else{ $('#listPresent').append('<li>'+val.first_name+'  '+val.last_name+'  -  '+val.initials+'</li>');}
			      }
		     }
         });
      });
    
    $('#btnCreateAgenda').click(function(){

		    var n = $('#txtTitle').val();
		    var c = $('#selectGetCommittees').val();	 
		    
		    var arrPresent = new Array();
		     var arrMembers = new Array();
		    
		    $('#tableContent input').each(function() { 
			    arrPresent.push(this.value); 
			  });
		 
		$('#listPresent li').each(function() { 
		        arrMembers.push(this.value); 
		       });
		
		 
		 var msj="";
	            var validaciones = false;
	            
	if (digit_regex.test(n)){
	   if (arrPresent.length > 0 && arrPresent[0] != ''){	    
	      if (arrMembers.length > 0){    
	         validaciones=true;
	       }else{msj="Select Committee";}
	     }else{msj="Content is mandatory";}	
	  }else{msj="Title is mandatory";}
	  

        if (validaciones == true){
		    
		    
          $.ajax({
		      url: "../agenda",
			  type: 'GET',
			  dataType : 'json',
			  data: {action:'Create',name:n,committee:c,content:arrPresent.toString() 
			         },//Parameters send to the servlet
			  error : function(){alert('error');},
			  success : function(data){ 		
                   alert("Agenda created Succesfully");
                   window.close();
                   $('#txtTitle').val('');
			     }
           });
            }else{alert(msj);}
        });
	
    $('a.CreateNewContent').live('click',function(){
	    $('#tableContent').append('<TR><TD style="width: 58px;" >Subject : </TD><TD><input type="text" id="txtContent" name="txtContent" class="textfields" style="width: 391px; "><a href="#" class="remove_person">Remove</a></TD></TR>');
      });
      
    $('a.remove_person').live('click',function(){
         $(this).parent().parent().remove();
     });
});
</script>
</head>
<body>

<div id="wrap">
<div id="content">
<div id="home_main"><div id="search"> 

<div class="tab">
<h2>Create New Agenda</h2>
</div>

<div class="container">
<form id="frmCreateAgenda" name="frmCreateAgenda">
<table id="frmCreateAgenda" class="search_form" style="width:100%; border:none;">
<TR><TD class="label">Title : <input type="text" id="txtTitle" name="txtTitle" class="textfields" style="width: 398px; "> </TD></TR>
<!--  <TR><TD>Location : <input type="text" id="txtLocation" name="txtLocation"></TD></TR>   -->
<TR><TD class="label">Select Committee :  <SELECT ID="selectGetCommittees" class="select_field" style="width: 391px; "></SELECT>
<INPUT TYPE="button" ID="btnSelectCommittee" NAME="btnSelectCommittee" VALUE="Select Committee" ></TD><TR/>
<TR><TD><DIV ID="divPresent">Members <UL id="listPresent"></UL></DIV></TD><TR/>

<TR>
  <TD>
    
      <TABLE ID="tableContent">   
        <TR><TD class="label" style="width: 58px; ">Subject : </TD><TD><input type="text" id="txtContent" name="txtContent" class="textfields" style="width: 391px; "></TD></TR>    
      </TABLE>  

   </TD> 
</TR>

<TR><TD>  <a href="#" class="CreateNewContent">+ Add New Subject</a>  </TD></TR> 

<TR><TD><INPUT TYPE="button" ID="btnCreateAgenda" NAME="btnCreateAgenda" VALUE="Create Agenda" ></TD></TR>
<TR><TD><INPUT TYPE="button" ID="btnCloseWindow" NAME="btnCloseWindow" VALUE="Close Window" onclick="window.close()"></TD></TR>
</table>
</form>
</div>
</div></div></div></div>
</body>
</html>