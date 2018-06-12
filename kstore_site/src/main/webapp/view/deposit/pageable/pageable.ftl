<#--
	pb:分页对象
 -->
<#macro pagination pb>
<#-- 取得 应用的绝对根路径 -->
    <#assign basePath=request.contextPath>
<div class="paging_area">
    <div class="paging">
        <#if ((pb.pageNo-2)>0)>
            <#assign pageNo="${pb.pageNo-2}" />
        <#else>
            <#assign pageNo="${pb.firstPageNo}" />
        </#if>
        <#if ((pb.lastPageNo-1)>0)>
            <#assign endNo="${pb.lastPageNo-2}" />
        <#else>
            <#assign endNo="1" />
        </#if>
        <#if pb.pageNo == 1>
            <a class="prev_null" href="javascript:void(0);">&lt;&nbsp;上一页</a>
        <#else>
            <a class="" href="javascript:void(0);" onclick="page('${pb.pageNo - 1}','${pb.pageSize}')">&lt;&nbsp;上一页</a>
            <#if (pb.pageNo >= 4)>
                <a class="num" href="javascript:void(0);" onclick="page('1','${pb.pageSize}')">${pb.firstPageNo}</a>
            </#if>
        </#if>
        <#if ((pb.pageNo-3)>1) >
            <span class="ellipsis">...</span>
        </#if>
        <#list pageNo?number .. pb.endNo as item>
            <#if (item=pb.pageNo)>
                <a class="num_cur prev" href="javascript:void(0);">${item}</a>
            <#else>
                <a class="num" href="javascript:void(0);" onclick="page('${item}','${pb.pageSize}')">${item}</a>
            </#if>
        </#list>
        <#if pb.endNo!=pb.lastPageNo>
            <span class="ellipsis">...</span>
        </#if>
        <#if (pb.pageNo == pb.lastPageNo || pb.endNo <= 1)>
            <#if pb.endNo!=pb.lastPageNo>
                <a class="num_cur" href="javascript:void(0);">${pb.lastPageNo}</a>
            </#if>
            <a class="next_null" href="javascript:void(0);">下一页&nbsp;&gt;</a>
        <#else>
            <#if pb.endNo!=pb.lastPageNo>
                <a class="num" href="javascript:void(0);" onclick="page('${pb.lastPageNo}','${pb.pageSize}')">${pb.lastPageNo}</a>
            </#if>
            <a class="" href="javascript:void(0);" onclick="page('${pb.pageNo + 1}','${pb.pageSize}')">下一页&nbsp;&gt;</a>
        </#if>
    </div>
</div><!--/page-->
</#macro>
<script>
    function(pageNo,pageSize){

    }
</script>