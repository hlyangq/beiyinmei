var UNINSTALLED = 1;
var INSTALLED = 2;
var RESOLVED = 4;
var STARTING = 8;
var STOPPING = 16;
var ACTIVE = 32;
var UPDATED = 64;

var shouldShow = true;
$(function() {
    loadApps();

    $("#freeAppsBtn").click(function(){
        $(".app-search").show();
    });
    $("#myAppsBtn").click(function(){
        $(".app-search").hide();
    });
});
function queryApp() {
    if(event.keyCode==13){
        loadApps();
    }
}
function loadApps(pageNo) {
    var installedApps;
    $.ajax({
        url:'queryAllInstallApps.htm?CSRFToken='+$("#CSRFToken").val(),
        async:false,
        success:function(data) {
            installedApps = data;
            appendInstalledApps(data);
        }
    });
    if(pageNo==undefined) pageNo=1;
    $.ajax({
        url:'appclient.htm?CSRFToken='+$("#CSRFToken").val()+'&pageNo='+pageNo+'&appName='+$("#appName").val(),
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        success:function(result) {
            var data = JSON.parse(result);
            var appList = data.list;
            var html = '';
            var uninstallCount = 0;
            for(var i=0;i<appList.length;i++) {
                var app = appList[i];
                var appFlag = getAppFlag(installedApps,app.id);
                if(appFlag=="undownload"||appFlag=="uninstalled") {
                    uninstallCount ++;
                    html +=
                        '<li>'+
                        '   <a href="javascript:;" class="app-img" onclick="showAppDetail('+app.id+')">'+
                        '       <img src="'+app.appLogo+'" alt="" width="100" height="100">'+
                        '       <span class="app-mask"></span>'+
                        '   </a>'+
                        '<div class="app-introduce">'+
                        '   <a class="app-infomation" href="javascript:;" onclick="showAppDetail('+app.id+')">'+
                        '       <h3 class="app-name">' + app.appName + '</h3>'+
                        '       <p class="app-desc">' + (app.appIntroduction==null?'':app.appIntroduction) + '</p>'+
                        '   </a>';

                    html +=
                        '   <div class="app-menu app-menu'+app.id+'">'+
                        '       <strong>';
                    if(app.installable=='1') {
                        html += '<a class="app-install" href="javascript:;" onclick="downloadApp('+app.id+')">安装</a>';
                    } else {
                        html += '<a class="app-install" href="javascript:;" style="background-color: #F5F5F5" onclick="showBuyTip()">安装</a>';
                    }
                    html +=  '       </strong>'+
                        '   </div>';

                    html += '   <div class="install-process none processbar'+app.id+'"><b></b></div>'+
                        '</div>'+
                    '</li>';
                }
            }

            if(uninstallCount<1) {
                html += '暂时没有可以安装的应用';
            }
            $("#appListUL").html(html);


           /* // 添加页脚
            $("#pageFoot").html("");
            var foot =  "";
            if(data.pageNo==1) {
                foot += '<span class="disabled"> 上一页 </span>';
            } else {
                foot += "<a href='javascript:void(0)' onclick='loadApps(" + data.prePageNo + ")'>上一页</a>";
            }
            var i = 1;
            if(data.startNo>1){
                foot = foot + "<a onclick='loadApps(1)' href='javascript:void(0)'>1</a>";
                foot = foot + '<span class="current">... </span>';
            }
            for (var l = data.startNo; l <= data.endNo; l++)
            {
                if ((i - 1 + data.startNo) == data.pageNo)
                {
                    foot = foot + '<span class="current"> '+data.pageNo+' </span>';
                }
                else
                {
                    foot = foot + "<a onclick='loadApps(" + (i - 1 + data.startNo) + ")' href='javascript:void(0)'>" + (i - 1 + data.startNo) + "</a>";
                }
                i++;
            }
            if(data.endNo<data.totalPages){
                foot = foot + '<span class="current">... </span>';
                foot = foot + "<a onclick='loadApps("+data.totalPages+")' href='javascript:void(0)'>"+data.totalPages+"</a>";
            }
            foot = foot + "<a onclick='loadApps(" + data.nextPageNo +  ")' href='javascript:void(0)'>下一页</a>" ;
            //添加tfoot分页信息
            $("#pageFoot").html(foot);*/
        }
    });
}

