<#assign basePath=request.contextPath>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>分类</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="keywords" content="${(seo.meteKey)!''}">
    <meta name="description" content="${(seo.meteDes)!''}">
<#if (sys.bsetName)??>
    <title>${sys.bsetName}</title>
    <input type="hidden" id="bsetName" value="${(sys.bsetName)!''}">
    <input type="hidden" id="bsetDesc" value="${(sys.bsetDesc)!''}">
<#else>
    <title>${(seo.mete)!''}</title>
    <input type="hidden" id="bsetName" value="${(seo.mete)!''}">
    <input type="hidden" id="bsetDesc" value="${(sys.bsetDesc)!''}">
</#if>
    <link href="${basePath}/css/style.min.css" rel="stylesheet">
    <link href="${basePath}/css/ui-dialog.css" rel="stylesheet">
    <script  src="${basePath}/js/jquery.js"></script>
</head>
<body>
<div class="header">
    <form action="${basePath}/searchProduct.htm" method="get" id="searchProductForm" style="margin-bottom:0;">
    <input type="text" name="keyWords" id="searchInput" class="search-text" placeholder="搜索商品">
        <input type="hidden" value="0" name="storeId" id="storeId">
    <a href="javascript:;" class="search-label">搜索</a>
    </form>
</div>
<div class="content-class">
    <div class="main-list">
        <#if catebars??&&catebars?size!=0>
            <#list catebars as cate>
                <a class="list-item <#if cate.cateBarId==cateId>selected</#if>" href="${basePath}/cates/${cate.cateBarId}">
                    <input type="hidden" value="${cate.name}"/>
                    <p class="name">${cate.name}</p>
                </a>
            </#list>
        </#if>
    </div>
    <div class="list">
        <#if cateBarVos??&&cateBarVos?size!=0>
            <#list cateBarVos as cateBarVo>
                <dl class="list-box">
                    <dt>${cateBarVo.name!''}<i></i></dt>
                    <div class="list-body">
                        <#if cateBarVo.childs??&&cateBarVo.childs?size!=0>
                            <#list cateBarVo.childs as cate>
                                <dd>
                                    <a class="sub-list-item" href="${basePath}/list/${(cate.cateId)!''}">
                                        <img alt="" src="${cate.imgSrc!''}" style="height: 78px;width: 78px">
                                        <span style="display: block;height: 18px;overflow: hidden;word-wrap: break-word;white-space: nowrap;width: 78px;">${cate.name!''}</span>
                                    </a>
                                </dd>
                            </#list>
                        </#if>
                    </div>
                </dl>
            </#list>
        </#if>
    </div>
</div>
<#include "/common/smart_menu.ftl"/>
<script src="${basePath}/js/jquery.js"></script>
<script type="text/javascript">
    $(function(){
        $(".bar-bottom a:eq(1)").addClass("selected");

        $(".search-label").click(function(){
            if($("#searchInput").val()==''){
                $("#searchInput").val($(".selected").find("input").val());
            }
            $("#searchProductForm").submit();
        })
    })


</script>
</body>
</html>