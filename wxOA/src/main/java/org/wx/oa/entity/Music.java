package org.wx.oa.entity;

/**
 *  音乐实体特有部分
 */
public class Music {
    private String Title;	//否	音乐标题
    private String Description;	//否	音乐描述
    private String MusicUrl;	//否	音乐链接 MusicUrl
    private String HQMusicUrl;	//否	高质量音乐链接，WIFI环境优先使用该链接播放音乐
    private String ThumbMediaId;	//是	缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id

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

    public String getMusicUrl() {
        return MusicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        MusicUrl = musicUrl;
    }

    public String getHQMusicUrl() {
        return HQMusicUrl;
    }

    public void setHQMusicUrl(String HQMusicUrl) {
        this.HQMusicUrl = HQMusicUrl;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }
}
