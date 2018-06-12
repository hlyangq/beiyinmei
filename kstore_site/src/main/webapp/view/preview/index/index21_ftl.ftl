 <#assign basePath=request.contextPath>
 <input type="hidden" id="isIndex" value="1"/>
 <div id="container" style="width: 1200px; margin: 0 auto;">
    <div class="part01 row">
        <div class="roll_ad pull-left">
            <div id="slides">
                <#if avc?? && (avc?size>0)>
                    <#list avc as bigAdvert>
                        <a target="_blank" href="${bigAdvert.adverHref}" ><img src="${bigAdvert.adverPath}" alt=""></a>
                    </#list>
                </#if>
            </div>
        </div>
        <div class="top_column pull-right">
            <div class="news" style=" height:170px;">
                <h4>${temp.expFleid2!'公告'}
                <a href="information/list/" target="_blank">更多&gt;&gt;</a></h4>
                <ul>
                <#if infoList?? && (infoList?size>0)>
                    <#list infoList as info>
                        <#if (info_index >= 0 ) && (info_index < 5 ) >
                            <li><a href="information/${info.infoId?c}" target="_blank"><strong>【${temp.expFleid2!'公告'}】</strong>
                                <#if info.title?length gt 15>
                                ${info.title?substring(0,15)}
                                <#else>
                                ${info.title}
                                </#if>
                            </a>
                            </li>
                        </#if>
                    </#list>
                </#if>
                </ul>
            </div><!--news-->

            <!--后台暂无功能写死-->
            <#if onepages?? >
                <div class="life_service">
                    <h4>热销商品</h4>
                    <div class="body">
                        <div id="lifeService">

                            <#list onepages as op>
                                <#if (op_index>=0) && (op_index<6)>
                                    <a href="${basePath}/onepage/${op.infoOPId?c}.html" target="_blank">
                                        <img src="${op.imgSrc!''}" alt="">
                                        <span>${op.title!''}</span>
                                    </a>
                                </#if>
                            </#list>

                        </div>
                    </div>
                </div>
            </#if>
            <div class="ads">
                <div class="imgad">
                <#if pageAdvs?? && (pageAdvs?size>0)>
                    <#list pageAdvs as pageAdv>
                        <#if (pageAdv.adverSort==2) >
                            <a href="${pageAdv.adverHref}" target="_blank"><img class="lazy" alt="" data-original="${pageAdv.adverPath}"/></a>
                        </#if>
                    </#list>
                </#if>
                </div>
            </div>
        </div>
    </div><!--part01 row-->

     <div class="part02">
         <div class="rec_imgs">
             <a href="javascript:;" class="prev">&lt;</a>
             <a href="javascript:;" class="next">&gt;</a>

             <div class="roll_imgs">
                 <ul>
                 <#if avs?? && (avs?size>0)>
                     <#list avs as smallAdvert>
                         <li><a href="${smallAdvert.adverHref!''}" target="_blank"><img  alt="" src="${smallAdvert.adverPath}" style="width:290px; height:184px;"/></a></li>
                     </#list>
                 </#if>
                 </ul>
             </div>
         </div>
     </div>

     <div class="you_like">
         <div class="likes">
             <h4>猜你喜欢<a href="javascript:;" id="replace_btn"><i class="iconfont icon-refresh"></i>换一批</a> </h4>
             <div class="cont"></div>
         </div>
     </div>


     <div class="special_buy">
         <h2>品质生活</h2>
         <div class="body">
             <div class="col">
                 <#if pageAdvs?? && (pageAdvs?size>0)>
                     <#list pageAdvs as pageAdv>
                         <#if (pageAdv.adverSort==4) >
                             <a href="${pageAdv.adverHref}" class="img" target="_blank">
                                 <img alt="" src="${pageAdv.adverPath}">
                             </a>
                         </#if>
                     </#list>
                 </#if>
             </div>
             <div class="col">
                 <#if pageAdvs?? && (pageAdvs?size>0)>
                     <#list pageAdvs as pageAdv>
                         <#if (pageAdv.adverSort==5) >
                             <a href="${pageAdv.adverHref}" class="img_word up" target="_blank">

                                 <img alt="" src="${pageAdv.adverPath}">
                             </a>
                         </#if>
                     </#list>
                 </#if>
                 <#if pageAdvs?? && (pageAdvs?size>0)>
                     <#list pageAdvs as pageAdv>
                         <#if (pageAdv.adverSort==6) >
                             <a href="${pageAdv.adverHref}" class="img_word down" target="_blank">

                                 <img alt="" src="${pageAdv.adverPath}">
                             </a>
                         </#if>
                     </#list>
                 </#if>
             </div>
             <div class="col">
                 <#if pageAdvs?? && (pageAdvs?size>0)>
                     <#list pageAdvs as pageAdv>
                         <#if (pageAdv.adverSort==7) >
                             <a href="${pageAdv.adverHref}" class="img_word up" target="_blank">

                                 <img alt="" src="${pageAdv.adverPath}">
                             </a>
                         </#if>
                     </#list>
                 </#if>
                 <#if pageAdvs?? && (pageAdvs?size>0)>
                     <#list pageAdvs as pageAdv>
                         <#if (pageAdv.adverSort==8) >
                             <a href="${pageAdv.adverHref}" class="img_word down" target="_blank">

                                 <img alt="" src="${pageAdv.adverPath}">
                             </a>
                         </#if>
                     </#list>
                 </#if>
             </div>
             <div class="col">
                 <#if pageAdvs?? && (pageAdvs?size>0)>
                     <#list pageAdvs as pageAdv>
                         <#if (pageAdv.adverSort==9) >
                             <a href="${pageAdv.adverHref}" class="img" target="_blank">
                                 <img alt="" src="${pageAdv.adverPath}">
                             </a>
                         </#if>
                     </#list>
                 </#if>
             </div>
             <div class="col">
                 <#if pageAdvs?? && (pageAdvs?size>0)>
                     <#list pageAdvs as pageAdv>
                         <#if (pageAdv.adverSort==10) >
                             <a href="${pageAdv.adverHref}" class="img_word up" target="_blank">

                                 <img alt="" src="${pageAdv.adverPath}">
                             </a>
                         </#if>
                     </#list>
                 </#if>
                 <#if pageAdvs?? && (pageAdvs?size>0)>
                     <#list pageAdvs as pageAdv>
                         <#if (pageAdv.adverSort==11) >
                             <a href="${pageAdv.adverHref}" class="img_word down" target="_blank">

                                 <img alt="" src="${pageAdv.adverPath}">
                             </a>
                         </#if>
                     </#list>
                 </#if>
             </div>
         </div>
     </div>

     <div class="banner_ad">
         <#if pageAdvs?? && (pageAdvs?size>0)>
             <#list pageAdvs as pageAdv>
                 <#if (pageAdv.adverSort==12) >
                     <a href="${pageAdv.adverHref}" target="_blank">
                         <img alt="" src="${pageAdv.adverPath}">
                     </a>
                 </#if>
             </#list>
         </#if>
     </div>


