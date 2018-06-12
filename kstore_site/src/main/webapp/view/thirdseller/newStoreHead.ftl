
<div class="headlogobox">
    <div  class="comwidth ">
    	
        <div class="headlogo">
        	<#if map.pageAdvs??>
            <#if map.pageAdvs[0]??>
        	<#if map.pageAdvs[0].adverSort==1>
                <a href="${(map.pageAdvs[0].adverHref)!'#'}" class="headlogolp fl"><img src="${(map.pageAdvs[0].adverPath)!''}"/></a>
            </#if>
            </#if>
            </#if>
                <form action="${basePath}/thirdstoreindex/${map.thirdId}.html"  id="searchTopForm" method = "POST" >
                    <input class="inputsearch fr" name="title" type="text" value="<#if map.search.title??>${(map.search.title)?html}</#if>" placeholder="请输入您要搜索的商品" />
                    <button id="headsearch" type="button" onclick="searchTitleHead($('.inputsearch'))"></button>
                </form>
        </div>
    </div>
</div>

<div class="shopheadbox clearfix">
    <div class="comwidth shopnavs">
    <#if map.classBar??>
        <ul>
        <#list map.classBar as bar>
        	<#--<#if (nav.barName=="首页") >
            <li class="shopnav current" ><a href="#">${(bar.name)!''}</a></li>
            </#if>-->
            <li class="shopnav"><a href="<#if (bar.url?lower_case)?contains("http://")>${(bar.url)!''}<#else>${basePath}/${(bar.url)!''}</#if>">${(bar.name)!''}</a></li>
       </#list>
        </ul>
     </#if>
    </div>
</div>