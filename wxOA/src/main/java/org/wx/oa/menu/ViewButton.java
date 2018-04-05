package org.wx.oa.menu;

/**
 *  view 类型的菜单
 */
public class ViewButton extends Button{
    private String url; // view、miniprogram类型必须

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
