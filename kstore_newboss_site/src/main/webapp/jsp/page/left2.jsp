<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>




<div class="sub_menu">
	<div class="sub_menu_in">


		<c:forEach items="${menus}" var="nav">
			<c:if test="${nav.parentId ==null&&nav.menuVos!=null}">
				<c:forEach items="${nav.menuVos}" var="navs">
					<c:if test="${navs.menuVos!=null }">
						<c:if test="${navs.id==menuId}">
							<dl>
								<c:forEach items="${navs.menuVos}" var="navss">
									<dt>
										<img alt="" data-pid="${navss.id}" src="<c:if test="${navss.id==menuParentId}">${navss.imgUrlSelected }</c:if><c:if test="${navss.id!=menuParentId}">${navss.imgUrl }</c:if>" width="18" height="18">
										<span>${navss.designation }</span>
									</dt>

									<dd <c:if test="${navss.id==menuParentId}">style="display:block;"</c:if><c:if test="${navss.id!=menuParentId}">style="display:none;"</c:if>>
										<ul>
											<c:if test="${navss.menuVos!=null }">
												<c:forEach items="${navss.menuVos}" var="navsss">
													<a <c:if test="${navsss.id==myselfId}"> class="selected"</c:if> href="${navsss.url }?menuId=${navs.id}&menuParentId=${navss.id}&myselfId=${navsss.id}">${navsss.designation }</a>
												</c:forEach>
											</c:if>
										</ul>
									</dd>
								</c:forEach>
							</dl>
						</c:if>
					</c:if>
				</c:forEach>
			</c:if>
		</c:forEach>

	</div>
</div><script type="text/javascript" src="<%=basePath%>js/sidebar-roll.js"></script>