<#if (floor.floorList)?? && (floor.floorList?size > 0)>
        <div class="section-02">
     <#list floor.floorList as floors> 
       <#if (floors.floorId%4==1)>
           <div class="floor_adv fuzhuangxiebao row" id="floor_${floors.floorId}">
               <div class="title">
                   <h2>
                       <span>${floors.floorId}F</span>
                   ${floors.storeyName}
                   </h2>
               </div>
               <div class="body row">
                   <div class="floor_ad pull-left">
                       <div class="imgad">
                           <#if (floors.indexAdvertList)?? && (floors.indexAdvertList?size > 0)>
                               <#list floors.indexAdvertList as adv>
                                   <#if adv.adverSort==1>
                                       <a href="${adv.adverHref}" target="_blank"><img class="lazy" alt="" src="${adv.adverPath}"/></a>
                                   </#if>
                               </#list>
                           </#if>
                       </div>
                       <ul class="themes">
                           <li>
                           </li>
                       </ul>
                       <ul class="words">
                           <li>
                           <#list floors.indexCateList as cates>
                               <a href="list/${cates.id?c}-${floors.goodsCatId?c}.html" target="_blank">${cates.name}</a>
                           </#list>
                           </li>
                       </ul>
                       <div class="ex_ad">
                           <#if (floors.indexAdvertList)?? && (floors.indexAdvertList?size > 0)>
                               <#list floors.indexAdvertList as adv>
                                   <#if adv.adverSort==2>
                                       <a href="${adv.adverHref}" target="_blank"><img class="lazy" alt="" src="${adv.adverPath}"/></a>
                                   </#if>
                               </#list>
                           </#if>
                       </div>
                   </div>
                   <div class="floor_goods pull-right">
                       <#if (floors.indexStoreyTagList)?? && (floors.indexStoreyTagList?size > 0)>
                           <ul class="tabs">
                               <#list floors.indexStoreyTagList as storeyTag>
                                   <#if (storeyTag.sort==1)>
                                       <li class="current"><a href="javascript:;" name=".current_${storeyTag.sort}">${storeyTag.name}</a></li>
                                   <#else>
                                       <li><a href="javascript:;" name=".current_${storeyTag.sort}">${storeyTag.name}</a></li>
                                   </#if>
                               </#list>
                           </ul>
                       </#if>
                       <div class="tab_content">
                           <#list floors.indexStoreyTagList as storeyTag>
                               <#if (storeyTag.sort==1)>
                                    <div name="current_${storeyTag.sort}" class="remen current_${storeyTag.sort}">
                                        <div class="col1">
                                            <div class="img_item">
                                                <#if (storeyTag.indexAdvertList)?? && (storeyTag.indexAdvertList?size>0)>
                                                    <#list storeyTag.indexAdvertList as adv>
                                                        <#if adv.adverSort=5>
                                                            <a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a>
                                                        </#if>
                                                    </#list>
                                                </#if>
                                            </div>
                                            <div class="floor_slide" id="floorSlide0${floors.floorId}" style="width:430px;height:315px;">
                                                <#if (storeyTag.indexAdvertList)?? && (storeyTag.indexAdvertList?size>0)>
                                                    <#list storeyTag.indexAdvertList as adv>
                                                        <#if adv.adverSort<=4>
                                                            <div><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></div>
                                                        </#if>
                                                    </#list>
                                                </#if>
                                            </div>
                                            <div class="img_item">
                                                <#if (storeyTag.indexAdvertList)?? && (storeyTag.indexAdvertList?size>0)>
                                                    <#list storeyTag.indexAdvertList as adv>
                                                        <#if adv.adverSort=6>
                                                            <a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a>
                                                        </#if>
                                                    </#list>
                                                </#if>
                                            </div>
                                        </div>

                                        <div class="col2">
                                            <#if (storeyTag.indexAdvertList)?? && (storeyTag.indexAdvertList?size>0)>
                                                <#list storeyTag.indexAdvertList as adv>
                                                    <#if adv.adverSort=7>
                                                    <div class="img_item"><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></div>
                                                    </#if>
                                                    <#if adv.adverSort=8>
                                                        <div class="img_item"><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></div>
                                                    </#if>
                                                    <#if adv.adverSort=9>
                                                        <div class="img_item"><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></div>
                                                    </#if>
                                                    <#if adv.adverSort=10>
                                                        <div class="img_item"><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></div>
                                                    </#if>
                                                </#list>
                                            </#if>
                                        </div>
                                        <div class="col3">
                                            <#if (storeyTag.indexAdvertList)?? && (storeyTag.indexAdvertList?size>0)>
                                                <#list storeyTag.indexAdvertList as adv>
                                                    <#if adv.adverSort=11>
                                                        <div class="img_item"><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></div>
                                                    </#if>
                                                    <#if adv.adverSort=12>
                                                        <div class="img_item"><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></div>
                                                    </#if>
                                                    <#if adv.adverSort=13>
                                                        <div class="img_item"><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></div>
                                                    </#if>
                                                </#list>
                                            </#if>
                                        </div>
                                    </div>
                               </#if>
                           </#list>
                           <#list floors.indexStoreyTagList as storeyTag>
                               <#if (storeyTag.sort>=2)>
                                   <div name="current_${storeyTag.sort}" class="current_${storeyTag.sort}">
                                       <div class="goods">
                                           <ul>
                                               <#if (storeyTag.indexGoodsList)?? && (storeyTag.indexGoodsList?size>0)>
                                                   <#list storeyTag.indexGoodsList as goods>
                                                       <#if (goods_index>=0) && (goods_index<8)>
                                                           <li>
                                                               <div class="good">
                                                                   <div class="img">
                                                                       <a href="item/${goods.id}" title="${goods.name}">
                                                                           <img alt="${goods.name}" src="${goods.urlpic}">
                                                                       </a>
                                                                   </div>
                                                                   <div class="name">
                                                                       <a href="item/${goods.id}" title="${goods.name}">
                                                                           <#if (goods.name?length>30)>
                                                                            ${goods.name?substring(0,30)}
                                                                            <#else>
                                                                           ${goods.name}
                                                                           </#if>
                                                                       </a>
                                                                   </div>
                                                                   <div class="price">
                                                                       <span>￥<strong>${goods.price}</strong></span>
                                                                   </div>
                                                               </div>
                                                           </li>
                                                       </#if>
                                                   </#list>
                                               </#if>
                                           </ul>
                                           <div class="cb"></div>
                                           <div class="ex_imgs">
                                               <#if (storeyTag.indexAdvertList)?? && (storeyTag.indexAdvertList?size>0)>
                                                   <#list storeyTag.indexAdvertList as adv>
                                                       <#if adv.adverSort<=4>
                                                           <a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a>
                                                       </#if>
                                                   </#list>
                                               </#if>
                                           </div>
                                       </div>
                                   </div>
                               </#if>
                           </#list>
                       </div>
                   </div>
               </div>
               <div class="brands">
                   <ul>
                       <#if (floors.indexBrandList)?? && (floors.indexBrandList?size>0) >
                           <#list floors.indexBrandList as brand>
                               <#if (brand.sort>=1) &&(brand.sort<=10) >
                                   <li><a href="${(brand.temp5)!''}" target="_blank"><img class="lazy" src="${brand.logoSrc}" alt=""/></a></li>
                               </#if>
                           </#list>
                       </#if>
                   </ul>
               </div>
           </div>

        </#if>
