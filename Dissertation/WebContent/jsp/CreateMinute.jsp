<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="../js/jquery-1.7.2.js"></script>
<script src="../js/form_validation.js"></script>

<link href="../css/css/layout.css" rel="stylesheet" type="text/css" />
<link href="../css/css/forms.css" rel="stylesheet" type="text/css" />


<title>Create New Minute</title>

<% String Id = request.getParameter("id");%>

<script type="text/javascript">

$(document).ready(function(){

	var agenda_id = <%=Id%>

    $.ajax({
	      url: "../agenda",
		  type: 'GET',
		  dataType : 'json',
		  data: {id:agenda_id,action:'GetAgendaInfo'},//Parameters send to the servlet
		  error : function(){alert('error');},
		  success : function(data){ 	
			

		    var i = 0;
		  
		    
		    $('#txtMinuteTitle').val(data[0].title);
		    $('#txtAgenda_id').val(data[0].id);
		   
		    
		  
		    
			for (i = 0; i < data[1].titles.length; i++) { 
				  val = data[1].titles[i];
				  $('#tableContent').append(
				      '<TR><TD>'+
				      '<INPUT TYPE="text" ID="txtTitles" NAME="txtTitles" VALUE="'+val+'" readonly="readonly" style="width: 398px; "> '+
				      '  </TD> </TR> <TR><TD>'+
				      '<textarea ID="txtBody" rows="10" cols="60"></textarea>'+
				      '</TD></TR>');
			      }  
			  getCommittee(data[0].committe_id);
			  $('#txtCommittee_id').val(data[0].committe_id);
		     }
       });
    
    
    
	
	function getCommittee(committee_id){
            $.ajax({
		      url: "../committee",
			  type: 'GET',
			  dataType : 'json',
			  data: {id:committee_id,action:'GetCommittee'},//Parameters send to the servlet
			  error : function(){alert('error');},
			  success : function(data){ 		
		  
				  var ch = data[1].chair;
		  
		  var frequ = data[2].meeting_frequency;
		  var nextMee = data[3].next_meeting;
		  if(frequ == 'ONE_TIME'){nextMee = 'never';}
				  $("#txtFrequency").val(frequ);
				  $("#txtNextMeeting").val(nextMee);
				  
				for (var i = 4; i < data.length; i++) { 
					  var d = data[i];
					
					  if(ch == d.id){
						  
						  $('#txtChair').val(d.first_name+'    '+d.last_name+'    '+d.initials);
					  }
					  
					  $('#listPresent').append('<li value="'+d.id+'">'+d.first_name+'  '+
					   d.last_name+'  '+d.initials+'</li><a href="#" class="remove_person">absent</a>');
				      }
				 $('#txtCommittee').val(data[0].title);
				 
			     }
             });
	       }
          
	
          $('a.remove_person').live('click',function(){
		      $('#listAbsent').append('<li value="'+$(this).prev().val()+'">'+$(this).prev().text()+'</li><a href="#" class="add_person">present</a>');
              $(this).prev().remove();
              $(this).remove();
           });

          
          $('a.add_person').live('click',function(){
		      $('#listPresent').append('<li value="'+$(this).prev().val()+'">'+$(this).prev().text()+'</li><a href="#" class="remove_person">absent</a>');
              $(this).prev().remove();
              $(this).remove();
           });
          
          
          $('#btnCreateMinute').click(function(){
        
              var arrPresent = new Array();
              var arrAbsent = new Array();
          
              var arrTitle = new Array();
              var arrContent = new Array();
          
              $('#listPresent li').each(function() { 
               var dat = this.value;
               if ($.trim(dat) != '') { arrPresent.push(this.value);}
		       });
              

		  
              $('#listAbsent li').each(function() { 
               var dat = this.value;
               if ($.trim(dat) != '') { arrAbsent.push(this.value);}
		       });
              

              
              
              $('#tableContent input[type="text"]').each(function() { 
              var dat = this.value;
              if ($.trim(dat) == '') { dat ="N/A";}
  		        arrTitle.push(dat); 
  		       });
             

              
              $('#tableContent textarea').each(function() { 
  		        var dat = this.value;
                if ($.trim(dat) == '') { dat ="N/A";}
  		        arrContent.push(dat); 
  		       });

          
		      var n = $('#txtMinuteTitle').val();
		      var l = $('#txtMinuteLocation').val();
		      var dh = $('#txtMinuteDurationH').val();
		      var dm = $('#txtMinuteDurationM').val();
		      var c = $('#txtCommittee').val();
		      var a = $('#txtAgenda_id').val();
		      var chair = $('#txtChair').val();
              var nm = $("#txtNextMeeting").val();
              var ci = $("#txtCommittee_id").val();
              
              
              	var msj="";
	            var validaciones = false;
	            
	if (n != ''){
	   if (l != ''){
	        if(arrPresent.length > 0){
	          if(num_regex.test(dh) && num_regex.test(dm)){
	          d = dh +' : ' +dm;
                     validaciones = true;
                 }else{msj="Hour / Minutes only numbers";}
               }else{msj="At least 1 person has to be Present";}	    
	     }else{msj="Location is mandatory";}
	   }else{msj="Title is mandatory";}	
		      
		      if (validaciones == true){
		      
              $.ajax({
		        url: "../minute",
			    type: 'GET',
			    dataType : 'json',
			    data: {action:'Create',name:n,location:l,duration:d,committee:c,present:arrPresent.toString(),absent:arrAbsent.toString(),
				       content:arrContent.toString(),titles: arrTitle.toString(),agenda_id:a,chair:chair,next_meeting:nm,committee_id:ci},//Parameters send to the servlet
			    error : function(){alert('error');},
			    success : function(data){  alert("Committee Created Succesfully");
			    window.close();
			    $('#txtMinuteTitle').val('');
			            }
                });
                
                }else{alert(msj);}
                
           });          
});

