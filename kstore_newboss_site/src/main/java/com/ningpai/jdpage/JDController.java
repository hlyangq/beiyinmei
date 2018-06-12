package com.ningpai.jdpage;

import java.io.IOException;
import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ningpai.util.MyLogger;

/**
 * JD 抓取功能
 *
 * @author ggn
 */
@Controller
public class JDController {
    private static final MyLogger LOGGER = new MyLogger(JDController.class);


    private static final String ERROR = "没有获取到信息";

    /**
     * 抓取扩展参数
     *
     * @param httpUrl
     * @return List<OutKeys>
     */
    @RequestMapping("/jdcatelist")
    @ResponseBody
    public List<OutKeys> jdCateList(String httpUrl) {
        Document doc;
        try {
            // 获取页面所有信息
            doc = Jsoup.connect(httpUrl).get();
        } catch (IOException e) {
            LOGGER.error("" + ERROR, e);
            return Collections.emptyList();
        }
        // 获取div 含有data-id属性的Div信息
        Elements links = doc.select("div.J_selectorLine");
        // 申明List
        List<OutKeys> outKeys = new ArrayList<>();
        // 循环 Element
        for (Element e : links) {
            // 获取当前节点 div 有Class样式为 sl-key 下面的span
            Elements key = e.select("div.sl-key").select("span");
            // 获取第一个内容
            String outKey = key.get(0).text();
            // 判断是否是品牌
            if ("品牌：".equals(outKey)) {
                continue;
            }
            // 设置属性
            OutKeys ok = new OutKeys();
            // 替换
            ok.setOutKey(outKey.replace("：", ""));
            // 申明值List
            List<OutValues> outValues = new ArrayList<OutValues>();
            // 获取当前节点下 Class是sl-value的DIv 下面UL 的Class为J_valueList下的Li
            Elements values = e.select("div.sl-value").select("ul.J_valueList").select("li");
            for (Element element : values) {
                // 获取到li的第一个
                Element es = element.child(0);
                OutValues ov = new OutValues();
                ov.setOutValue(es.text().replace("<i></i>", ""));
                outValues.add(ov);
            }
            ok.setValues(outValues);
            outKeys.add(ok);
        }

        return outKeys;
    }

    /**
     * 抓取详细参数
     *
     * @param httpUrl
     * @return List<OutKeys>
     */
    @RequestMapping("/jdtriggerlist")
    @ResponseBody
    public List<OutParam> jdTriggerList(String httpUrl) {
        try {
            // 获取页面所有信息
            Document doc = Jsoup.connect(httpUrl).get();
            // 获取CLass为Ptable的Table元素
            Elements links = doc.select("table.Ptable");
            List<OutParam> outPrams = new ArrayList<OutParam>();
            // 如果规格参数不为空
            if (links != null && !links.isEmpty()) {
                // 获取当前袁术下的第一个tr
                Elements elist = links.get(0).select("tr");
                if (elist != null && !elist.isEmpty()) {
                    for (int i = 0; i < elist.size(); i++) {
                        if (elist.get(i).childNodes().size() == 2) {
                            OutParam op = new OutParam();
                            op.setOutParam(elist.get(i).child(0).text());
                            outPrams.add(op);
                        }

                    }

                }
                return outPrams;
            } else {
                // 如果规格参数为空
                Elements paramlinks = doc.select("ul.p-parameter-list");
                if (paramlinks != null && !paramlinks.isEmpty()) {
                    Elements plist = paramlinks.get(0).select("li");
                    if (plist != null && !plist.isEmpty()) {
                        for (int j = 0; j < plist.size(); j++) {
                            OutParam op = new OutParam();
                            op.setOutParam(plist.get(j).text().split("：")[0]);
                            outPrams.add(op);

                        }

                    }

                }
                return outPrams;
            }

        } catch (Exception e) {
            LOGGER.error("" + ERROR, e);

        }
        return Collections.emptyList();
    }

    /**
     * 抓取详细参数 添加商品时使用
     *
     * @param httpUrl
     * @return List<OutKeys>
     */
    @RequestMapping("/jdtriggeraddgoods")
    @ResponseBody
    public Map<String, Object> jdTriggerAddGoods(String httpUrl) {
        try {
            Map<String, Object> map = new HashMap<>();
            Document doc = Jsoup.connect(httpUrl).get();
            map.put("imgList", returnImgList(httpUrl));
            map.put("mimgList", returnImgList(httpUrl));
            map.put("goodName", getName(httpUrl));
            map.put("seo", getSeo(httpUrl));
            // 抓取规格参数
            Elements links = doc.select("table.Ptable");

            List<OutParam> outPrams = new ArrayList<>();
            // 如果规格参数不为空
            if (links != null && !links.isEmpty()) {

                Elements elist = links.get(0).select("tr");
                if (CollectionUtils.isNotEmpty(elist)) {
                    for (Element element : elist) {
                        if (element.childNodes().size() == 2) {
                            OutParam op = new OutParam();
                            op.setOutParam(element.child(0).text());
                            op.setOutValue(element.child(1).text());
                            outPrams.add(op);
                        }

                    }

                }
                map.put("outPrams", outPrams);
                return map;
            } else {
                // 如果规格参数为空product-intro
                Elements paramlinks = doc.select("ul.p-parameter-list");
                if (paramlinks != null && !paramlinks.isEmpty()) {
                    Elements plist = paramlinks.get(0).select("li");
                    if (CollectionUtils.isNotEmpty(plist)) {
                        for (Element aPlist : plist) {
                            OutParam op = new OutParam();
                            String[] p = aPlist.text().split("：");

                            if (p.length > 1) {
                                if ("店铺".equals(p[0])) {
                                    op.setOutParam("平台自营");
                                } else {
                                    op.setOutParam(p[0]);
                                }
                                op.setOutValue(p[1]);
                            }
                            outPrams.add(op);

                        }

                    }

                }
                map.put("outPrams", outPrams);
                return map;
            }

        } catch (Exception e) {
            LOGGER.error("" + ERROR, e);

        }
        return null;
    }

