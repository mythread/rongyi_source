	//新浪微博
		function shareWeibo(title,picUrl){
			var url = window.location.href;
			var param = {
				url : url,
				type : '3',
			title : title,
			pic : picUrl,
			language : 'zh_cn',
				rnd : new Date().valueOf()
			};
		var temp = [];
		for ( var p in param) {
			temp.push(p + '=' + encodeURIComponent(param[p] || ''))
		};
		var weiboUrl = "http://service.weibo.com/share/share.php?"+ temp.join('&');
		window.open(weiboUrl,'sinaWeibo','height=550, width=580, top=100,left=350,scrollbars=0,status=0,titlebar=0,toolbar=0,resizable=0,menubar=0,location=0');
		}
		
		//腾讯微博
		function shareqqWeibo(title,picUrl){
		var url = window.location.href;
			var param = {
				c : 'share',
				a : 'index',
    			url : url,
    			title : title,
    			pic : picUrl
			}
		var temp = [];
		for ( var p in param) {
			temp.push(p + '=' + encodeURIComponent(param[p] || ''))
		}
		console.debug(temp.join('&'));
		var weiboUrl = "http://share.v.t.qq.com/index.php?"+ temp.join('&');
		window.open(weiboUrl,'sinaWeibo','height=550, width=580, top=100,left=350,scrollbars=0,status=0,titlebar=0,toolbar=0,resizable=0,menubar=0,location=0');
		}
		
		//qq空间
		function shareQQzone(title,picUrl){
		var url = window.location.href;
			var param = {
    			url : url,
    			title : title,
    			pic : picUrl
			}
		var temp = [];
		for ( var p in param) {
			temp.push(p + '=' + encodeURIComponent(param[p] || ''))
		}
		console.debug(temp.join('&'));
		var weiboUrl = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?"+ temp.join('&');
		window.open(weiboUrl,'sinaWeibo','height=550, width=580, top=100,left=350,scrollbars=0,status=0,titlebar=0,toolbar=0,resizable=0,menubar=0,location=0');
		}
		
		
$(function(){
	$('#id_news_sinaWeibo').click(function(){
		var title = $('#id_textTitle').text();
		var imgArray = $('img') , picUrl = '';
		var size = imgArray.size();
		if(size > 0){
			var ran = parseInt(size / 2);
			if(ran > 0){
				picUrl = imgArray.eq(ran).attr('src');
			}else{
				picUrl = '';
			}
		}
		shareWeibo(title,picUrl);
	});
	
	$('#id_news_qqWeibo').click(function(){
		var title = $('#id_textTitle').text();
		var imgArray = $('img') , picUrl = '';
		var size = imgArray.size();
		if(size > 0){
			var ran = parseInt(size / 2);
			if(ran > 0){
				picUrl = imgArray.eq(ran).attr('src');
			}else{
				picUrl = '';
			}
		}
		shareqqWeibo(title,picUrl);
	});
	
	$('#id_news_qzone').click(function(){
		var title = $('#id_textTitle').text();
		var imgArray = $('img') , picUrl = '';
		var size = imgArray.size();
		if(size > 0){
			var ran = parseInt(size / 2);
			if(ran > 0){
				picUrl = imgArray.eq(ran).attr('src');
			}else{
				picUrl = '';
			}
		}
		shareQQzone(title,picUrl);
	});
	
	//////////////////////////////////////////案列////////////////////////////////
	$('#id_case_sinaWeibo').click(function(){
		var title = $('#id_textTitle').text();
		var imgArray = $('img') , picUrl = '';
		var size = imgArray.size();
		if(size > 0){
			var ran = parseInt(size / 2);
			if(ran > 0){
				picUrl = imgArray.eq(ran).attr('src');
			}else{
				picUrl = '';
			}
		}
		shareWeibo(title,picUrl);
	});
	
	$('#id_case_qqWeibo').click(function(){
		var title = $('#id_textTitle').text();
		var imgArray = $('img') , picUrl = '';
		var size = imgArray.size();
		if(size > 0){
			var ran = parseInt(size / 2);
			if(ran > 0){
				picUrl = imgArray.eq(ran).attr('src');
			}else{
				picUrl = '';
			}
		}
		shareqqWeibo(title,picUrl);
	});
	
	$('#id_case_qzone').click(function(){
		var title = $('#id_textTitle').text();
		var imgArray = $('img') , picUrl = '';
		var size = imgArray.size();
		if(size > 0){
			var ran = parseInt(size / 2);
			if(ran > 0){
				picUrl = imgArray.eq(ran).attr('src');
			}else{
				picUrl = '';
			}
		}
		shareQQzone(title,picUrl);
	});
});
