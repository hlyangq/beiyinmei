<#assign basePath=request.contextPath>
<input type="hidden" id="basePath" value="${basePath}"/>
<div class="section-header">
        <div class="content">
            <h1 id="logo">
                <a href="${topmap.systembase.bsetAddress}">
                <img alt="" src="${topmap.systembase.bsetLogo}">
                </a>                
            </h1>

            <div class="logoBanner"><a href="${basePath}/${(topmap.logoAdv.adverHref)!'#'}"><img src="${(topmap.logoAdv.adverPath)!'${basePath}/index_fourteen/images/ad.jpg'}"></a></div>

            <div class="search searchdiv">
                <form class="mallSearch-form" action="${basePath}/goods/searchproduct2.html" method="get"">
                    <div class="mallSearch-input">
                        <label for=""></label>
                        
                        <#if (keyWorlds)??>
                    	<input class="inputSearch" type="text" name="title" autocomplete="off"  value="${(keyWorlds)!''}" />
                    	<#else>
		                    <#if (topmap.hotsearch)?? && (topmap.hotsearch?size>0)>
		                    <input class="inputSearch" type="text" name="title" autocomplete="off" value="" placeholder="${(topmap.hotsearch[0].keyword)!''}"/>
                            <#else>
                                <input class="inputSearch" type="text" name="title" autocomplete="off" value=""/>
		                    </#if>                    
                    	</#if>
                        <button class="btnSearch" type="button" onclick="checkSearch()"></button>
                    </div>
                </form>
                <div class="search_link">
                    热门搜索：
                    <#if (topmap.hotsearch)?? && (topmap.hotsearch?size>0)>
                    <#list topmap.hotsearch as hots>
	                    <#if (hots.sort==1)>
	                    	<#if (hots.keyword?length>7)>
	                    		 <a class="hot" href="javascript:;" onclick="changeSearchKey(this);">${(hots.keyword)?substring(0,7)}</a>
	                    	<#else>
	                    		 <a class="hot" href="javascript:;" onclick="changeSearchKey(this);">${(hots.keyword)!''}</a>
	                    	</#if>
	                    </#if>
	                    <#if (hots.sort>=2) && (hots.sort<=6)>
		                    <#if (hots.keyword?length>7)>
		                    	<a href="javascript:;" onclick="changeSearchKey(this);">${(hots.keyword)?substring(0,7)}</a>
		                    <#else>
		                    	<a href="javascript:;" onclick="changeSearchKey(this);">${(hots.keyword)!''}</a>
		                    </#if>
	                    </#if>
                    </#list>
                    </#if>
                </div>
            </div><!--searchdiv-->

            <div class="mainnav">
                <div class="navLinks">
                    <ul>
                    <#if (topmap.navList)?? && (topmap.navList?size>0)>
                    	<#list topmap.navList as nav>
                    	<#if (nav_index < 8)>
                    		<#if (nav.barName=="首页") || (nav.barUrl=="index.html")>	
                        		<li><a class="navLink on" onclick="clickNav('${(nav.barUrl)!''}','${nav_index+1}');"  href="javascript:;">${(nav.barName)!''}</a></li>
                        	<#else>
                        		<#if (nav.openChannel)?? && (nav.openChannel=='0')>
                        		<li><a class="navLink" onclick="clickNav('${(nav.barUrl)!''}','${nav_index+1}')" href="javascript:;">${(nav.barName)!''}</a></li>
                        		<#else>
                        		<li><a class="navLink" onclick="clickNav('barchannelview/${(nav.barId?c)!''}','${nav_index+1}')" href="javascript:;">${(nav.barName)!''}</a></li>
                        		</#if>
                        	</#if>
                        </#if>
                        </#list>
                   </#if>
                    </ul>
                </div><!--navLinks-->

                <div class="showlist">
                    <strong class="btnnav menucat">
                        <a class="hover" href="javascript:;">热门目的地分类</a>
                    </strong>
                </div><!--showlist-->
            </div><!--mainnav-->
        </div><!--content-->
    </div><!--section-header-->
    <script id="sort" type="text/html">
    <ul class="list dropdown-menu">
        {{each list as value i}}
        <li>
            {{each value.sorts as v index}}
            <a href="{{v.mhref}}">{{v.msort}}</a>
            {{/each}}
            <div class="links">
                {{each value.links as v index}}
                <a href="{{v.lhref}}">{{v.lname}}</a>
                {{/each}}
            </div>
        </li>
        {{/each}}
    </ul>
