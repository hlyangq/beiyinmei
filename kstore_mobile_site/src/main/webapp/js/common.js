/**
 * Created by dyt on 2016/10/26.
 * 公共包
 */
//替换所有内容
function replaceAll(str,propertyName,value){
    return str.replace(new RegExp(propertyName,"g"),value);
}

/**
 * HTML内容替换
 * @param html 源HTML代码
 * @param obj 数据
 * @returns 新html
 */
function templ2html(html,obj){
    var t_html = html;
    if(obj!=null){
        for(var key in obj){
            t_html = replaceAll(t_html,'#'+key+'#',obj[key]);
        }
    }

    //将其他没替换的都设为空
    t_html = replaceAll(t_html,'#(\\w)*#','');
    return t_html;
}