<#if (floors.floorId%4==2)>
    <div class="floor_adv gehuhuanzhuang row" id="floor_${floors.floorId}">
        <div class="title">
            <h2>
                <span>${floors.floorId}F</span>
                ${floors.storeyName}
            </h2>
        </div>
        <div class="body row">
            <div class="floor_ad narrow pull-left">
                <div class="imgad">
                    <#if (floors.indexAdvertList)?? && (floors.indexAdvertList?size > 0)>
                        <#list floors.indexAdvertList as adv>
                            <#if adv.adverSort==1>
                                <a href="${adv.adverHref}" target="_blank"><img class="lazy" alt="" src="${adv.adverPath}"/></a>
                            </#if>
                        </#list>
                    </#if>
                </div>
                <ul class="themes">
                    <li>
                    </li>
                </ul>
                <ul class="subcates">
                    <li>
                    </li>
                </ul>

                <ul class="words">
                    <li>
                        <#list floors.indexCateList as cates>
                            <a href="list/${cates.id?c}-${floors.goodsCatId?c}.html" target="_blank">${cates.name}</a>
                        </#list>
                    </li>
                </ul>
            </div>
            <div class="floor_goods pull-right">
                <#if (floors.indexStoreyTagList)?? && (floors.indexStoreyTagList?size > 0)>
                    <ul class="tabs">
                        <#list floors.indexStoreyTagList as storeyTag>
                            <#if (storeyTag.sort==1)>
                                <li class="current"><a href="javascript:;" name=".current_${storeyTag.sort}">${storeyTag.name}</a></li>
                            <#else>
                                <li><a href="javascript:;" name=".current_${storeyTag.sort}">${storeyTag.name}</a></li>
                            </#if>
                        </#list>
                    </ul>
                </#if>
                <div class="tab_content">
                    <#list floors.indexStoreyTagList as storeyTag>
                        <#if (storeyTag.sort==1)>
                            <div name="current_${storeyTag.sort}" class="remen current_${storeyTag.sort}">
                                <div class="floor_slide" id="floorSlide0${floors.floorId}" style="width:329px;height:474px;">
                                    <#if (storeyTag.indexAdvertList)?? && (storeyTag.indexAdvertList?size>0)>
                                        <#list storeyTag.indexAdvertList as adv>
                                            <#if adv.adverSort<=4>
                                                <div><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></div>
                                            </#if>
                                        </#list>
                                    </#if>
                                </div>
                                <div class="main_ads">
                                    <#if (storeyTag.indexAdvertList)?? && (storeyTag.indexAdvertList?size>0)>
                                        <ul>
                                            <#list storeyTag.indexAdvertList as adv>
                                                <#if adv.adverSort=5>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                                <#if adv.adverSort=6>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                                <#if adv.adverSort=7>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                                <#if adv.adverSort=8>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                                <#if adv.adverSort=9>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                                <#if adv.adverSort=10>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                            </#list>
                                        </ul>
                                    </#if>
                                </div>
                            </div>
                        </#if>
                    </#list>

                    <#list floors.indexStoreyTagList as storeyTag>
                        <#if (storeyTag.sort>=2)>
                            <div name="current_${storeyTag.sort}" class="guojidapai current_${storeyTag.sort}">
                                <div class="goods">
                                    <ul>
                                        <#if (storeyTag.indexGoodsList)?? && (storeyTag.indexGoodsList?size>0)>
                                            <#list storeyTag.indexGoodsList as goods>
                                                <#if (goods_index>=0) && (goods_index<10)>
                                                    <li>
                                                        <div class="good">
                                                            <div class="img">
                                                                <a href="item/${goods.id}" title="${goods.name}">
                                                                    <img alt="${goods.name}" src="${goods.urlpic}">
                                                                </a>
                                                            </div>
                                                            <div class="name">
                                                                <a href="item/${goods.id}" title="${goods.name}">
                                                                    <#if (goods.name?length>30)>
                                                                        ${goods.name?substring(0,30)}
                                                                        <#else>
                                                                    ${goods.name}
                                                                    </#if>
                                                                </a>
                                                            </div>
                                                            <div class="price">
                                                                <span>￥<strong>${goods.price}</strong></span>
                                                            </div>
                                                        </div>
                                                    </li>
                                                </#if>
                                            </#list>
                                        </#if>
                                    </ul>
                                </div>
                            </div>
                        </#if>
                    </#list>
                </div>
            </div>
        </div>

        <div class="brands">
            <ul>
                <#if (floors.indexBrandList)?? && (floors.indexBrandList?size>0) >
                    <#list floors.indexBrandList as brand>
                        <#if (brand.sort>=1) &&(brand.sort<=10) >
                            <li><a href="${(brand.temp5)!''}" target="_blank"><img class="lazy" data-original="${brand.logoSrc}" alt=""/></a></li>
                        </#if>
                    </#list>
                </#if>
            </ul>
        </div>
    </div>
