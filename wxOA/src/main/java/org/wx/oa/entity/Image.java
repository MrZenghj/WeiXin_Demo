package org.wx.oa.entity;
/**
 *      图片信息特有部分
 * */
public class Image {

    private String MediaId; //通过素材管理中的接口上传多媒体文件，得到的id。

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