function appendInstalledApps(installedApps) {
    var html = '';
    if(installedApps.length<1) {
        $("#installedAppsUL").html("您暂时未安装任何应用。");
        return;
    }
    for(var i=0;i<installedApps.length;i++) {
        var app = installedApps[i];
        html +=
            '<li id="appLi'+app.appId+'">'+
            '   <a href="javascript:;" class="app-img" onclick="showAppDetail('+app.appId+')">'+
            '       <img src="'+app.appLogo+'" alt="" width="100" height="100">'+
            '       <span class="app-mask"></span>'+
            '   </a>'+
            '<div class="app-introduce">'+
            '   <a class="app-infomation" href="javascript:;" onclick="showAppDetail('+app.appId+')">'+
            '       <h3 class="app-name">' + app.appName + '</h3>'+
            '       <p class="app-desc">' + (app.appIntroduction==null?'':app.appIntroduction) + '</p>'+
            '   </a>';
        if(app.installStatus=="4") {
            html +=
                '   <div class="app-menu app-menu'+app.appId+'">'+
                '       <strong>'+
                '           <a class="app-install href="javascript:;" onclick="downloadApp('+app.appId+')">安装</a>'+
                '           <span class="menu-down"><i></i></span>'+
                '       </strong>'+
                '   </div>';
        } else if(app.installStatus=="2") {
            html +=
                '<div class="app-menu app-menu'+app.appId+'">'+
                '   <strong>';
            //检查更新
            if(app.updateFlag=='1') {
                html += '<a class="app-install" href="javascript:;" onclick="updateApp('+app.appId+')">更新</a>';
            } else {
                html += '<a class="app-install" href="javascript:;">未启用</a>';
            }

            html +=
                '           <span class="menu-down"><i></i></span>'+
                '       </strong>'+
                '       <div class="extra-menu">'+
                '           <a href="javascript:;" onclick="startApp('+app.appId+')">启用模块</a>'+
                '           <a href="javascript:;" onclick="uninstallApp('+app.appId+')">卸载模块</a>'+
                '       </div>'+
                '   </div>';
        } else if(app.installStatus=="3") {
            html +=
                '   <div class="app-menu app-menu'+app.appId+'">'+
                '       <strong>';
            //检查更新
            if(app.updateFlag=='1') {
                html += '<a class="app-install" href="javascript:;" onclick="updateApp('+app.appId+')">更新</a>';
            } else {
                html +='<a class="app-install" href="javascript:;">已启用</a>';
            }
            html +=
                '           <span class="menu-down"><i></i></span>'+
                '       </strong>'+
                '       <div class="extra-menu">'+
                '           <a href="javascript:;" onclick="stopApp('+app.appId+')">停用模块</a>'+
                '           <a href="javascript:;" onclick="uninstallApp('+app.appId+')">卸载模块</a>'+
                '       </div>'+
                '   </div>';
        }
        html +=
            '   <div class="install-process none processbar'+app.appId+'"><b></b></div>'+
            '</div>'+
            '</li>';
    }
    $("#installedAppsUL").html(html);

}
function getAppFlag(installedApps,curAppId) {
    var downloaded = false;
    for(var i=0;i<installedApps.length;i++) {
        if(installedApps[i].appId==curAppId) {
            downloaded = true;
            if(installedApps[i].installStatus=='1') {
                return "resolved";
            } else if(installedApps[i].installStatus=='2') {
                return "resolved";
            } else if(installedApps[i].installStatus=='3') {
                return "active";
            } else if(installedApps[i].installStatus=='4') {
                return "uninstalled";
            }
        }
    }
    if(!downloaded) {
        return "undownload";
    }
}

function downloadApp(id) {
    //查询并显示开启进度
    shouldShow = true;
    showInstallProgress(id);
    $.ajax({
        url:'downloadapp.htm?CSRFToken='+$("#CSRFToken").val()+'&id='+id,
        success:function(data) {
            if(data==1) {
                //showTipAlert("正在安装应用，请稍后。请勿关闭或刷新页面，以免安装失败。");
            } else if(data==-1) {
                shouldShow = false;
                showTipAlert("安装失败，请检查您录入的每个网站地址信息，确保每个地址都可以正常访问。");
                refreshBtnGroup(id,UNINSTALLED);
            } else if(data==-2) {
                showTipAlert("该应用已正在安装。");
            }
        }
    });
}