</#if>
<#if (floors.floorId%4==3)>
<div class="floor_adv shoujitongxun row" id="floor_${floors.floorId}">
    <div class="title">
        <h2>
            <span>${floors.floorId}F</span>
        ${floors.storeyName}
        </h2>
    </div>
    <div class="body row">
        <div class="floor_ad pull-left">
            <div class="imgad">
                <#if (floors.indexAdvertList)?? && (floors.indexAdvertList?size > 0)>
                    <#list floors.indexAdvertList as adv>
                        <#if adv.adverSort==1>
                            <a href="${adv.adverHref}" target="_blank"><img class="lazy" alt="" src="${adv.adverPath}"/></a>
                        </#if>
                    </#list>
                </#if>
            </div>
            <ul class="themes">
                <li>
                </li>
            </ul>
            <ul class="subcates">
                <li>
                </li>
            </ul>
            <ul class="words">
                <li>
                    <#list floors.indexCateList as cates>
                        <a href="list/${cates.id?c}-${floors.goodsCatId?c}.html" target="_blank">${cates.name}</a>
                    </#list>
                </li>
            </ul>
        </div>
        <div class="floor_goods pull-right">
            <#if (floors.indexStoreyTagList)?? && (floors.indexStoreyTagList?size > 0)>
                <ul class="tabs">
                    <#list floors.indexStoreyTagList as storeyTag>
                        <#if (storeyTag.sort==1)>
                            <li class="current"><a href="javascript:;" name=".current_${storeyTag.sort}">${storeyTag.name}</a></li>
                        <#else>
                            <li><a href="javascript:;" name=".current_${storeyTag.sort}">${storeyTag.name}</a></li>
                        </#if>
                    </#list>
                </ul>
            </#if>
            <div class="tab_content">
                <#list floors.indexStoreyTagList as storeyTag>
                    <#if (storeyTag.sort==1)>
                        <div name="current_${storeyTag.sort}" class="remen current_${storeyTag.sort}">
                            <div class="col1">
                                <div class="floor_slide" id="floorSlide0${floors.floorId}" style="width:429px;height:235px;">
                                    <#if (storeyTag.indexAdvertList)?? && (storeyTag.indexAdvertList?size>0)>
                                        <#list storeyTag.indexAdvertList as adv>
                                            <#if adv.adverSort<=4>
                                                <div><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></div>
                                            </#if>
                                        </#list>
                                    </#if>
                                </div>
                                <div class="main_ads">
                                    <#if (storeyTag.indexAdvertList)?? && (storeyTag.indexAdvertList?size>0)>
                                        <ul>
                                            <#list storeyTag.indexAdvertList as adv>
                                                <#if adv.adverSort=5>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                                <#if adv.adverSort=6>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                            </#list>
                                        </ul>
                                    </#if>
                                </div>
                            </div>
                            <div class="col2">
                                <div class="main_ads">
                                    <#if (storeyTag.indexAdvertList)?? && (storeyTag.indexAdvertList?size>0)>
                                        <ul>
                                            <#list storeyTag.indexAdvertList as adv>
                                                <#if adv.adverSort=7>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                                <#if adv.adverSort=8>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                                <#if adv.adverSort=9>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                                <#if adv.adverSort=10>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                                <#if adv.adverSort=11>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                                <#if adv.adverSort=12>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                            </#list>
                                        </ul>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </#if>
                </#list>
                <#list floors.indexStoreyTagList as storeyTag>
                    <#if (storeyTag.sort>=2)>
                        <div name="current_${storeyTag.sort}" class="pinzhiyouxuan current_${storeyTag.sort}">
                            <div class="goods">
                                <ul>
                                    <#if (storeyTag.indexGoodsList)?? && (storeyTag.indexGoodsList?size>0)>
                                        <#list storeyTag.indexGoodsList as goods>
                                            <#if (goods_index>=0) && (goods_index<8)>
                                                <li>
                                                    <div class="good">
                                                        <div class="img">
                                                            <a href="item/${goods.id}" title="${goods.name}">
                                                                <img alt="${goods.name}" src="${goods.urlpic}">
                                                            </a>
                                                        </div>
                                                        <div class="name">
                                                            <a href="item/${goods.id}" title="${goods.name}">
                                                                <#if (goods.name?length>30)>
                                                                                    ${goods.name?substring(0,30)}
                                                                                    <#else>
                                                                ${goods.name}
                                                                </#if>
                                                            </a>
                                                        </div>
                                                        <div class="price">
                                                            <span>￥<strong>${goods.price}</strong></span>
                                                        </div>
                                                    </div>
                                                </li>
                                            </#if>
                                        </#list>
                                    </#if>
                                </ul>
                            </div>
                        </div>
                    </#if>
                </#list>
            </div>
        </div>
    </div>

    <div class="brands">
        <ul>
            <#if (floors.indexBrandList)?? && (floors.indexBrandList?size>0) >
                <#list floors.indexBrandList as brand>
                    <#if (brand.sort>=1) &&(brand.sort<=10) >
                        <li><a href="${(brand.temp5)!''}" target="_blank"><img class="lazy" data-original="${brand.logoSrc}" alt=""/></a></li>
                    </#if>
                </#list>
            </#if>
        </ul>
    </div>
