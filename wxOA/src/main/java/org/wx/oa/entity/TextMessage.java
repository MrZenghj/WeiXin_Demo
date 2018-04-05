package org.wx.oa.entity;
/**
 *   消息文本封装实体
 */
public class TextMessage extends BaseMessage {
    private String Content;    //文本消息内容
    private String MsgId;   //	消息id，64位整型

    public TextMessage() {
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
}