    /**
     * 获取手机端的图片 详细
     *
     * @param httpUrl
     * @return List
     */
    private List<OutImg> returnMImgList(String httpUrl) {
        List<OutImg> imglist = new ArrayList<OutImg>();

        String imgUrl = httpUrl.replace("http://item.jd.com", "http://item.m.jd.com/detail");
        String imgcontent = getHttpData(imgUrl);
        if (!"".equals(imgcontent)) {
            String[] imgother = imgcontent.split("360buyimg.com");
            for (int p = 1; p < imgother.length; p++) {
                String returnUrl = imgother[p].substring(0, imgother[p].indexOf("\""));
                if ((returnUrl.indexOf(".jpg") > 0 && returnUrl.endsWith(".jpg")) || (returnUrl.indexOf(".png") > 0 && returnUrl.endsWith(".png"))) {
                    OutImg oi = new OutImg();
                    oi.setImgUrl("http://img30.360buyimg.com" + returnUrl);
                    imglist.add(oi);
                }

            }
        }

        return imglist;
    }

    /**
     * 获取PC端的图片 详细
     *
     * @param httpUrl
     * @return List
     */
    public List<OutImg> returnImgList(String httpUrl) {
        List<OutImg> imglist = new ArrayList<OutImg>();
        Document doc;
        try {
            doc = Jsoup.connect(httpUrl).get();

            // 商品信息
            String html = doc.html();
            String[] pp = html.split("dx.3.cn/desc/");
            String id = pp[1].substring(0, pp[1].indexOf("'"));
            String imgUrl = "http://dx.3.cn/desc/" + id;

            String imgcontent = getHttpData(imgUrl);
            if (!"".equals(imgcontent)) {
                String[] imgother = imgcontent.split("360buyimg.com");
                for (int p = 1; p < imgother.length; p++) {
                    String returnUrl = imgother[p].substring(0, imgother[p].indexOf("\\\""));
                    if (returnUrl.contains(")")) {
                        returnUrl = returnUrl.substring(0, returnUrl.indexOf(")"));
                    }

                    OutImg oi = new OutImg();
                    oi.setImgUrl("http://img30.360buyimg.com" + returnUrl);
                    imglist.add(oi);
                }
            }

        } catch (IOException e) {
            imglist = null;
            LOGGER.error("" + ERROR, e);
        }

        return imglist;
    }

    /**
     * 获取商品名称
     *
     * @param httpUrl
     * @return String
     */
    private String getName(String httpUrl) {
        String goodsName = "";
        Document doc;
        try {
            doc = Jsoup.connect(httpUrl).get();
            Element productIntro = doc.getElementById("itemInfo");
            if (productIntro != null) {
                goodsName = productIntro.getElementById("name").child(0).text();
            } else {
                Elements productIntros = doc.getElementsByClass("itemInfo-wrap");
                Element productIntronew = productIntros.get(0);
                Elements skus = productIntronew.getElementsByClass("sku-name");
                Element sku = skus.get(0);
                goodsName = sku.text();
            }
        } catch (IOException e) {
            goodsName = null;
            LOGGER.error("" + ERROR, e);
        }
        // 商品信息

        return goodsName;
    }

    /***
     * 获取SEO信息
     *
     * @param httpUrl
     * @return OutSeo
     */
    private OutSeo getSeo(String httpUrl) {
        OutSeo os = new OutSeo();
        Document doc;
        try {
            // 获取内容
            doc = Jsoup.connect(httpUrl).get();
        } catch (IOException e) {
            LOGGER.error("" + ERROR, e);
            return os;

        }
        // 获取SeoTItle
        Elements d = doc.getElementsByTag("title");
        os.setSeoTitle(d.get(0).text());
        // 获取Meta所有的
        Elements contents = doc.getElementsByTag("meta");
        if (CollectionUtils.isNotEmpty(contents)) {
            for (Element content : contents) {
                // 获取Meta的name属性
                String tagName = content.attr("name");
                // 判断Name属性是否是keywords和description
                if ("keywords".equals(tagName)) {
                    os.setSeoKeywords(content.attr("content"));
                } else if ("description".equals(tagName)) {
                    os.setSeoDescription(content.attr("content"));
                }
            }
        }
        return os;
    }

    /**
     * 获取页面信息
     *
     * @param url
     * @return String
     */
    private String getHttpData(String url) {
        GetMethod getMethod = new GetMethod(url);
        HttpClient client = new HttpClient();
        try {
            client.executeMethod(getMethod);
            String data = getMethod.getResponseBodyAsString();
            return data;
        } catch (Exception e) {
            LOGGER.error("" + ERROR, e);
        }
        return "";
    }
}