</div>
</#if>
<#if (floors.floorId%4==0)>
    <div class="floor_adv yundongjianshen row" id="floor_4">
        <div class="title">
            <h2>
                <span>${floors.floorId}F</span>
            ${floors.storeyName}
            </h2>
        </div>
        <div class="body row">
            <div class="floor_ad narrow pull-left">
                <div class="imgad">
                    <#if (floors.indexAdvertList)?? && (floors.indexAdvertList?size > 0)>
                        <#list floors.indexAdvertList as adv>
                            <#if adv.adverSort==1>
                                <a href="${adv.adverHref}" target="_blank"><img class="lazy" alt="" src="${adv.adverPath}"/></a>
                            </#if>
                        </#list>
                    </#if>
                </div>
                <ul class="themes">
                    <li>
                    </li>
                </ul>
                <ul class="subcates">
                    <li>
                    </li>
                </ul>
                <ul class="words">
                    <li>
                        <#list floors.indexCateList as cates>
                            <a href="list/${cates.id?c}-${floors.goodsCatId?c}.html" target="_blank">${cates.name}</a>
                        </#list>
                    </li>
                </ul>
            </div>

            <div class="floor_goods pull-right">
                <ul class="tabs">
                    <#list floors.indexStoreyTagList as storeyTag>
                        <#if (storeyTag.sort==1)>
                            <li class="current"><a href="javascript:;" name=".current_${storeyTag.sort}">${storeyTag.name}</a></li>
                        <#else>
                            <li><a href="javascript:;" name=".current_${storeyTag.sort}">${storeyTag.name}</a></li>
                        </#if>
                    </#list>
                </ul>
                <div class="tab_content">
                    <#list floors.indexStoreyTagList as storeyTag>
                        <#if (storeyTag.sort==1)>
                            <div name="current_${storeyTag.sort}" class="remen current_${storeyTag.sort}">
                                <div class="main_ads" style="width:220px;">
                                    <#if (storeyTag.indexAdvertList)?? && (storeyTag.indexAdvertList?size>0)>
                                        <ul>
                                            <#list storeyTag.indexAdvertList as adv>
                                                <#if adv.adverSort=5>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                                <#if adv.adverSort=6>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                            </#list>
                                        </ul>
                                    </#if>
                                </div>
                                <div class="floor_slide" id="floorSlide0${floors.floorId}" style="width:328px;height:474px;">
                                    <#if (storeyTag.indexAdvertList)?? && (storeyTag.indexAdvertList?size>0)>
                                        <#list storeyTag.indexAdvertList as adv>
                                            <#if adv.adverSort<=4>
                                                <div><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></div>
                                            </#if>
                                        </#list>
                                    </#if>
                                </div>
                                <div class="main_ads">
                                    <#if (storeyTag.indexAdvertList)?? && (storeyTag.indexAdvertList?size>0)>
                                        <ul>
                                            <#list storeyTag.indexAdvertList as adv>
                                                <#if adv.adverSort=7>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                                <#if adv.adverSort=8>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                                <#if adv.adverSort=9>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                                <#if adv.adverSort=10>
                                                    <li><a href="${adv.adverHref}" target="_blank"><img  alt="" src="${adv.adverPath}" /></a></li>
                                                </#if>
                                            </#list>
                                        </ul>
                                    </#if>
                                </div>
                            </div>
                        </#if>
                    </#list>

                    <#list floors.indexStoreyTagList as storeyTag>
                        <#if (storeyTag.sort>=2)>
                            <div name="current_${storeyTag.sort}" class="dapaisudi current_${storeyTag.sort}">
                                <div class="goods">
                                    <ul>
                                        <#if (storeyTag.indexGoodsList)?? && (storeyTag.indexGoodsList?size>0)>
                                            <#list storeyTag.indexGoodsList as goods>
                                                <#if (goods_index>=0) && (goods_index<10)>
                                                    <li>
                                                        <div class="good">
                                                            <div class="img">
                                                                <a href="item/${goods.id}" title="${goods.name}">
                                                                    <img alt="${goods.name}" src="${goods.urlpic}">
                                                                </a>
                                                            </div>
                                                            <div class="name">
                                                                <a href="item/${goods.id}" title="${goods.name}">
                                                                    <#if (goods.name?length>30)>
                                                                                    ${goods.name?substring(0,30)}
                                                                                    <#else>
                                                                    ${goods.name}
                                                                    </#if>
                                                                </a>
                                                            </div>
                                                            <div class="price">
                                                                <span>￥<strong>${goods.price}</strong></span>
                                                            </div>
                                                        </div>
                                                    </li>
                                                </#if>
                                            </#list>
                                        </#if>
                                    </ul>
                                </div>
                            </div>
                        </#if>
                    </#list>
                </div>
            </div>
        </div>

        <div class="brands">
            <ul>
                <#if (floors.indexBrandList)?? && (floors.indexBrandList?size>0) >
                    <#list floors.indexBrandList as brand>
                        <#if (brand.sort>=1) &&(brand.sort<=10) >
                            <li><a href="${(brand.temp5)!''}" target="_blank"><img class="lazy" data-original="${brand.logoSrc}" alt=""/></a></li>
                        </#if>
                    </#list>
                </#if>
            </ul>
        </div>
    </div>