function showInstallProgress(id,p) {
    if(p==undefined) {
        $(".app-menu"+id).html(
            '       <strong>'+
            '           <a class="app-install progressbar'+id+'" href="javascript:;" onclick="stopApp('+id+')">正在安装...</a>'+
            '       </strong>');
    }
    $.ajax({
        url: 'installprocess.htm?CSRFToken=' + $("#CSRFToken").val() + '&id=' + id,
        success: function (data) {
            p = data;
            if(p==0||p=='0'||p==undefined) {
                p = 30;
            }
            startProcessBar(id,p);
            if(p=='100') {
                startProcessBar(id,70);
                startProcessBar(id,100);
                //refreshBtnGroup(id,INSTALLED);
                //showConfirmAlert("应用已经安装。","refreshPage()");
                loadApps();
                $.amaran({
                    'message'   :'应用安装成功',
                    'position'  :'bottom right',
                    'inEffect'  :'slideBottom'
                });
            } else {
                setTimeout("showInstallProgress("+id+","+p+")", 3000);
            }
        }
    });
}
function showAppDetail(id) {
    $.ajax({
        url:'queryAppDetail.htm?id='+id+"&CSRFToken="+$("#CSRFToken").val(),
        success:function(data) {
            $("#appDetailLogo").attr("src",data.appLogo);
            $("#appDetailName").html(data.appName);
            $("#appDetailDesc").html(data.appDesc);
            $("#appDetailVersion").html("最新版本："+data.appVersion);
            $("#appMemory").html("所需内存："+data.needMemory+" M");
            $("#appDetailIntroduction").html(data.appIntroduction);
            $("#updateTime").html(new Date(data.createTime).format("yyyy年MM月dd日"));
        }
    });
    $(".dia-mask, .app-dialog").fadeIn("fast");
}

function updateApp(id) {
    shouldShow = true;
    //查询并显示更新进度
    showProgress(id,10,UPDATED);
    $.ajax({
        url:'updateapp.htm?id='+id+"&CSRFToken="+$("#CSRFToken").val(),
        success:function(data) {
            if (data == 1) {

            } else if (data == -1) {
                shouldShow = false;
                showTipAlert("更新失败，请检查您录入的每个网站地址信息，确保每个地址都可以正常访问。");
                refreshBtnGroup(id, UPDATED);
            }
        }
    });
}

function startApp(id) {
    shouldShow = true;
    //查询并显示开启进度
    showStartProgress(id);
	$.ajax({
		url:'startapp.htm?id='+id+"&CSRFToken="+$("#CSRFToken").val(),
		success:function(data) {
            if (data == 1) {
                //showTipAlert("应用已正在启动，请稍后。请勿关闭或刷新页面，以免启动失败。");
            } else if (data == -1) {
                shouldShow = false;
                showTipAlert("启动失败，请检查您录入的每个网站地址信息，确保每个地址都可以正常访问。");
                refreshBtnGroup(id, INSTALLED);
            }
        }
	});
}

function showStartProgress(id,p) {
    if(p==undefined) {
        $(".app-menu"+id).html(
            '       <strong>'+
            '           <a class="app-install progressbar'+id+'" href="javascript:;" onclick="stopApp('+id+')">正在启动...</a>'+
            '       </strong>');
    }
    $.ajax({
        url: 'startprocess.htm?CSRFToken=' + $("#CSRFToken").val() + '&id=' + id,
        success: function (data) {
            if(parseInt(p)<parseInt(data)) {
                p = data;
            }
            if(p==0||p=='0'||p==undefined) {
                p = 10;
            }
            startProcessBar(id,p);
            if(p=='100') {
                startProcessBar(id,70);
                startProcessBar(id,100);
                //refreshBtnGroup(id,ACTIVE);
                //showConfirmAlert("应用已经启用，刷新页面后即可使用。","refreshPage()");
                loadApps();
                $.amaran({
                    'message'   :'应用启动成功，页面稍后将自动刷新',
                    'position'  :'bottom right',
                    'inEffect'  :'slideBottom'
                });
                timeRefresh();
            } else {
                setTimeout("showStartProgress("+id+","+p+")", 3000);
            }
        }
    });
}
function timeRefresh()
{
    var t=setTimeout("location.reload()",2000)
}
function stopApp(id) {
    shouldShow = true;
    //查询并显示停止进度
    showStopProgress(id);
    $.ajax({
        url:'stopapp.htm?id='+id+"&CSRFToken="+$("#CSRFToken").val(),
        success:function(data) {
            if (data == 1) {
                //showTipAlert("功能正在停用，请稍后。请勿关闭或刷新页面，以免启动失败。");
            } else if (data == -1) {
                shouldShow = false;
                showTipAlert("停用失败，请检查您录入的每个网站地址信息，确保每个地址都可以正常访问。");
                refreshBtnGroup(id, ACTIVE);
            }
        }
    });
}