</script>
<script id="menu" type="text/html">
    {{each menu as value i}}
    <div class="menuView hide" id="{{value.mid}}">
        <div class="hotLinks">
            {{each value.hotLinks as value i}}
            <a href="{{value.hLinks}}">{{value.htValue}}<b><i></i></b></a>
            {{/each}}
        </div>
        <ul>
            {{each value.mlist as value i}}
            <li>
                <h3><a href="{{value.mhref}}">{{value.msort}}<b></b></a></h3>
                <p>
                    {{each value.menus as value i}}
                    <a href="{{value.shref}}">{{value.sname}}</a>
                    {{/each}}
                </p>
            </li>
            {{/each}}
        </ul>
        <div class="menuImg">
            <ul class="bd-list">
                {{each value.brand as value i}}
                <li><a href="{{value.bdHref}}"><img alt="" src="{{value.bdImg}}"/></a></li>
                {{/each}}
            </ul>
            <a class="bd-pro" href="{{value.tHref}}"><img src="{{value.tImg}}" alt="" /></a>
            <a class="bd-pro" href="{{value.bHref}}"><img src="{{value.bImg}}" alt="" /></a>
        </div>
    </div>
    {{/each}}
</script>
<script>
    var data = {
        list: [
        <#if (topmap.classifyBar.classifyBarList)?? && (topmap.classifyBar.classifyBarList?size>0)>
           <#list topmap.classifyBar.classifyBarList as cBar>
	           <#if (cBar.barCate)?? && (cBar.barCate?size>0)>
	            	{"sorts":[
	            	<#list cBar.barCate as bcate>
	            	<#if bcate.cateId==-1>
	            		<#if bcate.temp2=="">
	            			{"msort":"${bcate.cateName}","mhref":"javascript:;"}
	            		<#else>
	            			{"msort":"${bcate.cateName}","mhref":"${basePath}/${bcate.temp2!'0'}"}
	            		</#if>
	            	<#elseif bcate.cateId??>
	            		{"msort":"${bcate.cateName}","mhref":"${basePath}/list/${bcate.cateId?c}-${bcate.cateId?c}.html"}
	            	</#if>
	            	<#if bcate_has_next>,</#if>
	            	</#list>
					],  
	           </#if>
            "links":[
            <#if (cBar.barQuick)?? && (cBar.barQuick?size>0)>
            <#list cBar.barQuick as bquick>
            	<#if bquick.cateId==0>
            		<#if bquick.temp2=="">
                		{"lname":"${bquick.cateName}","lhref":"javascript:;"}
                	<#else>
                		{"lname":"${bquick.cateName}","lhref":"${basePath}/${bquick.temp2!'0'}"}
                	</#if>
                <#elseif bquick.cateId??>
                	{"lname":"${bquick.cateName}","lhref":"${basePath}/list/${bquick.cateId?c}-${bquick.temp1!'0'}.html"}
                </#if>
                <#if bquick_has_next>,</#if>
            </#list>
            </#if>                
            ]}
            <#if cBar_has_next>,</#if>
            </#list>
      	</#if>    
        ],
        menu: [
        <#if (topmap.classifyBar.classifyBarList)?? && (topmap.classifyBar.classifyBarList?size>0)>
        <#list topmap.classifyBar.classifyBarList as cBar>
            {"mid":"submenu-${cBar_index+1}",
            <#if (cBar.indexAdvertList)?? && (cBar.indexAdvertList?size>0)>
            <#list cBar.indexAdvertList as cbAdvert>
            	<#if (cbAdvert.adverSort==1)>
            	"tHref":"${cbAdvert.adverHref}","tImg":"${cbAdvert.adverPath}",
            	</#if>
            	<#if (cbAdvert.adverSort==2)>
            	"bHref":"${cbAdvert.adverHref}","bImg":"${cbAdvert.adverPath}",
            	</#if>
            </#list>
            </#if>
            "hotLinks":[
                {"hLinks":"javascript:;","htValue":"美装城"},
                {"hLinks":"javascript:;","htValue":"女性生活馆"}
            ],"brand":[
            	<#if (cBar.indexBrandList)?? && (cBar.indexBrandList?size>0)>
            	<#list cBar.indexBrandList as cbBrand>
            	<#if (cbBrand.sort>=1) && (cbBrand.sort<=8)>
                {"bdHref":"${(cbBrand.url)!''}","bdImg":"${cbBrand.logoSrc}"}
                </#if>
                <#if cbBrand_has_next>,</#if> 
                </#list>
                </#if>
                <#--{"bdHref":"javascript:;","bdImg":"../images/bd-02.jpg"},
                {"bdHref":"javascript:;","bdImg":"../images/bd-03.jpg"},
                {"bdHref":"javascript:;","bdImg":"../images/bd-04.jpg"},
                {"bdHref":"javascript:;","bdImg":"../images/bd-05.jpg"},
                {"bdHref":"javascript:;","bdImg":"../images/bd-06.jpg"},
                {"bdHref":"javascript:;","bdImg":"../images/bd-07.jpg"},
                {"bdHref":"javascript:;","bdImg":"../images/bd-08.jpg"}-->
            ],"mlist":[
            <#if (cBar.childs)?? && (cBar.childs?size>0)>
            	<#list cBar.childs as cbtwo>
            	<#if cbtwo.goodsCatId==-1>
            		<#if !cbtwo.url?? || cbtwo.url=="">
                		{"mhref":"javascript:;","msort":"${cbtwo.name}",
                	<#else>
                		{"mhref":"${basePath}/${cbtwo.url!'0'}","msort":"${cbtwo.name}",
                	</#if>
                <#else>
                {"mhref":"${basePath}/list/${cbtwo.goodsCatId?c}-${cbtwo.temp3!'0'}.html","msort":"${cbtwo.name}",
                </#if>
                
                
                "menus":[
                   <#if (cbtwo.childs)?? && (cbtwo.childs?size>0)>
			       <#list cbtwo.childs as cbthird>
			        <#if cbthird.goodsCatId==-1>
			        	<#if cbthird.url=="">
                    		{"shref":"javascript:;","sname":"${cbthird.name}"}
                    	<#else>
                    		{"shref":"${basePath}/${cbthird.url!'0'}","sname":"${cbthird.name}"}
                    	</#if>
                    <#else>
                    	{"shref":"${basePath}/list/${cbthird.goodsCatId?c}-${cbthird.temp3!'0'}.html","sname":"${cbthird.name}"}
                    </#if>
                    <#if cbthird_has_next>,</#if>
                   </#list>
                   </#if>  
                ]}
              <#if cbtwo_has_next>,</#if>  
              </#list>
              </#if>  
            ]
            }
         <#if cBar_has_next>,</#if>   
         </#list>   
         </#if>   
        ]
    };
    var html = template('sort', data);
    var menu = template('menu', data);
    $(".menucat").after(html);
    $(".showlist").append(menu);
</script>
<script id="likes" type="text/html">
    <ul class="like-pros">
        {{each likes as value i}}
        <li>
            <a class="lk-img" href="{{value.proHref}}"><img alt="" src="{{value.proImg}}"/></a>
            <p class="lk-name"><a href="{{value.proHref}}">{{value.proName}}</a></p>
            <p class="lk-price">&yen;<strong>{{value.proPrice}}</strong></p>
        </li>
        {{/each}}
    </ul>
</script>
<script>
    var data = {
        likes: [
            {"proHref":"javascript:;","proImg":"../images/images_09.jpg","proName":"嘉实多（Castrol）磁护 5W-40 4L合成机油润滑","proPrice":"239.00"},
            {"proHref":"javascript:;","proImg":"../images/images_10.jpg","proName":"嘉实多（Castrol）磁护 5W-40 4L合成机油润滑","proPrice":"239.00"},
            {"proHref":"javascript:;","proImg":"../images/images_11.jpg","proName":"嘉实多（Castrol）磁护 5W-40 4L合成机油润滑","proPrice":"239.00"},
            {"proHref":"javascript:;","proImg":"../images/images_12.jpg","proName":"嘉实多（Castrol）磁护 5W-40 4L合成机油润滑","proPrice":"239.00"},
            {"proHref":"javascript:;","proImg":"../images/images_13.jpg","proName":"嘉实多（Castrol）磁护 5W-40 4L合成机油润滑","proPrice":"239.00"},
            {"proHref":"javascript:;","proImg":"../images/images_14.jpg","proName":"嘉实多（Castrol）磁护 5W-40 4L合成机油润滑","proPrice":"239.00"},
            {"proHref":"javascript:;","proImg":"../images/images_09.jpg","proName":"嘉实多（Castrol）磁护 5W-40 4L合成机油润滑","proPrice":"239.00"},
            {"proHref":"javascript:;","proImg":"../images/images_10.jpg","proName":"嘉实多（Castrol）磁护 5W-40 4L合成机油润滑","proPrice":"239.00"},
            {"proHref":"javascript:;","proImg":"../images/images_11.jpg","proName":"嘉实多（Castrol）磁护 5W-40 4L合成机油润滑","proPrice":"239.00"},
            {"proHref":"javascript:;","proImg":"../images/images_12.jpg","proName":"嘉实多（Castrol）磁护 5W-40 4L合成机油润滑","proPrice":"239.00"},
            {"proHref":"javascript:;","proImg":"../images/images_13.jpg","proName":"嘉实多（Castrol）磁护 5W-40 4L合成机油润滑","proPrice":"239.00"},
            {"proHref":"javascript:;","proImg":"../images/images_14.jpg","proName":"嘉实多（Castrol）磁护 5W-40 4L合成机油润滑","proPrice":"239.00"},
            {"proHref":"javascript:;","proImg":"../images/images_11.jpg","proName":"嘉实多（Castrol）磁护 5W-40 4L合成机油润滑","proPrice":"239.00"},
            {"proHref":"javascript:;","proImg":"../images/images_12.jpg","proName":"嘉实多（Castrol）磁护 5W-40 4L合成机油润滑","proPrice":"239.00"},
            {"proHref":"javascript:;","proImg":"../images/images_13.jpg","proName":"嘉实多（Castrol）磁护 5W-40 4L合成机油润滑","proPrice":"239.00"},
            {"proHref":"javascript:;","proImg":"../images/images_14.jpg","proName":"嘉实多（Castrol）磁护 5W-40 4L合成机油润滑","proPrice":"239.00"}
        ]
    };
    var html = template('likes', data);
    $(".likes .cont").append(html);
</script>
<script type="text/javascript">
        <#--$(function(){-->
           <#--$.ajax({ url:"${basePath}/getnavsort.htm", async:false ,success: function(data){-->
                    <#--$(".navLinks ul").find("li").each(function(){-->
                    <#--$(this).find("a").removeClass("on");-->
                        <#--if($(this).index()+1==data){-->
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
</script>

