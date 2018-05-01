package org.wx.oa.controller;

import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wx.oa.entity.TextMessage;
import org.wx.oa.util.CheckUtil;
import org.wx.oa.util.MessageUtil;
import org.wx.oa.util.WxOAUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 *      接入微信公众平台开发
 *         微信接入接口
 * */
@Controller
@RequestMapping("/Weixin")
public class JoinWxController {
    /**
     * 开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上，GET请求携带参数如下表所示
     */
    @RequestMapping(value = "/Wx.do",method = RequestMethod.GET)
    public void joinWX(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取服务传递过来的信息
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        //开发者通过检验signature对请求进行校验
        //若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效
        if(CheckUtil.checkSignature(signature,timestamp,nonce)){
           PrintWriter pw = response.getWriter();
           pw.write(echostr);
        };
    }
    /**
     *    当普通微信用户向公众账号发消息时，微信服务器将POST消息的XML数据包到开发者填写的URL上。
     *    用于处理用户发送的消息请求
     * */
    @RequestMapping(value = "/Wx.do",method = RequestMethod.POST)
    public void handleMessage(HttpServletRequest request,HttpServletResponse response) {
        PrintWriter pw = null;
        try{
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            //将接收数据换成map
            Map<String,String> map = MessageUtil.xmlToMap(request);
            //获取信息
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content"); //用户传递过来的内容

            String message = null;
            pw = response.getWriter();
            if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
                if("1".equals(content)){ //1 回复的内容(爱你)
                    message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.first());
                }else if("2".equals(content)){ //2 回复的内容(音乐)
                    message = MessageUtil.initMusicMessage(toUserName,fromUserName,null);
                }else if("3".equals(content)){ //3 回复的内容（图文）
                    message = MessageUtil.initNewsMessage(toUserName,fromUserName);
                }else if("4".equals(content)){ //4 回复的内容（图片）
                    message = MessageUtil.initPictureMessage(toUserName,fromUserName,null);
                }else if(content.startsWith("翻译")) {
                    String word = content.replace("翻译", "").trim();
                    message = MessageUtil.initText(toUserName, fromUserName, WxOAUtil.translateWord(word));
                }else if("?".equals(content) || "？".equals(content)){ //? 回复的内容
                    message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.menuText());
                }
            }else if (MessageUtil.MESSAGE_EVENT.equals(msgType)){
                //属于事件
                String eventType = map.get("Event");
                if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){ // 订阅事件 以及unsubscribe
                    message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.menuText());
                }else if(MessageUtil.MESSAGE_CLICK.equals(eventType)) { //点击菜单拉取消息时的事件推送单击事件
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                }else if(MessageUtil.MESSAGE_VIEW.equals(eventType)){ //点击菜单跳转链接时的事件推送
                    String url = map.get("EventKey");
                    message = MessageUtil.initText(toUserName, fromUserName, url);
                }else if(MessageUtil.MESSAGE_SCANCODE.equals(eventType)){  // 扫码则进入此逻辑
                    String key = map.get("EventKey");
                    message = MessageUtil.initText(toUserName, fromUserName, key);
                }
            }else if(MessageUtil.MESSAGE_LOCATION.equals(msgType)){  //地理位置 与文档有点出入
                String label = map.get("Label");
                message = MessageUtil.initText(toUserName, fromUserName, label);
            }
            pw.write(message);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(pw != null ){
                pw.close();
            }
        }
    }





}
