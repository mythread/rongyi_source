var J = jQuery;
$(function(){
	$('#gobuy').click(function(){
		J('#cboxOverlay').show();
		J('#useraccount').css("z-index","9999").show();
	});
	$('#close').click(function(){
		$('#useraccount').hide();
		J('#cboxOverlay').hide();
	});
	
	J('.buyerr-close').click(function(){
		J(this).parent().hide();
	});
});

function submitAccount(){
	var account = $('#id_aliAccount').val();
	if(account == ''){
		alert('请输入支付宝帐号!');
		return ;
	}
	J.post('/login.htm',{account:account},function(res){
		alert(res.message);
	});
}

//跳到爱淘宝
function goBuy(){
	var uid = J.cookie('f_uid');
	if(uid == ''){
		alert("请先在上面输入支付宝帐号！");
		return;
	}
	var F  = document.forms['gototb'];
	var iframe = J('#writeable_iframe_0') , taobaokeUrl = '';
	if(iframe.size() > 0){
		taobaokeUrl = iframe.contents().find('a').attr('href');
	}
	var inputHtml = '<input type="hidden" name="taobaokeUrl" value="' + taobaokeUrl +'" >';
	J(F).append(inputHtml);
	F.submit();
}