</#if>
 </#list>
        </div><!--section-02-->
 
</#if>

     <div id="elevator" class="elevator">
     <#if (floor.floorList)?? && (floor.floorList?size > 0)>
         <ul>
             <#list floor.floorList as floors>
                 <li data-index="${floors.floorId}">
                     <a href="javascript:;">${floors.floorId}F</a>
                     <a href="javascript:;" class="etitle">
                         <#if floors.storeyName ??>
                            <#if floors.storeyName?length lt 2>
                                 ${floors.storeyName}
                            <#else>
                                ${floors.storeyName[0..1]}
                            </#if>
                         </#if>
                     </a>
                 </li>
             </#list>
         </ul>
     </#if>
     </div>

     <div class="toolbar">
         <div class="toolbar_tabs">
             <div class="item">
                 <a href="${basePath}/customer/index.html">
                     <span>会员中心</span>
                     <i class="iconfont icon-wode"></i>
                 </a>
             </div>
             <div class="item">
                 <a href="${basePath}/myshoppingcart.html">
                     <span>购物车</span>
                     <i class="iconfont icon-cart"></i>
                 </a>
             </div>
             <div class="item">
                 <a href="${basePath}/customer/myfollw.html">
                     <span>我的收藏</span>
                     <i class="iconfont icon-heart"></i>
                 </a>
             </div>
             <div class="item">
                 <a href="${basePath}/customer/insideletter.html">
                     <span>消息中心</span>
                     <i class="iconfont icon-xiaoxi"></i>
                 </a>
             </div>
             <div class="item customer_service">
                 <a href="javascript:;">
                     <span>在线客服</span>
                     <i class="iconfont icon-kefu"></i>
                 </a>
             </div>
         </div>
         <div class="toolbar_bottom">
             <div class="item">
                 <a href="javascript:;" onclick="$('html,body').animate({scrollTop:0},500)">
                     <span>回到顶部</span>
                     <i class="iconfont icon-top"></i>
                 </a>
             </div>
         </div>

         <div class="customer-box " >
             <span class="title">${customerService.title}</span>
             <hr>
             <a href="javascript:;" class="close-cs"></a>
         <#if QQs??>
             <#list QQs as qq>
                 <p><span class="qq_name" style="line-height:23px;">${qq.name}</span><span class="qq_img"><a style="float:right;" target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${qq.contactNumber}&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:${qq.contactNumber}:51" alt="点击这里给我发消息" title="点击这里给我发消息"/></a></span></p>
             </#list>
         </#if>
         </div>
     </div>

