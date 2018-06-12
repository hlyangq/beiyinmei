<#assign basePath=request.contextPath>

<style>
    .divhide{
        display: none;
    }
    .mallSearch-input input[class="inputSearch"]{
        outline:medium;
    }
    .mallSearch-input .btnSearch{
        outline:medium;
    }
    /*搜索提示-liangck-20151127*/
    .section-header .content .search{
        z-index: 999;
    }
    .ex_search {
        background: #fff;
        border-color: #c33;
        border-width: 1px;
        border-style: solid;
        border-top: none;
        position: absolute;
        top: 40px;
        width: 417px;
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
    .main_nav .container {position: relative;}
    .search {position: static;}

</style>

<input type="hidden" id="basePath" value="${basePath}"/>
    <#if (topmap.topAdv.adverPath) ??>

        <div class="banner full_banner_s" >
            <div class="container pr">
                <span class="close_btn" onclick="removeBanner();"><i class="iconfont icon-cross"></i></span>
            </div>
            <a href="${(topmap.topAdv.adverHref)!'#'}" style="background-image:url(${(topmap.topAdv.adverPath)})"></a>
        </div>
    </#if>
    <div class="container row">
        <div class="pull-right">
            <div class="minicart" style="z-index: 101;!important;">
                <div class="front">
                    <i class="iconfont icon-cart"></i>
                    <a href="${basePath}/myshoppingcart.html" class="myminishopcart">我的购物车<span class="badge cartNum">0</span> </a>
                    <i class="iconfont icon-right"></i>
                </div>
                <div class="minicart_cont" style="z-index: 101;!important;">
                    <div>
                        <ul class="goods headMiniShopcart">
                        </ul>
                        <div class="total_info">
                            <span>共<strong class="cartNum">0</strong>件商品</span>
                            <span>共计<strong class="mcTotalFee">￥0</strong></span>
                            <a href="${basePath}/myshoppingcart.html" class="btn btn-red btn-sm">去结算</a>
                        </div>
                    </div>
                    <div>
                        <div class="minicart_empty">
                            <p>购物车中还没有商品，赶快选购吧</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="pull-left" style="height: 62px;width: 195px;">
            <h1 class="logo">
                <a href="${topmap.systembase.bsetAddress}"><img alt="" src="${topmap.systembase.bsetLogo}"></a>
            </h1>
        </div>
        <div class="pull-left" style="height: 81px;width: 180px;">
            <div class="imgad">
                <a href="${(topmap.logoAdv.adverHref)!'#'}">
                <#if (topmap.logoAdv.adverPath) ??>
                    <img alt="" src="${(topmap.logoAdv.adverPath)}">
                </#if>
                </a>
            </div>
        </div>
        <div class="pull-left">
            <div class="search">
                <div class="search_box">
                    <form class="mallSearch-form" action="${basePath}/goods/searchproduct2.html" method="get">
                        <#if (keyWorlds)??>
                            <input class="text" type="text" name="title" autocomplete="off"  value="${keyWorlds!''}" />
                        <#else>
                            <#if (topmap.hotsearch)?? && (topmap.hotsearch?size>0)>
                                <input class="text hotsearchInput" type="text" name="title" autocomplete="off" value="" placeholder="${(topmap.hotsearch[0].keyword)!''}"/>
                                <input type="hidden" value="${(topmap.hotsearch[0].keyword)!''}" class = "hotsearch" />
                            <#else>
                                <input class="text" type="text" name="title" autocomplete="off" value=""/>
                            </#if>
                        </#if>
                            <button class="btn" type="button" onclick="checkSearch()" >搜索</button>
                    </form>
                </div>
                <#if (topmap.hotsearch)?? && (topmap.hotsearch?size>0)>
                    <div class="hot_search">
                        <ul>
                            <li>
                                <#list topmap.hotsearch as hots>
                                    <#if (hots_index==0)>
                                        <#if (hots.keyword?length>7)>
                                            <a class="hot" href="javascript:;" onclick="changeSearchKey(this);">${(hots.keyword)?substring(0,7)}</a>
                                        <#else>
                                            <a class="hot" href="javascript:;" onclick="changeSearchKey(this);">${(hots.keyword)!''}</a>
                                        </#if>
                                    </#if>
                                </#list>
                                <#list topmap.hotsearch as hots>
                                    <#if (hots_index>=1) && (hots_index<=5)>
                                        <#if (hots.keyword?length>7)>
                                            <a href="javascript:;" onclick="changeSearchKey(this);">${(hots.keyword)?substring(0,7)}</a>
                                        <#else>
                                            <a href="javascript:;" onclick="changeSearchKey(this);">${(hots.keyword)!''}</a>
                                        </#if>
                                    </#if>
                                </#list>
                            </li>
                        </ul>
                    </div>
                </#if>
            </div>
            <#--搜索提示-liangck-20151127-->
            <div class="ex_search pa">
                <ul>
                </ul>
                <p class="tr"><a class="close_ex" href="javascript:;">关闭</a></p>
            </div>
        </div>

    </div>

    <div class="main_nav">
        <div class="container row">
            <div class="pull-left">
            <#if (topmap.temp.expFleid5)?? && (topmap.temp.expFleid5=='0')>
                <div class="showlist divhide cates">
            <#else>
                <div class="showlist cates">
            </#if>
                    <div class="title">
                        <a href="javascript:;">全部商品分类</a>
                    </div>
                    <div class="cont" style="display: none;">
                        <div class="cate_top">
                        <#if (topmap.classifyBar.classifyBarList)?? && (topmap.classifyBar.classifyBarList?size>0)>
                            <#list topmap.classifyBar.classifyBarList as cBar>
                                <div class="item">
                                    <i class="iconfont icon-right"></i>
                                    <ul>
                                    <#if (cBar.barCate)?? && (cBar.barCate?size>0)>
                                        <#list cBar.barCate as bcate>
                                            <#if bcate.cateId==-1>
                                                <#if bcate.temp2=="">
                                                    <li><a href="javascript:;">${bcate.cateName}</a></li>
                                                <#else>
                                                    <li><a href="${basePath}/${bcate.temp2!'0'}">${bcate.cateName}</a></li>
                                                </#if>
                                            <#elseif bcate.cateId??>
                                                <li><a href="${basePath}/list/${bcate.cateId?c}-${bcate.cateId?c}.html">${bcate.cateName}</a></li>
                                            </#if>
                                        </#list>
                                    </#if>
                                    </ul>
                                    <div class="cate_sub">
                                        <div class="item">
                                            <#if (cBar.childs)?? && (cBar.childs?size>0)>
                                                <#list cBar.childs as cbtwo>
                                                    <dl>
                                                        <#if cbtwo.goodsCatId==-1>
                                                            <#if !cbtwo.url?? || cbtwo.url=="">
                                                                <dt><a href="javascript:;">${cbtwo.name} &gt;</a></dt>
                                                            <#else>
                                                                <dt><a href="${basePath}/${cbtwo.url!'0'}">${cbtwo.name} &gt;</a></dt>
                                                            </#if>
                                                        <#else>
                                                            <dt><a href="${basePath}/list/${cbtwo.goodsCatId?c}-${cbtwo.temp3!'0'}.html">${cbtwo.name} &gt;</a></dt>
                                                        </#if>
                                                        <dd>
                                                            <#if (cbtwo.childs)?? && (cbtwo.childs?size>0)>
                                                                <#list cbtwo.childs as cbthird>
                                                                    <#if cbthird.goodsCatId==-1>
                                                                        <#if cbthird.url=="">
                                                                            <a href="javascript:;">${cbthird.name}</a>
                                                                        <#else>
                                                                            <a href="${basePath}/${cbthird.url!'0'}">${cbthird.name}</a>
                                                                        </#if>
                                                                    <#else>
                                                                        <a href="${basePath}/list/${cbthird.goodsCatId?c}-${cbthird.temp3!'0'}.html">${cbthird.name}</a>
                                                                    </#if>
                                                                </#list>
                                                            </#if>
                                                        </dd>
                                                    </dl>
                                                </#list>
                                            </#if>
                                            <div class="cate_ads">
                                                <#if (cBar.indexAdvertList)?? && (cBar.indexAdvertList?size>0)>
                                                    <#list cBar.indexAdvertList as cbAdvert>
                                                        <#if (cbAdvert.adverSort==0)>
                                                            <a href="${cbAdvert.adverHref}"><img alt="" src="${cbAdvert.adverPath}"> </a>
                                                        </#if>
                                                        <#if (cbAdvert.adverSort==1)>
                                                            <a href="${cbAdvert.adverHref}"><img alt="" src="${cbAdvert.adverPath}"> </a>
                                                        </#if>
                                                    </#list>
                                                </#if>
                                                <#if (cBar.indexBrandList)?? && (cBar.indexBrandList?size>0)>
                                                    <#list cBar.indexBrandList as cbBrand>
                                                        <#if (cbBrand.sort>=1) && (cbBrand.sort<=8)>
                                                            <a href="${(cbBrand.url)!''}"><img alt="" src="${cbBrand.logoSrc}"> </a>
                                                        </#if>
                                                    </#list>
                                                </#if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </#if>
                        </div>
                    </div>
                </div>
            </div>
            <div class="pull-left">
                <ul class="nav_list">
                    <li>
                        <#if (topmap.navList)?? && (topmap.navList?size>0)>
                            <#list topmap.navList as nav>
                                <#if (nav_index < 8)>
                                    <#if (nav.barName=="首页") || (nav.barUrl=="index.html")>
                                        <li><a class="navLink on" onclick="clickNav('${(nav.barUrl)!''}','${nav_index+1}');" href="javascript:;">${(nav.barName)!''}</a></li>
                                    <#else>
                                        <#if (nav.openChannel)?? && (nav.openChannel=='0')>
                                            <li><a class="navLink" onclick="clickNav('${(nav.barUrl)!''}','${nav_index+1}')"  href="javascript:;">${(nav.barName)!''}</a></li>
                                        <#else>
                                            <li><a class="navLink" onclick="clickNav('barchannelview/${(nav.barId?c)!''}','${nav_index+1}')" href="javascript:;">${(nav.barName)!''}</a></li>
                                        </#if>
                                    </#if>
                                </#if>
                            </#list>
                        </#if>
                    </li>
                </ul>
            </div>
            <input type="hidden" id="tempcbshowflag" value="${(topmap.temp.expFleid5)!''}">
        </div>
    </div>

<#--<script id="sort" type="text/html">-->
    <#--<ul class="list dropdown-menu">-->
        <#--{{each list as value i}}-->
        <#--<li>-->
        	<#--{{each value.menuSort as value i}}-->
            <#--<a href="{{value.mhref}}" target="_blank">{{value.msort}}</a>-->
            <#--{{/each}}-->
            <#--<div class="links">-->
                <#--{{each value.links as v index}}-->
                <#--<a href="{{v.lhref}}" target="_blank">{{v.lname}}</a>-->
                <#--{{/each}}-->
            <#--</div>-->
        <#--</li>-->
        <#--{{/each}}-->
    <#--</ul>-->
<#--</script>-->
<#--<script id="menu" type="text/html">-->
    <#--{{each menu as value i}}-->
    <#--<div class="menuView hide" id="{{value.mid}}">-->
        <#--<div class="hotLinks">-->
            <#--{{each value.hotLinks as value i}}-->
            <#--<a href="{{value.hLinks}}" target="_blank">{{value.htValue}}<b><i></i></b></a>-->
            <#--{{/each}}-->
        <#--</div>-->
        <#--<ul>-->
            <#--{{each value.mlist as value i}}-->
            <#--<li>-->
                <#--<h3><a href="{{value.mhref}}" target="_blank">{{value.msort}}<b></b></a></h3>-->
                <#--<p>-->
                    <#--{{each value.menus as value i}}-->
                    <#--<a href="{{value.shref}}" target="_blank">{{value.sname}}</a>-->
                    <#--{{/each}}-->
                <#--</p>-->
            <#--</li>-->
            <#--{{/each}}-->
        <#--</ul>-->
        <#--<div class="menuImg">-->
            <#--<ul class="bd-list">-->
                <#--{{each value.brand as value i}}-->
                <#--<li><a href="{{value.bdHref}}" target="_blank"><img alt="" src="{{value.bdImg}}"/></a></li>-->
                <#--{{/each}}-->
            <#--</ul>-->
            <#--<a class="bd-pro" href="{{value.tHref}}" target="_blank"><img src="{{value.tImg}}" alt="" /></a>-->
            <#--<a class="bd-pro" href="{{value.bHref}}" target="_blank"><img src="{{value.bImg}}" alt="" /></a>-->
        <#--</div>-->
    <#--</div>-->
    <#--{{/each}}-->
<#--</script>-->
<#--<script>-->
<#--var s=0;-->
    <#--var data = {-->
        <#--list: [-->
        <#--<#if (topmap.classifyBar.classifyBarList)?? && (topmap.classifyBar.classifyBarList?size>0)>-->
           <#--<#list topmap.classifyBar.classifyBarList as cBar>-->
           		<#--<#if (cBar.barCate)?? && (cBar.barCate?size>0)>-->
                  <#--{-->
                    <#--"menuSort": [-->
                  <#---->
                  <#--<#list cBar.barCate as bcate>-->
                  <#--<#if bcate.cateId==-1>-->
	                  	<#--<#if bcate.temp2=="">-->
	                  	<#--{"msort":"${bcate.cateName}","mhref":"javascript:;"}-->
	                  	<#--<#else>-->
	                  	<#--{"msort":"${bcate.cateName}","mhref":"${basePath}/${bcate.temp2!'0'}"}-->
	                  	<#--</#if>-->
	             <#--<#elseif bcate.cateId??>-->
                    <#--{"msort":"${bcate.cateName}","mhref":"${basePath}/list/${bcate.cateId?c}-${bcate.cateId?c}.html"}-->
                 <#--</#if> -->
                  <#--<#if bcate_has_next>,</#if>       -->
            	  <#--</#list>-->
            	<#---->
            	<#--],-->
            	<#--</#if>-->
            <#--"links":[-->
            <#--<#if (cBar.barQuick)?? && (cBar.barQuick?size>0)>-->
                <#--<#list cBar.barQuick as bquick>-->
                	<#--<#if bquick.cateId==0>-->
                		<#--<#if bquick.temp2=="">-->
                			<#--{"lname":"${bquick.cateName}","lhref":"javascript:;"}-->
                		<#--<#else>-->
                			<#--{"lname":"${bquick.cateName}","lhref":"${basePath}/${bquick.temp2!'0'}"}-->
                		<#--</#if>-->
                	<#--<#elseif bquick.cateId??>-->
                			<#--{"lname":"${bquick.cateName}","lhref":"${basePath}/list/${bquick.cateId?c}-${bquick.temp1!'0'}.html"}-->
                	<#--</#if>-->
                	<#--<#if bquick_has_next>,</#if>-->
                <#--</#list>-->
                <#---->
            <#--</#if> -->
	            <#--]}-->
	         <#--<#if cBar_has_next>,</#if>   -->
          	<#--</#list>-->
      <#--</#if>-->
        <#--],-->
        <#--menu: [-->
        <#--<#if (topmap.classifyBar.classifyBarList)?? && (topmap.classifyBar.classifyBarList?size>0)>-->
			<#--<#list topmap.classifyBar.classifyBarList as cBar>-->
			<#---->
            <#--{"mid":"submenu-${cBar_index+1}",-->
            <#---->
            <#--<#if (cBar.indexAdvertList)?? && (cBar.indexAdvertList?size>0)>-->
            <#--<#list cBar.indexAdvertList as cbAdvert>-->
	            <#--<#if (cbAdvert.adverSort==0)>-->
	            <#--"tHref":"${cbAdvert.adverHref}","tImg":"${cbAdvert.adverPath}",-->
	            <#--</#if>-->
	            <#--<#if (cbAdvert.adverSort==1)>-->
	            <#--"bHref":"${cbAdvert.adverHref}","bImg":"${cbAdvert.adverPath}",-->
	            <#--</#if>-->
            <#--</#list>-->
            <#--</#if>-->
            <#--"hotLinks":[                -->
            <#--],"brand":[-->
            	<#--<#if (cBar.indexBrandList)?? && (cBar.indexBrandList?size>0)>-->
                   <#--<#list cBar.indexBrandList as cbBrand>-->
                  <#--<#if (cbBrand.sort>=1) && (cbBrand.sort<=8)> -->
                	<#--{"bdHref":"${(cbBrand.url)!''}","bdImg":"${cbBrand.logoSrc}"}-->
                  <#--</#if> -->
                  <#--<#if cbBrand_has_next>,</#if>       -->
                <#--</#list>-->
                <#--</#if>-->
            <#--],"mlist":[-->
             <#--<#if (cBar.childs)?? && (cBar.childs?size>0)>-->
            	<#--<#list cBar.childs as cbtwo>-->
	            	<#--<#if cbtwo.goodsCatId==-1>-->
	            		<#--<#if !cbtwo.url?? || cbtwo.url=="">-->
	                		<#--{"mhref":"javascript:;","msort":"${cbtwo.name}",-->
	                	<#--<#else>-->
	                		<#--{"mhref":"${basePath}/${cbtwo.url!'0'}","msort":"${cbtwo.name}",-->
	                	<#--</#if>-->
	                <#--<#else>-->
	                <#--{"mhref":"${basePath}/list/${cbtwo.goodsCatId?c}-${cbtwo.temp3!'0'}.html","msort":"${cbtwo.name}",-->
	                <#--</#if>-->
	                <#--"menus":[-->
	                	<#--<#if (cbtwo.childs)?? && (cbtwo.childs?size>0)>-->
			              <#--<#list cbtwo.childs as cbthird> -->
	                		<#--<#if cbthird.goodsCatId==-1>-->
	                			<#--<#if cbthird.url=="">-->
	                				<#--{"shref":"javascript:;","sname":"${cbthird.name}"}-->
	                			<#--<#else>-->
	                				<#--{"shref":"${basePath}/${cbthird.url!'0'}","sname":"${cbthird.name}"}-->
	                			<#--</#if>-->
	                		<#--<#else>-->
	                			<#--{"shref":"${basePath}/list/${cbthird.goodsCatId?c}-${cbthird.temp3!'0'}.html","sname":"${cbthird.name}"}-->
	                		<#--</#if>-->
	                		<#--<#if cbthird_has_next>,</#if>-->
	                      <#--</#list>-->
	                    <#--</#if>  -->
	                <#--]}-->
	               <#--<#if cbtwo_has_next>,</#if>-->
                <#--</#list>-->
                <#--</#if>-->
                <#---->
            <#--]}-->
            <#--<#if cBar_has_next>,</#if>-->
         <#--</#list>-->
   		<#--</#if>	-->
        <#--]-->
    <#--};-->
    <#--var html = template('sort', data);-->
    <#--var menu = template('menu', data);-->
    <#--$(".menucat").after(html);-->
    <#--$(".showlist").append(menu);-->
<#--</script>-->
<script type="text/javascript">
        //删除头部广告
        function removeBanner(){
            $(".banner").hide();
        }
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
                       $(".ex_search ul").append('<li class="search-item"><a href="javascript:;" class="completion-words">'+ c.substring(0,46)+'</a><a class="del_history" href="javascript:;"style="display: none;">删除</a></li>');
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
