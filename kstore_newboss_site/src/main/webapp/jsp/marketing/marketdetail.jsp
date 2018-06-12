<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>促销详细</title>

    <!-- Bootstrap -->
    <link href="<%=basePath %>css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath %>css/font-awesome.min.css">
    <link href="<%=basePath %>iconfont/iconfont.css" rel="stylesheet">
    <link href="<%=basePath %>css/summernote.css" rel="stylesheet">
    <link href="<%=basePath %>css/bootstrap-select.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="<%=basePath %>css/style.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">body{background:none;}</style>
  </head>
  <body>
   <fmt:formatDate value="${customer.modifiedTime }" pattern="yyyy-MM-dd HH:mm:ss" var="modifiedTime"/>
       <div class="mb20 container-fluid">
              <div class="row">
                <div class="col-sm-8">
                  <p>促销名称：${marketing.marketingName }</p>
                </div>
                <div class="col-sm-10">
                    <c:if test="${marketing.codexType==6}">
                        <p>营销规则：满赠</p>
                    </c:if>
                    <c:if test="${marketing.codexType!=6}">
                        <p>营销规则：${marketing.codexName}</p>
                    </c:if>
                </div>
                <c:if test="${marketing.codexType==6}">
                    <div class="col-sm-6">

                            <c:forEach items="${marketing.fullbuyPresentMarketings }" var="present" varStatus="status">
                                <c:if test="${status.index==0}">
                                    <c:if test="${present.presentType==0}">
                                        <p>满赠类型：满金额赠</p>
                                    </c:if>
                                    <c:if test="${present.presentType==1}">
                                        <p>满赠类型：满数量赠</p>
                                    </c:if>
                                </c:if>
                            </c:forEach>
                    </div>
                </c:if>
              </div>
              <div class="row">
                <div class="col-sm-8">
                  <p>开始时间： <fmt:formatDate value="${marketing.marketingBegin }" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                </div>
                <div class="col-sm-10">
                  <p>结束时间：<fmt:formatDate value="${marketing.marketingEnd }" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                </div>
                <div class="col-sm-6">
                  <p>创建时间：<fmt:formatDate value="${marketing.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-8">
                  <p>修改时间：<fmt:formatDate value="${marketing.modTime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                </div>
                <div class="col-sm-10">
                 <c:if test="${marketing.codexType==1}">
                  <p>直降金额：${marketing.priceOffMarketing.offValue}</p>
                  </c:if>
                  <c:if test="${marketing.codexType==2}">
                   <p>赠送商品：
					<c:forEach items="${marketing.giftList }" var="gift">
	                		${gift.giftName }。
	                	</c:forEach>
					</p>
                  </c:if>
                   <c:if test="${marketing.codexType==3}">
                    <p>赠送优惠券：
						<c:forEach items="${marketing.couponList }" var="coupon">
	                		${coupon.couponName }。
	                	</c:forEach>
					</p>
                  </c:if>
                   <c:if test="${marketing.codexType==4}">
                    <p>折扣：${marketing.discountMarketing.discountValue }</p>
                  </c:if>
                   <%-- <c:if test="${marketing.codexType==5}"> 
                    <p> 满减：满${marketing.fullbuyReduceMarketing.fullPrice } 减${marketing.fullbuyReduceMarketing.reducePrice }</p>
                  </c:if> --%>
                  <c:if test="${marketing.codexType==5}"> 
                    <c:forEach items="${marketing.fullbuyReduceMarketings }" var="buyreduce" varStatus="status">
                    	<c:if test="${status.index==0 }"> 满减：</c:if>满${buyreduce.fullPrice }减${buyreduce.reducePrice }
                    </c:forEach>
                  </c:if> 
                   <c:if test="${marketing.codexType==7}">
                    <p>满赠优惠券：
                    	满${marketing.fullbuyCouponMarketing.fullPrice } 送
	              	 <c:forEach items="${marketing.couponList }" var="coupon">
	                		${coupon.couponName }
	                	</c:forEach>
	               </p>
                  </c:if>
                    <c:if test="${marketing.codexType==8}">
                      <c:forEach items="${marketing.fullbuyDiscountMarketings }" var="buydiscount" varStatus="status">
                        <c:if test="${status.index==0 }">满折扣：</c:if>满${buydiscount.fullPrice }折${buydiscount.fullbuyDiscount } 
                      </c:forEach>
                   <%--  <p>满折扣：满${marketing.fullbuyDiscountMarketing.fullPrice } 折 ${marketing.fullbuyDiscountMarketing.fullbuyDiscount }</p> --%>
                  </c:if>
                    <c:if test="${marketing.codexType==9}">
                    <p> 限购：${marketing.limitBuyMarketing.limitCount }</p>
                  </c:if>
                    <c:if test="${marketing.codexType==10}">
                    <p>团购折扣率： ${marketing.groupon.grouponDiscount }</p>
                  </c:if>
                    <c:if test="${marketing.codexType==11}">
                    <p>抢购折扣率：${marketing.rushs[0].rushDiscount }</p>
                  </c:if>
                    <c:if test="${marketing.codexType==12}">
                    <p>包邮：满 ${marketing.shippingMoney } 元包邮</p>
                  </c:if>
                  <c:if test="${marketing.codexType==13}"> 
                    <c:forEach items="${marketing.fullbuyNoDiscountMarketings }" var="buydiscount" varStatus="status">
                    	<c:if test="${status.index==0 }"> 满件：</c:if>满${buydiscount.packagesNo }件打${buydiscount.packagebuyDiscount }折
                    </c:forEach>
                  </c:if> 
                  <c:if test="${marketing.codexType==14}"> 
                    <c:forEach items="${marketing.fullbuyNoCountMarketings }" var="buycount" varStatus="status">
                    	<c:if test="${status.index==0 }"> 满件：</c:if>满${buycount.countNo }件共${buycount.countMoney }元
                    </c:forEach>
                  </c:if> 
                  <c:if test="${marketing.isAll==1}"><h2 style="color: red;font-weight: bold;">全场促销</h2></c:if>
                </div>
