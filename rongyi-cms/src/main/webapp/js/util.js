
var _util={
	SHOW_IMG_PATH : "/upload",//这个要跟常量里面Constant.PicPath.rootPath一起修改
	/**
	 * 公用上传
	 * @param fileId
	 */
	ajaxFileUploadPublic:function(fileId, paramsJson, fn){  
		if ($('#'+fileId).val().length > 0) {
			//开始上传文件时显示一个图片,文件上传完成将图片隐藏  
		    //$("#loading").ajaxStart(function(){$(this).show();}).ajaxComplete(function(){$(this).hide();});  
		    //执行上传文件操作的函数  
//			var paramsJson = {};
//			paramsJson['picPath'] = picPath;
//			paramsJson['maxSize'] = maxSize;
//			paramsJson['picType'] = picType;
		    $.ajaxFileUpload({
		        //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)  
		        url:'../fileUpload/file?paramsJson='+JSON.stringify(paramsJson),  
		        secureuri:false,                           //是否启用安全提交,默认为false   
		        fileElementId:fileId,               //文件选择框的id属性  
		        dataType:'json',                           //服务器返回的格式,可以是json或xml等  
		        success:function(data, status){            //服务器响应成功时的处理函数
		        	if(data.status == 1) {
	            		fn && fn(data);
						return;
	            	}else {
	            		alert(data.msg);
	            	}
		        },  
		        error:function(data, status, e){ //服务器响应失败时的处理函数  
		        	alert("图片上传失败，请重试！");
		        }  
		    }); 
		}else {
			alert('请先选择图片！');
		}
	},
	ajaxSubmitForm:function(formId,fn) {
//		alert(">>>"+$('#'+formId).serialize());
		$.ajax({
            type: "POST",
            url:$('#'+formId).attr('action'),
            data:$('#'+formId).serialize(),// 你的formid
            dataType:'json',
            error: function(request) {
                alert("something error");
            },
            success: function(data) {
            	if(data.status == 1) {
            		fn && fn(data);
					return;
            	}else {
            		alert(data.msg);
            	}
            }
        });
	},
	ajaxSubmit:function(url_, paramsJson_, fn) {//异步提交数据
		//在url后面加个时间戳,防止IE8缓存
		paramsJson_['timeStamp_'] = new Date().getTime();
		$.ajax({
			url: url_,
			type: 'POST',
			data:{paramsJson:JSON.stringify(paramsJson_)},
			dataType: 'json',
			success: function(data){
				if(data.status==1){
					fn && fn(data);
					return;
				}else {
					alert(data.msg);
				}
			}
		});
	},
	ajaxSubmit_:function(url_, paramsJson_, fn) {//异步提交数据
		//在url后面加个时间戳,防止IE8缓存
		paramsJson_['timeStamp_'] = new Date().getTime();
		$.ajax({
			url: url_,
			type: 'POST',
			data:{paramsJson:JSON.stringify(paramsJson_)},
			dataType: 'json',
			success: function(data){
				fn && fn(data);
				return;
			}
		});
	},
	ajaxSubmit2:function(url_, paramert,fn) {//异步提交数据
		//在url后面加个时间戳,防止IE8缓存
		$.ajax({
			url: url_,
			type: 'POST',
			data:paramert,
			success: function(data){
					fn && fn(data);
					return;

			},
			 error: function(request) {
	                alert("something error");
	          }
		});
	},
	// JavaScript 构建一个 form  
	createForm:function(action_, paramsJson_, target_) {
	    // 创建一个 form  
	    var form1 = document.createElement("form");  
	    form1.id = "common_form1";  
	    form1.name = "common_form1";  
	    // 添加到 body 中  
	    document.body.appendChild(form1);  
	    // 创建一个输入  
	    var input = document.createElement("input");  
	    // 设置相应参数  
	    input.type = "hidden";  
	    input.name = "paramsJson";  
	    input.value = paramsJson_;  
	    // 将该输入框插入到 form 中  
	    form1.appendChild(input);  
	    // form 的提交方式  
	    form1.method = "POST";  
	    // form 提交路径  
	    form1.action = action_;  
	    form1.target = target_; 
	    // 对该 form 执行提交  
	    form1.submit();  
	    // 删除该 form  
	    document.body.removeChild(form1);  
	}  
}