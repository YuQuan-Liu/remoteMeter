window.onload = function(){
	$('#loading-mask').fadeOut();
};
$(function() {
	tabClose();
	tabCloseEven();
	
	$('#topmenu a').click(function() {
		$('#topmenu a').removeClass('active');
		$(this).addClass('active');
		var name = $(this).attr('name');
		if(name=='index'){
			$('body').layout('collapse','west');  
			Clearnav();
			$('#tabs').tabs('select', 0);
			return;
		}else{
			if($('.layout-panel-west').is(":hidden")){
				$('body').layout('expand','west');
			}
		}
		var d = _menus[name];
		
		Clearnav();
		addNav(d);
		InitLeftMenu();
	});

	// 导航菜单绑定初始化
	$("#wnav").accordion( {
		animate : false
	});
	
});

function Clearnav() {
	var pp = $('#wnav').accordion('panels');

	$.each(pp, function(i, n) {
		if (n) {
			var t = n.panel('options').title;
			$('#wnav').accordion('remove', t);
		}
	});

	pp = $('#wnav').accordion('getSelected');
	if (pp) {
		var title = pp.panel('options').title;
		$('#wnav').accordion('remove', title);
	}
}

function addNav(data) {
	$.each(data, function(i, sm) {
		var menulist = "";
		menulist += '<ul>';
		$.each(sm.menus, function(j, o) {
			menulist += '<li><div><a ref="' + o.menuid + '" href="javascript:;" rel="'
					+ o.url + '" ><span class="micon ' + o.icon
					+ '" >&nbsp;</span><span class="nav">' + o.menuname
					+ '</span></a></div></li> ';
		});
		menulist += '</ul>';

		$('#wnav').accordion('add', {
			title : sm.menuname,
			content : menulist,
			iconCls : 'micon ' + sm.icon
		});

	});

	var pp = $('#wnav').accordion('panels');
	var t = pp[0].panel('options').title;
	$('#wnav').accordion('select', t);

}

// 初始化左侧
function InitLeftMenu() {
	hoverMenuItem();
	
	var firstsub = $('#wnav li a')[0];
//	console.log($(firstsub).attr("rel"));
	
	addTab($(firstsub).children('.nav').text(), $(firstsub).attr("rel"), getIcon($(firstsub).attr("ref")));
	$(firstsub).parent().addClass("selected");
	
	$('#wnav li a').click( function() {
		var tabTitle = $(this).children('.nav').text();

		var url = $(this).attr("rel");
		var menuid = $(this).attr("ref");
		var icon = getIcon(menuid);

		addTab(tabTitle, url, icon);
		$('#wnav li div').removeClass("selected");
		$(this).parent().addClass("selected");
	});
}

/**
 * 菜单项鼠标Hover
 */
function hoverMenuItem() {
	$(".easyui-accordion").find('a').hover(function() {
		$(this).parent().addClass("hover");
	}, function() {
		$(this).parent().removeClass("hover");
	});
}

// 获取左侧导航的图标
function getIcon(menuid) {
	var icon = 'micon ';
	$.each(_menus, function(i, n) {
		$.each(n, function(j, o) {
			$.each(o.menus, function(k, m){
				if (m.menuid == menuid) {
					icon += m.icon;
					return false;
				}
			});
		});
	});
	return icon;
}

function addTab(subtitle, url, icon) {
	if ($('#tabs').tabs('exists', subtitle)) {
		$('#tabs').tabs('close', subtitle);
	}
	
	$('#tabs').tabs('add', {
		title : subtitle,
		content : createFrame(url),
		closable : true,
		icon : icon
	});
	tabClose();
}

function createFrame(url) {
	var s = '<iframe scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
	return s;
}

function tabClose() {
	/* 双击关闭TAB选项卡 */
	$(".tabs-inner").dblclick(function() {
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close', subtitle);
	});
	/* 为选项卡绑定右键 */
	$(".tabs-inner").bind('contextmenu', function(e) {
		$('#mm').menu('show', {
			left : e.pageX,
			top : e.pageY
		});

		var subtitle = $(this).children(".tabs-closable").text();

		$('#mm').data("currtab", subtitle);
		$('#tabs').tabs('select', subtitle);
		return false;
	});
}

//绑定右键菜单事件
function tabCloseEven() {
    $('#mm').menu({
        onClick: function (item) {
            closeTab(item.id);
        }
    });
    return false;
}

function closeTab(action){
    var alltabs = $('#tabs').tabs('tabs');
    var currentTab =$('#tabs').tabs('getSelected');
	var allTabtitle = [];
	$.each(alltabs,function(i,n){
		allTabtitle.push($(n).panel('options').title);
	});

    switch (action) {
        case "refresh":
            var iframe = $(currentTab.panel('options').content);
            var src = iframe.attr('src');
            $('#tabs').tabs('update', {
                tab: currentTab,
                options: {
                    content: createFrame(src)
                }
            });
            break;
        case "close":
            var currtab_title = currentTab.panel('options').title;
            $('#tabs').tabs('close', currtab_title);
            break;
        case "closeall":
            $.each(allTabtitle, function (i, n) {
                if (i != 0){
                    $('#tabs').tabs('close', n);
				}
            });
            break;
        case "closeother":
            var currtab_title = currentTab.panel('options').title;
            $.each(allTabtitle, function (i, n) {
                if (n != currtab_title && i != 0)
				{
                    $('#tabs').tabs('close', n);
				}
            });
            break;
        case "closeright":
            var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);

            if (tabIndex == alltabs.length - 1){
               // alert('亲，后边没有啦 ^@^!!');
                return false;
            }
            $.each(allTabtitle, function (i, n) {
                if (i > tabIndex) {
                    if (i != 0){
                        $('#tabs').tabs('close', n);
					}
                }
            });

            break;
        case "closeleft":
            var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);
           /* if (tabIndex == 1) {
               // alert('亲，前边那个上头有');
                return false;
            }*/
            $.each(allTabtitle, function (i, n) {
                if (i < tabIndex) {
                    if (i != 0){
                        $('#tabs').tabs('close', n);
					}
                }
            });

            break;
        case "exit":
            $('#mm').menu('hide');
            break;
    }
}