<c:if test="${marketing.codexType==11}">
                  <div class="col-sm-6">
                      <p>ID限购：${marketing.rushs[0].rushLimitation }</p>
                  </div>
</c:if>
              </div>
               <div class="row">
	               <c:if test="${marketing.addGiveType==0}">
			             <div class="col-sm-8">
			                <p>附加赠送积分：${marketing.giveIntegral }</p> 
			             </div>
	                 </c:if>
	                 <c:if test="${marketing.addGiveType==1}">
		             	<div class="col-sm-8">
		                 	<p>附加赠送优惠券：${marketing.couponName }</p>
		                </div>
	                 </c:if>
	                 <c:if test="${marketing.addGiveType==''}">
		                <div class="col-sm-8">
		                 		<p>附加赠送信息:无</p>
		                </div>
	                 </c:if> 
		      </div>
              <c:if test="${fn:length(marketing.marketLelvels)>0}">
               <div class="row">
	             <div class="col-sm-24">
	               <p>会员等级:<span>
	                   <c:forEach items="${marketing.marketLelvels }" var="level" varStatus="status">
	                    ${level.levelName } <c:if test="${status.index !=fn:length(marketing.marketLelvels)-1}">,</c:if>
	                   </c:forEach>
	                   </span></p>
	              </div>
             </div>
              </c:if>
             <c:if test="${fn:length(marketing.marketLogos)>0}">
              <div class="row">
	             <div class="col-sm-24">
	                 <p>促销logo:<span>
	                   <c:forEach items="${marketing.marketLogos }" var="logo" varStatus="status">
	                    <img title="${logo.promotionLogoName }" alt="${logo.promotionLogoName }" src="${logo.promotionLogoUrl }" height="30"/>
	                   </c:forEach>
	                   </span>
		           
	             </div>
             </div> 
            </c:if>  
              <div class="row">
	             <div class="col-sm-8">
	                <c:if test="${marketing.activeSiteType == 0}">
		               <p>活动平台设置：平台 </p>
		             </c:if>
	                <c:if test="${marketing.activeSiteType == 1}">
		               <p>活动平台设置：移动端 </p>
		             </c:if>
	                <c:if test="${marketing.activeSiteType == 2}">
		               <p>活动平台设置：平台和移动端 </p>
		             </c:if>
	             </div>
             </div>

            <c:if test="${marketing.codexType==6}">
                <c:forEach items="${marketing.fullbuyPresentMarketings }" var="present" varStatus="status">
               <fmt:formatNumber value="${present.fullPrice}" var="fullPrice" pattern="##"></fmt:formatNumber>
                       <c:if test="${present.presentType == '0'}">
                            <h4>满${present.fullPrice}元赠<c:if test="${present.presentMode == '0'}">（默认全增）</c:if><c:if test="${present.presentMode == '1'}">（任选一种）</c:if></h4>
                       </c:if>
                       <c:if test="${present.presentType == '1'}">
                            <h4>满${fullPrice}件赠<c:if test="${present.presentMode == '0'}">（默认全增）</c:if><c:if test="${present.presentMode == '1'}">（任选一种）</c:if></h4>
                       </c:if>

                       <c:if test="${present.fullbuyPresentScopes!=null}">
                           <div  style="height:200px;overflow-y:scroll;">
                               <table class="table table-striped table-hover table-bordered">
                                   <thead>
                                       <tr>
                                           <th width="150">商品编号</th>
                                           <th>赠品名称</th>
                                           <th>赠送数量</th>
                                       </tr>
                                   </thead>
                                   <tbody>
                                       <c:forEach items="${present.fullbuyPresentScopes}" var="scope" varStatus="i">
                                           <tr>
                                               <td>${scope.goodsProduct.goodsInfoItemNo }</td>
                                               <td>${scope.goodsProduct.goodsInfoName }</td>
                                               <td>${scope.scopeNum }</td>
                                           </tr>
                                       </c:forEach>
                                   </tbody>
                               </table>
                           </div>
                       </c:if>
                </c:forEach>
            </c:if>

           <h4>促销范围</h4>
            <c:if test="${kulist!=null}">
           	 <div  style="height:200px;overflow-y:scroll;">
	            <table class="table table-striped table-hover table-bordered">
	              <thead>
                <c:if test="${marketing.codexType==15}">
                    <tr>
                        <!--  <th width="80">商品图片</th> -->
                        <th width="150">商品编号</th>
                        <th>商品名称</th>
                        <th>折扣</th>
                    </tr>
                </c:if>
                <c:if test="${marketing.codexType!=15}">
                <tr>

	               <!--  <th width="80">商品图片</th> -->
	                <th width="150">商品编号</th>
	                <th>商品名称</th>
	              </tr>
                </c:if>
	              </thead>
	              <tbody>
                <c:if test="${marketing.codexType!=15}">
	              	<c:forEach items="${kulist}" var="sku" varStatus="i">
			              <tr>
			              <!--   <td><img height="50" alt="" src="images/good_1_small.jpg"></td> -->
			                <td>${sku.goodsInfoItemNo }</td>
			               	<td>${sku.goodsInfoName }</td>

			              </tr>
	              	</c:forEach>
                </c:if>
                    <c:if test="${marketing.codexType==15}">
                        <c:forEach items="${marketing.preDiscountMarketings }" var="sku">
                            <tr>
                              <td>${sku.goodsProduct.goodsInfoItemNo}</td>
                              <td>${sku.goodsProduct.goodsInfoName}</td>
                              <td>${sku.discountInfo}</td>
                            </tr>
                        </c:forEach>
                    </c:if>



	              </tbody>
	            </table>
	            </div>
	          </c:if>
            <c:if test="${brandlist!=null}">
	            <table class="table table-striped table-hover table-bordered">
	              <thead>
	              <tr>
	               	<th>序号</th>
					<th>品牌编号</th>
					<th>品牌名称</th> 
	              </tr>
	              </thead>
	              <tbody>
	              	<c:forEach items="${brandlist}" var="brand" varStatus="i">
			              <tr>
			            		<td>${i.count}</td> 
					     		<td>${brand.brandId }</td>
					     		<td>${brand.brandName }</td>
			              </tr>
	              	</c:forEach>
	              </tbody>
	            </table>
	          </c:if>
            <c:if test="${ catelist!=null}">
	            <table class="table table-striped table-hover table-bordered">
	              <thead>
	              <tr>
	              	<th>序号</th>
					<th>分类编号</th>
					<th>分类名称</th> 
	              </tr>
	              </thead>
	              <tbody>
	              	<c:forEach items="${catelist}" var="cate" varStatus="i">
			              <tr>
			            		<td>${i.count}</td> 
					     		<td>${cate.catId }</td>
					     		<td>${cate.catName }</td>
			              </tr>
	              	</c:forEach>
	              </tbody>
	            </table>
	          </c:if>
           <h4>促销描述：</h4>
           <p>${marketing.marketingDes }</p>
          <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<%=basePath %>js/bootstrap.min.js"></script>
    <script src="<%=basePath %>js/summernote.min.js"></script>
    <script src="<%=basePath %>js/language/summernote-zh-CN.js"></script>
    <script src="<%=basePath %>js/bootstrap-select.min.js"></script>
    <script src="<%=basePath %>js/bootstrap-datetimepicker.min.js"></script>
    <script src="<%=basePath %>js/language/bootstrap-datetimepicker.zh-CN.js"></script>
  
  </body>
</html>
          