 <#assign basePath=request.contextPath>
 <!-- 天猫模板没有轮播小广告，这块内容暂时先注释了，后期要加的时候下面注释的代码需要改动，下面这块代码当初写的人取的是页面广告 -->
 <div class="show-box">
     <div id="slides">
        <#if avc?? && (avc?size>0)>
         <#list avc as bigAdvert>
             <div class="slide">
                 <a class="slide-item" href="${bigAdvert.adverHref}"
                    style="background-image:url(${bigAdvert.adverPath});"></a>

                 <div class="slide-wp">
                     <div class="side">
                         <#if avs?? && (avs?size>0)>
                             <#list avs as smallAdvert>
                                 <#if smallAdvert_index==0>
                                     <a href="${smallAdvert.adverHref}"><img alt="" src="${smallAdvert.adverPath}" width="190"
                                                                             height="234"/></a>
                                 </#if>
                                 <#if smallAdvert_index==1>
                                     <a href="${smallAdvert.adverHref}"><img alt="" src="${smallAdvert.adverPath}" width="190"
                                                                             height="234"/></a>
                                 </#if>
                             </#list>
                         </#if>
                     </div>
                 </div>
             </div>
         </#list>
     </#if>
     </div>
 </div>
<div id="wrapper">
        <div id="content">
            <div class="section-01">
                <div class="popular-brands">
                    <div class="title">
                        <h2>热门品牌</h2>
                        <a id="replace-btn" href="javascript:;"><b></b>换一批</a>
                    </div>
                    <div class="cont">
                    	<#if pageAdvs?? && (pageAdvs?size>0)>
                    		<#list pageAdvs as pageAdv>
                    		<#if (pageAdv.adverSort==2)>
                        		<div class="popular-left"><a href="${pageAdv.adverHref}"><img class="lazy" alt="" data-original="${pageAdv.adverPath}"/></a></div>
                        	</#if>
                        	<#if (pageAdv.adverSort==3)>
                        		<div class="popular-right"><a href="${pageAdv.adverHref}"><img class="lazy" alt="" data-original="${pageAdv.adverPath}"/></a></div>
                        	</#if>
                        	</#list>
                        </#if>
                        <div class="brands-cont">
                            <ul class="brands-list">
                            <#if trademarkList?? &&(trademarkList?size>0) >
                            	<#list trademarkList as trademark >	
                                <li style="line-height: 83px;"><a href="${trademark.url}"><img class="lazy" alt=""width="154" height="79" data-original="${trademark.logoSrc}"/></a></li>
                                </#list>
                            </#if>
                            </ul>
                        </div>
                    </div>
                </div>
                <#if pageAdvs?? && (pageAdvs?size>0)>
                  <#list pageAdvs as pageAdv>
                    <#if (pageAdv.adverSort==4)>
                <div class="hotBar"><a href="${pageAdv.adverHref}"><img class="lazy" alt="" data-original="${pageAdv.adverPath}"/></a></div>
                	</#if>
                  </#list>
                </#if>  
            </div>
<#if (floor.floorList)?? && (floor.floorList?size > 0)>
            <div class="section-02">
 <#list floor.floorList as floors>
                 <div class="proLists" id="proLists0${floors.floorId}">
                    <div class="proBox">
                        <div class="title">
                            <span>${floors.floorId}F</span>
                            <h1><img class="lazy" alt="" data-original="${floors.storeyImg}"/>${floors.storeyName}</h1>
                        </div>
                        <#if (floors.indexAdvertList)?? && (floors.indexAdvertList?size > 0)>
                        <#list floors.indexAdvertList as adv>
                      		<#if adv.adverSort==1>
                        		<a href="${adv.adverHref}"><img class="lazy" alt="" data-original="${adv.adverPath}"/></a>
                        	</#if>
                        </#list>
                       	</#if>	
                        
                        <#if (floors.indexCateList)?? && (floors.indexCateList?size > 0)>
                        <div class="plinks">
                        	<#list floors.indexCateList as cates>
                            <a href="list/${cates.id?c}-${floors.goodsCatId?c}.html">${cates.name}</a>
                            </#list>                            
                        </div>
                        </#if>
                    </div><!--proBox-->
                    <div class="proWp">
                    	<#if (floors.indexAdvertList)?? && (floors.indexAdvertList?size > 0)>
                        <#list floors.indexAdvertList as adv>
                      		<#if adv.adverSort==2>
                        		<a class="hot-pro" href="${adv.adverHref}"><img class="lazy" alt="" data-original="${adv.adverPath}"/></a>
                        	</#if>
                        </#list>
                       	</#if>	
                        
                        <div class="proList">
                            <ul>
                           	<#if (floors.indexAdvertList)?? && (floors.indexAdvertList?size > 0)>
                        	<#list floors.indexAdvertList as adv>
                      		<#if (adv.adverSort>=3) && (adv.adverSort<=8)>
                                <li class="pro01"><a href="${adv.adverHref}"><img class="lazy" data-original="${adv.adverPath}" alt=""/></a></li>
                            </#if>
                            </#list>
                            </#if>
                            </ul>
                        </div>
                    </div><!--proWp-->
                </div><!--proLists01-->
</#list>
            </div><!--section-02-->
</#if>
        </div><!--content -->
    </div><!--wrapper-->

 <div class="sideBar">
 <#if (floor.floorList)?? && (floor.floorList?size > 0)>
     <#list floor.floorList as floors>
         <a class="sideItem" href="#proLists0${floors.floorId}">${floors.floorId}F<span>${floors.storeyName?substring(0,2)}</span></a>
     </#list>
 </#if>
 <#if customerService??&&customerService.onlineIndex=='Y'>
     <a class="customer_service" href="javascript:;"><em>${customerService.title}</em><b></b></a>
     <div class="customer-box onlineServiceQQ">
         <span class="title">${customerService.title}</span>
         <hr>
         <a href="javascript:;" class="close-cs"></a>
         <#if QQs??>
             <#list QQs as qq>
                 <p><span class="qq_name">${qq.name}</span><span class="qq_img"><a style="float:right;" target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${qq.contactNumber}&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:${qq.contactNumber}:51" alt="点击这里给我发消息" title="点击这里给我发消息"/></a></span></p>
             </#list>
         </#if>
     </div>
 </#if>
     <a id="backtoTop" href="javascript:;"></a>

 </div>
    
 <script>
$(function() {
        	//图片延迟加载
			$("img.lazy").lazyload({
				threshold: 200,
				effect: "fadeIn",
				failurelimit : 10,
				placeholder: "${basePath}/index_eight/images/lazy_img.png",
				skip_invisible: false
			});
});
</script>
