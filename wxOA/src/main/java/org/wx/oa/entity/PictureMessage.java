package org.wx.oa.entity;
/**
 *  图片信息封装实体
 * */
public class PictureMessage extends BaseMessage{
    private Image Image; // 图片

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        Image = image;
    }
}