<script id="likes" type="text/html">
    <ul class="like-pros">
        {{each likes as value i}}
        <li>
            <div class="good">
                <div class="img">
                    <a href="{{value.proHref}}" target="_blank">
                        <img alt="" src="{{value.proImg}}">
                    </a>
                </div>
                <div class="name">
                    <a href="{{value.proHref}}" target="_blank">
                        {{value.proName}}
                    </a>
                </div>
                <div class="price">
                    <span>￥<strong>{{value.proPrice}}</strong></span>
                </div>
            </div>
        </li>
        {{/each}}
    </ul>
</script>
<script>
    var data = {
        likes: [
        <#if hotSale?? && (hotSale?size>0)>
          <#list hotSale as hotgoods>
            {"proHref":"item/${(hotgoods.goodsproductId?c)!''}",
            "proImg":"${(hotgoods.goodsproductImgsrc)!''}",
	             "proName": "${(hotgoods.goodsproductName)!''}",
             "proPrice":"${(hotgoods.goodsproductPrice?string("0.00"))!''}"}
             <#if hotgoods_has_next>,</#if>
          </#list>
        </#if>
        ]
    };
    var html = template('likes', data);
    $(".likes .cont").append(html);
</script>
<script src="${basePath}/index_twentyone/js/indexAdv.m.js"></script>
<script>
$(function() {
        	//图片延迟加载
			$("img.lazy").lazyload({
				threshold: 200,
				effect: "fadeIn",
				failurelimit : 10,
				placeholder: "${basePath}/index_seven/images/lazy_img.png",
				skip_invisible: false
			});

    /*
* 热销商品交互
* */
    $('#lifeService').slidesjs({
        width: 240,
        height: 160,
        navigation: {
            effect: "slide"
        },
        pagination: {
            effect: "slide"
        },
        effect: {
            fade: {
                speed: 400
            }
        },
        play:{
            interval:3000,
            effect:'slide',
            auto:true
        }
    });

    $('#floorSlide02').slidesjs({//楼层轮播广告-2楼
        width: 330,
        height: 474,
        navigation: {
            effect: "slide"
        },
        pagination: {
            effect: "slide"
        },
        effect: {
            fade: {
                speed: 400
            }
        },
        play:{
            interval:3000,
            effect:'slide',
            auto:true
        }
    });
    $('#floorSlide01').slidesjs({//楼层轮播广告-1楼
        width: 430,
        height: 315,
        navigation: {
            effect: "slide"
        },
        pagination: {
            effect: "slide"
        },
        effect: {
            fade: {
                speed: 400
            }
        },
        play:{
            interval:3000,
            effect:'slide',
            auto:true
        }
    });
    $('#floorSlide03').slidesjs({//楼层轮播广告-3楼
        width: 429,
        height: 235,
        navigation: {
            effect: "slide"
        },
        pagination: {
            effect: "slide"
        },
        effect: {
            fade: {
                speed: 400
            }
        },
        play:{
            interval:3000,
            effect:'slide',
            auto:true
        }
    });
    $('#floorSlide04').slidesjs({//楼层轮播广告-3楼
        width: 328,
        height: 474,
        navigation: {
            effect: "slide"
        },
        pagination: {
            effect: "slide"
        },
        effect: {
            fade: {
                speed: 400
            }
        },
        play:{
            interval:3000,
            effect:'slide',
            auto:true
        }
    });
    $('#floorSlide06').slidesjs({//楼层轮播广告-2楼
        width: 330,
        height: 474,
        navigation: {
            effect: "slide"
        },
        pagination: {
            effect: "slide"
        },
        effect: {
            fade: {
                speed: 400
            }
        },
        play:{
            interval:3000,
            effect:'slide',
            auto:true
        }
    });
    $('#floorSlide05').slidesjs({//楼层轮播广告-1楼
        width: 430,
        height: 315,
        navigation: {
            effect: "slide"
        },
        pagination: {
            effect: "slide"
        },
        effect: {
            fade: {
                speed: 400
            }
        },
        play:{
            interval:3000,
            effect:'slide',
            auto:true
        }
    });
    $('#floorSlide07').slidesjs({//楼层轮播广告-3楼
        width: 429,
        height: 235,
        navigation: {
            effect: "slide"
        },
        pagination: {
            effect: "slide"
        },
        effect: {
            fade: {
                speed: 400
            }
        },
        play:{
            interval:3000,
            effect:'slide',
            auto:true
        }
    });
    $('#floorSlide08').slidesjs({//楼层轮播广告-3楼
        width: 328,
        height: 474,
        navigation: {
            effect: "slide"
        },
        pagination: {
            effect: "slide"
        },
        effect: {
            fade: {
                speed: 400
            }
        },
        play:{
            interval:3000,
            effect:'slide',
            auto:true
        }
    });
});
</script>



      
