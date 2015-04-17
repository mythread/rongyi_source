/**
 * 主页JS
 * author:叉叉哥 - 吴操
 * date:2014-05-20
 */
$(document).ready(function(){
	
	// 焦点图
	$('#focus-imgs').bxSlider({
		auto: true, 
		mode: 'fade'
	});
	
	// 滑动图（案例分享）
	$('#silde-imgs ul').bxSlider({
		slideWidth: 220,
	    minSlides: 4,
	    maxSlides: 4,
	    moveSlides: 1,
	    slideMargin: 8,
	    pager: false
	});
	
});