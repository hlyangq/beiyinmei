<!--<div class="search">
    <div class="search_bg"></div>
    <div class="search_box">
        <div class="s_left">
            <img alt="" src="${basePath}/images/qianmi_logo.png">
            <a href="javascript:;" class="backto_page">×</a>
        </div>
        <form action="${basePath}/searchProduct.htm" method="get" id="searchProductForm" >
            <div class="search_form">
                <i class="ion-ios-search-strong"></i>
                <input id="searchInput"   name="keyWords" type="text"  placeholder="搜索商品">
            </div>
            <div class="s_right">
            <#if status?? && status=='0' ><a href="locationset.html"><i class="ion-ios-location-outline"></i></a><#else>
                <a href="member/notice1.html"><i class="iconfont icon-xiaoxi2"></i></a>
            </#if>
                <a href="pro-list.html" class="search_btn">搜索</a>
            </div>
        </form>
    </div>-->

    <div class="search">
        <div class="search_bg" style="background-color: rgb(255,76,100);opacity:0;"></div>
        <form action="${basePath}/searchProduct.htm" method="get" id="searchProductForm" >
        <div class="search_box">
            <div class="s_left">
                <img alt="" src="${sys.mobileLogoImg!''}">
                <a href="javascript:;" class="backto_page">×</a>
            </div>
            <div class="search_form">
                <i class="ion-ios-search-strong"></i>
                <input id="searchInput"   name="keyWords" type="text"  placeholder="搜索商品">
            </div>
            <div class="s_right">
            <#if status?? && status=='0' ><a href="locationset.html"><i class="ion-ios-location-outline"></i></a><#else>
                <a href="${basePath}/adminnotice.html"><i class="iconfont icon-xiaoxi2"></i></a>
            </#if>
                <a href="javascript:" id="search_btn" class="search_btn">搜索</a>
            </div>
        </div>
            </form>
        <div class="search_page">
            <div class="search_history">
                <h4>历史搜索</h4>
                <ul id="historySearch">

                </ul>
                <div class="clear_history">
                    <button class="btn btn-full-grey" onclick="clearCookie('searchTitle')">清空搜索历史记录</button>
                </div>
            </div>
        </div>
    </div>

</div>
<script type="text/javascript">
    $(function(){
        getList();

        $('#searchInput').on("keydown", function(event) {
            var key = event.which;
            if (key == 13) {
                $("#search_btn").click();
            }
        });
        $("#search_btn").click(function(){
            $("#storeId").val($(".storeId").val());
            checkCookie( $("#searchInput").val());
            $("#searchProductForm").submit();
        })

        $(window).scroll(function(){
            if($(this).scrollTop()<500){
                $('.search_bg').css('opacity',$(this).scrollTop()*0.002);
            }
            else{
                $('.search_bg').css('opacity',1);
            }
        });
    })
        function getList(){
            var title=getCookie("searchTitle");
        var list=spiltCookie(title);
        var str="";
        var count=0;
        for(var i =0;i<list.length;i++){

            if(count <9&&list[i]!=""){
                count++;
                str+='<li><a onclick="subForm(this)" attr-value="'+decodeURIComponent(list[i])+'" href="javascript:;">'+decodeURIComponent(list[i])+'</a></li>';
            }
        }
        $("#historySearch").html(str);
    }

    function subForm(obj){
        $("#searchInput").val($(obj).attr("attr-value"));
        $("#searchProductForm").submit();
    }
    function setCookie(cname, cvalue, exdays) {
        var d = new Date();
        d.setTime(d.getTime() + (exdays*24*60*60*1000));
        var expires = "expires="+d.toUTCString();
        document.cookie = cname + "=" + cvalue + "; " + expires;
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

</script>


<script>
    $(function(){



    });

    /* roll_banner ad */
    $('#roll_banner1').height($(window).width()/(720/396));
    var rollAd1 = new Swiper('#roll_banner1 .swiper-container',{
        pagination: '#roll_banner1 .pagination',
        loop:true,
        grabCursor: true,
        paginationClickable: true
    });

    var rollAd2 = new Swiper('#roll_banner2 .swiper-container',{
        pagination: '#roll_banner2 .pagination',
        loop:true,
        grabCursor: true,
        paginationClickable: true
    });

    var rollAd3 = new Swiper('#roll_banner3 .swiper-container',{
        pagination: '#roll_banner3 .pagination',
        loop:true,
        grabCursor: true,
        paginationClickable: true
    });

    var rollAd4 = new Swiper('#roll_banner4 .swiper-container',{
        pagination: '#roll_banner4 .pagination',
        loop:true,
        grabCursor: true,
        paginationClickable: true
    });

    var rollAd5 = new Swiper('#roll_banner5 .swiper-container',{
        pagination: '#roll_banner5 .pagination',
        loop:true,
        grabCursor: true,
        paginationClickable: true
    });

    /* search */
    $('#searchInput').focus(function(){
        $('.search').addClass('complete_search');
        $('.search_bg').css('backgroundColor','#dfdfdf');
        $('.search_page').height($(window).height()-46);
        $('.backto_page').click(function(){
            $('.search').removeClass('complete_search');
            $('#searchInput').blur();
            if($(window).scrollTop()<500){
                $('.search_bg').css({
                    'opacity': $(window).scrollTop()*0.002,
                    'background-color': 'rgb(255,76,100)'
                });
            }
            else{
                $('.search_bg').css({
                    'opacity':1,
                    'background-color': 'rgb(255,76,100)'
                });
            }
        });
    });

    /* search end */


</script>
