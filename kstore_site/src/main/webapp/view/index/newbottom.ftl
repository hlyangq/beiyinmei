
<#if shopKuaiShang?? && shopKuaiShang.operation?? && shopKuaiShang.isuseing?? && shopKuaiShang.isuseing =='0'>
${shopKuaiShang.operation !''}
</#if>
<#assign basePath=request.contextPath>
	<link rel="stylesheet" type="text/css" href="${basePath}/index_two/css/footer.css" />
    <style>
        .mt20 {
            margin-top: 20px;
        }
    </style>
    <div style="background-color: #fff;">
    <div class="wp" >
        <div class="service mt20 clearfix" style="padding-left:125px">
        	<#if (bottom.helpCates)?? && (bottom.helpCates?size>0)>
        	<#list bottom.helpCates as helpcate>
        	<#if (helpcate_index>=0) && (helpcate_index<5)>
        	<dl class="svc_box svc_01 fl" style="background-image: url(${(helpcate.helpcateImg)!''});width:170px;">
                <dt>${(helpcate.helpcateName)!''}</dt>
                <dd>
                	<#if (helpcate.helpCenters)?? && (helpcate.helpCenters?size>0)>
                	<#list helpcate.helpCenters as helpcenter>
                	<#if (helpcenter_index>=0) && (helpcenter_index<6)>
                	<#if (helpcenter.isFoot=='1')>
                        <#setting number_format="0">
                		<div><a href="${basePath}/help/${helpcenter.helpId}" target="_blank">${(helpcenter.helpTitle)!''}</a></div>
                	</#if>
                	</#if>
                	</#list>
                	</#if>
                	
                </dd>
            </dl>
        	</#if>
        	</#list>
        	</#if>
        	<#--
            <div class="service_intro fr">
                <h3>宁派自营覆盖区县</h3>
                <p>宁派已向全国1739个区县提供自营配送服务，支持货到付款、POS机刷卡和售后上门服务。</p>
                <p class="tr"><a class="view_more" href="${basePath}/help/1">查看详情 &gt;</a></p>
            </div>
        	-->
        </div><!--/service-->

        <div class="footer mt20">
            <ul class="ft_links tc">
            <#--
            	<#if helpCenters?? && (helpCenters?size>0)>
            	<#list helpCenters as hc>
            	<#if (hc_index>=0) && (hc_index<11)>
	            	<li><a href="${basePath}/help/${hc.helpId}">${hc.helpTitle}</a></li>
	            	<#if (hc_index<(11-1))>|</#if>
            	</#if>
            	</#list>
            	</#if>
            -->
            </ul><!--/ft_links-->
        
            <div style="margin: 15px 0px;" id="bq">
            ${topmap.systembase.bsetCopyright}

		    <ul style="text-align:center;margin-top:20px;">
        	<!--站长统计-->
            	<li>
            	<#if sCodeList?? && (sCodeList?size>0)>
			    	<#list sCodeList as sCode>
			    		<#if (sCode.code)??>
						<#if (sCode.code?starts_with("<script") && sCode.code?ends_with("</script>"))>
							${(sCode.code)!''}
						</#if>
			    		</#if>
			    	</#list>
				</#if>
            	</li>
        </ul>
        	</div>
        	
        </div><!--/footer-->
    </div><!--/wp-->
    </div>