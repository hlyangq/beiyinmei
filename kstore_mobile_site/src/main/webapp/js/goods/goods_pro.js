function setCookie(cname, cvalue, exdays) {
        var d = new Date();
        d.setTime(d.getTime() + (exdays*24*60*60*1000));
        var expires = "expires="+d.toUTCString();
    if($(basePath).val()==""){
        $(basePath).val("/");
    }
    document.cookie=cname+ "=" +cvalue+
        ((exdays==null) ? "" : ";expires="+exdays)+";path="+$(basePath).val();
    //获取cookie
}

//获取cookie
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
    }
    return "";
}
//清除cookie
function clearCookie(name) {
    setCookie(name, "", -1);
    getList();
}
function checkCookie(obj) {
    var title=getCookie("searchTitle");
    var list=spiltCookie(title);
    var str="";

    var count=0;
    for(var i =0;i<list.length;i++){
        if(count<8){
            str+=list[i]+'==='
            if(list[i]==encodeURI(obj)) {
                obj="";
            }
            count++;
        }

    }
    str=encodeURI(obj)+"==="+str;
    setCookie("searchTitle",str,1);
}

function spiltCookie(dValue){
    var strs= new Array(); //定义一数组
    strs=dValue.split("===");
    var list= new Array(); //定义一数组
    for(var i =strs.length-1;i>=0;i--){
        if(strs[i]!=""){
            list.push(strs[i]);
        }
    }
    return strs;
}

$('#searchInput').on("keydown", function(event) {
    var key = event.which;
    if (key == 13) {
        $("#searchBtn").click();
    }
});