function showStopProgress(id,p) {
    if(p==undefined) {
        $(".app-menu"+id).html(
            '       <strong>'+
            '           <a class="app-install progressbar'+id+'" href="javascript:;" onclick="stopApp('+id+')">正在停止...</a>'+
            '       </strong>');
    }
    $.ajax({
        url: 'stopprocess.htm?CSRFToken=' + $("#CSRFToken").val() + '&id=' + id,
        success: function (data) {
            p = data;

            if(p==0||p=='0'||p==undefined) {
                p = 10;
            }
            startProcessBar(id,p);
            if(p=='100') {
                startProcessBar(id,100);
                refreshBtnGroup(id,RESOLVED);
                //showConfirmAlert("应用已经停用。","refreshPage()");
                $.amaran({
                    'message'   :'应用已经停用，页面稍后将自动刷新',
                    'position'  :'bottom right',
                    'inEffect'  :'slideBottom'
                });
                timeRefresh();
            } else {
                setTimeout("showStopProgress("+id+","+p+")", 3000);
            }
        }
    });
}
function installApp(id) {
    shouldShow = true;
    //查询并显示安装进度
    showProgress(id,0,INSTALLED);
    $.ajax({
        url:'installapp.htm?id='+id+"&CSRFToken="+$("#CSRFToken").val(),
        success:function(data) {
            if (data == 1) {
                //showTipAlert("功能正在安装,请稍后。请勿关闭或刷新页面，以免安装失败。");
            } else if (data == -1) {
                shouldShow = false;
                showTipAlert("安装失败，请检查您录入的每个网站地址信息，确保每个地址都可以正常访问。");
                refreshBtnGroup(id, INSTALLED);
            }
        }
    });
}

function uninstallApp(id) {
    shouldShow = true;
    //查询并显示卸载进度
    showUninstallProgress(id);
    $.ajax({
        url:'uninstallapp.htm?id='+id+"&CSRFToken="+$("#CSRFToken").val(),
        success:function(data) {
            if (data == 1) {
                //showTipAlert("功能正在卸载，请稍后。请勿关闭或刷新页面，以免卸载失败。");
            } else if (data == -1) {
                shouldShow = false;
                showTipAlert("卸载失败，请检查您录入的每个网站地址信息，确保每个地址都可以正常访问。");
                refreshBtnGroup(id, RESOLVED);
            }

        }
    });
}

function showUninstallProgress(id,p) {
    if(p==undefined) {
        $(".app-menu"+id).html(
            '       <strong>'+
            '           <a class="app-install progressbar'+id+'" href="javascript:;" onclick="stopApp('+id+')">正在停止...</a>'+
            '       </strong>');
    }
    $.ajax({
        url:'queryappstate.htm?CSRFToken='+$("#CSRFToken").val()+'&id='+id,
        success:function(data) {
            if(data==UNINSTALLED) {
                startProcessBar(id,100);
                refreshBtnGroup(id,UNINSTALLED);
                //showConfirmAlert("应用已经卸载。","refreshPage()");
                $("#appLi"+id).remove();
                $.amaran({
                    'message'   :'应用已经卸载，页面稍后将自动刷新',
                    'position'  :'bottom right',
                    'inEffect'  :'slideBottom'
                });
                timeRefresh();
            } else {
                p=50;
                setTimeout("showUninstallProgress("+id+","+p+")", 3000);
            }
        }
    });
}

