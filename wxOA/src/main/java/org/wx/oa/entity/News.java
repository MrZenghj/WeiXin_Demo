package org.wx.oa.entity;
/**
 *      图文消息的 文章描述部分
 * */
public class News {
    private String Title; //图文标题
    private String Description; //图文描述
    private String PicUrl; //图片地址
    private String Url; //跳转地址

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