</script>

</head>
<body style="background:#E3F6FC">
<div id="wrap">
<div id="content">
<div id="home_main"><div id="search"> 

<div class="tab">
<h2>Create New Minute</h2>
</div>

<div>
<form name="frmCreateMinute">
<input type="hidden" id="txtAgenda_id" name="txtAgenda_id">
<input type="hidden" id="txtCommittee_id" name="txtCommittee_id">
<table id = "tamble Main" style="width: 718px; ">
<tr>
<td>
<table id="tableCreateMinute" class="search_form">
<TR><TD class="label" style="width: 107px; ">Title   :      </td><td><INPUT TYPE="text" ID="txtMinuteTitle" NAME="txtMinuteTitle" class="textfields" readonly="readonly" style="width: 398px; "></TD></TR>
<TR><TD class="label" style="width: 107px; ">Location :     </td><td><INPUT TYPE="text" ID="txtMinuteLocation" NAME="txtMinuteLocation" class="textfields" style="width: 398px; "></TD></TR>
<TR><TD class="label" style="width: 107px; ">Duration :   </td><td> HR <INPUT TYPE="text" ID="txtMinuteDurationH" NAME="txtMinuteDurationH" class="textfields" style="width: 24px; " maxlength="2"> M<INPUT TYPE="text" ID="txtMinuteDurationM" NAME="txtMinuteDurationM" class="textfields" style="width: 24px;" maxlength="2"></TD></TR>
<TR><TD class="label" style="width: 107px; ">Committee :    </td><td><INPUT TYPE="text" ID="txtCommittee" NAME="txtCommittee" class="textfields" readonly="readonly" style="width: 398px; "> </TD><TR/>
<TR><TD class="label" style="width: 107px; ">Chair :        </td><td><INPUT TYPE="text" ID="txtChair" NAME="txtChair" class="textfields" readonly="readonly" style="width: 398px; "> </TD><TR/>
<TR><TD class="label" style="width: 107px; ">Frequency :    </td><td><INPUT TYPE="text" ID="txtFrequency" NAME="txtFrequency" class="textfields" readonly="readonly" style="width: 108px; "></TD></TR>
<TR><TD class="label" style="width: 107px; ">Next Meeting : </td><td><INPUT TYPE="text" ID="txtNextMeeting" NAME="txtNextMeeting" class="textfields" readonly="readonly" ></TD></TR>
</table>
</td>
<td>
<table id="tablePerson">
<TR><TD class="label"><DIV ID="divPresent" >PRESENT<UL id="listPresent"></UL></DIV></TD></TR>
<TR><TD class="label"><DIV ID="divAbsent" >ABSENT<UL id="listAbsent"></UL></DIV></TD></TR>
</table>
</td>
</tr>
</table>



<table id="tableContent" class="search_form" style="width:100%; border:none;">
<TR><TD><DIV ID="divContent">
          <TABLE id="tableContent">
          </TABLE>
        </DIV></TD></TR>
</table>

<table id="tableBtns" class="search_form" style="width:100%; border:none;">
<TR><TD><INPUT TYPE="button" ID="btnCreateMinute" NAME="btnCreateMinute" VALUE="Create Minute"></TD><TR/>
</table>
</form>
</div></div></div></div>
</div>
</body>
</html>