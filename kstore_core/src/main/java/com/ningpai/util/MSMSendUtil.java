package com.ningpai.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by guoguangnan on 2015/9/15.
 */
public class MSMSendUtil {

    /**
     * 日志
     * */
    public static final MyLogger LOGGER = new MyLogger(MSMSendUtil.class);

    /**
     * 无参构造
     * */
    private MSMSendUtil() {
    }

    /**
     * zyer send
     * 
     * @return
     * @throws IOException
     */
    public static boolean sendMsm(String userId, String loginName, String password, String[] mobiles, String content, String expSmsId, String httpUrl) throws IOException {
        StringBuilder sub = new StringBuilder();
        BufferedReader br;
        URL url;
        HttpURLConnection con;
        String line;
        try {
            String bBstring = "";
            String bQstring = "";
            if (expSmsId != null) {
                String[] baob = expSmsId.split("\\|");

                if (baob.length == 2) {
                    bBstring = baob[0];
                    bQstring = baob[1];
                }
            }

            // 设置发送内容的编码方式
            String sendContent = URLEncoder.encode((bBstring + content + bQstring).replaceAll("<br/>", " "), "UTF-8");// 发送内容
            String mobile = "";
            for (int j = 0; j < mobiles.length; j++) {
                mobile += mobiles[j];
                if (j < mobiles.length - 1) {
                    mobile += ",";
                }
            }

            url = new URL(httpUrl + "?action=send&userid=&account=" + loginName + "&password=" + password + "&mobile=" + mobile + "&content=" + sendContent + "&sendTime=");
            con = (HttpURLConnection) url.openConnection();

            br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            // br=new BufferedReader(new InputStreamReader(url.openStream()));

            while ((line = br.readLine()) != null) {
                // 追加字符串获得XML形式的字符串
                sub.append(line + "");
                // System.out.println("提取数据 :  "+line);
            }
            br.close();
            //打印日志
            LOGGER.info("------------------------------没错，发送成功了------------------------------");

        } catch (IOException e) {
            LOGGER.error("",e);
        }
        return readStringXml(sub.toString());
    }

    /**
     *  解析xml字符串
     * */
    public static boolean readStringXml(String xml) {
        Document doc;

        try {
            // 将字符转化为XML
            doc = DocumentHelper.parseText(xml);
            // 获取根节点
            Element rootElt = doc.getRootElement();

            // 拿到根节点的名称
            // System.out.println("根节点名称："+rootElt.getName());

            // 获取根节点下的子节点的值
            String returnstatus = rootElt.elementText("returnstatus").trim();
            String message = rootElt.elementText("message").trim();

            System.out.println("返回状态为：" + returnstatus);
            System.out.println("返回信息提示：" + message);
            if ("Success".equals(returnstatus)) {
                return true;
            } else {
                return false;
            }
        } catch (DocumentException e) {
            LOGGER.error("",e);
            return false;
        }

    }
}
