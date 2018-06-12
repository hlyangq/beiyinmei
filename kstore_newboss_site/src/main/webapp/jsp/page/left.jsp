<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<style>

    .sidebar{min-height:427px;}
</style>
        <div class="col-lg-4 col-md-5 col-sm-6 sidebar">

          <div class="menu">


           <c:forEach items="${menus}" var="nav">
	                	<!-- 判断菜单不是全部 全部为null -->
	        	<c:if test="${nav.parentId ==null&&nav.menuVos!=null}">
	        		 <c:forEach items="${nav.menuVos}" var="navs">
	        		 	<c:if test="${navs.menuVos!=null }">


	        		 	   <%--
	        		 		<c:if test="${navs.id==menuId}">
	        		 	
		        		 	 	<c:forEach items="${navs.menuVos}" var="navss">--%>
		        		 	 	  <div class="menu_item">
					              <div class="menu_title" nid="${navs.id}">
									  <c:if test="${navs.menuVos!=null }">
										  <c:forEach items="${navs.menuVos}" var="navss" varStatus="i">
											  <c:if test="${i.index==0 }">
												  <c:set var="tt" value=""></c:set>
												  <c:forEach  items="${navss.menuVos}" var="navsss" varStatus="j">
													  <c:if test="${j.index==0 }">
														  <c:set var="tt" value="${navsss.url }?menuId=${navs.id}&menuParentId=${navss.id}&myselfId=${navsss.id}"></c:set>
													  </c:if>
												  </c:forEach>
											  </c:if>
										  </c:forEach>
									  </c:if>
					                <h4><a <c:if test="${navs.id==menuId}">class="selected"</c:if> href="${tt}" data-pid="${navs.id}"><img src="<c:if test="${navs.id==menuParentId}"><c:if test="${navs.imgUrlSelected==''}"><%=basePath%>images/default.png</c:if><c:if test="${navs.imgUrlSelected!=''}">${navs.imgUrlSelected }</c:if></c:if><c:if test="${navs.id!=menuParentId}"><c:if test="${navs.imgUrl==''}"><%=basePath%>images/default.png</c:if><c:if test="${navs.imgUrl!=''}">${navs.imgUrl }</c:if></c:if>" width="18" height="18" style="margin-top:-3px;"/> ${navs.designation }</a></h4>
					              </div>

					            </div>
		        		 		<%--</c:forEach>
	        		 		</c:if>--%>
	        		 	</c:if>
				            
				       </c:forEach>
            	</c:if>
             </c:forEach>
             
          </div>
       
        </div>
<script type="text/javascript" src="<%=basePath%>js/sidebar-roll.js"></script>






<%--

<c:if test="${menus!=null }">
	<!-- 获取全部菜单 -->
	<c:forEach items="${menus}" var="nav">
		<!-- 判断菜单不是全部 全部为null -->
		<c:if test="${nav.parentId ==null&&nav.menuVos!=null}">
			<c:forEach  items="${nav.menuVos}" var="navs">
				<c:if test="${navs.parentId==0}">
					<c:set var="tt" value=""></c:set>
					<c:forEach  items="${navs.menuVos}" var="navss" varStatus="i">
						<c:if test="${i.index==0 }">
							<c:forEach  items="${navss.menuVos}" var="navsss" varStatus="j">
								<c:if test="${j.index==0 }">
									<c:set var="tt" value="${navsss.url }?menuId=${navs.id}&menuParentId=${navss.id}&myselfId=${navsss.id}"></c:set>
								</c:if>
							</c:forEach>
						</c:if>
					</c:forEach>

					<li class="<c:if test="${navs.id==menuId}">active</c:if>">
						<a  href="${tt }">${navs.designation }</a></li>
					<c:if test="${navs.menuVos!=null}">
						<div style="display:none;">
							<c:forEach  items="${navs.menuVos}" var="navss">
								<div class="popover-content">
									<div class="nav_sub">
										<h4>${navss.designation }</h4>
										<ul>
											<c:if test="${navss.menuVos!=null}">
												<c:forEach  items="${navss.menuVos}" var="navsss">
													<li><a href="${navsss.url }?menuId=${navs.id}&menuParentId=${navss.id}&myselfId=${navsss.id}">${navsss.designation }</a></li>
												</c:forEach>
											</c:if>
										</ul>

									</div>
								</div>
							</c:forEach>
						</div>
					</c:if>
				</c:if>
			</c:forEach>
			&lt;%&ndash;  <li class="active"><a href="javascript:;">${nav.designation }</a></li> &ndash;%&gt;
		</c:if>

	</c:forEach>
</c:if>--%>
