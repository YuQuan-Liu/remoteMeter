function msgTips(title,msg,showType){//右下角提示
			$.messager.show({
				title:title,
				msg:msg,
				showType:showType
			});
		}
$.messager.confirm('My Title', 'Are you confirm this?', function(r){
		if (r){
			alert('confirmed: '+r);
		}
	});
