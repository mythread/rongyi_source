/**
 * 商家平台 JS
 * author:叉叉哥 - 吴操
 * date:2014-05-20
 */
$(document).ready(function(){
	
	// 记住账号选中与未选中
	$(".login-bottom-left").click(function() {
		if($(this).hasClass("login-selected")) {
			$(this).removeClass("login-selected");
		} else {
			$(this).addClass("login-selected");
		}
	});
	
	// qq小框框关闭按钮
	$("#leave-msg-close").click(function() {
		$(this).parent().fadeOut();
	});
	
	// 兼容不支持placeholder的浏览器
	if(!("placeholder" in document.createElement("input"))) {
		
		$("#input-username").val($("#input-username").attr("placeholder"));
		$("#input-password").css("display", "none");
		$forPlaceHolder = $("<input type=\"text\" />").val($("#input-password").attr("placeholder"));
		$("#input-password").parent().append($forPlaceHolder);
		
		var usernameEdit = false;
		$("#input-username").focus(function() {
			if(!usernameEdit) {
				$(this).val("");
			}
		});
		$("#input-username").blur(function() {
			if($(this).val() == null || $(this).val() == "") {
				$(this).val($(this).attr("placeholder"));
				usernameEdit = false;
			} else {
				usernameEdit = true;
			}
		});
		
		$forPlaceHolder.focus(function() {
			$(this).css("display", "none");
			$("#input-password").css("display", "inline-block");
			$("#input-password").focus();
		});
		$("#input-password").blur(function() {
			if($(this).val() == null || $(this).val() == "") {
				$(this).css("display", "none");
				$forPlaceHolder.css("display", "inline-block");
			}
		});
	}
});