package org.wx.oa.util;

import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.wx.oa.entity.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 *      用于处理微信
 *          接收普通消息
 * */
public class MessageUtil {

    //微信端信息基本类型和事件类型
    public static final String MESSAGE_TEXT = "text";  //文本类型
    public static final String MESSAGE_IMAGE = "image"; //图片类型
    public static final String MESSAGE_VOICE = "voice"; //语音
    public static final String MESSAGE_VIDEO = "video"; //视频
    public static final String MESSAGE_LINK = "link";  //链接
    public static final String MESSAGE_LOCATION = "location";  //地理位置
    public static final String MESSAGE_EVENT = "event";  //事件
    public static final String MESSAGE_SUBSCRIBE = "subscribe";  //订阅
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe"; //取消订阅
    public static final String MESSAGE_CLICK = "CLICK"; //单击时间
    public static final String MESSAGE_VIEW = "VIEW"; //视图
    public static final String MESSAGE_NEWS = "news"; //新闻
    public static final String MESSAGE_MUSIC = "music"; //音乐
    public static final String MESSAGE_SCANCODE = "scancode_push";

    /**
     *   将xml文件传换成Map集合
     *   @param  request 请求
     *   @return map map集合
     * */
    public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String,String> map = new HashMap<>();
        SAXReader reader = new SAXReader(); //通过SAXReader读取xml配置文件
        InputStream is = request.getInputStream();
        Document document = reader.read(is);
        Element element = document.getRootElement(); //取根元素
        List<Element> list = element.elements();    //所有元素集
        for ( Element e: list) {
            map.put(e.getName(),e.getText()); //将元素名和值进行存储
        }
        is.close();
        return map;
    }
    /**
     *  将文本转换成xml
     *  @param textMessage 转换的信息
     *  @return  String 返回xml格式
     * */
    public static String textToXml(TextMessage textMessage){
        XStream xStream = new XStream();
        //将根节点转换成xml
        xStream.alias("xml",textMessage.getClass());
        return xStream.toXML(textMessage);
    }
    /**
     *  将图文消息转换成xml
     *  @param newsMessage 转换的信息
     *  @return  String 返回xml格式
     * */
    public static String newsMessageToXml(NewsMessage newsMessage){
        XStream xStream = new XStream();
        //将根节点转换成xml
        xStream.alias("xml",newsMessage.getClass());
        xStream.alias("item",new News().getClass());
        return xStream.toXML(newsMessage);
    }

    /**
     *  图片对象 转换成xml
     *  @param pictureMessage 转换的信息
     *  @return  String 返回xml格式
     * */
    public static String pictureMessageToXml(PictureMessage pictureMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", pictureMessage.getClass());
        return xstream.toXML(pictureMessage);
    }

    /**
     *  音乐对象 转换成xml
     *  @param musicMessage 转换的信息
     *  @return  String 返回xml格式
     * */
    public static String musicMessageToXml(MusicMessage musicMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }

    /**
     *   初始化菜单 内容
     *   @ return 返回内容
     * */
    public static String menuText(){
        StringBuffer sb = new StringBuffer();
        sb.append("welcome to my house , join us let's play together: \n\n");
        sb.append("回复一下数字，获取你想要的内容 \n\n ");
        sb.append("1.i can take you to the world \n\n ");
        sb.append("2.分享一首音乐给你 \n\n");
        sb.append("3.分享图文消息 \n\n ");
        sb.append("4.git a picture to you \n\n");
        sb.append("? 重新获取指令。。");
        return sb.toString();
    }

    /**
     * @Title: 回复1 发送的内容
     * @return String
     */
    public static String first() {
        StringBuffer sb = new StringBuffer();
        sb.append("爱你，let's go to the world");
        return sb.toString();
    }

    /**
     *   初始化 所封装的内容
     *      将信息进行封装然后进行输出
     *  @param  toUserName
     *  @param  fromUserName
     *  @param  content 封装的内容
     *  @return String  xml格式输出
     * */
    public static String initText(String toUserName,String fromUserName,String content){
        TextMessage message = new TextMessage();
        message.setToUserName(fromUserName); //发送者和接受者互换
        message.setFromUserName(toUserName);
        message.setContent(content);
        message.setMsgType(MessageUtil.MESSAGE_TEXT);
        message.setCreateTime(new Date().getTime());
        return MessageUtil.textToXml(message);
    }

    /**
     *  图文消息的封装
     *  @param  toUserName
     *  @param  fromUserName
     *  @return String  xml输出
     * */
    public static String initNewsMessage(String toUserName,String fromUserName){
        //设置图文
        List<News> listNews = new ArrayList<>();
        //图文一
        News news = new News();
        news.setTitle("夏目友人帐"); //图片描述
        news.setPicUrl("http://zhoufei.free.ngrok.cc/static/image/miao.jpg"); //图片地址
        news.setDescription("夏目贵志从外祖母夏目玲子的遗物中得到了那些契约书所做成的“友人帐”"
                + "，他决定将友人帐中妖怪们的名字一一归还。在夏目身边，开始聚集起各种各样的妖怪们。"
                + "能看到妖怪的少年夏目贵志，与招财猫外表的妖怪斑一起，为大家讲述一个个奇异、悲伤、怀念、令人感动的温馨故事。");
        news.setUrl("https://baike.baidu.com/item/%E5%A4%8F%E7%9B%AE%E5%8F%8B%E4%BA%BA%E5%B8%90/27649?fr=aladdin"); //跳转地址
        //图文二
        News news2 = new News();
        news2.setTitle("进击的巨人"); //图片描述
        news2.setPicUrl("http://zhoufei.free.ngrok.cc/static/image/sanli.jpg"); //图片地址
        news2.setDescription("世界上突然出现了人类的天敌“巨人”。面临着生存" +
                "单行本封面单行本封面(13张)" +
                "危机而残存下来的人类逃到了一个地方，盖起了三重巨大的城墙");
        news2.setUrl("https://baike.baidu.com/item/%E8%BF%9B%E5%87%BB%E7%9A%84%E5%B7%A8%E4%BA%BA/65641?fr=aladdin"); //跳转地址
        //加入集合中
        listNews.add(news);
        listNews.add(news2);
        //封装
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setFromUserName(toUserName); //发送端和接收端互换
        newsMessage.setToUserName(fromUserName);
        newsMessage.setMsgType(MESSAGE_NEWS); //设置信息文本为News
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setArticles(listNews);
        newsMessage.setArticleCount(listNews.size());
        return MessageUtil.newsMessageToXml(newsMessage);
    }

    /**
     *   图片消息的封装
     *   @param  toUserName
     *   @param  fromUserName
     *   @param  mediaId 临时上传的素材的mediaId
     * */
    public static String initPictureMessage(String toUserName,String fromUserName,String mediaId){
        Image image  = new Image();
        //设置临时素材的mediaId
        image.setMediaId("owkIQB2oLs1iYJB1j8Z-pheJJXoIcu4ekOfyIDvMFbvyhcM9ldanpeALiHBF4S-e");
        PictureMessage pictureMessage = new PictureMessage();
        pictureMessage.setImage(image);
        pictureMessage.setCreateTime(new Date().getTime());
        pictureMessage.setFromUserName(toUserName);
        pictureMessage.setToUserName(fromUserName);
        pictureMessage.setMsgType(MESSAGE_IMAGE);

        return  MessageUtil.pictureMessageToXml(pictureMessage);
    }

    /**
     *   音乐消息的封装
     *   @param  toUserName
     *   @param  fromUserName
     *   @param  mediaId 临时上传的素材的mediaId
     *
     *   原因：由于映射地址的缘故 故不能播放音乐
     * */
    public static String initMusicMessage(String toUserName,String fromUserName,String mediaId) {
        Music music = new Music();
        music.setThumbMediaId("V-NG_ll-M0K2jUxtHVtaEdIS7I_davsz0L-CtAtEjYCp4KzpUmAhivK5v8Zoe9ex");
        music.setDescription("love song");
        music.setHQMusicUrl("http://zhoufei.free.ngrok.cc/static/music/Try.mp3");
        music.setMusicUrl("http://zhoufei.free.ngrok.cc/static/music/Try.mp3");
        music.setTitle("Try");

        MusicMessage musicMessage = new MusicMessage();
        musicMessage.setFromUserName(toUserName);
        musicMessage.setToUserName(fromUserName);
        musicMessage.setCreateTime(new Date().getTime());
        musicMessage.setMsgType(MESSAGE_MUSIC); //设置信息文本
        musicMessage.setMusic(music);
        return  MessageUtil.musicMessageToXml(musicMessage);
    }


}
