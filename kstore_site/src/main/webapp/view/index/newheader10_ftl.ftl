<#assign basePath=request.contextPath>

<style>
    .divhide{
        display: none;
    }
    /*搜索提示-liangck-20151127*/
    .section-header .content .search{
        z-index: 999;
    }
    .inputSearch:focus{outline: none;}
    .ex_search {
        background: #fff;
        border: 1px solid #dcdcdc !important;
        border-top: none;
        position: absolute;
        top: 38px;
        width: 265px;
        z-index: 999;
        box-shadow: 0 4px 8px rgba(140, 140, 140, .5);
        display: none;
    }

    .ex_search ul {
        border-bottom: 1px solid #ddd;
    }

    .ex_search li {
        position: relative;
    }

    .ex_search li a {
        display: block;
        height: 25px;
        line-height: 25px;
        padding: 0 10px;
        color: #666;
    }

    .ex_search li:hover {
        background: #f2f2f2;
    }

    .ex_search li a span {
        color: #ccc;
        float: right;
    }

    .ex_search li .del_history {
        position: absolute;
        top: 0;
        right: 0;
        color: #369;
        display: none;
    }

    .ex_search p {
        height: 25px;
        line-height: 25px;
        padding: 0 10px;
    }
    .cur {background: #f2f2f2;}
</style>
<div class="section-header">
            <div class="content">
                <#--<h1 id="logo" class="hide">
                    <a href="javascript:;"><img alt="" src="../images/logo.png"></a>
                    <div class="logoBanner"><a href="javascript:;"><img alt="" src="../images/logoImg.jpg"></a></div>
                </h1>-->
                <div class="mainnav">
                    <div class="home">
                    <#if (topmap.navList)?? && (topmap.navList?size>0)>
	                <#list topmap.navList as nav>
	                	<#if (nav.barName=="首页") || (nav.barUrl=="index.html")>	
                    		<a href="javascript:;" class="index_icon" onclick="clickNav('${(nav.barUrl)!''}','${nav_index+1}');" ></a>
                    	</#if>
                    </#list>
                    </#if>
                    </div>
                    <div class="navLinks nav1">
                        <ul>
                        <#if (topmap.navList)?? && (topmap.navList?size>0)>
	                		<#list topmap.navList as nav>
				                 	<#if (nav.barName=="首页") || (nav.barUrl=="index.html")>				                 	
				                 	<#else>
					                 	<#if (nav.barSort>=2) && (nav.barSort<=5)>
					                 		<#if (nav.openChannel)?? && (nav.openChannel=='0')>
					                 		<li><a class="navLink" onclick="clickNav('${(nav.barUrl)!''}','${nav_index+1}')" href="javascript:;">${(nav.barName)!''}</a></li>
					                 		<#else>
					                 		<li><a class="navLink" onclick="clickNav('barchannelview/${(nav.barId?c)!''}','${nav_index+1}')" href="javascript:;">${(nav.barName)!''}</a></li>
					                 		</#if>
					                 	</#if>
				                 	</#if>
				             </#list>
				        </#if>
                            <#--<li><a class="navLink" href="javascript:;">新品</a></li>
                            <li><a class="navLink" href="javascript:;">特价</a></li>
                            <li><a class="navLink" href="javascript:;">热卖</a></li>
                            <li><a class="navLink" href="javascript:;">买赠</a></li>-->
                        </ul>
                    </div><!--navLinks-->
                    <div class="search searchdiv right">
                        <form class="mallSearch-form" action="${basePath}/goods/searchproduct2.html" method="get">
                            <div class="mallSearch-input">
                                <label for=""></label>
                                 <#if (keyWorlds)??>
                                <input class="inputSearch" name="title" type="text" autocomplete="off"  value="${(keyWorlds)!''}"/>
                                <#else>
                                <input class="inputSearch" name="title" type="text" autocomplete="off" placeholder=""/>
                                </#if>
                                <button class="btnSearch" type="button" onclick="checkSearch()"></button>
                            </div>
                        </form>
                    <#--搜索提示-liangck-20151127-->
                        <div class="ex_search pa">
                            <ul>
                            </ul>
                            <p class="tr"><a class="close_ex" href="javascript:;">关闭</a></p>
                        </div>
                    </div><!--search-->
                    <div class="navLinks nav2" style="float:right; border-left:1px solid #eee; padding-left:0px;">
                        <ul>
                         <#if (topmap.navList)?? && (topmap.navList?size>0)>
	                		<#list topmap.navList as nav>
	                        	<#if (nav.barSort>=6) && (nav.barSort<=7)>
	                        		<#if (nav.openChannel)?? && (nav.openChannel=='0')>
	                            	<li><a class="navLink" onclick="clickNav('${(nav.barUrl)!''}','${nav_index+1}')" href="javascript:;">${(nav.barName)!''}</a></li>
	                            	<#else>
	                            	<li><a class="navLink" onclick="clickNav('barchannelview/${(nav.barId?c)!''}','${nav_index+1}')" href="javascript:;">${(nav.barName)!''}</a></li>
	                            	</#if>
	                            </#if>
                            </#list>
                        </#if>    
                            <#--<li><a class="navLink" href="javascript:;">特价</a></li>-->
                        </ul>
                    </div><!--navLinks-->


                    <input type="hidden" id="tempcbshowflag" value="${(topmap.temp.expFleid5)!''}">
                <#if (topmap.temp.expFleid5)?? && (topmap.temp.expFleid5=='0')>

                <div class="showlist divhide">
                <#else>
                <div class="showlist">
                </#if>
                        <strong class="btnnav menucat">
                            <a class="hover" href="javascript:;">
                                全部商品分类
                            </a>
                        </strong>
                        <#if (topmap.classifyBar.classifyBarList)?? && (topmap.classifyBar.classifyBarList?size>0)>                        
                        <div class="list dropdown-menu">
                        <#list topmap.classifyBar.classifyBarList as cBar>
                            <div class="menu-list">
                                <div class="menu-list-left">
                                   <p class="arial">${cBar.name}</p>
                                     <#if (cBar.barCate)?? && (cBar.barCate?size>0)>
                                     <#list cBar.barCate as bcate>
	                                     <#if bcate.cateId==-1>
	                                     	<#if bcate.temp2=="">
	                                    	<#--<p>当季流行</p>-->
	                                    	<a href="javascript:;">${bcate.cateName}</a>
	                                    	<#else>
	                                    	<a href="${basePath}/${bcate.temp2!'0'}" target="_blank">${bcate.cateName}</a>
	                                    	</#if>
	                                    <#elseif bcate.cateId??>
	                                    	<a href="${basePath}/list/${bcate.cateId?c}-${bcate.cateId?c}.html" target="_blank">${bcate.cateName}</a>
	                                    </#if>
                                    </#list>
                                    </#if>
                                </div>
                                <#if (cBar.childs)?? && (cBar.childs?size>0)>
                                <div class="menu-list-rig">
                                	<#list cBar.childs as cbtwo>
                                		<#if cbtwo.goodsCatId==-1>
                                			<#if !cbtwo.url??|| cbtwo.url=="">
                                    		<a href="javascript:;">${cbtwo.name}</a>
                                    		<#else>
                                    		<a href="${basePath}/${cbtwo.url!'0'}" target="_blank">${cbtwo.name}</a>
                                    		</#if>
                                    	<#else>
                                    	<a href="${basePath}/list/${cbtwo.goodsCatId?c}-${cbtwo.temp3!'0'}.html" target="_blank">${cbtwo.name}</a>
                                    	</#if>
                                    </#list>
                                    <#--<a href="#">潮流冬装</a>
                                    <a href="#">商场同款</a>
                                    <a href="#">羽绒</a>
                                    <a href="#">毛呢外套</a>
                                    <a href="#">连衣裙</a>-->
                                </div>
                                </#if>
                            </div><!--menu-list-->
                       </#list>
                        </div><!--dropdown-menu-->
                  </#if>
                    </div><!--showlist-->
                </div><!--mainnav-->
            </div><!--content-->
        </div><!--section-header-->
        
<script type="text/javascript">
        <#--$(function(){-->
           <#--$.ajax({ url:"${basePath}/getnavsort.htm", async:false ,success: function(data){-->

                    <#--$(".nav1 ul").find("li").each(function(){-->
                    <#--$(this).find("a").removeClass("on");-->
                        <#--if($(this).index()+2==data){-->
                            <#--$(this).find("a").addClass("on");-->
                        <#--}-->
                    <#--});-->
                    <#--$(".nav2 ul").find("li").each(function(){-->
                    <#--$(this).find("a").removeClass("on");-->
                        <#--if($(this).index()+6==data){-->
                            <#--$(this).find("a").addClass("on");-->
                        <#--}-->
                    <#--});-->
            <#--}-->
            <#--});-->
        <#--});-->
       function clickNav(url,sort){ 
            $.ajax({ url:"${basePath}/navsort.htm?navsort="+sort, async:false ,success: function(date){
                }
            }); 
            //window.location.href="${basePath}/"+url;
           if(url.indexOf("ttp://")!=-1){
               window.open(url);
           }else{
               window.open("${basePath}/"+url);
           }

        }
        //------------------搜索提示-liangck-20151127
        $(".inputSearch").bind('input propertychange', function () {
            $.getJSON("${basePath}/completionWords.htm?t="+new Date().getTime(), {keyWords: $.trim($(this).val())}, function (data) {
                if (data.length > 0) {
                    $(".ex_search ul").html("");
                    data.forEach(function(c){
                        $(".ex_search ul").append('<li class="search-item"><a href="javascript:;" class="completion-words">'+ c.substring(0,30)+'</a><a class="del_history" href="javascript:;"style="display: none;">删除</a></li>');
                    });
                    $(".ex_search").show();
                }else {
                    $(".ex_search").hide();
                }
            });
        })
        ;
        $(".ex_search").on("click","ul li",function(){
            window.location.href="${basePath}/goods/searchproduct2.html?title="+$(this).find(".completion-words").html();
        });
        $(".close_ex").click(function () {
            $(".ex_search").hide();
            $(".inputSearch").blur();
        });
        var _n = $(".search-item").length - 1;
        $("body").keydown(function(event){
            if(event.keyCode == 38) {
                if($(".search-item.cur").length > 0) {
                    var _index = $(".search-item.cur").index();
                    $(".search-item:eq("+ (_index - 1) +")").addClass("cur").siblings(".cur").removeClass("cur");
                    $(".inputSearch").val($(".search-item.cur").find(".completion-words").html());
                } else {
                    $(".search-item:eq("+ (_n) +")").addClass("cur");
                    $(".inputSearch").val($(".search-item.cur").find(".completion-words").html());
                };
            };
            if(event.keyCode == 40) {
                if($(".search-item.cur").length > 0 && $(".search-item.cur").index() !== _n) {
                    var _index = $(".search-item.cur").index();
                    $(".search-item:eq("+ (_index + 1) +")").addClass("cur").siblings(".cur").removeClass("cur");
                    $(".inputSearch").val($(".search-item.cur").find(".completion-words").html());
                } else if ($(".search-item.cur").index() == _n) {
                    $(".search-item:eq(0)").addClass("cur").siblings(".cur").removeClass("cur");
                    $(".inputSearch").val($(".search-item.cur").find(".completion-words").html());
                } else {
                    $(".search-item:eq(0)").addClass("cur");
                    $(".inputSearch").val($(".search-item.cur").find(".completion-words").html());
                };
            };
        });
</script>    