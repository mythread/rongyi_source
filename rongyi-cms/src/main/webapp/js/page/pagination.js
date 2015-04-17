

	function changePageSize(formId,isAjaxForm){
		goPage(1,formId,isAjaxForm);
	}
	
	function goPage(p,formId,isAjaxForm){
		 var pageNumInput="<input type='hidden' id='pageNum' name='pageNum' value=''/>"; 
		 $("#"+formId).append(pageNumInput);
		$("#pageNum").val(p);
//		alert($("#"+formId).html());
//		alert($("#pageNum").val());
		if(formId ==""){
			myGoPage(p);
		}else{
			if(!isAjaxForm){
				$("#"+formId).submit();
			}else{
				var myForm = $("#"+formId);
				$.ajax({
					url:myForm.attr("action"),
					type:"post",
					data:myForm.serialize()
				}).done(function(data) {
					submitSuccess(data);
				});
			}
		}
	}
	function myGoPage(p){
	}

	
	
	