/*
根据状态查询并显示进度
state:
int UNINSTALLED = 1;
 int INSTALLED = 2;
 int RESOLVED = 4;
 int STARTING = 8;
int STOPPING = 16;
int ACTIVE = 32;*/
function showProgress(id,progress,state) {
    if(!shouldShow) return;
    $(".btngroup"+id).hide();
    if(progress==undefined||progress==0) progress=10;

    if(progress>=100) {
        if(state==ACTIVE) {
            refreshBtnGroup(id,ACTIVE);
            showConfirmAlert("应用已经启用，刷新页面后即可使用。","refreshPage()");
        } else if(state==RESOLVED) {
            refreshBtnGroup(id,RESOLVED);
            showConfirmAlert("应用已经停用。","refreshPage()");
        } else if(state==UNINSTALLED) {
            refreshBtnGroup(id,UNINSTALLED);
            showConfirmAlert("应用已经卸载。","refreshPage()");
        } else if(state==INSTALLED) {
            refreshBtnGroup(id,INSTALLED);
            showConfirmAlert("应用已经成功安装。","refreshPage()");
        } else if(state==UPDATED) {
            refreshBtnGroup(id,UPDATED);
            showConfirmAlert("应用已经成功更新，刷新页面后即可使用。","refreshPage()");
        }
        //$(".progressbar"+id).removeClass().addClass("progressbar"+id);
        //$(".progressbar"+id+" .ui-progressbar-value").hide();
        return;
    }
    startProcessBar(id,progress);

    $.ajax({
        url:'queryappstate.htm?CSRFToken='+$("#CSRFToken").val()+'&id='+id,
        async:false,
        success:function(data) {

            if(data==state) {
                //如果状态已是active，进度最低从70开始
                if(state==ACTIVE) {
                    $.ajax({
                        url:'isBundleAccessable.htm?CSRFToken='+$("#CSRFToken").val()+'&id='+id,
                        async:false,
                        success:function(result) {
                            if(result==1||result=='1') {
                                progress = 100;
                            } else {
                                progress=progress<85?progress:85;
                            }
                        }
                    });
                } else if(state==RESOLVED||state==UNINSTALLED||state==INSTALLED) {
                    progress=progress>96?progress:96;
                }

            }
            setTimeout("showProgress("+id+","+(progress+4)+","+state+")", 3000);
        }
    });
}

function refreshBtnGroup(id,state) {
    if(state==ACTIVE) {
        $(".app-menu"+id).html(
            '       <strong>'+
            '           <a class="app-install progressbar'+id+'" href="javascript:;" onclick="stopApp('+id+')">已启用</a>'+
            '           <span class="menu-down"><i></i></span>'+
            '       </strong>'+
            '       <div class="extra-menu">'+
            '           <a href="javascript:;" onclick="stopApp('+id+')">停用模块</a>'+
            '           <a href="javascript:;" onclick="uninstallApp('+id+')">卸载模块</a>'+
            '       </div>');
    } else if(state==RESOLVED||state==INSTALLED) {
        $(".app-menu"+id).html(
            '       <strong>'+
            '           <a class="app-install" href="javascript:;" onclick="startApp('+id+')">未启用</a>'+
            '           <span class="menu-down"><i></i></span>'+
            '       </strong>'+
            '       <div class="extra-menu">'+
            '           <a href="javascript:;" onclick="startApp('+id+')">启用模块</a>'+
            '           <a href="javascript:;" onclick="uninstallApp('+id+')">卸载模块</a>'+
            '       </div>');
    } else if(state==UNINSTALLED) {
        $(".app-menu"+id).html(
            '       <strong>'+
            '           <a class="app-install" href="javascript:;" onclick="downloadApp('+id+')">安装</a>'+
            '           <span class="menu-down"><i></i></span>'+
            '       </strong>');
    } else if(state==UPDATED) {
        $(".app-menu"+id).html(
            '       <strong>'+
            '           <a class="app-install" href="javascript:;">已更新</a>'+
            '           <span class="menu-down"><i></i></span>'+
            '       </strong>');
    }
    $(".app-menu"+id).removeClass("none");
    $(".app-menu"+id).next().addClass("none");
}

function refreshPage() {
    location.reload();
}

function startProcessBar(id,proccess) {
    $(".processbar"+id).each(function(){
        $(this).prev().addClass("none");
        $(this).removeClass("none");
        $(this).find("b").animate({width: proccess+"%"});
    });
}

function closeAppDialog() {
    $(".dia-mask, .app-dialog").fadeOut("fast");
}

function showBuyTip() {
    showTipAlert("您暂未开通此应用的权限，请联系我们开通。联系电话：13151590854");
}

function bindMenuDownClick() {
    $('.menu-down').click(function () {
        var e = $(this),
            n = e.parent().next('.extra-menu');
        $('.extra-menu:visible').slideUp('fast'),
            'block' == n.css('display') ? n.slideUp('fast')  : n.show()
    });
}