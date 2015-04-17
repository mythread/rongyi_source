
function openOrCloseShop(shopId,onstatus, fn) {
	$.ajax({
		type : "post",
		url : "../shops/openOrCloseShop",
		data : {
				id : shopId,
				onStatus : onstatus
				}
			}).done(function(data) {
				if(data=="success"){
					fn && fn();
					return;
					/*var openOrclose=shopId+"onstatus";
					var val=$("#"+openOrclose).val();
					if(val=="启用"){
						$("#"+openOrclose).val("停用");
						
					}
					if(val=="停用"){
						$("#"+openOrclose).val("启用");
					}
					loadAajxListBySearchType();*/
				}
			}).fail(function() {
				alert("error");
			});
	
		}

function recomShop(shopId,recommend, fn) {
	$.ajax({
		type : "post",
		url : "../shops/recomShop",
		data : {
				id : shopId,
				recommend : recommend
				}
			}).done(function(data) {
				if(data=="success"){
					fn && fn();
					return;
					/*var openOrclose=shopId+"recommend";
					var val=$("#"+openOrclose).val();
					if(val=="不推荐"){
						$("#"+openOrclose).val("推荐");	
						}
					if(val=="推荐"){
						$("#"+openOrclose).val("不推荐");
						}
					loadAajxListBySearchType();*/
				}
			}).fail(function() {
				alert("error");
			});
	
		}